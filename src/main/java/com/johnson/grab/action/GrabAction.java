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
package com.johnson.grab.action;

import com.johnson.grab.Action;
import com.johnson.grab.ActionJob;
import com.johnson.grab.account.Account;
import com.johnson.grab.account.AccountManager;
import com.johnson.grab.browser.BrowserManager;
import com.johnson.grab.utils.Log;
import com.johnson.grab.utils.RandomUtil;
import com.johnson.grab.utils.StringUtil;
import org.quartz.SchedulerException;

/**
 * Created by Johnson.Liu
 * <p/>
 * Author: Johnson.Liu
 * Date: 2014/05/21
 * Time: 04:01
 */
public class GrabAction extends Action<Object, Account[]> {

    // 一个完成之后启动下一个

    @Override
    protected Account[] doAction() {
        Account[] accounts = AccountManager.getAllAccountsByRandom();
        Log.debug("本次加载账号"  + accounts.length + "个: " + StringUtil.arrayToString(accounts));
        return accounts;
    }

    public GrabAction() {
        this.setHandler(new Handler() {
            @Override
            public void handle(Account[] accounts) {
                for (Account account : accounts) {
                    // Action
                    GetHao123Action hao123 = new GetHao123Action();
                    hao123.setFeed(account);
                    int keywordNum = RandomUtil.randomInteger(20, 40);
                    hao123.setKeywordNum(keywordNum);
                    hao123.setBrowser(BrowserManager.getBrowser(account));
                    // Job
                    ActionJob job = new ActionJob();
                    job.setAction(hao123);
                    try {
                        job.run();
                        Log.info(Log.TYPE.INFO, "账号 " + account + " 任务开启, 分配关键词数量：" + keywordNum);
                    } catch (SchedulerException e) {
                        Log.info(Log.TYPE.FAIL, "账号 " + account + " 任务开启失败");
                        e.printStackTrace();
                    }
                    try {
                        Thread.sleep(RandomUtil.randomInteger(12, 20) * 60 * 1000L);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }
}
