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

import com.johnson.grab.account.Account;
import com.johnson.grab.account.AccountManager;
import com.johnson.grab.action.GrabAction;
import com.johnson.grab.job.QuartzManager;
import com.johnson.grab.utils.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.SchedulerException;

/**
 * Created by Johnson.Liu
 * <p/>
 * Author: Johnson.Liu
 * Date: 2014/05/09
 * Time: 17:32
 */
public class Main {

    public static void main(String[] args) {
        // close log
        LogFactory.getFactory().setAttribute("org.apache.commons.logging.Log", "org.apache.commons.logging.impl.NoOpLog");
        Log.info(Log.TYPE.INFO, "程序启动");
        GrabAction grab = new GrabAction();
        ActionJob job = new ActionJob();
        job.setAction(grab);
        job.setDelay(1000);
        try {
            job.run();
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
        try {
            boolean isTerminate;
            do {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                isTerminate = QuartzManager.isTerminate();
            }
            while (!isTerminate);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
        Log.info(Log.TYPE.INFO, "程序运行完毕，自动退出");
    }
}
