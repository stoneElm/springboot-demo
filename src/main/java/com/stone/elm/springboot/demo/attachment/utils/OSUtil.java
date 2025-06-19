package com.stone.elm.springboot.demo.attachment.utils;

public class OSUtil {
    // 使用volatile关键字确保多线程环境下的可见性
    private static volatile OSUtil instance;

    // 私有构造函数防止外部实例化
    private OSUtil() {}

    // 全局访问点
    public static OSUtil getInstance() {
        if (instance == null) { // 第一重检查
            synchronized (OSUtil.class) {
                if (instance == null) { // 第二重检查
                    instance = new OSUtil();
                }
            }
        }
        return instance;
    }

    // 判断操作系统的方法
    public String getOS() {
        String osName = System.getProperty("os.name").toLowerCase();
        if (osName.contains("linux")) {
            return "Linux";
        } else if (osName.contains("windows")) {
            return "Windows";
        } else if (osName.contains("mac")) {
            return "Mac OS";
        } else {
            return "Unknown";
        }
    }
}
