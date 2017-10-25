package com.david.tasktodo.domain;


import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.validator.constraints.Length;
import org.springframework.validation.annotation.Validated;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name = "toDoItem")
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@DynamicUpdate
@Validated
public class ToDoItem {
    @Id
    @GeneratedValue()
    @NotNull(groups = Existing.class)
    @Null(groups = New.class)
    @ApiModelProperty(example = "42", value = "", position = 0)
    private long id;

    @Column
    @NotNull(groups = {Existing.class, New.class})
    @Length(
            min=1,
            max=50,
            message = "text must between 1 and 50 characters",
            groups = {Existing.class, New.class}
    )
    @ApiModelProperty(example = "Uulwi ifis halahs gag erh'ongg w'ssh.", required = true, value = "", position = 1)
    private String text;

    @Column
    @ApiModelProperty(example = "false", value = "", position = 2)
    private boolean isCompleted;

    @Column
    @ApiModelProperty(value = "", position = 3)
    private String createdAt;

    public ToDoItem() {
    }

    public ToDoItem(long id, String text, boolean isCompleted, String createdAt) {
        this.id = id;
        this.text = text;
        this.isCompleted = isCompleted;
        this.createdAt = createdAt;
    }

    public ToDoItem(String text, boolean isCompleted, String createdAt) {
        this.text = text;
        this.isCompleted = isCompleted;
        this.createdAt = createdAt;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ToDoItem)) return false;

        ToDoItem todo = (ToDoItem) o;

        if (id != todo.id) return false;
        if (isCompleted != todo.isCompleted) return false;
        return (text != null ? text.equals(todo.text) : todo.text == null) && (createdAt != null ? createdAt.equals(todo.createdAt) : todo.createdAt == null);
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (text != null ? text.hashCode() : 0);
        result = 31 * result + (isCompleted ? 1 : 0);
        result = 31 * result + (createdAt != null ? createdAt.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Todo{" +
                "id=" + id +
                ", text='" + text + '\'' +
                ", isCompleted=" + isCompleted +
                ", createdAt='" + createdAt + '\'' +
                '}';
    }

    public interface Existing {
    }

    public interface New {
    }
}



