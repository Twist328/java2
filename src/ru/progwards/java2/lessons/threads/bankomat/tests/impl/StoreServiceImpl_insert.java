package ru.progwards.java2.lessons.threads.bankomat.tests.impl;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import ru.progwards.java2.lessons.threads.bankomat.Store;
import ru.progwards.java2.lessons.threads.bankomat.model.Account;
import ru.progwards.java2.lessons.threads.bankomat.servise.StoreService;
import ru.progwards.java2.lessons.threads.bankomat.servise.impl.StoreServiceImpl;

import java.util.Date;
import java.util.Map;
import java.util.UUID;

public class StoreServiceImpl_insert {

    static StoreService service;
    static Map<String, Account> store;
    static String testKey;
    static Account testAccount;

    @BeforeClass
    public static void init() {
        Store s = new Store();
        store = s.getStore();
        service = new StoreServiceImpl(s);

        testAccount = new Account();
        testKey = UUID.randomUUID().toString();
        testAccount.setId(testKey);
        testAccount.setPin(2345);
        testAccount.setHolder("Account_for_tests");
        testAccount.setDate(new Date(System.currentTimeMillis()+365*24*3600*1000));
        testAccount.setAmount(Math.random()*1_000_000);
    }

    @Test
    public void insert_testAccount() {

        service.insert(testAccount);

        boolean exists = store.containsKey(testAccount.getId());
        Assert.assertTrue("test account was not added", exists);
    }

}
