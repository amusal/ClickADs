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

import java.io.*;

/**
 * Created by Johnson.Liu
 * <p/>
 * Author: Johnson.Liu
 * Date: 2014/05/15
 * Time: 08:52
 */
public class FileUtil {

    public static final String CLASSPATH_PREFIX = "classpath:";

    public static String readFile(String file) throws IOException {
        if (file.startsWith(CLASSPATH_PREFIX)) {
            String filePath = file.substring(CLASSPATH_PREFIX.length()).trim();
            System.out.println("Read file by classpath: " + filePath);
            return readFileByClassPath(filePath);
        } else {
            System.out.println("Read file by path: " + file);
            return readFile(new File(file));
        }
    }

    public static String readFile(File file) throws IOException {
        return readFromInputStream(new FileInputStream(file));
    }

    public static String readFromInputStream(InputStream inputStream) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder text = new StringBuilder();
        String line;
        while ((line=reader.readLine()) != null) {
            text.append(line).append('\n');
        }
        reader.close();
        return text.toString();
    }

    public static String readFileByClassPath(String filename) throws IOException {
        InputStream input = FileUtil.class.getClassLoader().getResourceAsStream(filename);
        return readFromInputStream(input);
    }
}
