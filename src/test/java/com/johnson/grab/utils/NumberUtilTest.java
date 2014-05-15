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

import org.junit.Assert;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Johnson.Liu
 * <p/>
 * Author: Johnson.Liu
 * Date: 2014/05/15
 * Time: 13:54
 */
public class NumberUtilTest {
    @Test
    public void testRandomInteger() {
        int begin = 5, end = 10;
        for (int i=0; i<100; i++) {
            int random = NumberUtil.randomInteger(begin, end);
            Assert.assertTrue(random >= begin && random < end);
        }
    }

    @Test
    public void testRandomOrderedIntegers() {
        int begin=20, end=300, size = 11;
        int[] orders = NumberUtil.randomOrderedIntegers(begin, end, size);
        Assert.assertNotNull(orders);
        Set<Integer> cache = new HashSet<Integer>();
        for (int i=0; i<orders.length; i++) {
            System.out.print(orders[i] + " ");
            Assert.assertTrue(orders[i] >= begin && orders[i] < end);
            Assert.assertTrue(cache.add(orders[i]));
        }
        System.out.println();
    }
}
