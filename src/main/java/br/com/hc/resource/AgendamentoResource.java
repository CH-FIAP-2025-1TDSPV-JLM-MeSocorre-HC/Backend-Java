package br.com.hc.resource;

import br.com.hc.dao.AgendamentosDao;
import br.com.hc.dao.PacienteDao;
import br.com.hc.dto.CadastroAgendamentoDto;
import br.com.hc.dto.DetalhesAgendamentosDto;
import br.com.hc.exception.EntidadeNaoEncontradaException;
import br.com.hc.model.agendamento.*;
import br.com.hc.model.paciente.Paciente;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;
import org.modelmapper.ModelMapper;

import java.net.URI;
import java.sql.SQLException;
import java.util.List;

@Path("/agendamentos")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AgendamentoResource {

    @Inject
    private AgendamentosDao agendamentosDao;

    @Inject
    private PacienteDao pacienteDao;

    @Inject
    private ModelMapper modelMapper;

    // LISTAR TODOS
    @GET
    public List<DetalhesAgendamentosDto> listar() throws SQLException {
        return agendamentosDao.listar()
                .stream()
                .map(a -> modelMapper.map(a, DetalhesAgendamentosDto.class))
                .toList();
    }

    // LISTAR POR PACIENTE
    @GET
    @Path("/paciente/{pacienteId}")
    public List<DetalhesAgendamentosDto> listarPorPaciente(@PathParam("pacienteId") int pacienteId) throws SQLException {
        Paciente paciente = pacienteDao.buscar(pacienteId);
        if (paciente == null) {
            throw new NotFoundException("Paciente não encontrado");
        }

        return agendamentosDao.listar()
                .stream()
                .filter(ag -> ag.getPaciente().getId() == pacienteId)
                .map(a -> modelMapper.map(a, DetalhesAgendamentosDto.class))
                .toList();
    }

    // BUSCAR POR ID
    @GET
    @Path("/{id}")
    public Response buscar(@PathParam("id") int id) throws SQLException, EntidadeNaoEncontradaException {
        Agendamentos agendamento = agendamentosDao.buscar(id);
        DetalhesAgendamentosDto dto = modelMapper.map(agendamento, DetalhesAgendamentosDto.class);
        return Response.ok(dto).build();
    }

    // CRIAR
    @POST
    public Response cadastrar(@Valid CadastroAgendamentoDto dto, @Context UriInfo uriInfo) throws SQLException {
        Agendamentos agendamento = fromDto(dto);
        agendamentosDao.cadastrar(agendamento);

        URI uri = uriInfo.getAbsolutePathBuilder()
                .path(String.valueOf(agendamento.getId()))
                .build();

        return Response.created(uri)
                .entity(modelMapper.map(agendamento, DetalhesAgendamentosDto.class))
                .build();
    }

    // ATUALIZAR
    @PUT
    @Path("/{id}")
    public Response atualizar(@PathParam("id") int id, @Valid CadastroAgendamentoDto dto)
            throws SQLException, EntidadeNaoEncontradaException {
        Agendamentos agendamento = fromDto(dto);
        agendamento.setId(id);
        agendamentosDao.atualizar(agendamento);
        return Response.ok().build();
    }

    // DELETAR
    @DELETE
    @Path("/{id}")
    public Response deletar(@PathParam("id") int id) throws SQLException, EntidadeNaoEncontradaException {
        agendamentosDao.deletar(id);
        return Response.noContent().build();
    }

    // =====================================================
    // Métodos auxiliares
    // =====================================================

    private Agendamentos fromDto(CadastroAgendamentoDto dto) throws SQLException {
        Paciente paciente = pacienteDao.buscar(dto.getPacienteId());
        if (paciente == null) {
            throw new NotFoundException("Paciente não encontrado");
        }

        Agendamentos agendamento;

        // O ModelMapper agora já entende os campos específicos por tipo
        switch (dto.getTipo().toUpperCase()) {
            case "ONLINE" -> agendamento = modelMapper.map(dto, AgendamentoOnline.class);
            case "PRESENCIAL" -> agendamento = modelMapper.map(dto, AgendamentoPresencial.class);
            case "EXAME" -> agendamento = modelMapper.map(dto, Exame.class);
            default -> throw new IllegalArgumentException("Tipo de agendamento inválido: " + dto.getTipo());
        }

        // Campos comuns
        agendamento.setPaciente(paciente);
        return agendamento;
    }


}
