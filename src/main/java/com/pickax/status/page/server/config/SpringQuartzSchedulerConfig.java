package com.pickax.status.page.server.config;

import com.pickax.status.page.server.scheduler.AutoWiringSpringBeanJobFactory;
import com.pickax.status.page.server.scheduler.StatusLogJob;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobDetail;
import org.quartz.SimpleTrigger;
import org.quartz.Trigger;
import org.springframework.boot.autoconfigure.quartz.QuartzDataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.scheduling.quartz.SimpleTriggerFactoryBean;
import org.springframework.scheduling.quartz.SpringBeanJobFactory;

import javax.sql.DataSource;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class SpringQuartzSchedulerConfig {

    private final ApplicationContext applicationContext;

    @PostConstruct
    public void init() {
        log.info("Spring Quartz Scheduler init");
    }

    @Bean
    @QuartzDataSource
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource quartzDataSource() {
        log.info("Configuring quartz data Source");
        return DataSourceBuilder.create().build();
    }

    @Bean
    public SpringBeanJobFactory springBeanJobFactory() {
        AutoWiringSpringBeanJobFactory jobFactory = new AutoWiringSpringBeanJobFactory();
        log.debug("Configuring Job factory");

        jobFactory.setApplicationContext(applicationContext);
        return jobFactory;
    }

    @Bean
    public JobDetailFactoryBean jobDetail() {
        JobDetailFactoryBean jobDetailFactory = new JobDetailFactoryBean();
        final String JOB_NAME = "COMPONENT_STATUS_INSPECTION";

        jobDetailFactory.setJobClass(StatusLogJob.class);
        jobDetailFactory.setName(JOB_NAME);
        jobDetailFactory.setDescription("Invoke Status History service...");
        jobDetailFactory.setDurability(true);
        return jobDetailFactory;
    }

    @Bean
    public SimpleTriggerFactoryBean trigger(JobDetail job) {
        SimpleTriggerFactoryBean trigger = new SimpleTriggerFactoryBean();
        final String TRIGGER_NAME = "COMPONENT_STATUS_INSPECTION_TRIGGER";

        trigger.setJobDetail(job);

        int frequencyInSeconds = 60;
        final long SCHEDULER_FREQUENCY = frequencyInSeconds * 1000;
        log.info("Configuring trigger to fire every {} seconds", frequencyInSeconds);
        trigger.setRepeatInterval(SCHEDULER_FREQUENCY);
        trigger.setRepeatCount(SimpleTrigger.REPEAT_INDEFINITELY);

        trigger.setName(TRIGGER_NAME);
        return trigger;
    }

    @Bean
    public SchedulerFactoryBean scheduler(Trigger trigger, JobDetail job, DataSource quartzDataSource) {
        SchedulerFactoryBean schedulerFactory = new SchedulerFactoryBean();
        final String QUARTZ_CONFIG_PATH = "application.yml";

        schedulerFactory.setConfigLocation(new ClassPathResource(QUARTZ_CONFIG_PATH));

        log.debug("Setting the Scheduler up");
        schedulerFactory.setJobFactory(springBeanJobFactory());
        schedulerFactory.setJobDetails(job);
        schedulerFactory.setTriggers(trigger);
        schedulerFactory.setDataSource(quartzDataSource);
        return schedulerFactory;
    }

}
