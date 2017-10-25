package com.david.tasktodo.api.rest;


import com.david.tasktodo.domain.BalanceTestResult;
import com.david.tasktodo.domain.ToDoItem;
import com.david.tasktodo.domain.ToDoItemAddRequest;
import com.david.tasktodo.domain.ToDoItemValidationError;
import com.david.tasktodo.service.TaskService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@RequestMapping(value = "/tasks")
@Api(tags = {"tasks"}, description = "General algorithmic tasks")
public class TaskController  extends AbstractRestHandler {

    @Autowired
    private TaskService taskService;

    @RequestMapping(value = "/validateBrackets",
            method = RequestMethod.GET,
            consumes = {"application/json"},
            produces = {"application/json"})
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Input string (max length 50)", response = BalanceTestResult.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK",  response = BalanceTestResult.class),
            @ApiResponse(code = 400,message = "Validation error", response = ToDoItemValidationError.class)})
    public ResponseEntity<?> createTodo(@RequestParam String input,
                                        HttpServletRequest request, HttpServletResponse response) {
        return new ResponseEntity<>(taskService.isBalanced(input),HttpStatus.OK);
    }
}
