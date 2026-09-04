FM560 WebHTV Base V6

V5 已经成功解决本地 Maven 仓库问题，构建已经进入真正的 Java 编译阶段。

这次截图里的 6 个 Java 错误已经对应修复：

PlayerEngine.java（2个）
- C.DECODE_SOFTWARE 不存在
- C.DECODE_HARDWARE 不存在
处理：完全参考已经可打包的 WebHTV：
  SOFT = 0
  HARD = 1

SubtitleSetting.java（4个）
WebHTV 锁定的 Media3 UI 实际 API 已核对：
- CaptionStyleCompat 没有 DEFAULT_EDGE_WIDTH
- CaptionStyleCompat 没有 DEFAULT_SHADOW_OFFSET
- CaptionStyleCompat 构造函数是 6 参数，不是旧 FM 的 8 参数
- SubtitleView 没有 setTextSizeScale
但它有：
- reset()
- setBottomPosition()
- setFractionalTextSize()

V6 已按真实 AAR API 修改：
- 边缘宽度/阴影默认值使用兼容常量
- CaptionStyleCompat 改回 6 参数构造
- 字幕缩放改用 setFractionalTextSize(DEFAULT_TEXT_SIZE_FRACTION * scale)

使用：
1. 解压到纯英文目录，例如 C:\FM560V6
2. 双击 00_BUILD_TV_ARM64_DEBUG.bat
3. 如果还有错误，只上传新的 build_final.txt

目前已经从：
Gradle下载问题 → 本地Maven仓库问题 → Java API兼容问题
逐层推进到这里。
