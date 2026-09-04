FM 5.6.3 新 Player 一键接入修复版

这版专门修复你截图中的：
Failed to resolve: androidx.media3:media3-mpvplayer:1.11.0-alpha01-fongmi

真正原因：
media3-mpvplayer 并没有发布到你工程 third_party/maven，也不是 MavenCentral/JitPack 可直接下载的 AAR。
FongMi/media 的 release-1.11.0-fongmi 分支里，它是一个“源码模块”：
libraries/mpvplayer/build.gradle.kts

所以必须用 FongMi/media 官方 README 说明的 local composite build：
pluginManagement includeBuild(build-logic-settings)
plugins gradlebuild.media3-settings-logic
includeMedia3(...)

V8.2/V3.2 的问题：
工程里已经提前声明了 media3-mpvplayer 依赖，但如果下载/切换 FongMi/media 这一步没有先完成，
Gradle 就会把它当普通 Maven 依赖去找，于是必然 Failed to resolve。

V8.3/V3.3 改法：
1. 默认仍保持旧播放器可 Sync 状态，不声明不存在的 media3-mpvplayer。
2. 新 Player 文件先放在 _NEW_PLAYER_PATCH，不直接污染当前工程。
3. 双击 00_必须先运行_安装新Player.cmd。
4. 脚本先把 FongMi/media release-1.11.0-fongmi 完整下载并验证。
5. 验证 libraries/mpvplayer 和 build-logic-settings 都存在后，才切换 settings/app/版本目录和新 Player 源码。
6. 最后自动执行 gradlew help 验证 composite build。
7. 只有这一步成功后，02_BUILD_TV_ARM64_DEBUG.cmd 才允许编译。

这样不会再出现“工程先进入半完成状态，然后 Gradle 去网上找不存在的 media3-mpvplayer AAR”。

当前工程：OK563_MultiLine_V8_3
默认状态：旧播放器稳定模式
目标状态：FM 5.6.3 新 Player + FongMi/media release-1.11.0-fongmi
