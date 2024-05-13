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