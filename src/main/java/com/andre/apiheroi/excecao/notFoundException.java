package com.andre.apiheroi.excecao;

public class notFoundException extends RuntimeException {
    public notFoundException(String message) {
        super(message);
    }
    public notFoundException(Long id) {
        super("Objeto não encontrado com id " + id);
    }

}
