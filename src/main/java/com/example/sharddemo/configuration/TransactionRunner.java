package com.example.sharddemo.configuration;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


@Component
public class TransactionRunner {

    @Transactional(transactionManager = "defaultTransactionManager")
    public void processDefaultTransaction(Runnable runnable) {
        runnable.run();
    }

    @Transactional(transactionManager = "oneTransactionManager")
    public void processOneTransaction(Runnable runnable) {
        runnable.run();
    }

    @Transactional(transactionManager = "twoTransactionManager")
    public void processTwoTransaction(Runnable runnable) {
        runnable.run();
    }

}
