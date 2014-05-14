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

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.ScriptException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.*;
import com.gargoylesoftware.htmlunit.javascript.JavaScriptErrorListener;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.w3c.css.sac.CSSException;
import org.w3c.css.sac.CSSParseException;
import org.w3c.css.sac.ErrorHandler;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;

/**
 * Created by Johnson.Liu
 * <p/>
 * Author: Johnson.Liu
 * Date: 2014/05/09
 * Time: 22:56
 */
public class HtmlUnitTest {

    WebClient client = new WebClient(BrowserVersion.INTERNET_EXPLORER_11);
    private static final String URL_BAIDU = "http://www.baidu.com/s";

    @Before
    public void setUp() {
        client.setJavaScriptErrorListener(new JavaScriptErrorListener() {
            @Override
            public void scriptException(HtmlPage htmlPage, ScriptException scriptException) {
//                System.out.println(scriptException);
            }

            @Override
            public void timeoutError(HtmlPage htmlPage, long allowedTime, long executionTime) {
//                System.out.println(htmlPage);
            }

            @Override
            public void malformedScriptURL(HtmlPage htmlPage, String url, MalformedURLException malformedURLException) {
                System.out.println(htmlPage);
            }

            @Override
            public void loadScriptError(HtmlPage htmlPage, URL scriptUrl, Exception exception) {
//                System.out.println(htmlPage);
            }
        });
        client.setCssErrorHandler(new ErrorHandler() {
            @Override
            public void warning(CSSParseException e) throws CSSException {
//                e.printStackTrace();
            }

            @Override
            public void error(CSSParseException e) throws CSSException {
//                e.printStackTrace();
            }

            @Override
            public void fatalError(CSSParseException e) throws CSSException {
//                e.printStackTrace();
            }
        });
    }

    private static final String URL_HAO123 = "http://www.hao123.com/?tn=93566780_hao_pg";

    @Test
    public void testHelloWorld() {
        try {
            // hao123
            HtmlPage hao123 = requestHao123(URL_HAO123);
            Assert.assertNotNull("hao123 is null!", hao123);
            // baidu
            HtmlPage baidu = searchBaidu(hao123, "皮鞋");
            Assert.assertNotNull("baidu is null!", baidu);
            // click ads
            HtmlPage[] ads = clickADs(baidu);
            Assert.assertNotNull("ads is null!", ads);
            Assert.assertTrue("ads is empty!", ads.length>0);
        } catch (IOException e) {
            e.printStackTrace();
            Assert.assertFalse(true);
        }
    }

    private HtmlPage requestHao123(String url) throws IOException {
        HtmlPage page = client.getPage(url);
        page.initialize();
        return page;
    }

    private HtmlPage searchBaidu(HtmlPage hao123, String keyword) throws IOException {
        if (hao123 == null) {
            System.out.println("");
            return null;
        }
        // getform
        List<HtmlForm> forms = hao123.getForms();
        for (HtmlForm form : forms) {
            System.out.println(form);
            String action = form.getAttribute("action");
            System.out.println("action: " + action);
            if (URL_BAIDU.equalsIgnoreCase(action)) {
                HtmlInput word = form.getInputByName("word");
                word.setValueAttribute(keyword);
                HtmlSubmitInput commit = form.getInputByValue("百度一下");
                HtmlPage baidu = commit.click();
                baidu.initialize();
                return baidu;
            }
        }
        return null;
    }

//    private static final String XPATH_ADS = "//div[@id=\"container\"]//table//a[@id=\"aw*\"]";
    private static final String XPATH_ADS = "//div[@id=\"container\"]/div[@id=\"content_left\"]/table//a[@data-is-main-url=\"true\"]";

    private HtmlPage[] clickADs(HtmlPage baidu) {
        if (baidu == null) {
            return null;
        }
        List<HtmlAnchor> links = (List<HtmlAnchor>) baidu.getByXPath(XPATH_ADS);
        if (links == null || links.isEmpty()) {
            System.out.println("No ad links found.");
            return null;
        }
        // filter duplicate ads
        Map<String, HtmlPage> map = new HashMap<String, HtmlPage>();
        for (HtmlAnchor link : links) {
            String id = link.getId();
            if (!map.containsKey(id)) {
                try {
                    HtmlPage page = link.click();
                    page.initialize();
                    map.put(id, page);
                    Thread.sleep(1000);
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        return map.values().toArray(new HtmlPage[0]);
    }
}
