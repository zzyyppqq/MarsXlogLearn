# MarsXlogLearn

## 创建可调试mars xlog功能步骤：

### 1. 创建新项目MarsXlogLearn app
### 2. 创建xlog native lib
### 3. 拷贝Log.java、Xlog.java到xlog java目录（保留包名）
### 4. xlog cpp目录下创建mars-xlog目录，并在此目录下执行以下命令下载mars源码
```shell
git clone https://github.com/Tencent/mars.git
```
### 5. 修改xlog cpp目录下CMakeLists.txt
```cmake
cmake_minimum_required(VERSION 3.22.1)

project("xlog_sample")

# ---- add start ----
add_subdirectory(mars-xlog/mars/mars mars)

include_directories(mars-xlog/mars)
include_directories(mars-xlog/mars/mars)
include_directories(mars-xlog/mars/mars/comm)
include_directories(mars-xlog/mars/mars/comm/xlogger)
# ---- add end ----

add_library(${CMAKE_PROJECT_NAME} SHARED
        xlog_sample.cpp)

target_link_libraries(${CMAKE_PROJECT_NAME}
        android
        log
        marsxlog) # add
```
### 6. 编译运行目，即可在Android设备上debug mars xlog C/C++模块
### 7. 若仅关注xlog，可屏蔽marsstn，修改mars/mars/CMakeLists.txt，注释掉不必要的编译模块和目标即可
```cmake
cmake_minimum_required (VERSION 3.6)

set(CMAKE_INSTALL_PREFIX "${CMAKE_BINARY_DIR}" CACHE PATH "Installation directory" FORCE)
message(STATUS "CMAKE_INSTALL_PREFIX=${CMAKE_INSTALL_PREFIX}")

include_directories(openssl/include)

add_subdirectory(comm comm)
#add_subdirectory(boot boot)
add_subdirectory(boost boost)
#add_subdirectory(app app)
#add_subdirectory(baseevent baseevent)
add_subdirectory(xlog xlog)
#add_subdirectory(sdt sdt)
#add_subdirectory(stn stn)

# for zstd
option(ZSTD_BUILD_STATIC "BUILD STATIC LIBRARIES" ON)
option(ZSTD_BUILD_SHARED "BUILD SHARED LIBRARIES" OFF)
set(ZSTD_SOURCE_DIR "${CMAKE_CURRENT_SOURCE_DIR}/zstd")
set(LIBRARY_DIR ${ZSTD_SOURCE_DIR}/lib)
include(GNUInstallDirs)
add_subdirectory(zstd/build/cmake/lib zstd)

project (mars)

include(comm/CMakeUtils.txt)

include_directories(.)
include_directories(..)

set(SELF_LIBS_OUT ${CMAKE_SYSTEM_NAME}.out)

if(ANDROID)

    if(NATIVE_CALLBACK)
        message("common native callback")
        add_definitions(-DNATIVE_CALLBACK)
    endif()

    find_library(log-lib log)
    find_library(z-lib z)

    link_directories(app baseevent xlog sdt stn comm boost zstd)

    # marsxlog
    set(SELF_LIB_NAME marsxlog)
    file(GLOB SELF_SRC_FILES libraries/mars_android_sdk/jni/JNI_OnLoad.cc
            libraries/mars_xlog_sdk/jni/import.cc)
    add_library(${SELF_LIB_NAME} SHARED ${SELF_SRC_FILES})
    install(TARGETS ${SELF_LIB_NAME} LIBRARY DESTINATION ${SELF_LIBS_OUT} ARCHIVE DESTINATION ${SELF_LIBS_OUT})
    get_filename_component(EXPORT_XLOG_EXP_FILE libraries/mars_android_sdk/jni/export.exp ABSOLUTE)
    set(SELF_XLOG_LINKER_FLAG "-Wl,--gc-sections -Wl,--version-script='${EXPORT_XLOG_EXP_FILE}'") 
    if(ANDROID_ABI STREQUAL "x86_64" OR ANDROID_ABI STREQUAL "x86")
        set(SELF_XLOG_LINKER_FLAG "-Wl,--gc-sections -Wl,--version-script='${EXPORT_XLOG_EXP_FILE}' -Wl,--no-warn-shared-textrel") 
    endif()
    target_link_libraries(${SELF_LIB_NAME} "${SELF_XLOG_LINKER_FLAG}"
                            xlog
                            mars-boost
                            comm
                            libzstd_static
                            ${log-lib}
                            ${z-lib}
                            )
 
    # marsstn
#    set(SELF_LIB_NAME marsstn)
#    file(GLOB SELF_SRC_FILES libraries/mars_android_sdk/jni/*.cc)
#    add_library(${SELF_LIB_NAME} SHARED ${SELF_SRC_FILES})
#    install(TARGETS ${SELF_LIB_NAME} LIBRARY DESTINATION ${SELF_LIBS_OUT} ARCHIVE DESTINATION ${SELF_LIBS_OUT})
#    link_directories(${SELF_LIBS_OUT})
#    find_library(CRYPT_LIB crypto PATHS openssl/openssl_lib_android/${ANDROID_ABI} NO_DEFAULT_PATH NO_CMAKE_FIND_ROOT_PATH)
#    find_library(SSL_LIB ssl PATHS openssl/openssl_lib_android/${ANDROID_ABI} NO_DEFAULT_PATH NO_CMAKE_FIND_ROOT_PATH)
#    target_link_libraries(${SELF_LIB_NAME} "-Wl,--gc-sections"
#                        ${log-lib}
#                        stn
#                        sdt
#                        app
#                        baseevent
#                        comm
#                        boot
#                        mars-boost
#                        marsxlog
#                        libzstd_static
#                        ${SSL_LIB}
#                        ${CRYPT_LIB})
endif()

```
