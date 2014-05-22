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
package com.johnson.grab.utils;

/**
 * Created by Johnson.Liu
 * <p/>
 * Author: Johnson.Liu
 * Date: 2014/05/18
 * Time: 18:11
 */
public class Log {

    private static final String FORMAT = "[%s]: %s";

    private static final boolean debug = true;

    public static enum TYPE {
        SUCCESS, FAIL, INFO;
    }

    public static void info(TYPE type, String detail) {
        System.out.println(String.format(FORMAT, type, detail));
    }

    public static void debug(String detail) {
        if (debug) {
            System.out.println("[DEBUG]: " + detail);
        }
    }
}
