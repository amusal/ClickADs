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

import com.johnson.grab.Browsable;
import com.johnson.grab.Closable;
import org.apache.http.HttpHost;
import org.apache.http.client.HttpClient;

/**
 * Created by Johnson.Liu
 * <p/>
 * Author: Johnson.Liu
 * Date: 2014/04/03
 * Time: 11:53
 */
public abstract class BasicHttpClientBrowser implements Browsable<Object> {

    private HttpClient client;

    private HttpHost proxy;

    public BasicHttpClientBrowser(HttpClient client) {
        this.client = client;
    }
}