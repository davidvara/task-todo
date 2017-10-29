package com.david.tasktodo.service;

import com.david.tasktodo.domain.BalanceTestResult;

public interface TaskService {
    BalanceTestResult isBalanced(String expr);
}
