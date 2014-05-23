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

import com.johnson.grab.account.Account;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Johnson.Liu
 * <p/>
 * Author: Johnson.Liu
 * Date: 2014/05/16
 * Time: 09:11
 */
public class BrowserManagerTest {

    private Account[] accounts = new Account[] {new Account("123"), new Account("234"),
            new Account("345"), new Account("456"), new Account("567"), new Account("678")};

    @Test
    public void testGetBrowser() {
        Map<Account, Browser<?>> map = new HashMap<Account, Browser<?>>();
        for (Account account : accounts) {
            Browser<?> browser = BrowserManager.getBrowser(account);
            map.put(account, browser);
        }
        for (Account account : accounts) {
            Assert.assertTrue(map.get(account) == BrowserManager.getBrowser(account));
            Browser<?> browser = map.remove(account);
            Assert.assertFalse(map.values().contains(browser));
            BrowserManager.close(account);
        }
    }
}
