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
package com.johnson.grab.browser;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.ScriptException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.javascript.JavaScriptErrorListener;
import org.w3c.css.sac.CSSException;
import org.w3c.css.sac.CSSParseException;
import org.w3c.css.sac.ErrorHandler;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Johnson.Liu
 * <p/>
 * Author: Johnson.Liu
 * Date: 2014/05/15
 * Time: 16:22
 */
public class HtmlUnitBrowser extends Browser<HtmlPage> {

    private WebClient client = new WebClient(BrowserVersion.INTERNET_EXPLORER_11);

    public HtmlUnitBrowser() {
        client.setJavaScriptErrorListener(new JavaScriptErrorListener() {
            @Override
            public void scriptException(HtmlPage htmlPage, ScriptException scriptException) {}

            @Override
            public void timeoutError(HtmlPage htmlPage, long allowedTime, long executionTime) {}

            @Override
            public void malformedScriptURL(HtmlPage htmlPage, String url, MalformedURLException malformedURLException) {}

            @Override
            public void loadScriptError(HtmlPage htmlPage, URL scriptUrl, Exception exception) {}
        });
        client.setCssErrorHandler(new ErrorHandler() {
            @Override
            public void warning(CSSParseException e) throws CSSException {}

            @Override
            public void error(CSSParseException e) throws CSSException {}

            @Override
            public void fatalError(CSSParseException e) throws CSSException {}
        });
    }

    @Override
    public HtmlPage get(String url) throws IOException {
        return client.getPage(url);
    }

    @Override
    public void close() {
        client.closeAllWindows();
    }
}
