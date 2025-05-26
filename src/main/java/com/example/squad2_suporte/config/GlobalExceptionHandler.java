package com.example.squad2_suporte.config;

import com.example.squad2_suporte.config.exceptions.AmostraInvalidaException;
import com.example.squad2_suporte.config.exceptions.LoteInvalidoException;
import com.example.squad2_suporte.config.exceptions.RecursoNaoEncontradoException;
import com.example.squad2_suporte.config.exceptions.RequisicaoInvalidaException;
import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Hidden
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RecursoNaoEncontradoException.class)
    public ResponseEntity<RestErrorMessage> handleNotFound(RecursoNaoEncontradoException ex) {
        RestErrorMessage error = new RestErrorMessage(ex.getMessage(), HttpStatus.NOT_FOUND);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(RequisicaoInvalidaException.class)
    public ResponseEntity<RestErrorMessage> handleBadRequest(RequisicaoInvalidaException ex) {
        RestErrorMessage error = new RestErrorMessage(ex.getMessage(), HttpStatus.BAD_REQUEST);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(AmostraInvalidaException.class)
    public ResponseEntity<RestErrorMessage> handleAmostraInvalid(AmostraInvalidaException ex) {
        RestErrorMessage error = new RestErrorMessage(ex.getMessage(), HttpStatus.BAD_REQUEST);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(LoteInvalidoException.class)
    public ResponseEntity<RestErrorMessage> handleLoteInvalid(LoteInvalidoException ex) {
        RestErrorMessage error = new RestErrorMessage(ex.getMessage(), HttpStatus.BAD_REQUEST);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }
}
