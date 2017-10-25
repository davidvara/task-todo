package com.david.tasktodo.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

@XmlRootElement
public class ToDoItemValidationError implements CustomError {
    @ApiModelProperty(example = "validation Error", value = "")
    private String name = "validation Error";

    @ApiModelProperty(value = "details", dataType =
            "Map[string,com.david.tasktodo.domain.ToDoItemValidationError$Details]")
    private Details details;

    public ToDoItemValidationError() {
    }

    public ToDoItemValidationError(Details details) {
        this.details = details;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Details getDetails() {
        return details;
    }

    public void setDetails(Details details) {
        this.details = details;
    }

    @ApiModel(description = "details attributes")
    public static class Details implements Serializable {
        @ApiModelProperty(example = "params", value = "")
        private String location;
        @ApiModelProperty(example = "text", value = "")
        private String param;
        @ApiModelProperty(example = "Must be between 1 and 50 chars long", value = "")
        private String msg;
        @ApiModelProperty(example = "", value = "")
        private String value;

        public Details() {
        }

        public Details(String location, String param, String msg, String value) {
            this.location = location;
            this.param = param;
            this.msg = msg;
            this.value = value;
        }

        public String getLocation() {
            return location;
        }

        public void setLocation(String location) {
            this.location = location;
        }

        public String getParam() {
            return param;
        }

        public void setParam(String param) {
            this.param = param;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }
}
