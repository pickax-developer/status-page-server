package com.pickax.status.page.server.scheduler;

import com.pickax.status.page.server.service.StatusLogService;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class StatusLogJob implements Job {

    @Autowired
    private StatusLogService statusLogService;


    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        log.debug("status log job execute");
        this.statusLogService.inspectHealthCheckRequest();
    }
}
