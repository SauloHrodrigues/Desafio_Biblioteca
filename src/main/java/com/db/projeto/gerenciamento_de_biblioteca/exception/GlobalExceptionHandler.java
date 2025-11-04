package com.db.projeto.gerenciamento_de_biblioteca.exception;

import com.db.projeto.gerenciamento_de_biblioteca.enuns.CategoriaDoLivro;
import com.db.projeto.gerenciamento_de_biblioteca.exception.autor.AutorComLivroNoBancoException;
import com.db.projeto.gerenciamento_de_biblioteca.exception.autor.AutorJaCadastradoException;
import com.db.projeto.gerenciamento_de_biblioteca.exception.autor.AutorNaoCadastradoException;
import com.db.projeto.gerenciamento_de_biblioteca.exception.autor.AutorNaoInformadoException;
import com.db.projeto.gerenciamento_de_biblioteca.exception.autor.CpfJaCadastradoException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import java.util.Arrays;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(AutorJaCadastradoException.class)
    public ResponseEntity<Object> handlerAutorJaCadastrado(AutorJaCadastradoException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
    }

    @ExceptionHandler(AutorNaoCadastradoException.class)
    public ResponseEntity<Object> handlerAutorNaoCadastrado(AutorNaoCadastradoException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(AutorComLivroNoBancoException.class)
    public ResponseEntity<Object> handlerAutorComLivroNoBancoException(AutorComLivroNoBancoException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body( ex.getMessage());
    }
    @ExceptionHandler(AutorNaoInformadoException.class)
    public ResponseEntity<Object> handlerAutorNaoInformadoException(AutorNaoInformadoException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
    }

    @ExceptionHandler(CpfJaCadastradoException.class)
    public ResponseEntity<String> tratarCpfJaCadastrado(CpfJaCadastradoException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> tratarErroDeValidacao(MethodArgumentNotValidException ex) {
        FieldError erro = ex.getBindingResult().getFieldError();
        String mensagem = erro != null ? erro.getDefaultMessage() : "Erro de validação.";
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(mensagem);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<String> tratarErroDeEnum(HttpMessageNotReadableException ex) {
        if (ex.getCause() instanceof InvalidFormatException invalidFormatException &&
                invalidFormatException.getTargetType() == CategoriaDoLivro.class) {

            List<String> categoriasValidas = Arrays.stream(CategoriaDoLivro.values())
                    .map(Enum::name)
                    .toList();

            String mensagem = "Categoria inválida. As categorias válidas são: " + categoriasValidas;

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(mensagem);
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("Requisição inválida. Verifique os dados enviados.");
    }

}