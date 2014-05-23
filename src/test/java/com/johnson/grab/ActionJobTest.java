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

import com.johnson.grab.action.GetHao123Action;
import com.johnson.grab.browser.BrowserManager;
import com.johnson.grab.job.QuartzManager;
import com.johnson.grab.utils.Log;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.quartz.SchedulerException;

/**
 * Created by Johnson.Liu
 * <p/>
 * Author: Johnson.Liu
 * Date: 2014/05/19
 * Time: 10:08
 */
public class ActionJobTest {

    ActionJob job = new ActionJob();
    GetHao123Action hao123Action = new GetHao123Action();

    @Before
    public void setUp() {
        hao123Action.setBrowser(BrowserManager.getBrowser(TestConstants.DEFAULT_ACCOUNT));
        hao123Action.setFeed(TestConstants.DEFAULT_ACCOUNT);
        hao123Action.setKeywordNum(10);
        job.setAction(hao123Action);
        job.setDelay(1000);
    }

    @Test
    public void testRun() {
        try {
            job.run();
            int count = 0;
//            job.shutdown(true);
            while (!QuartzManager.isTerminate()) {
                Thread.sleep(1000);
                count++;
            }
            Log.info(Log.TYPE.INFO, "All task finished in " + count + " seconds");
            Assert.assertTrue(true);
        } catch (SchedulerException e) {
            e.printStackTrace();
            Assert.assertFalse(true);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        ActionJobTest test = new ActionJobTest();
        test.setUp();
        test.testRun();
    }
}
