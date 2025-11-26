package br.unitins.topicos1.lgc.Exception;

import java.time.OffsetDateTime;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
@ApplicationScoped
public class ValidationExceptionMapper implements ExceptionMapper<ValidationException> {
    
    @Context
    UriInfo uri;

    // Lembre-se de configurar "problem.base-url" no application.properties
    // senão isso pode virar null ou dar erro. 
    // No seu arquivo atual já tem: %dev.problem.base-url=...
    @ConfigProperty(name = "problem.base-url") 
    String baseUrl;

    @Override
    public Response toResponse(ValidationException e) {
        var p = new Problem();
        p.type = baseUrl + "/errors/validation-error";
        p.title = "Erro de validação";
        p.status = Response.Status.BAD_REQUEST.getStatusCode();
        p.detail = e.getMessage();
        p.instance = (uri != null ? uri.getRequestUri().getPath() : null);
        p.timestamp = OffsetDateTime.now();
        p.errors = e.getFieldErrors();
        
        return Response.status(p.status).type("application/problem+json").entity(p).build();
    }
}