$ErrorActionPreference = "Stop"
$Root = Split-Path -Parent $PSScriptRoot
$Log  = Join-Path $Root "build_mobile_arm64_release.log"

function Banner([string]$Text) {
    Write-Host ""
    Write-Host "============================================================"
    Write-Host " $Text"
    Write-Host "============================================================"
}

function Assert-File([string]$Relative) {
    $path = Join-Path $Root $Relative
    if (-not (Test-Path -LiteralPath $path -PathType Leaf)) {
        throw "缺少文件：$Relative"
    }
}

function Static-Validate {
    $required = @(
        "app\src\main\java\androidx\media3\mpvplayer\MpvPlayer.java",
        "app\src\main\java\androidx\media3\mpvplayer\MpvPlayerConfig.java",
        "app\src\main\java\is\xyz\mpv\MPVLib.java",
        "app\src\main\java\com\fongmi\android\tv\player\mpv\MpvPlayerEngine.java",
        "app\src\arm64_v8a\assets\mpv-libs\arm64-v8a\libmpv.so",
        "app\src\arm64_v8a\assets\mpv-libs\arm64-v8a\libplayer.so"
    )
    foreach ($f in $required) { Assert-File $f }

    $factory = Get-Content -LiteralPath (Join-Path $Root "app\src\main\java\com\fongmi\android\tv\player\engine\PlayerEngineFactory.java") -Raw
    if ($factory -notmatch 'new MpvPlayerEngine') { throw "PlayerEngineFactory 尚未启用 MPV。" }

    $gradle = Get-Content -LiteralPath (Join-Path $Root "app\build.gradle") -Raw
    if ($gradle -match "exclude 'com/fongmi/android/tv/player/mpv/MpvPlayerEngine.java'") {
        throw "app/build.gradle 仍排除了 MpvPlayerEngine。"
    }
}

function Find-BuiltApk {
    $base = Join-Path $Root "app\build\outputs\apk"
    if (-not (Test-Path -LiteralPath $base)) { return $null }
    return Get-ChildItem -LiteralPath $base -Recurse -File -Filter "*.apk" |
        Where-Object { $_.FullName -match 'mobile' -and $_.FullName -match 'arm64' -and $_.FullName -match 'release' } |
        Sort-Object LastWriteTime -Descending |
        Select-Object -First 1
}

function Verify-Apk([System.IO.FileInfo]$Apk) {
    Add-Type -AssemblyName System.IO.Compression.FileSystem -ErrorAction SilentlyContinue
    $zip = [System.IO.Compression.ZipFile]::OpenRead($Apk.FullName)
    try {
        $mpv = $zip.Entries | Where-Object { $_.FullName -eq 'assets/mpv-libs/arm64-v8a/libmpv.so' } | Select-Object -First 1
        $jni = $zip.Entries | Where-Object { $_.FullName -eq 'assets/mpv-libs/arm64-v8a/libplayer.so' } | Select-Object -First 1
        if (-not $mpv) { throw "APK 已生成，但缺少 assets/mpv-libs/arm64-v8a/libmpv.so。" }
        if (-not $jni) { throw "APK 已生成，但缺少 assets/mpv-libs/arm64-v8a/libplayer.so。" }
        return @([math]::Round($mpv.Length / 1MB, 2), [math]::Round($jni.Length / 1KB, 1))
    }
    finally { $zip.Dispose() }
}

try {
    Banner "OK影视 5.6.3 空壳真MPV / Mobile ARM64 Release"
    Write-Host "[1/3] 检查 MPV 源码、JNI 和原生库..."
    Static-Validate
    Write-Host "      OK：MPV 已内置，不需要再下载 fongmi-media 或 mpv-android。"

    Write-Host "[2/3] 编译 Mobile ARM64 Release..."
    Push-Location $Root
    try {
        & .\gradlew.bat :app:assembleMobileArm64_v8aRelease --console=plain --stacktrace 2>&1 | Tee-Object -FilePath $Log
        if ($LASTEXITCODE -ne 0) { throw "Gradle 编译失败。日志：$Log" }
    }
    finally { Pop-Location }

    Write-Host "[3/3] 验证 APK 内 MPV..."
    $apk = Find-BuiltApk
    if (-not $apk) { throw "编译结束但没有找到 Mobile ARM64 Release APK。" }
    $sizes = Verify-Apk $apk
    $apkMB = [math]::Round($apk.Length / 1MB, 2)

    Banner "成功：真 MPV 已打进 APK"
    Write-Host ("APK：{0}" -f $apk.FullName)
    Write-Host ("APK 大小：{0} MB" -f $apkMB)
    Write-Host ("libmpv.so：{0} MB" -f $sizes[0])
    Write-Host ("libplayer.so：{0} KB" -f $sizes[1])
    Start-Process explorer.exe -ArgumentList "/select,`"$($apk.FullName)`"" -ErrorAction SilentlyContinue
    exit 0
}
catch {
    Write-Host ""
    Write-Host "==================== 失败 ====================" -ForegroundColor Red
    Write-Host $_.Exception.Message -ForegroundColor Red
    Write-Host ""
    Write-Host "日志：$Log"
    exit 1
}
