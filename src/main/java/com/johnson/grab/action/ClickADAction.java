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

import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.johnson.grab.Action;
import com.johnson.grab.utils.Log;
import com.johnson.grab.utils.NumberUtil;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Johnson.Liu
 * <p/>
 * Author: Johnson.Liu
 * Date: 2014/05/18
 * Time: 21:55
 */
public class ClickADAction extends Action<HtmlPage, HtmlPage[]> {

    private String keyword;

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    @Override
    public HtmlPage[] doAction() {
        return clickADs(getFeed());
    }

    private static final String XPATH_ADS = "//div[@id=\"container\"]/div[@id=\"content_left\"]/table//a[@data-is-main-url=\"true\"]";

    private HtmlPage[] clickADs(HtmlPage baidu) {
        if (baidu == null) {
            Log.info(Log.TYPE.FAIL, "参数为空，可能百度搜索页打开失败");
            return null;
        }
        List<HtmlAnchor> links = (List<HtmlAnchor>) baidu.getByXPath(XPATH_ADS);
        if (links == null || links.isEmpty()) {
            Log.info(Log.TYPE.FAIL, "当前关键词可能已失效，页面不包含推广链接");
            return null;
        }
        // filter duplicate ads
        Map<String, HtmlPage> map = new HashMap<String, HtmlPage>();
        for (HtmlAnchor link : links) {
            String id = link.getId();
            if (!map.containsKey(id)) {
                try {
                    String toClickLog = "准备点击广告：%s\\n关键词：%s\\n链接：%s";
                    String adTitle = link.asText();
                    String adUrl = link.getHrefAttribute();
                    Log.info(Log.TYPE.INFO, String.format(toClickLog, adTitle, keyword, adUrl));
                    HtmlPage page = link.click();
                    page.initialize();
                    map.put(id, page);
                    int sleep = NumberUtil.randomInteger(3, 8) * 1000;
                    Log.info(Log.TYPE.SUCCESS, "点击广告完毕:" + adTitle + ", 随机休息 " + sleep + "ms");
                    Thread.sleep(sleep);
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        return map.values().toArray(new HtmlPage[0]);
    }

    public ClickADAction() {
        this.setHandler(new Handler() {
            @Override
            public void handle(HtmlPage[] htmlPages) {
                if (htmlPages == null) {
                    String word = ((ClickADAction) this.getAction()).keyword;
                    Log.info(Log.TYPE.INFO, "关键词处理结束，没有找到相应广告");
                } else {
                    Log.info(Log.TYPE.INFO, "关键词处理结束，共点击 " + htmlPages.length + " 个广告");
                }
            }
        });
    }
}
