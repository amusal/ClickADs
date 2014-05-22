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
import com.johnson.grab.utils.Log;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by Johnson.Liu
 * <p/>
 * Author: Johnson.Liu
 * Date: 2014/05/18
 * Time: 22:30
 */
public class ClickADActionTest extends SearchBaiduActionTest {

    protected ClickADAction clickADAction = new ClickADAction();

    @Before
    @Override
    public void setUp() {
        super.setUp();
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
                        clickADAction.setFeed(page);
                        clickADAction.setHandler(clickADAction.new Handler() {
                            @Override
                            public void handle(HtmlPage[] htmlPages) {
                                Assert.assertNotNull(htmlPages);
                                Log.info(Log.TYPE.INFO, "ads length: " + htmlPages.length);
                            }
                        });
                        clickADAction.run();
                    }
                });
                baiduAction.run();
            }
        });
        hao123Action.run();
    }
}
