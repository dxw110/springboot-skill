package com.dcool.springbootschedule.scheuled;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author DCool
 * date 2020-07-13
 */
@Component
public class ScheduleTask {

    @Scheduled(cron = "5 0 0 * * ?")
    public void scheduledTask1() {
        System.out.println("定时任务1");
    }


    /**
     * initialDelay：启动后多久开始执行，单位时毫秒
     * fixedRate：下次执行时间，任务开始运行的时候就计时。
     * fixedDelay：下次执行时间，fixedDelay等任务进行完了才开始计时
     */
    @Scheduled(initialDelay = 1000 * 10, fixedDelay = 1000 * 5)
    public void scheduledTask2() {
        System.out.println("任务2执行时间：" + System.currentTimeMillis());
        System.out.println("定时任务2");
        try {
            Thread.sleep(2 * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("任务2结束时间：" + System.currentTimeMillis());
    }

    @Scheduled(initialDelay = 1000 * 10, fixedRate = 1000 * 5)
    public void scheduledTask3() {
        System.out.println("任务3执行时间：" + System.currentTimeMillis());
        System.out.println("定时任务3");
        try {
            Thread.sleep(2 * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("任务3结束时间：" + System.currentTimeMillis());
    }
}
