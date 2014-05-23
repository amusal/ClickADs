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
import org.junit.Before;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.IOException;

/**
 * Created by Johnson.Liu
 * <p/>
 * Author: Johnson.Liu
 * Date: 2014/05/15
 * Time: 09:22
 */
public class FileUtilTest {

    private static final String HOSTS_LINUX = "/etc/hosts";
    private static final String HOSTS_WINDOWS = "C:\\Windows\\System32\\drivers\\etc\\hosts";
    private String hostFile;

    @Before
    public void setUp() {
        hostFile = OSUtil.getOS() == OSUtil.OS.WINDOWS ? HOSTS_WINDOWS : HOSTS_LINUX;
    }


    @Test
    public void testReadFromInputStream() {
        try {
            String text = FileUtil.readFromInputStream(new FileInputStream(hostFile));
            System.out.println("text: " + text);
            Assert.assertTrue(text != null && text.length() > 0);
        } catch (IOException e) {
            e.printStackTrace();
            Assert.assertTrue(false);
        }
    }

    @Test
    public void testReadFileByClassPath() {
        try {
            String text = FileUtil.readFileByClassPath("keywords.txt");
            System.out.println(text);
            Assert.assertNotNull(text);
            Assert.assertTrue(text.length() > 0);
        } catch (IOException e) {
            e.printStackTrace();
            Assert.assertFalse(true);
        }
    }

    @Test
    public void testReadFile() {
        String fileName = FileUtil.CLASSPATH_PREFIX + "keywords.txt";
        try {
            String text = FileUtil.readFile(fileName);
            System.out.println("text: " + text);
            Assert.assertNotNull(text);
            Assert.assertTrue(text.length() > 0);
        } catch (IOException e) {
            e.printStackTrace();
            Assert.assertFalse(true);
        }
    }
}
