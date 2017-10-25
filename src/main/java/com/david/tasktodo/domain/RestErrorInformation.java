package com.david.tasktodo.domain;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class RestErrorInformation {
    public final String detail;
    public final String message;

    public RestErrorInformation(Exception ex, String detail) {
        this.message = ex.getLocalizedMessage();
        this.detail = detail;
    }
}
