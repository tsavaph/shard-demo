package com.example.sharddemo.configuration;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


@Component
public class TransactionRunner {

    @Transactional(transactionManager = "defaultTransactionManager")
    public void runDefaultTransaction(Runnable runnable) {
        runnable.run();
    }

    @Transactional(transactionManager = "oneTransactionManager")
    public void runOneTransaction(Runnable runnable) {
        runnable.run();
    }

    @Transactional(transactionManager = "twoTransactionManager")
    public void runTwoTransaction(Runnable runnable) {
        runnable.run();
    }

}
