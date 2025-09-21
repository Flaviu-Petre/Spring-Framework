package exception;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import response.ErrorResponse;
import response.ValidationErrorResponse;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @Autowired
    private MessageSource messageSource;

    @ExceptionHandler(CustomerNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleCustomerNotFoundException(
            CustomerNotFoundException ex, WebRequest request) {

        Locale locale = LocaleContextHolder.getLocale();
        String localizedMessage = messageSource.getMessage(
                "customer.notfound",
                new Object[]{ex.getCustomerId()},
                ex.getMessage(),
                locale
        );

        ErrorResponse errorResponse = new ErrorResponse(
                localizedMessage,
                HttpStatus.NOT_FOUND.value(),
                messageSource.getMessage("customer.notfound.type", null, "Customer Not Found", locale),
                request.getDescription(false).replace("uri=", "")
        );

        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationErrorResponse> handleValidationExceptions(
            MethodArgumentNotValidException ex, WebRequest request) {

        Locale locale = LocaleContextHolder.getLocale();
        Map<String, String> validationErrors = new HashMap<>();

        // Extract field validation errors with localized messages
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            validationErrors.put(fieldName, errorMessage);
        });

        String localizedValidationMessage = messageSource.getMessage(
                "validation.failed",
                null,
                "Validation failed for one or more fields",
                locale
        );

        String localizedErrorType = messageSource.getMessage(
                "validation.error",
                null,
                "Validation Error",
                locale
        );

        ValidationErrorResponse errorResponse = new ValidationErrorResponse(
                localizedValidationMessage,
                HttpStatus.BAD_REQUEST.value(),
                localizedErrorType,
                request.getDescription(false).replace("uri=", ""),
                validationErrors
        );

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGlobalException(
            Exception ex, WebRequest request) {

        Locale locale = LocaleContextHolder.getLocale();
        String localizedMessage = messageSource.getMessage(
                "error.internal",
                new Object[]{ex.getMessage()},
                "An unexpected error occurred: " + ex.getMessage(),
                locale
        );

        String localizedErrorType = messageSource.getMessage(
                "error.internal.type",
                null,
                "Internal Server Error",
                locale
        );

        ErrorResponse errorResponse = new ErrorResponse(
                localizedMessage,
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                localizedErrorType,
                request.getDescription(false).replace("uri=", "")
        );

        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}