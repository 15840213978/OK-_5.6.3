OK影视 5.6.3 V8.6.3 真 MPV 内置说明

1. 用户提供的独立项目：third_party/mpv-android-master_source.zip
   该项目是 mpv-android 源码参考，本身不包含可直接打进 APK 的预编译 libmpv.so。

2. 本工程实际启用：
   - app/src/main/java/androidx/media3/mpvplayer/*        FongMi Media3 MPV Player 层
   - app/src/main/java/is/xyz/mpv/MPVLib.java            JNI/原生库装载桥
   - app/src/arm64_v8a/assets/mpv-libs/arm64-v8a/*       ARM64 MPV/FFmpeg/JNI 原生库
   - app/src/armeabi_v7a/assets/mpv-libs/armeabi-v7a/*   ARMv7 MPV/FFmpeg/JNI 原生库

3. PlayerEngineFactory 已恢复真正 MPV 分支。
   只有用户选择 MPV 且设备能加载内置库时走 MPV；DRM/SMB 继续走 Exo。

4. Release 构建后的一键脚本会验证：
   assets/mpv-libs/arm64-v8a/libmpv.so
   assets/mpv-libs/arm64-v8a/libplayer.so
   两项都存在才判定 MPV 真正打进 APK。
