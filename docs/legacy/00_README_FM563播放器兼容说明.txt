FM 5.6.3 旧播放器兼容更新版

这版针对你截图中的编译错误修复。

截图第一类错误：
package androidx.media3.mpvplayer.audio does not exist
以及 PlayerEngineFactory / ExoMediaSourceFactory / ExoPlayerSession / ExternalFont 等成批错误。

原因：
旧跑通工程使用的是 5.6.2 的 patched Media3 / nextlib 依赖和 5 个本地兼容 shim。
FM 5.6.3 的新 player/playback 源码已经换到另一套 Media3/MPV 接口。
如果只把 5.6.3 player Java 文件拉进旧工程，会出现“新源码 + 旧播放器依赖”的接口错位。

本版策略：
- 继续以原来跑通的 5.6.2 工程为母版。
- 保留整套旧 player/playback/Media3 兼容层，不升级播放器接口。
- 恢复并保留：
  DolbyVisionOutputPolicy.java
  DiskPreloadManager.java
  DecodeTrackSelector.java
  AudioChannelMix.java
  PlayerSeekView.java
- 不拉 5.6.3 的 PlayerEngine / Exo / MPV / Playback 新接口。
- 其余与播放器无关的 5.6.3 更新继续合入：
  BackupManager、Decoder、Loader、JarLoader、EpgParser、数据库、配置/恢复、工具类、CatVod/QuickJS/Chaquo 运行源码等。
- 旧 Gradle / AGP / 依赖版本 / 签名方式 / Build Variants 全部不动。
- versionCode=563，versionName=5.6.3。

这是“旧工程稳定优先”的 5.6.3 兼容更新，不再强行把新播放器体系塞进旧工程。
