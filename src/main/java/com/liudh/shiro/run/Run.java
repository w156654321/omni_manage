package com.liudh.shiro.run;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 启动类
 *
 * @author liudh
 * @since 2017-07-28
 */
@SuppressWarnings({"unused", "resource"})
public class Run {
    private static Logger logger = LoggerFactory.getLogger(Run.class);

    public static void main(String[] args) {

        try (ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("spring/spring.xml")) {
            context.registerShutdownHook();
            context.start();

            logger.info("spring 容器启动完成...");

            Object lock = new Object();
            synchronized (lock) {
                try {
                    while (true)
                        lock.wait();
                } catch (InterruptedException e) {
                    logger.error(e.getMessage(), e);
                }
            }
        } catch (Exception e) {
            logger.error("spring 容器启动错误", e);
        }
    }
}