cmake_minimum_required(VERSION 3.30.5)

## 设置目标API级别为Android 14 新的27
set(ANDROID_PLATFORM android-27)

## 设置目标系统架构和ABI（根据你的需要设置）
set(ANDROID_ABI armeabi-v7a) # 例如

set(CMAKE_CXX_STANDARD 11)

#set(CMAKE_C_FLAGS_RELEASE "${CMAKE_C_FLAGS_RELEASE} -Os -Wall -s")
#set(CMAKE_CXX_FLAGS_RELEASE "${CMAKE_CXX_FLAGS_RELEASE} -Os -Wall -s")
#set(CMAKE_CXX_FLAGS_RELEASE "${CMAKE_CXX_FLAGS_RELEASE} -O3")
#set(CMAKE_CXX_FLAGS_DEBUG "${CMAKE_CXX_FLAGS_DEBUG} -g -O0 -Wall")
#set(CMAKE_CXX_FLAGS_DEBUG "${CMAKE_CXX_FLAGS_RELEASE} -O3")


#set(CMAKE_CXX_FLAGS "${CMAKE_CXX_FLAGS} -Os")

set(CMAKE_CXX_FLAGS "${CMAKE_CXX_FLAGS} -g")

project("mcpelauncher")
add_definitions(-DLSYM_DEBUG)

add_library(${CMAKE_PROJECT_NAME} SHARED
        # List C/C++ source files with relative paths to this CMakeLists.txt.
        libcorkscrew/backtrace.c
        libcorkscrew/backtrace-helper.c
        libcorkscrew/demangle.c
        libcorkscrew/map_info.c
        libcorkscrew/ptrace.c
        libcorkscrew/symbol_table.c
        libcorkscrew/arch-arm/backtrace-arm.c
        libcorkscrew/arch-arm/ptrace-arm.c


        simpleuuid.c
        utf8proc_slim.c

        dobby.cpp

        marauders_map.c
        controller_jni.cpp

        signalhandler.cpp
        signalhandler_arm.cpp

        fmod_filesystem.cpp

        nativepatch.c

        modscript.cpp

        modscript_nextgen.cpp
        modscript_renderer.cpp
        modscript_cape.cpp
        prepatch.cpp
)


target_link_libraries(${CMAKE_PROJECT_NAME}
        android
        stdc++
        fmod
        mcpelauncher_tinysubstrate
        minecraftpe
        log)


include_directories(${PROJECT_SOURCE_DIR})


## mcpelauncher_lite库
#if ("x" STREQUAL "x")
#    add_library(mcpelauncher_lite SHARED
#            dobby.cpp
#            marauders_map.c
#            fmod_filesystem.cpp
#
#            gothook/got_hook.c
#
#            nativepatch.c
#            prepatch.cpp
#    )
#
#    target_link_libraries(mcpelauncher_lite
#            log
#            android
#            dobby
#            xhook
#
#            fmod
#            mcpelauncher_tinysubstrate
#    )
#
#    add_definitions(-DMCPELAUNCHER_LITE)
#endif ()


#message(${PROJECT_SOURCE_DIR}/so)
#
## 设置第三方库的路径
#set(LIB_PATH ${PROJECT_SOURCE_DIR}/so/libxhook.so)
#
## 创建IMPORTED库目标
#add_library(xhook IMPORTED SHARED)
#
## 设置IMPORTED_LOCATION属性
#set_target_properties(xhook PROPERTIES
#		IMPORTED_LOCATION ${LIB_PATH})
#
### 如果库有头文件，设置包含目录
##set_target_properties(xhook PROPERTIES
##		INTERFACE_INCLUDE_DIRECTORIES ${PROJECT_SOURCE_DIR}/libxhook/jni)
#
## 链接第三方库到你的目标
#target_link_libraries(${CMAKE_PROJECT_NAME} xhook)
#
##target_include_directories(${CMAKE_PROJECT_NAME} INTERFACE ${PROJECT_SOURCE_DIR}/so)





