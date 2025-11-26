package br.unitins.topicos1.lgc.Exception;

import java.util.List;

public class ValidationException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    private final List<Problem.FieldError> fieldErrors;

    public ValidationException(String msg, List<Problem.FieldError> errors) {
        super(msg);
        this.fieldErrors = (errors == null) ? List.of() : List.copyOf(errors);
    }

    public static ValidationException of(String field, String msg) {
        return new ValidationException("Dados inválidos", List.of(new Problem.FieldError(field, msg)));
    }

    public List<Problem.FieldError> getFieldErrors() {
        return fieldErrors;
    }
}