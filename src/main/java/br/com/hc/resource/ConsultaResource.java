package br.com.hc.resource;

import br.com.hc.dao.ConsultaDao;
import br.com.hc.dto.consulta.AgendarConsultaDto;
import br.com.hc.dto.consulta.DetalhesConsultaDto;
import br.com.hc.model.agendamento.Agendamentos;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;
import org.modelmapper.ModelMapper;
import java.net.URI;
import java.sql.SQLException;
import java.util.List;
import jakarta.validation.Valid;
import br.com.hc.exception.ErrorResponseDto;

@Path("/consultas")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ConsultaResource {

    private Long pacienteId;
    private String pacienteNome;
    private Long medicoId;
    private String especialidade;

    @Inject
    private ConsultaDao consultaDao;

    @Inject
    private ModelMapper mapper;

    @POST
    public Response agendar(@Valid AgendarConsultaDto dto, @Context UriInfo urlinfo) throws SQLException {
        Agendamentos consulta = mapper.map(dto, Agendamentos.class);
        consultaDao.agendar(consulta);

        URI url = urlinfo.getAbsolutePathBuilder()
                .path(String.valueOf(consulta.getId()))
                .build();

        return Response.created(url)
                .entity(mapper.map(consulta, DetalhesConsultaDto.class))
                .build();
    }

    @GET
    @Path("/{id}")
    public Response buscar(@PathParam("id") int id) throws SQLException {
        try {
            Agendamentos consulta = consultaDao.buscar(id);
            return Response.ok(mapper.map(consulta, DetalhesConsultaDto.class)).build();
        } catch (Exception e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(new ErrorResponseDto("Consulta não encontrada", e.getMessage()))
                    .build();
        }
    }

    @GET
    public List<DetalhesConsultaDto> listar() throws SQLException {
        return consultaDao.listar()
                .stream()
                .map(consulta -> mapper.map(consulta, DetalhesConsultaDto.class))
                .toList();
    }
}