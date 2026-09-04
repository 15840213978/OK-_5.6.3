FM560 WebHTV Base V8 - Windows 路径修复

你这次截图的错误不是 V7 的 RTMP 修复错了，而是 Android Gradle Plugin 在 Windows
检测到工程路径中有非 ASCII/中文字符，直接在配置阶段停止：

Your project path contains non-ASCII characters

这会导致连 Java 编译都进不去，看起来像“V7 编译不过”。

V8 做了两层保险：
1. gradle.properties 加：
   android.overridePathCheck=true

2. 新增：
   00_SAFE_BUILD_ASCII_PATH.bat

推荐只双击这个 BAT。
它会把整个工程自动复制到纯英文目录：
   C:\FM560V8

然后在那里执行：
   :app:assembleLeanbackArm64_v8aDebug

V7 的 RTMP 重复类修复保持不变：
- 已删除 implementation libs.rtmp.client
- Media3 RTMP 仍保留

如果失败，只上传：
   C:\FM560V8\build_final.txt

不要直接在带中文目录名的解压路径里点 Android Studio Generate APKs。
