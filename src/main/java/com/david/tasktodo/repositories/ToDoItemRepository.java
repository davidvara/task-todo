package com.david.tasktodo.repositories;

import com.david.tasktodo.domain.ToDoItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ToDoItemRepository  extends JpaRepository<ToDoItem, Long> {
}

