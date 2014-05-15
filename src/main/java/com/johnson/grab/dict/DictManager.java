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

import com.johnson.grab.utils.FileUtil;
import com.johnson.grab.utils.NumberUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Johnson.Liu
 * <p/>
 * Author: Johnson.Liu
 * Date: 2014/04/03
 * Time: 11:43
 */
public class DictManager {

    public static final Holder holder = new Holder();
    private static final String DEFAULT_KEYWORD_PATH = "classpath: keywords";

    public static final class Holder {
        private Holder() {}

        private List<String> dicts = new ArrayList<String>();

        public String[] allKeywords() {
            return dicts.toArray(new String[0]);
        }

        public int getKeywordsCount() {
            return dicts.size();
        }
    }

    static {
        try {
            System.out.println("Load dicts...");
            init();
            System.out.println("Dicts loaded successfully, total found " + holder.dicts.size() + " keywords.");
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    private static void init() throws IOException {
        holder.dicts.addAll(loadDict(DEFAULT_KEYWORD_PATH));
    }

    public static List<String> loadDict(String file) throws IOException {
        List<String> dicts = new ArrayList<String>();
        String text = FileUtil.readFile(file);
        String[] lines = text.split("[\n\r]{1,2}");
        for (String line : lines) {
            dicts.addAll(Arrays.asList(line.split("\\s+")));
        }
        return dicts;
    }

    public static String[] randomKeywords(int size) {
        int total = holder.dicts.size();
        int generateSize = Math.min(size, total);
        int[] orders = NumberUtil.randomOrderedIntegers(0, total, generateSize);
        String[] keywords = new String[orders.length];
        for (int i=0, len=orders.length; i<len; i++) {
            keywords[i] = holder.dicts.get(orders[i]);
        }
        return keywords;
    }
}
