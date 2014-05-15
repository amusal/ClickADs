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

import java.util.*;

/**
 * Created by Johnson.Liu
 * <p/>
 * Author: Johnson.Liu
 * Date: 2014/05/15
 * Time: 13:46
 */
public class NumberUtil {

    private static final Random rand = new Random();

    public static int randomInteger(int begin, int end) {
        if (begin >= end) {
            throw new IllegalArgumentException(String.format("End number %d should greater than begin number %s", end));
        }
        return begin + rand.nextInt(end - begin);
    }

    public static int randomInteger(int size) {
        return randomInteger(0, size);
    }

    public static int[] randomOrderedIntegers(int size) {
        return randomOrderedIntegers(0, size, size);
    }

    public static int[] randomOrderedIntegers(int regionStart, int regionEnd, int num) {
        int offset = regionStart;
        int diff = regionEnd - regionStart;
        int realNum = Math.min(num, diff);
        // initialize to be select
        List<Integer> options = new ArrayList<Integer>();
        for (int i=0; i<diff; i++) {
            options.add(i);
        }
        int[] orders = new int[realNum];
        for (int i=0; i<realNum; i++) {
            int idx = randomInteger(options.size());
            int select = options.remove(idx);
            orders[i] = select + offset;
        }
        return orders;
    }
}
