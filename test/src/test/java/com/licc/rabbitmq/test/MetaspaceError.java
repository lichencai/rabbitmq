package com.licc.rabbitmq.test;

import java.util.ArrayList;
import java.util.List;

public class MetaspaceError {
	/**
	 * 如果设置运行时jvm的参数为：-XX:PermSize=8m -XX:MaxPermSize=8m -Xmx16m时候，在jdk1.8中是不存在永久代PermGen Space,故设置永生代的大小-XX:PermSize
	 * 和-XX:MaxPermSize是不起效果的。
	 * 已经将永生代转移到java heap中，故报出的错误是java.lang.OutOfMemoryError: Java heap space
	 */
	/**
	 * 在jdk1.8中可以设置metaspace的大小，
	 * -XX:MetaspaceSize=8m -XX:MaxMetaspaceSize=8m
	 * 会导致元空间溢出 MetaspaceError
	 */
	static String  base = "string";  
    public static void main(String[] args) {  
        List<String> list = new ArrayList<String>();  
        for (int i=0;i< Integer.MAX_VALUE;i++){
            String str = base + base;  
            base = str;  
            list.add(str.intern());  
        }  
    }
}
