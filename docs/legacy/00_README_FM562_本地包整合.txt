FM 5.6.2 + 宸龙本地包 源码整合版

基线：
FM560 WebHTV 修复 V8。

本版修改：
1. app/build.gradle
   versionCode 562
   versionName "5.6.2"

2. 更新检测仍保留原逻辑：
   Updater.java 会用 BuildConfig.VERSION_CODE 比较远端 code。
   当前本地 code=562，因此远端 5.6.1 / code=561 不会再弹更新。

3. 本地包完整内置到：
   app/src/main/assets/clys/

4. 首次安装 / 没有保存过点播配置时默认读取：
   assets://clys/clys.top

5. 覆盖安装已有数据时，不强制覆盖用户原来的配置。

内置文件：
- Y3.webp
- chenlong.jpg
- chenlongys.png
- clys.top
- clys260515.png
- lib/BB.js

蜘蛛 MD5：
b81a5747f91e7169bdc7e3f68bd995e0

打包：
- 电视8A：
  00_SAFE_BUILD_ASCII_PATH.bat
  自动复制到 C:\FM562 后编译。

- 一次打四个 Debug：
  01_BUILD_ALL_4_DEBUG.bat
  包括：
  leanbackArm64_v8aDebug
  leanbackArmeabi_v7aDebug
  mobileArm64_v8aDebug
  mobileArmeabi_v7aDebug

说明：
如果手机/电视已经装过旧版并保存过线路，覆盖安装后会继续用旧线路。
要验证“默认内置本地包”，建议清除应用数据或全新安装。
