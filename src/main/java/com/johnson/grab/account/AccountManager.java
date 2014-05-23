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
package com.johnson.grab.account;

import com.johnson.grab.utils.FileUtil;
import com.johnson.grab.utils.Log;
import com.johnson.grab.utils.RandomUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Johnson.Liu
 * <p/>
 * Author: Johnson.Liu
 * Date: 2014/04/14
 * Time: 11:15
 */
public class AccountManager {

    public static final String ACCOUNT_FILE = "classpath: accounts.txt";
    public static final Holder holder = new Holder();

    static {
        try {
            init();
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    private static final class Holder {
        private Holder() {}

        private final List<Account> accounts = new ArrayList<Account>();

        public int countAccounts() {
            return accounts.size();
        }

        public Account[] getAllAccounts() {
            return accounts.toArray(new Account[0]);
        }
    }

    private static void init() throws IOException {
        String text = FileUtil.readFile(ACCOUNT_FILE);
        String[] lines = text.split("[\\s]+");
        for (int i=0; i<lines.length; i++) {
            if (!lines[i].startsWith("#")) {
                holder.accounts.add(new Account(lines[i]));
            }
        }
        Log.debug("账号加载完毕，共加载账号" + holder.accounts.size() + "个");
    }

    /**
     * Return all accounts
     * @return
     */
    public static Account[] getAllAccounts() {
        return holder.getAllAccounts();
    }

    public static Account[] getAllAccountsByRandom() {
        int[] order = RandomUtil.randomOrderedIntegers(holder.countAccounts());
        Account[] accounts = new Account[order.length];
        for (int i=0, len=order.length; i<len; i++) {
            accounts[i] = holder.accounts.get(order[i]);
        }
        return accounts;
    }
}
