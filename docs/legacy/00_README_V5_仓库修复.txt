FM560 WebHTV Base V5

V4 的失败原因已经确定，不是 Media3 文件缺失：
third_party/maven 里的 AAR 实际都在。

问题是 V4 把：
    maven { url = uri("$rootDir/third_party/maven") }
错误加进了 pluginManagement.repositories。

应用依赖真正读取的是：
    dependencyResolutionManagement.repositories

因此日志里 Gradle 只搜索 Maven Central / Google / app/libs / JitPack，
完全没有搜索 third_party/maven。

V5 已直接采用你那份“已经成功生成 APK”的
webhtv-5.6.0-202608201815/settings.gradle 仓库配置。

正确结构：
dependencyResolutionManagement {
    repositories {
        maven { url = uri("$rootDir/third_party/maven") }
        mavenCentral()
        google()
        flatDir { dirs "$rootDir/app/libs" }
        maven { url = "https://jitpack.io" }
    }
}

同时新增 verifyLocalFongMiRepo：
编译前会先检查下面 4 个真实文件：
- media3-common
- media3-exoplayer
- media3-ui
- nextlib-media3ext

使用：
双击 00_BUILD_TV_ARM64_DEBUG.bat

看到：
LOCAL FONGMI REPOSITORY OK
以后才会进入正式 APK 编译。

如果后面失败，再上传新的 build_final.txt。
