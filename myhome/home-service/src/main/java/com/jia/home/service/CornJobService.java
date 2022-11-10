package com.jia.home.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jia.home.config.cron.CornTaskRegister;
import com.jia.home.config.cron.SchedulingRunnable;
import com.jia.home.mapper.CornJobMapper;
import com.jia.home.model.CornJob;
import com.jia.home.utils.RespBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CornJobService extends ServiceImpl<CornJobMapper, CornJob> {
    @Autowired
    private CornJobMapper cornJobMapper;
    @Autowired
    private CornTaskRegister cornTaskRegister;

    public RespBean delJobById(Integer id) {
        CornJob job = cornJobMapper.selectById(id);
        SchedulingRunnable schedulingRunnable = new SchedulingRunnable(job.getBeanName(), job.getMethodName(), job.getMethodParams());
        cornTaskRegister.removeCronTask(schedulingRunnable);
        return RespBean.ok();
    }

    public RespBean addJob(CornJob cornJob) {
        List<CornJob> cornJobs = cornJobMapper.selectList(null);
        for (CornJob job:cornJobs) {
            if (job.equals(cornJob)){
                return RespBean.error("任务已存在");
            }
        }
        cornJobMapper.insert(cornJob);
        SchedulingRunnable schedulingRunnable = new SchedulingRunnable(cornJob.getBeanName(), cornJob.getMethodName(), cornJob.getMethodParams());
        if (cornJob.getJobStatus() ==1){
            cornTaskRegister.addCornTask(schedulingRunnable,cornJob.getCornExpression());
        }
        return RespBean.ok();
    }
}
