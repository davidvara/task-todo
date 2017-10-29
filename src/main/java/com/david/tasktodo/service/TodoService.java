package com.david.tasktodo.service;

import com.david.tasktodo.domain.ToDoItem;
import com.david.tasktodo.domain.ToDoItemUpdateRequest;

public interface TodoService {
    ToDoItem createTodo(ToDoItem todo);

    ToDoItem getToDoItem(Long id);

    void updateToDoItem(ToDoItem toDoItem);

    ToDoItem updatePartialToDoItem(ToDoItemUpdateRequest toDoItemUpdateRequest, Long id);
}
