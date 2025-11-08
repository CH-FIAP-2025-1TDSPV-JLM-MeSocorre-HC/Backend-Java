package br.com.hc.exception;

import jakarta.validation.ConstraintViolationException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class ApiValidationMapper implements ExceptionMapper<ConstraintViolationException> {
    @Override
    public Response toResponse(ConstraintViolationException exception) {
        StringBuilder errors = new StringBuilder();
        exception.getConstraintViolations().forEach(violation ->
                errors.append(violation.getMessage()).append("; ")
        );
        return Response.status(Response.Status.BAD_REQUEST)
                .entity(new ErrorResponseDto("Dados inválidos", errors.toString()))
                .build();
    }
}