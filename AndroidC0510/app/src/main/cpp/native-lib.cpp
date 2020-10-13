#include <jni.h>
#include <string>
#include <android/log.h>
#include <linux/i2c-dev.h>
#include <errno.h>
#include <fcntl.h>
#include <sys/ioctl.h>
#include <linux/types.h>
#include "linux/i2c.h"
#include <stdio.h>
#include <stdint.h>
#include <stdlib.h>
#include <stdio.h>
#include <fcntl.h>
#include "linux/i2c-dev.h"
#include "I2C.h"

extern "C" JNIEXPORT jstring JNICALL
Java_com_example_androidc0510_MainActivity_stringFromJNI(
        JNIEnv* env,
        jobject /* this */) {
    I2C i2c(2, 0x33);
    if(i2c.error()) {
        printf("ERROR: I2C failed to initialize.\n");
        std::string hello = "ERROR: I2C failed to initialize ";
        return env->NewStringUTF(hello.c_str());
    }
    printf("I2c initialized.\n");

    if(!i2c.enablePacketErrorChecking()) {
        printf("ERROR: failed to enable PEC.\n");
        std::string hello = "ERROR: failed to enable PEC.";
        return env->NewStringUTF(hello.c_str());
    }
    if(!i2c.disablePacketErrorChecking()) {
        printf("ERROR: failed to disable PEC.\n");
        std::string hello = "ERROR: failed to disable PEC.";
        return env->NewStringUTF(hello.c_str());
    }
    uint8_t byte = i2c.read_byte(0x00);

    // read a word from register 0x00
    uint16_t word = i2c.read_word(0x00);

    // read eight bytes starting from register 0x00
    uint16_t size = 255;
    uint8_t block[size];
    if(!i2c.read_block(0x00, size, block)) {
        printf("ERROR: failed to read block of bytes.\n");

        std::string hello = "ERROR: failed to read block of bytes.";
        return env->NewStringUTF(hello.c_str());
    }

    std::string res = std::to_string(block[0]);
    for(int i = 1; i < size; i++){
        res += "-"+std::to_string(block[i]);
    }
    return env->NewStringUTF(res.c_str());




//    const int adapter_nr = 2;
//    char filename[20];
//    const int file = open("/dev/i2c-2", O_RDWR);
//    if (file < 0) {
//        std::string hello = "Oh dear, something went wrong with open! ";
//        return env->NewStringUTF(hello.c_str());
//        printf("Oh dear, something went wrong with open()! %s\n", strerror(errno));
////        exit(EXIT_FAILURE);
//    }
//
//    // The I2C address. Got it from `i2cdetect -y 2;`
//    const int addr = 0x33;
//
//    if (ioctl(file, I2C_SLAVE, addr) < 0) {
////        printf("Oh dear, something went wrong with ioctl()! %s\n", strerror(errno));
//        std::string hello = "Oh dear, something went wrong with ioctl()! ";
//        return env->NewStringUTF(hello.c_str());
////        exit(EXIT_FAILURE);
//    }
//
//
//    // Device register to access.
//    // Using SMBus commands
//    union i2c_smbus_data data;
//    struct i2c_smbus_ioctl_data args;
//
//    args.read_write = I2C_SMBUS_READ;
//    args.command = 0xc5;
//    args.size = I2C_SMBUS_BYTE_DATA;
//    args.data = &data;
//
//    __s32 access = ioctl(file,I2C_SMBUS,&args);
//    __s32 result;
////    if(access){
////        result = -1;
////    }else{
////        0x0FF & data.byte;
////    }
//    if (access) {
//        // ERROR HANDLING: i2c transaction failed
//        printf("Oh dear, something went wrong with i2c_smbus_read_byte_data()>i2c_smbus_access()>ioctl()! %s\n", strerror(errno));
//        std::string hello = "Oh dear, something went wrong with i2c_smbus_read_byte_data()>i2c_smbus_access()>ioctl()! ";
//        return env->NewStringUTF(hello.c_str());
////        exit(EXIT_FAILURE);
//    } else {
//        // res contains the read word
//        __s32 result = 0x0FF & data.byte;
//
////        printf("0x%+02x\n", result);
//        std::string hello = std::to_string(result);
//        return env->NewStringUTF(hello.c_str());
//    }

//    close(file);

//    return(EXIT_SUCCESS);

    std::string hello = "Hello from C++";
//    __android_log_print(ANDROID_LOG_INFO, "TRACKERS", "%s", "Minh");
    return env->NewStringUTF(hello.c_str());
}
