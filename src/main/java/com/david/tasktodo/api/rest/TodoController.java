package com.david.tasktodo.api.rest;

import com.david.tasktodo.domain.*;
import com.david.tasktodo.service.TodoService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.Date;
import java.util.function.Function;

@RestController
@RequestMapping(value = "/todo")
@Api(tags = {"todo"}, description = "To Do List endpoints")
public class TodoController  extends AbstractRestHandler {

    @Autowired
    private TodoService todoService;

    @RequestMapping(value = "",
            method = RequestMethod.POST,
            consumes = {"application/json"},
            produces = {"application/json"})
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "Create a todo resource.", response = ToDoItem.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK",  response = ToDoItem.class),
            @ApiResponse(code = 400,message = "Validation error", response = ToDoItemValidationError.class)})
    public ResponseEntity<?> createTodo(@Valid @RequestBody ToDoItemAddRequest body,
                                     HttpServletRequest request, HttpServletResponse response) {
        ToDoItem toDoItem = this.todoService.createTodo(toDoItemAddRequestToToDoItem.apply(body));
        return new ResponseEntity<>(toDoItem, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/{id}",
            method = RequestMethod.GET,
            produces = {"application/json"})
    @ResponseStatus(HttpStatus.FOUND)
    @ApiOperation(value = "Get a single todo.",
            notes = "You have to provide a valid todo ID.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK",  response = ToDoItem.class),
            @ApiResponse(code = 400,message = "Validation error", response = ToDoItemValidationError.class),
            @ApiResponse(code = 404,message = "Not Found Error", response = ToDoItemNotFoundError.class)})
    public ResponseEntity<?> getToDoItem(@ApiParam(value = "The ID of the todo.", required = true)
                   @PathVariable("id") Long id,
                   HttpServletRequest request, HttpServletResponse response) throws Exception {
        ToDoItem toDoItem = this.todoService.getToDoItem(id);
        checkResourceFound(toDoItem);
        return new ResponseEntity<>(toDoItem, HttpStatus.FOUND);
    }

    @RequestMapping(value = "/{id}",
            method = RequestMethod.PATCH,
            consumes = {"application/json"},
            produces = {"application/json"})
    @ResponseStatus(HttpStatus.ACCEPTED)
    @ApiOperation(value = "Update a todo resource.",
            notes = "You have to provide a valid todo ID in the URL and in the payload. The ID attribute can not be updated.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK",  response = ToDoItem.class),
            @ApiResponse(code = 400,message = "Validation error", response = ToDoItemValidationError.class),
            @ApiResponse(code = 404,message = "Not Found Error", response = ToDoItemNotFoundError.class)})
    public ResponseEntity<?> updateTodo(@ApiParam(value = "The ID of the existing todo resource.", required = true)
                            @PathVariable("id") Long id,
                            @Valid @RequestBody ToDoItemUpdateRequest body,
                            HttpServletRequest request, HttpServletResponse response) {
        ToDoItem toDoItem = this.todoService.updatePartialToDoItem(body, id);
        return new ResponseEntity<>(toDoItem, HttpStatus.ACCEPTED);
    }

    private Function<ToDoItemAddRequest, ToDoItem> toDoItemAddRequestToToDoItem = TodoController::apply;

    private static ToDoItem apply(ToDoItemAddRequest toDoItemAddRequest) {
        return new ToDoItem(toDoItemAddRequest.getText(), false, new Date().toString());
    }
}
