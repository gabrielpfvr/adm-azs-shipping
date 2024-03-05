package br.com.azship.admazsshipping.api.exception;

import br.com.azship.admazsshipping.infrastructure.exception.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@Slf4j
@RestControllerAdvice
public class ExceptionHandlingController {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({MethodArgumentNotValidException.class, BindException.class})
    public List<ErrorMessage> handleBeanValidationException(Exception ex) {
        log.error(ex.getMessage());
        BindingResult result;
        if (ex instanceof MethodArgumentNotValidException res) {
            result = res.getBindingResult();
        } else {
            result = ((BindException) ex).getBindingResult();
        }

        return result.getFieldErrors()
            .stream()
            .map(err ->
                new ErrorMessage(
                    String.format("The field %s %s", err.getField(), err.getDefaultMessage()),
                    err.getField())
            )
            .toList();
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    public ErrorMessage handleNotFoundException(NotFoundException ex) {
        log.error(ex.getMessage());
        return new ErrorMessage(ex.getMessage());
    }
}
