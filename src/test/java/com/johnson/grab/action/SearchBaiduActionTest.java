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
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by Johnson.Liu
 * <p/>
 * Author: Johnson.Liu
 * Date: 2014/05/18
 * Time: 19:45
 */
public class SearchBaiduActionTest extends GetHao123ActionTest {

    protected SearchBaiduAction baiduAction = new SearchBaiduAction();

    @Before
    @Override
    public void setUp() {
        super.setUp();
        baiduAction.setKeyword(TestConstants.DEFAULT_KEYWORD);
    }

    @Test
    public void testDoAction() {
        hao123Action.setHandler(hao123Action.new Handler() {
            @Override
            public void handle(HtmlPage page) {
                Assert.assertNotNull(page);
                baiduAction.setFeed(page);
                baiduAction.setHandler(baiduAction.new Handler() {
                    @Override
                    public void handle(HtmlPage page) {
                        Assert.assertNotNull(page);
                    }
                });
                baiduAction.run();
            }
        });
        hao123Action.run();
    }
}
