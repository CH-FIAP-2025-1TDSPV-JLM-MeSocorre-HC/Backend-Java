package br.com.hc.exception;

import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

import java.sql.SQLException;

@Provider
public class ApiExceptionMapper implements ExceptionMapper<Exception> {

    @Override
    public Response toResponse(Exception exception) {
        if (exception instanceof NotFoundException) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(new ErrorResponseDto("Recurso não encontrado", exception.getMessage()))
                    .build();
        } else if (exception instanceof SQLException) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(new ErrorResponseDto("Erro no banco de dados", exception.getMessage()))
                    .build();
        } else if (exception instanceof IllegalArgumentException) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new ErrorResponseDto("Dados inválidos", exception.getMessage()))
                    .build();
        } else {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(new ErrorResponseDto("Erro interno", exception.getMessage()))
                    .build();
        }
    }
}