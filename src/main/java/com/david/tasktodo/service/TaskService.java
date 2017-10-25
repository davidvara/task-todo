package com.david.tasktodo.service;

import com.david.tasktodo.domain.BalanceTestResult;
import org.springframework.stereotype.Component;

import java.util.Stack;
import java.util.function.BiFunction;

@Component
public class TaskService {


    public BalanceTestResult isBalanced(String expr) {
      return checkBalancedParentesis(expr);
    }

    private BalanceTestResult checkBalancedParentesis(String expr)
    {
        if (expr.isEmpty())
            return createBalanceTestResult.apply(expr,true);

        Stack<Character> stack = new Stack<Character>();
        for (int i = 0; i < expr.length(); i++)
        {
            char current = expr.charAt(i);
            if (current == '{' || current == '(' || current == '[')
            {
                stack.push(current);
            }
            if (current == '}' || current == ')' || current == ']')
            {
                if (stack.isEmpty())
                    return createBalanceTestResult.apply(expr,false);
                char last = stack.peek();
                if (current == '}' && last == '{' || current == ')' && last == '(' || current == ']' && last == '[')
                    stack.pop();
                else
                    return createBalanceTestResult.apply(expr,false);
            }
        }
        return stack.isEmpty()? createBalanceTestResult.apply(expr,true):createBalanceTestResult.apply(expr,false);
    }

    BiFunction<String,Boolean, BalanceTestResult > createBalanceTestResult = TaskService::apply;

    private static BalanceTestResult apply(String s, Boolean b) {
        return new BalanceTestResult(s, b);
    }
}
