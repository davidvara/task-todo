package com.david.tasktodo.api.rest;

import com.david.tasktodo.domain.*;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

public interface TodoControl {
    @RequestMapping(value = "",
            method = RequestMethod.POST,
            consumes = {"application/json"},
            produces = {"application/json"})
    @ApiOperation(value = "Create a todo resource.", response = ToDoItem.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK",  response = ToDoItem.class),
            @ApiResponse(code = 400,message = "Validation error", response = ToDoItemValidationError.class)})
    ResponseEntity<?> createTodo(@Valid @RequestBody ToDoItemAddRequest body,
                                        HttpServletRequest request, HttpServletResponse response, UriComponentsBuilder builder);

    @RequestMapping(value = "/{id}",
            method = RequestMethod.GET,
            produces = {"application/json"})
    @ApiOperation(value = "Get a single todo.",
            notes = "You have to provide a valid todo ID.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK",  response = ToDoItem.class),
            @ApiResponse(code = 400,message = "Validation error", response = ToDoItemValidationError.class),
            @ApiResponse(code = 404,message = "Not Found Error", response = ToDoItemNotFoundError.class)})
    ResponseEntity<?> getToDoItem(@ApiParam(value = "The ID of the todo.", required = true)
                                         @PathVariable("id") Long id,
                                         HttpServletRequest request, HttpServletResponse response, UriComponentsBuilder builder);

    @RequestMapping(value = "/{id}",
            method = RequestMethod.PATCH,
            consumes = {"application/json"},
            produces = {"application/json"})
    @ApiOperation(value = "Update a todo resource.",
            notes = "You have to provide a valid todo ID in the URL and in the payload. The ID attribute can not be updated.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK",  response = ToDoItem.class),
            @ApiResponse(code = 400,message = "Validation error", response = ToDoItemValidationError.class),
            @ApiResponse(code = 404,message = "Not Found Error", response = ToDoItemNotFoundError.class)})
    ResponseEntity<?> updateTodo(@ApiParam(value = "The ID of the existing todo resource.", required = true)
                                        @PathVariable("id") Long id,
                                        @Valid @RequestBody ToDoItemUpdateRequest body,
                                        HttpServletRequest request, HttpServletResponse response);
}
