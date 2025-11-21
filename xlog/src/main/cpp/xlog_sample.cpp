#include <jni.h>
#include <string>


#include "xloggerbase.h"
#include "xlogger.h"
#include "android_xlog.h"

#include "jni/util/scoped_jstring.h"

extern "C" JNIEXPORT jstring JNICALL
Java_com_zyp_xlog_XLogSample_stringFromJNI(
        JNIEnv* env,
        jobject /* this */) {

    xinfo_function();


    xdebug2(TSF "test xlog, have level filter. line:%0, func:%1", __LINE__, __FUNCTION__);

    xdebug2("test xlog, have level filter. line:%d, func:%s", __LINE__, __FUNCTION__);

    xassert2(1<0, "assert false info");

    xassert2(false);
    xassert2(true);

    xverbose2(TSF "test xlog, have level filter");


    LOGD("testxlog", "-------user define:%d--------", __LINE__);

    __android_log_print(ANDROID_LOG_INFO, "test", "123");

    __android_log_print(ANDROID_LOG_INFO, "test", "123:%d", 4);
    __android_log_write(ANDROID_LOG_INFO, "test", "123");

    __android_log_assert(1>0, "test", "%d", 3455);

    __android_log_assert(1<0, "test", "%d", 3455);

    __android_log_assert(1<0, "test", "3455dfdddddddddd");


    LOGI("test", "111111111111");

    std::string hello = "Hello from C++";
    return env->NewStringUTF(hello.c_str());
}


extern "C" JNIEXPORT jstring JNICALL
Java_com_zyp_xlog_XLogSample_getPathFromJNI(
        JNIEnv* env,
        jobject /* this */,
        jstring name,
        jstring logdir) {

    //jstring logdir = (jstring)JNU_GetField(env, _log_config, "logdir", "Ljava/lang/String;").l;

    // 将 JNI 参数转换为 C++ 数据类型
    const char* str = env->GetStringUTFChars(name, NULL);
    // str转std::string
    std::string name_str = str;
    // 释放资源
    env->ReleaseStringUTFChars(name, str);


    std::string logdir_str;
    if (NULL != logdir) {
        ScopedJstring logdir_jstr(env, logdir);
        logdir_str = logdir_jstr.GetChar();
    }
    return env->NewStringUTF(logdir_str.c_str());
}