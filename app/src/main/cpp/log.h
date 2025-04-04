//
// Created by Park Yu on 2024/6/18.
//

#ifndef PLUSFRIEND_LOG_H
#define PLUSFRIEND_LOG_H

#include <android/log.h>

#define LOGD(...)  ((void)__android_log_print(ANDROID_LOG_DEBUG, "NL1.12.6.1", __VA_ARGS__))
#define LOGI(...)  ((void)__android_log_print(ANDROID_LOG_INFO, "NL1.12.6.1", __VA_ARGS__))
#define LOGV(...)  ((void)__android_log_print(ANDROID_LOG_VERBOSE, "NL1.12.6.1", __VA_ARGS__))
#define LOGW(...)  ((void)__android_log_print(ANDROID_LOG_WARN, "NL1.12.6.1", __VA_ARGS__))
#define LOGE(...)  ((void)__android_log_print(ANDROID_LOG_ERROR, "NL1.12.6.1", __VA_ARGS__))

#endif //PLUSFRIEND_LOG_H
