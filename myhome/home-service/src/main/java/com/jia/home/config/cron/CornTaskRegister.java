package com.jia.home.config.cron;


import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.config.CronTask;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class CornTaskRegister implements DisposableBean {

    private final Map<Runnable,ScheduledTask> scheduledTasks = new ConcurrentHashMap<>(16);

    @Autowired
    TaskScheduler taskScheduler;

    public TaskScheduler taskScheduler(){
        return this.taskScheduler;
    }

    /**
     * 添加一个定时任务
     */
    public void addCornTask(Runnable task,String cornExpression){
        addCornTask(new CronTask(task,cornExpression));
    }

    public void addCornTask(CronTask cronTask) {
        if (cronTask!=null){
            Runnable runnable = cronTask.getRunnable();
            if (this.scheduledTasks.containsKey(runnable)){
                //要添加的定时任务已经存在 先把已经存在的定时任务移除然后再添加新的定时任务
                removeCronTask(runnable);
            }
            //添加一个定时任务
            scheduledTasks.put(runnable,scheduledCronTask(cronTask));
        }
    }

    public ScheduledTask scheduledCronTask(CronTask cronTask) {
        ScheduledTask scheduledTask = new ScheduledTask();
        scheduledTask.future = this.taskScheduler.schedule(cronTask.getRunnable(),cronTask.getTrigger());
        return scheduledTask;
    }

    public void removeCronTask(Runnable runnable) {
        //从map集合中移除
        ScheduledTask task = this.scheduledTasks.remove(runnable);
        //取消执行
        if (task!=null){
            task.cancel();
        }
    }


    @Override
    public void destroy() throws Exception {
        //终止定时任务
        for (ScheduledTask value : this.scheduledTasks.values()) {
            value.cancel();
        }
        //清空集合
        this.scheduledTasks.clear();
    }
}
