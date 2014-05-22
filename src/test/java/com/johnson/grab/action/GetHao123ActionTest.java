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
import com.johnson.grab.TestConstants;
import com.johnson.grab.browser.BrowserManager;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;



/**
 * Created by Johnson.Liu
 * <p/>
 * Author: Johnson.Liu
 * Date: 2014/05/16
 * Time: 10:18
 */
public class GetHao123ActionTest {

    protected GetHao123Action hao123Action = new GetHao123Action();

    @Before
    public void setUp() {
        hao123Action.setFeed(TestConstants.DEFAULT_ACCOUNT);
        hao123Action.setBrowser(BrowserManager.getBrowser(hao123Action.getFeed()));
    }

    @Test
    public void testDoAction() {
        hao123Action.setHandler(hao123Action.new Handler() {
            @Override
            public void handle(HtmlPage page) {
                Assert.assertNotNull(page);
            }
        });
        hao123Action.doAction();
    }
}
