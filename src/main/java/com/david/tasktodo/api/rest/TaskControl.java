package com.david.tasktodo.api.rest;

import com.david.tasktodo.domain.BalanceTestResult;
import com.david.tasktodo.domain.ToDoItemValidationError;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Api(tags = {"todo"}, description = "To Do List endpoints")
public interface TaskControl {
    
    @RequestMapping(value = "/validateBrackets",
            method = RequestMethod.GET,
            consumes = {"application/json"},
            produces = {"application/json"})
    @ApiOperation(value = "Input string (max length 50)", response = BalanceTestResult.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK",  response = BalanceTestResult.class),
            @ApiResponse(code = 400,message = "Validation error", response = ToDoItemValidationError.class)})
    ResponseEntity<?> createTodo(@RequestParam String input,
                                        HttpServletRequest request, HttpServletResponse response, UriComponentsBuilder builder);
}
