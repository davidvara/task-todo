package com.david.tasktodo.api.rest;

import com.david.tasktodo.domain.RestErrorInformation;
import com.david.tasktodo.domain.ToDoItemNotFoundError;
import com.david.tasktodo.domain.ToDoItemValidationError;
import com.david.tasktodo.exception.DataFormatException;
import com.david.tasktodo.exception.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.servlet.http.HttpServletResponse;

@ControllerAdvice(basePackages = {"com.david.tasktodo.api.rest"} )
public abstract class AbstractRestHandler implements ApplicationEventPublisherAware {

    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private ApplicationEventPublisher eventPublisher;

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public ResponseEntity<?> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        log.warn("Method Argument not Valid Exception : " + ex.getMessage());
        return new ResponseEntity<>(createToDoItemValidationError(ex), HttpStatus.BAD_REQUEST);
    }


    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseBody
    public ResponseEntity<?> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex, WebRequest request, HttpServletResponse response) {
        log.warn("Method Argument Type Mismatch Exception to RestResponse : " + ex.getMessage());
        return new ResponseEntity<>(createToDoItemValidationError(ex), HttpStatus.BAD_REQUEST);
    }



    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(DataFormatException.class)
    @ResponseBody
    public ResponseEntity<?> handleDataStoreException(DataFormatException ex, WebRequest request, HttpServletResponse response) {
        log.warn("Converting Data Store exception to RestResponse : " + ex.getMessage());
        return new ResponseEntity<>(createToDoItemValidationError(ex), HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(RuntimeException.class)
    @ResponseBody
    public ResponseEntity<?> handleRuntimeException(RuntimeException ex, WebRequest request, HttpServletResponse response) {
        log.warn("Generic Runtime exception to RestResponse : " + ex.getMessage());

        RestErrorInformation restErrorInformation = new RestErrorInformation(ex, "Generic Runtime Exception" );
        return new ResponseEntity<>(restErrorInformation, HttpStatus.BAD_REQUEST);

    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HttpClientErrorException.class)
    @ResponseBody
    public ResponseEntity<?> handleHttpClientErrorException(HttpClientErrorException ex, WebRequest request, HttpServletResponse response) {
        log.error("HttpClientErrorException handler:" + ex.getMessage());

        //TODO : to be debugged and tested
        RestErrorInformation restErrorInformation = new RestErrorInformation(ex, "Generic Runtime Exception" );
        return new ResponseEntity<>(restErrorInformation, HttpStatus.BAD_REQUEST);
    }


    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseBody
    public ResponseEntity<?> handleResourceNotFoundException(ResourceNotFoundException ex, WebRequest request, HttpServletResponse response) {
        log.error("ResourceNotFoundException handler:" + ex.getMessage());

        ToDoItemNotFoundError.Details1 details1 = new ToDoItemNotFoundError.Details1();
        details1.setMessage(ex.getMessage());
        ToDoItemNotFoundError toDoItemNotFoundError = new ToDoItemNotFoundError(details1);
        return new ResponseEntity<>(toDoItemNotFoundError, HttpStatus.NOT_FOUND);
    }

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.eventPublisher = applicationEventPublisher;
    }

    static <T> T checkResourceFound(final T resource, final Long id) {
        if (resource == null) {
            throw new ResourceNotFoundException(String.format("Item with %s not found", id));
        }
        return resource;
    }


    private ToDoItemValidationError createToDoItemValidationError(Exception ex) {
        if (ex instanceof  MethodArgumentNotValidException) {
            return getToDoItemValidationError((MethodArgumentNotValidException) ex);
        }

        if (ex instanceof  MethodArgumentTypeMismatchException) {
            return getToDoItemValidationError((MethodArgumentTypeMismatchException) ex);
        }

        if (ex instanceof  DataFormatException) {
            //TODO : to be implemented
            return new ToDoItemValidationError();
        }

        //TODO : to be implemented
        return new ToDoItemValidationError();
    }

    private ToDoItemValidationError getToDoItemValidationError(MethodArgumentNotValidException ex) {
        if (ex.getBindingResult().getFieldError() != null
                && ex.getBindingResult().getFieldError().getRejectedValue() != null) {
            FieldError fieldError = ex.getBindingResult().getFieldError();
            return createToDoItemValidationError("params", fieldError.getField(),  fieldError.getDefaultMessage(), fieldError.getRejectedValue().toString());
        }
        return createToDoItemValidationError("params", "parameter", "An error has occurred", "value");
    }

    private ToDoItemValidationError getToDoItemValidationError(MethodArgumentTypeMismatchException ex) {
        if (ex.getName() != null && ex.getValue() != null) {
            String field = ex.getName();
            String value = ex.getValue().toString();

            String message = String.format("The value provided '%s' mismatches the type required for the argument %s", value, field);
            return createToDoItemValidationError("params", ex.getName(),message, ex.getValue().toString());
        }

        return createToDoItemValidationError("params", "parameter", "An error has occurred", "value");
    }


    private ToDoItemValidationError createToDoItemValidationError(String location,String field, String message, String rejectedValue) {
        ToDoItemValidationError.Details details = new ToDoItemValidationError.Details(location, field, message, rejectedValue);
        return new ToDoItemValidationError(details);
    }
}
