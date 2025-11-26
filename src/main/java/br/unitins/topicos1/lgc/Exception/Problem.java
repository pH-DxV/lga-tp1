package br.unitins.topicos1.lgc.Exception;

import java.time.OffsetDateTime;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public final class Problem {
    public String type;
    public String title;
    public Integer status;
    public String detail;
    public String instance;
    public OffsetDateTime timestamp;
    public List<FieldError> errors;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static final class FieldError {
        public final String field;
        public final String message;

        public FieldError(String field, String message) {
            this.field = field;
            this.message = message;
        }
    }
}