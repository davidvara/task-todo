package com.david.tasktodo.api.rest;

import com.david.tasktodo.domain.*;
import com.david.tasktodo.exception.DataFormatException;
import com.david.tasktodo.exception.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@ControllerAdvice(basePackages = {"com.david.tasktodo.api.rest"} )
public abstract class AbstractRestHandler implements ApplicationEventPublisherAware {

    protected final Logger log = LoggerFactory.getLogger(this.getClass());
    protected ApplicationEventPublisher eventPublisher;

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public ResponseEntity<?> invalidInput(MethodArgumentNotValidException ex) {
        return new ResponseEntity<>(createErrorResponse(ex), HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(DataFormatException.class)
    @ResponseBody
    public ResponseEntity<?> handleDataStoreException(DataFormatException ex, WebRequest request, HttpServletResponse response) {
        log.warn("Converting Data Store exception to RestResponse : " + ex.getMessage());

        ToDoItemValidationError todoValidationError = new ToDoItemValidationError();

        return new ResponseEntity<Object>(todoValidationError, HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(RuntimeException.class)
    @ResponseBody
    public ResponseEntity<?> handleRuntimeException(RuntimeException ex, WebRequest request, HttpServletResponse response) {
        log.warn("Generic Runtime exception to RestResponse : " + ex.getMessage());

        RestErrorInformation restErrorInformation = new RestErrorInformation(ex, "Generic Runtime Exception" );

        return new ResponseEntity<>(restErrorInformation, HttpStatus.BAD_REQUEST);
    }



    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseBody
    public RestErrorInformation handleResourceNotFoundException(ResourceNotFoundException ex, WebRequest request, HttpServletResponse response) {
        log.error("ResourceNotFoundException handler:" + ex.getMessage());

        return new RestErrorInformation(ex, "Sorry, Resource not found.");
    }

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.eventPublisher = applicationEventPublisher;
    }

    public static <T> T checkResourceFound(final T resource) {
        if (resource == null) {
            throw new ResourceNotFoundException("resource not found");
        }
        return resource;
    }

    private CustomError createErrorResponse (MethodArgumentNotValidException ex) {

        Object object = ex.getBindingResult().getTarget();

        if(object instanceof ToDoItemAddRequest || object instanceof ToDoItemUpdateRequest) {
            return createToDoItemValidationError(ex);
        }

        return null;
    }

    private ToDoItemValidationError createToDoItemValidationError(MethodArgumentNotValidException ex) {

        ToDoItemValidationError.Details details = new ToDoItemValidationError.Details(
                "location",
                ex.getBindingResult().getFieldError().getField(),
                ex.getBindingResult().getFieldError().getDefaultMessage(),
                ex.getBindingResult().getFieldError().getRejectedValue().toString()
        );

        return new ToDoItemValidationError(details);
    }


}
