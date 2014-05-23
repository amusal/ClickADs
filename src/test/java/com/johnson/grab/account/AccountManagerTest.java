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


import org.junit.Assert;
import org.junit.Test;

/**
 * Created by Johnson.Liu
 * <p/>
 * Author: Johnson.Liu
 * Date: 2014/04/14
 * Time: 11:39
 */
public class AccountManagerTest {

    @Test
    public void testGetAvailableAccouts() {
        Account[] accounts = AccountManager.getAllAccounts();
        Assert.assertNotNull(accounts);
        Assert.assertTrue("Account length should great than 0", accounts.length > 0);
    }

    @Test
    public void testGetAccountsByRandom() {
        for (int i=0; i<10; i++) {
            Account[] accounts = AccountManager.getAllAccountsByRandom();
            Assert.assertNotNull(accounts);
            Assert.assertTrue(accounts.length > 0);
            printAccounts(accounts);
        }
    }

    private void printAccounts(Account[] accounts) {
        for (Account account : accounts) {
            System.out.print(account + "; ");
        }
        System.out.println();
    }
}
