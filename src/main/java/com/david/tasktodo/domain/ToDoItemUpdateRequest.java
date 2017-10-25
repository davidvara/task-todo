package com.david.tasktodo.domain;

import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ToDoItemUpdateRequest {
    @NotNull
    @Length(
            min=1,
            max=50,
            message = "text must between 1 and 50 characters",
            groups = {ToDoItem.Existing.class, ToDoItem.New.class}
    )
    @ApiModelProperty(example = "Uulwi ifis halahs gag erh'ongg w'ssh.", value = "")
    private String text;

    @NotNull
    @ApiModelProperty(example = "false", value = "")
    private boolean isCompleted;

    public ToDoItemUpdateRequest() {
    }

    public ToDoItemUpdateRequest(String text, boolean isCompleted) {
        this.text = text;
        this.isCompleted = isCompleted;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }
}
