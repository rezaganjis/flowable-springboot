package com.rgn.bpms.service;

import org.flowable.engine.delegate.DelegateExecution;
import org.flowable.engine.delegate.JavaDelegate;

public class LoanService implements JavaDelegate {
    public void execute(DelegateExecution execution) {

        System.out.println("doing something in loan");
    }
}