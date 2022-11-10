package com.jia.home.config.cron;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jia.home.model.CornJob;
import com.jia.home.service.CornJobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class initTask implements CommandLineRunner {

    @Autowired
    CornTaskRegister cornTaskRegister;

    @Autowired
    CornJobService cornJobService;

    @Override
    public void run(String... args) throws Exception {
        List<CornJob> jobStatus = cornJobService.list(new QueryWrapper<CornJob>().eq("job_status", 1));
        for (CornJob status : jobStatus) {
            //遍历执行
            cornTaskRegister.addCornTask(new SchedulingRunnable(status.getBeanName(),status.getMethodName()),status.getCornExpression());
            System.out.println(status);
        }

    }
}
