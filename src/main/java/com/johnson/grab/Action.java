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


import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.johnson.grab.browser.Browser;

/**
 * Created by Johnson.Liu
 * <p/>
 * Author: Johnson.Liu
 * Date: 2014/04/03
 * Time: 11:43
 */
public abstract class Action<F, T> {

    private Browser<HtmlPage> browser;

    public void setBrowser(Browser<HtmlPage> browser) {
        this.browser = browser;
    }

    public Browser<HtmlPage> getBrowser() {
        return browser;
    }

    public abstract class Handler {

        private Action action;

        public Action getAction() {
            return action;
        }

        public void setAction(Action action) {
            this.action = action;
        }

        public abstract void handle(T t);
    }

    private Handler handler;

    public void setHandler(Handler handler) {
        this.handler = handler;
        handler.setAction(this);
    }

    public Handler getHandler() {
        return handler;
    }

    private F feed;

    public void setFeed(F feed) {
        this.feed = feed;
    }

    public F getFeed() {
        return feed;
    }

    protected abstract T doAction();

    public void run() {
        T t = doAction();
        if (handler != null) {
            handler.handle(t);
        }
    }

    @Override
    public String toString() {
        String name = this.getClass().getSimpleName();
        int idx = name.indexOf("Action");
        if (idx == -1) {
            return name;
        }
        return name.substring(0, idx);
    }
}
