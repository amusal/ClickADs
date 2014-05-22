/*
 * Copyright 2013 Future TV, Inc.
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
package com.johnson.grab.tmp;

/**
 * Created by Johnson.Liu
 *
 * This class defines a crawling exception
 * <p/>
 * Author: Johnson.Liu
 * Date: 2013/10/22
 * Time: 19:20
 */
public class CrawlException extends Exception {

    public CrawlException(String message) {
        super(message);
    }

    public CrawlException(String message, Throwable t) {
        super(message, t);
    }
}
