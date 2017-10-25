package com.david.tasktodo.domain;

import io.swagger.annotations.ApiModelProperty;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class BalanceTestResult {

    @ApiModelProperty(example = "[(]", value = "")
    private String input;

    @ApiModelProperty(example = "false", value = "")
    private boolean isBalanced;

    public BalanceTestResult() {
    }

    public BalanceTestResult(String input, boolean isBalanced) {
        this.input = input;
        this.isBalanced = isBalanced;
    }

    public String getInput() {
        return input;
    }

    public void setInput(String input) {
        this.input = input;
    }

    public boolean isBalanced() {
        return isBalanced;
    }

    public void setBalanced(boolean balanced) {
        isBalanced = balanced;
    }
}
