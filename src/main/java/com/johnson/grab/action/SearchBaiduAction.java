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

import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlInput;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlSubmitInput;
import com.johnson.grab.Action;
import com.johnson.grab.ActionJob;
import com.johnson.grab.utils.Log;
import com.johnson.grab.utils.RandomUtil;
import com.johnson.grab.utils.StringUtil;
import org.quartz.SchedulerException;

import java.io.IOException;
import java.util.List;

/**
 * Created by Johnson.Liu
 * <p/>
 * Author: Johnson.Liu
 * Date: 2014/05/16
 * Time: 10:56
 */
public class SearchBaiduAction extends Action<HtmlPage, HtmlPage> {

    private static final String URL_BAIDU = "http://www.baidu.com/s";

    private String keyword;

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    private boolean toClickAD = true;

    public void setToClickAD(boolean toClickAD) {
        this.toClickAD = toClickAD;
    }

    public SearchBaiduAction() {
        this.setHandler(new Handler() {
            @Override
            public void handle(HtmlPage htmlPage) {
                if (toClickAD) {
                    ClickADAction clickAD = new ClickADAction();
                    clickAD.setFeed(htmlPage);
                    clickAD.setKeyword(keyword);
                    ActionJob job = new ActionJob();
                    job.setAction(clickAD);
                    job.setDelay(RandomUtil.randomInteger(3, 12) * 1000);
                    try {
                        job.run();
                    } catch (SchedulerException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    @Override
    public HtmlPage doAction() {
        if (getFeed() == null || !getFeed().getUrl().getHost().equalsIgnoreCase("www.hao123.com")
                || StringUtil.isEmpty(keyword)) {
            return null;
        }
        // getform
        List<HtmlForm> forms = getFeed().getForms();
        for (HtmlForm form : forms) {
            String action = form.getAttribute("action");
            if (URL_BAIDU.equalsIgnoreCase(action)) {
                Log.info(Log.TYPE.INFO, "已定位百度搜索框");
                try {
                    HtmlPage baidu;
                    synchronized (getFeed()) {
                        HtmlInput word = form.getInputByName("word");
                        word.setValueAttribute(keyword);
                        HtmlSubmitInput commit = form.getInputByValue("百度一下");
                        baidu = commit.click();
                    }
                    baidu.initialize();
                    Log.info(Log.TYPE.SUCCESS, "搜索页面已打开，当前关键词:" + keyword);
                    return baidu;
                } catch (IOException e) {
                    Log.info(Log.TYPE.FAIL, "搜索页面打开失败");
//                    e.printStackTrace();
                }
            }
        }
        Log.info(Log.TYPE.FAIL, "hao123页面结构可能已经改变，需要修改程序");
        return null;
    }
}
