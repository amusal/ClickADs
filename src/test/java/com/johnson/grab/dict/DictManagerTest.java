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
package com.johnson.grab.dict;

import com.johnson.grab.TestConstants;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

/**
 * Created by Johnson.Liu
 * <p/>
 * Author: Johnson.Liu
 * Date: 2014/05/15
 * Time: 11:30
 */
public class DictManagerTest {

    @Test
    public void testLoadDict() {
        try {
            List<String> dicts = DictManager.loadDict(TestConstants.KEYWORD_FILE);
            Assert.assertNotNull(dicts);
            Assert.assertFalse(dicts.isEmpty());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testRandomKeywords() {
        for (int i=0, len=DictManager.holder.getKeywordsCount(); i<len; i++) {
            String[] keywords = DictManager.randomKeywords(i + 1);
            Assert.assertNotNull(keywords);
            Assert.assertEquals(i+1, keywords.length);
//            for (String keyword : keywords.txt) {
//                System.out.print(keyword + '\b');
//            }
//            System.out.println();
        }
    }
}
