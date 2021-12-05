package com.rgn.bpms.service;

import org.flowable.engine.delegate.DelegateExecution;
import org.flowable.engine.delegate.JavaDelegate;

public class DepositService implements JavaDelegate {
    @Override
    public void execute(DelegateExecution delegateExecution) {

        System.out.println("doing something in deposit");
    }
}
