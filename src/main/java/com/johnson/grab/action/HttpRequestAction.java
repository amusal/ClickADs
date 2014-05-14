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

import com.johnson.grab.Action;
import com.johnson.grab.browser.CrawlException;
import com.johnson.grab.browser.HttpClientUtil;
import com.johnson.grab.browser.Response;
import com.johnson.grab.utils.StringUtil;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Johnson.Liu
 * <p/>
 * Author: Johnson.Liu
 * Date: 2014/04/14
 * Time: 17:20
 */
public class HttpRequestAction extends Action<String, Response<String>> {

    @Override
    public Response<String> doAction(String url) {
        Response<String> response = null;
        try {
            response = HttpClientUtil.request(url);
        } catch (CrawlException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }
}
