package com.bukaedu.jobs;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author lihongxing
 * @version 1.0
 * @ClassName Application
 * @date 2022/8/13 10:58
 */

public class Application {
    public static void main(String[] args) {
        new ClassPathXmlApplicationContext("spring-jobs.xml");
    }
}
