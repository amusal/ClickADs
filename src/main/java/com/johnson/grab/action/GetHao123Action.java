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

import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.johnson.grab.Action;
import com.johnson.grab.ActionJob;
import com.johnson.grab.account.Account;
import com.johnson.grab.browser.Browser;
import com.johnson.grab.dict.DictManager;
import com.johnson.grab.utils.Log;
import com.johnson.grab.utils.NumberUtil;
import org.quartz.SchedulerException;

import java.io.IOException;

/**
 * Created by Johnson.Liu
 * <p/>
 * Author: Johnson.Liu
 * Date: 2014/05/09
 * Time: 18:30
 */
public class GetHao123Action extends Action<Account, HtmlPage> {

    private static final String URL_HAO123 = "http://www.hao123.com/?tn=%s";

    private int keywordNum;

    public void setKeywordNum(int keywordNum) {
        this.keywordNum = keywordNum;
    }

    public GetHao123Action() {
        this.setHandler(new Handler() {
            @Override
            public void handle(HtmlPage htmlPage) {
                int keywordNum = ((GetHao123Action) getAction()).keywordNum;
                if (keywordNum == 0) {
                    keywordNum = NumberUtil.randomInteger(15, 30);
                }
                String[] keywords = DictManager.randomKeywords(keywordNum);
                Log.info(Log.TYPE.INFO, "随机加载关键词" + keywords.length + "个");
                int succNum, failNum;
                int addedDelayTime = NumberUtil.randomInteger(8, 18) * 1000;
                for (String keyword : keywords) {
                    // create search action
                    SearchBaiduAction baidu = new SearchBaiduAction();
                    baidu.setFeed(htmlPage);
                    baidu.setKeyword(keyword);
                    // create job to run action
                    ActionJob job = new ActionJob();
                    job.setAction(baidu);
                    job.setDelay(addedDelayTime);
                    try {
                        job.run();
                        Log.info(Log.TYPE.INFO, addedDelayTime + "ms后搜索关键词:" + keyword);
                    } catch (SchedulerException e) {
                        e.printStackTrace();
                        Log.info(Log.TYPE.FAIL, "添加搜索任务时失败，目标关键词:" + keyword);
                    }
                    int delay = NumberUtil.randomInteger(10, 20) * 1000;
                    addedDelayTime += delay;
                }
            }
        });
    }

    @Override
    public HtmlPage doAction() {
        if (getFeed() == null) {
            throw new IllegalArgumentException("Account can't be null.");
        }
        String url = String.format(URL_HAO123, getFeed().getName());
        HtmlPage page = null;
        try {
            page = getBrowser().get(url);
            page.initialize();
            Log.info(Log.TYPE.SUCCESS, "打开页面 " + url);
        } catch (IOException e) {
            e.printStackTrace();
            Log.info(Log.TYPE.SUCCESS, "打开页面 " + url + " 时发生错误");
        } finally {
            return page;
        }
    }
}
