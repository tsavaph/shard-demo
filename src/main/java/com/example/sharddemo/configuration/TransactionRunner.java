package com.example.sharddemo.configuration;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


@Component
public class TransactionRunner {

    @Transactional(transactionManager = "defaultTransactionManager")
    public void processDefaultTransaction(Runnable runnable) {
        process(runnable);
    }

    @Transactional(transactionManager = "oneTransactionManager")
    public void processOneTransaction(Runnable runnable) {
        process(runnable);
    }

    @Transactional(transactionManager = "twoTransactionManager")
    public void processTwoTransaction(Runnable runnable) {
        process(runnable);
    }

    private void process(Runnable runnable) {
        runnable.run();
    }

}
