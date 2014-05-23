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
package com.johnson.grab.job;

import com.johnson.grab.utils.Log;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.util.*;

/**
 * Created by Johnson.Liu
 * <p/>
 * Author: Johnson.Liu
 * Date: 2014/05/20
 * Time: 22:49
 */
public class QuartzManager {

    private static class JobHolder {
        Map<JobKey, JobDetail> pool = Collections.synchronizedMap(new HashMap<JobKey, JobDetail>());

        void add(JobDetail job) {
            pool.put(job.getKey(), job);
        }

        JobKey[] getKeys() {
            return Collections.unmodifiableMap(pool).keySet().toArray(new JobKey[0]);
        }

        JobDetail remove(JobKey key) {
            return pool.remove(key);
        }

        boolean isEmpty() {
            return pool.isEmpty();
        }
    }

    private static final JobHolder jobsHolder = new JobHolder();

    public static void addJob(JobDetail job) {
        jobsHolder.add(job);
    }

    private static final SchedulerFactory sf = new StdSchedulerFactory();

    private static volatile Scheduler scheduler;

    public static Scheduler getScheduler() throws SchedulerException {
        if (scheduler != null) {
            if (!scheduler.isShutdown()) {
                return scheduler;
            }
            scheduler.clear();
        }
        scheduler = sf.getScheduler();
        return scheduler;
    }

    public static boolean isTerminate() throws SchedulerException {
        if (scheduler == null) {
            return false;
        }
        if (scheduler.isShutdown()) {
            scheduler.clear();
            scheduler = null;
            return true;
        }
        if (!scheduler.isStarted()) {
            return false;
        }
        JobKey[] keys = jobsHolder.getKeys();
        for (JobKey key : keys) {
            if (!scheduler.checkExists(key)) {
                Log.debug("Key exists in holder but not exists in scheduler: " + key.getName());
            }
        }
        if (jobsHolder.isEmpty()) {
            scheduler.clear();
            scheduler.shutdown();
            scheduler = null;
            return true;
        }
        return false;
    }

    public static JobDetail remove(JobKey key) {
        return jobsHolder.remove(key);
    }
}
