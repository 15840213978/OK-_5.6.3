FM 5.6.0 参考 WebHTV 5.6.0-202608201815 编译修复 V4

这版是真正直接拿你刚上传、已经在 Android Studio 成功 Generate APKs 的
webhtv-5.6.0-202608201815.zip 当构建参考，不再用 webhtv-main，也不再猜 Media3。

保留：
- TV-fongmi(6).zip 的 FM 原始主体、界面、mobile/leanback 四种 Debug Variant。
- FongMi MediaChapter / MediaEdition / Danmaku PlayerView API。

直接移植：
- WebHTV 快照里的 third_party/maven
- FongMi 定制 Media3：1.11.0-alpha01-fongmi
- nextlib-media3ext 对应版本
- WebHTV 已验证的 Gradle 9.5.1 / AGP 9.2.1 组合

兼容修复：
- DolbyVisionOutputPolicy
- DecodeTrackSelector
- DiskPreloadManager
- PlayerSeekView
- AudioChannelMix
- ExoPlayerEffect 旧私有 API
- PlayerView 旧 Debug Overlay API
- local.properties / release 签名缺失

重要：
WebHTV 这一版的 MPV Java 集成和你这份 FM 源码里引用的旧私有 MPV wrapper
不是同一套 API。为了先让“FM 原版主体”稳定进入真实编译，本 V4 第一阶段：
- MPV 菜单/配置文件代码仍保留；
- 播放器实际统一回退到 EXO；
- 不删除 FM 原代码，只从 main Java 编译中排除 5 个旧 MPV wrapper 依赖文件。

等 V4 的真实 build_final.txt 出来，如果 EXO/主体已通过，
下一阶段再把 WebHTV 已经能运行的 MPV native + Java wrapper 单独接回，
这样不会再把 Exo、UI、Gradle 三层问题混在一起。

测试：
1. 最好解压到纯英文路径，例如 C:\FM560WEB
2. 双击 00_BUILD_TV_ARM64_DEBUG.bat
3. 或在 Android Studio 直接 Build -> Generate APKs
4. 失败只上传 build_final.txt

一次打四个 Debug：
双击 01_BUILD_ALL_4_DEBUG.bat
