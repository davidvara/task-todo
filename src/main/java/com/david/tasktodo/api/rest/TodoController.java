package com.david.tasktodo.api.rest;

import com.david.tasktodo.domain.ToDoItem;
import com.david.tasktodo.domain.ToDoItemAddRequest;
import com.david.tasktodo.domain.ToDoItemUpdateRequest;
import com.david.tasktodo.service.TodoService;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.Date;
import java.util.function.Function;

@RestController
@RequestMapping(value = "/todo")
public class TodoController extends AbstractRestHandler implements TodoControl {

    @Autowired
    private TodoService todoService;

    public ResponseEntity<?> createTodo(@Valid @RequestBody ToDoItemAddRequest body,
                                     HttpServletRequest request, HttpServletResponse response, UriComponentsBuilder builder) {
        ToDoItem toDoItem = this.todoService.createTodo(toDoItemAddRequestToToDoItem.apply(body));
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentLength(request.getContentLength());
//        headers.setLocation(builder.path( request.getServletPath()).build().toUri());

        return new ResponseEntity<>(toDoItem, HttpStatus.OK);
    }

    public ResponseEntity<?> getToDoItem(@ApiParam(value = "The ID of the todo.", required = true)
                   @PathVariable("id") Long id,
                   HttpServletRequest request, HttpServletResponse response, UriComponentsBuilder builder) {
        ToDoItem toDoItem = this.todoService.getToDoItem(id);
        checkResourceFound(toDoItem, id);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentLength(request.getContentLength());
        headers.setLocation(builder.path( request.getServletPath()).build().toUri());
        return new ResponseEntity<>(toDoItem,headers, HttpStatus.OK);
    }

    public ResponseEntity<?> updateTodo(@ApiParam(value = "The ID of the existing todo resource.", required = true)
                            @PathVariable("id") Long id,
                            @Valid @RequestBody ToDoItemUpdateRequest body,
                            HttpServletRequest request, HttpServletResponse response) {
        ToDoItem toDoItem = this.todoService.updatePartialToDoItem(body, id);
        return new ResponseEntity<>(toDoItem, HttpStatus.OK);
    }

    private Function<ToDoItemAddRequest, ToDoItem> toDoItemAddRequestToToDoItem = TodoController::apply;

    private static ToDoItem apply(ToDoItemAddRequest toDoItemAddRequest) {
        return new ToDoItem(toDoItemAddRequest.getText(), false, new Date().toString());
    }
}
