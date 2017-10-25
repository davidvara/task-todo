package com.david.tasktodo.domain;

import io.swagger.annotations.ApiModelProperty;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Task {
    @ApiModelProperty(example = "42", value = "")
    private String input;
    @ApiModelProperty(example = "true", value = "")
    private boolean isBalanced;

    public Task() {
    }

    public Task(String input, boolean isBalanced) {
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

    @Override
    public String toString() {
        return "Task{" +
                "input='" + input + '\'' +
                ", isBalanced=" + isBalanced +
                '}';
    }
}
