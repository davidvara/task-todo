package com.david.tasktodo.service;

import com.david.tasktodo.domain.ToDoItem;
import com.david.tasktodo.domain.ToDoItemUpdateRequest;
import com.david.tasktodo.repositories.ToDoItemRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.actuate.metrics.CounterService;
import org.springframework.stereotype.Component;

@Component
public class TodoServiceImpl implements TodoService{

    private static final Logger log = LoggerFactory.getLogger(TodoServiceImpl.class);

    @Autowired
    private ToDoItemRepository toDoItemRepository;


    @Qualifier("counterService")
    @Autowired
    CounterService counterService;


    public ToDoItem createTodo(ToDoItem todo) {
        return saveAndFlush(todo);
    }

    public ToDoItem getToDoItem(Long id) {
        return toDoItemRepository.findOne(id);
    }

    public void updateToDoItem(ToDoItem toDoItem) {
        saveAndFlush(toDoItem);
    }

    public ToDoItem updatePartialToDoItem(ToDoItemUpdateRequest toDoItemUpdateRequest, Long id) {
        ToDoItem toDoItem = toDoItemRepository.findOne(id);
        toDoItem.setText(toDoItemUpdateRequest.getText());
        toDoItem.setCompleted(toDoItemUpdateRequest.isCompleted());
        return toDoItemRepository.save(toDoItem);
    }

    private ToDoItem saveAndFlush(ToDoItem toDoItem) {
        return toDoItemRepository.saveAndFlush(toDoItem);
    }
}
