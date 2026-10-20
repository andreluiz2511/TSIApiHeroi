
package com.andre.apiheroi.excecao;

import java.util.Date;
import java.util.stream.Collectors;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class ExceptionHandlerModel extends ResponseEntityExceptionHandler {

    @ExceptionHandler(notFoundException.class)
    public ResponseEntity<exceptionCustomModel> naoEncontrado(
            WebRequest requisicao,
            Exception excecao) {

        exceptionCustomModel retorno = new exceptionCustomModel(
                new Date(),
                excecao.getMessage(),
                requisicao.getDescription(false)
        );

        return new ResponseEntity<>(retorno, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<exceptionCustomModel> argumentoInvalido(
            IllegalArgumentException excecao,
            WebRequest requisicao) {

        exceptionCustomModel retorno = new exceptionCustomModel(
                new Date(),
                excecao.getMessage(),
                requisicao.getDescription(false)
        );

        return new ResponseEntity<>(retorno, HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException excecao,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest requisicao) {

        String mensagens = excecao.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(FieldError::getDefaultMessage)
                .distinct()
                .collect(Collectors.joining("; "));

        exceptionCustomModel retorno = new exceptionCustomModel(
                new Date(),
                mensagens,
                requisicao.getDescription(false)
        );

        return new ResponseEntity<>(
                retorno,
                headers,
                HttpStatus.BAD_REQUEST
        );
    }
}
