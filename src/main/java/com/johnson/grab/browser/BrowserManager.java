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

import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.johnson.grab.account.Account;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Johnson.Liu
 * <p/>
 * Author: Johnson.Liu
 * Date: 2014/04/14
 * Time: 16:09
 */
public class BrowserManager {

//    public static Browser<HtmlPage> getBrowser(Account account) {
//        Browser<HtmlPage> browser = holder.pool.get(account);
//        if (browser == null) {
//            browser = new HtmlUnitBrowser();
//            holder.pool.put(account, browser);
//        }
//        return browser;
//    }

    public static Browser<HtmlPage> getBrowser(Account account) {
        return new HtmlUnitBrowser();
    }

    public static void close(Account account) {
        Browser browser = holder.pool.remove(account);
        if (browser != null) {
            browser.close();
        }
    }

    private static final Holder holder = new Holder();

    private static class Holder {
        Map<Account, Browser<HtmlPage>> pool = new HashMap<Account, Browser<HtmlPage>>();
        private Holder() {}
    }

}
