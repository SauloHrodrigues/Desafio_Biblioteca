package com.db.projeto.gerenciamento_de_biblioteca.exception;

import com.db.projeto.gerenciamento_de_biblioteca.exception.autor.AutorComLivroNoBancoException;
import com.db.projeto.gerenciamento_de_biblioteca.exception.autor.AutorJaCadastradoException;
import com.db.projeto.gerenciamento_de_biblioteca.exception.autor.AutorNaoCadastradoException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(AutorJaCadastradoException.class)
    public ResponseEntity<Object> handlerAutorJaCadastrado(AutorJaCadastradoException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body("ERRO! " + ex.getMessage());
    }

    @ExceptionHandler(AutorNaoCadastradoException.class)
    public ResponseEntity<Object> handlerAutorNaoCadastrado(AutorNaoCadastradoException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("ERRO! " + ex.getMessage());
    }

    @ExceptionHandler(AutorComLivroNoBancoException.class)
    public ResponseEntity<Object> handlerAutorComLivroNoBancoException(AutorComLivroNoBancoException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body("ERRO! " + ex.getMessage());
    }

}