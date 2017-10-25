package com.david.tasktodo.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@XmlRootElement
public class ToDoItemNotFoundError implements CustomError {
    @ApiModelProperty(example = "validation Error", value = "validation Error")
    private String name = "validation Error";

    @ApiModelProperty(value = "details1", dataType =
            "Map[string,com.david.tasktodo.domain.ToDoItemNotFoundError$Details1]")
    private Details1 details1;

    public ToDoItemNotFoundError() {
    }

    public ToDoItemNotFoundError(String name, Details1 details1) {
        this.name = name;
        this.details1 = details1;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Details1 getDetails1() {
        return details1;
    }

    public void setDetails1(Details1 details1) {
        this.details1 = details1;
    }

    @ApiModel(description = "details attribute")
    public static class Details1 implements Serializable {
        @ApiModelProperty(example = "Item with 9 not found", value = "")
        private String message;

        public Details1(String message) {
            this.message = message;
        }

        public Details1() {
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }
}
