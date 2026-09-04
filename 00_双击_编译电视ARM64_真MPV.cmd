@echo off
chcp 65001 >nul
cd /d "%~dp0"
call gradlew.bat :app:assembleLeanbackArm64_v8aRelease
pause
