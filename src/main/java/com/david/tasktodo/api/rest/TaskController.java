package com.david.tasktodo.api.rest;

import com.david.tasktodo.domain.BalanceTestResult;
import com.david.tasktodo.domain.ToDoItemValidationError;
import com.david.tasktodo.service.TaskService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping(value = "/tasks")
@Api(tags = {"tasks"}, description = "General algorithmic tasks")
public class TaskController extends AbstractRestHandler implements TaskControl {

    @Autowired
    private TaskService taskService;

    @RequestMapping(value = "/validateBrackets",
            method = RequestMethod.GET,
            consumes = {"application/json"},
            produces = {"application/json"})
    @ApiOperation(value = "Input string (max length 50)", response = BalanceTestResult.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK",  response = BalanceTestResult.class),
            @ApiResponse(code = 400,message = "Validation error", response = ToDoItemValidationError.class)})
    public ResponseEntity<?> createTodo(@RequestParam String input,
                                        HttpServletRequest request, HttpServletResponse response, UriComponentsBuilder builder) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentLength(request.getContentLength());
        headers.setLocation(builder.path( request.getServletPath()).build().toUri());
        return new ResponseEntity<>(taskService.isBalanced(input), headers, HttpStatus.OK);
    }
}
