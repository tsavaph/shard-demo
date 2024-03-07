package com.example.sharddemo.configuration;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class TransactionProcessor {

    private final TransactionRunner transactionRunner;

    public void process(Runnable runnable, DBSourceEnum dbSource) {
        if (dbSource == DBSourceEnum.SOURCE_ONE) {
            transactionRunner.runOneTransaction(runnable);
        } else if (dbSource == DBSourceEnum.SOURCE_TWO) {
            transactionRunner.runTwoTransaction(runnable);
        } else {
            transactionRunner.runDefaultTransaction(runnable);
        }
    }

}
