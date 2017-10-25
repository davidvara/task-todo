package com.david.tasktodo.domain;

import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.Length;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@Validated
public class ToDoItemAddRequest {

    @NotNull
    //@Length(min = 1, max = 50)
    @Length(
            min=1,
            max=50,
            message = "text must between 1 and 50 characters"
    )
    @ApiModelProperty(example = "Uulwi ifis halahs gag erh'ongg w'ssh.", value = "")
    private String text;

    public ToDoItemAddRequest() {
    }

    public ToDoItemAddRequest(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
