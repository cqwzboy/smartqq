package com.smartqq;

/**
 * Hello world!
 *
 */
public class App {
    public static void main( String[] args ) {
        String data = "set name zhangsan";
        System.out.println(data.indexOf("set name "));
        String result = data.substring(data.indexOf("set name ")+9);
        System.out.println(result);
    }
}
