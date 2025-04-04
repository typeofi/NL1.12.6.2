#!/bin/bash
@echo off
chcp 65001
rem current direction
set cur_dir=%cd%
rem addr2line tool path
set add2line_path=D:\Android\android-sdk\ndk\16.1.4479499\toolchains\aarch64-linux-android-4.9\prebuilt\windows-x86_64\bin\aarch64-linux-android-addr2line.exe


echo set debug_file_path=%cur_dir%\libmcpelauncher.so

set debug_file_path= C:\Users\CC\Desktop\NL1.12.6.999\app\build\intermediates\cmake\debug\obj\armeabi-v7a\libmcpelauncher.so

rem debug address:
set /p debug_addr=请输入异常时PC寄存器值:

echo ----------------------- addr2line ------------------------  
echo debug文件路径: (%debug_file_path%), PC=(%debug_addr%)

if exist %debug_file_path% (
	%add2line_path% -e %debug_file_path% -f %debug_addr%
		)
echo ---------------------------------------------------------  
pause