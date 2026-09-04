@echo off
chcp 65001 >nul
title OK影视源码 GitHub 清理工具

echo =====================================
echo       OK影视源码上传前清理
echo =====================================

echo 删除 Gradle 缓存...
for /d /r %%i in (.gradle) do if exist "%%i" rd /s /q "%%i"

echo 删除 IDEA 配置...
for /d /r %%i in (.idea) do if exist "%%i" rd /s /q "%%i"

echo 删除 build 目录...
for /d /r %%i in (build) do if exist "%%i" rd /s /q "%%i"

echo 删除 local.properties...
for /r %%i in (local.properties) do del /f /q "%%i"

echo 删除 APK...
for /r %%i in (*.apk) do del /f /q "%%i"

echo 删除 AAB...
for /r %%i in (*.aab) do del /f /q "%%i"

echo 删除日志...
for /r %%i in (*.log) do del /f /q "%%i"

echo 删除临时文件...
for /r %%i in (*.tmp) do del /f /q "%%i"

echo 删除 IDEA 文件...
for /r %%i in (*.iml) do del /f /q "%%i"

echo.
echo 清理完成！
pause
