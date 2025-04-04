#!/bin/bash
chcp 65001
@echo off
setlocal

:: 设置手机设备号（如果需要）
set DEVICE_ID=your_device_id

:: 设置要发送的文件路径和目标路径
set SOURCE_FILE=C:\path\to\your\file.txt
set TARGET_DIR=/sdcard/download/

:: 确保ADB连接到设备
adb devices
if not "%DEVICE_ID%"=="" (
    adb -s %DEVICE_ID% connect %DEVICE_ID%
) else (
    adb connect
)

:: 检查设备是否已连接
:check_connection
adb get-state > temp.txt
type temp.txt | find "device" > nul
if errorlevel 1 (
    echo Device not connected. Please check the connection and try again.
    timeout /t 5
    goto check_connection
) else (
    echo Device connected.
)

:: 发送文件到指定目录
adb push %SOURCE_FILE% %TARGET_DIR%

echo File has been sent to device.

:: 清理临时文件
del temp.txt

endlocal