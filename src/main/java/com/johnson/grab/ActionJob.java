/*
 * Copyright 2014 Future TV, Inc.
 *
 *      Licensed under the Apache License, Version 2.0 (the "License");
 *      you may not use this file except in compliance with the License.
 *      You may obtain a copy of the License at
 *
 *          http://www.apache.org/licenses/LICENSE-2.0
 *
 *      Unless required by applicable law or agreed to in writing, software
 *      distributed under the License is distributed on an "AS IS" BASIS,
 *      WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *      See the License for the specific language governing permissions and
 *      limitations under the License.
 */
package com.johnson.grab;

import com.johnson.grab.job.QuartzManager;
import com.johnson.grab.utils.Log;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by Johnson.Liu
 * <p/>
 * Author: Johnson.Liu
 * Date: 2014/05/19
 * Time: 09:13
 */
public class ActionJob {

    private static final String ACTION_KEY = "action";
    private static final String ACTION_JOB_KEY = "action_job";

    private Action<?, ?> action;

    private int delay;

    public void setAction(Action<?, ?> action) {
        this.action = action;
    }

    public void setDelay(int delay) {
        this.delay = delay;
    }

    public static class InnerJob implements Job {
        @Override
        public void execute(JobExecutionContext context) throws JobExecutionException {
            JobDataMap map = context.getMergedJobDataMap();
            try {
                Action action = (Action) map.get(ACTION_KEY);
                if (action == null) {
                    Log.info(Log.TYPE.FAIL, "Action not found.");
                    return;
                }
                action.run();
            } catch (Throwable t) {
                t.printStackTrace();
            } finally {
                QuartzManager.remove((JobKey) map.get(ACTION_JOB_KEY));
            }
        }
    }

    private static final AtomicInteger generater = new AtomicInteger(10000);

    private static String generateId() {
        int id = generater.addAndGet(1);
        return "" + id;
    }

    public void run() throws SchedulerException {
        // Scheduler
        Scheduler scheduler = QuartzManager.getScheduler();
        // Build job
        JobDetail job = JobBuilder
                .newJob(InnerJob.class)
                .withIdentity("Job-" + action.getClass().getSimpleName() + "-" + generateId(), "actions")
                .build();
        job.getJobDataMap().put(ACTION_KEY, action);
        // use to remove job from QuartzManager
        job.getJobDataMap().put(ACTION_JOB_KEY, job.getKey());
        QuartzManager.addJob(job);

        // Build trigger
        Date startTime = DateBuilder.futureDate(delay, DateBuilder.IntervalUnit.MILLISECOND);
        SimpleTrigger trigger = (SimpleTrigger) TriggerBuilder
                .newTrigger()
                .withIdentity("Tri-" + action.getClass().getSimpleName() + generateId(), "triggers")
                .startAt(startTime)
                .build();
        // Schedule
        scheduler.scheduleJob(job, trigger);
        if (!scheduler.isStarted()) {
            scheduler.start();
        }
        Log.debug("任务 " + action + " 将于" + delay + "ms后启动");
    }
}
