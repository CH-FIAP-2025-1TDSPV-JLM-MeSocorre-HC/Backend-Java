package br.com.hc.resource;

import br.com.hc.dao.AgendamentosDao;
import br.com.hc.dao.PacienteDao;
import br.com.hc.dto.CadastroAgendamentosDto;
import br.com.hc.dto.DetalhesAgendamentosDto;
import br.com.hc.exception.EntidadeNaoEncontradaException;
import br.com.hc.model.agendamento.AgendamentoOnline;
import br.com.hc.model.agendamento.AgendamentoPresencial;
import br.com.hc.model.agendamento.Agendamentos;
import br.com.hc.model.agendamento.Exame;
import br.com.hc.model.paciente.Paciente;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;

import java.net.URI;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

@Path("/agendamentos")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AgendamentoResource {

    @Inject
    AgendamentosDao agendamentosDao;

    @Inject
    PacienteDao pacienteDao;

    // LISTAR TODOS (para admin, ou remover se não for necessário)
    @GET
    public List<DetalhesAgendamentosDto> listar() throws SQLException {
        return agendamentosDao.listar()
                .stream()
                .map(this::toDetalhesDto)
                .collect(Collectors.toList());
    }

    // LISTAR AGENDAMENTOS DO PACIENTE ESPECÍFICO
    @GET
    @Path("/paciente/{pacienteId}")
    public List<DetalhesAgendamentosDto> listarPorPaciente(@PathParam("pacienteId") int pacienteId) throws SQLException {
        // Valida se o paciente existe
        Paciente paciente = pacienteDao.buscar(pacienteId);
        if (paciente == null) {
            throw new NotFoundException("Paciente não encontrado");
        }

        // Filtra os agendamentos no Java (ou pode criar um método no DAO se for muitos dados)
        return agendamentosDao.listar()
                .stream()
                .filter(ag -> ag.getPaciente().getId() == pacienteId)
                .map(this::toDetalhesDto)
                .collect(Collectors.toList());
    }

    // BUSCAR AGENDAMENTO ESPECÍFICO (qualquer um pode buscar, desde que exista)
    @GET
    @Path("/{id}")
    public Response buscar(@PathParam("id") int id) throws SQLException {
        try {
            Agendamentos agendamento = agendamentosDao.buscar(id);
            return Response.ok(toDetalhesDto(agendamento)).build();
        } catch (EntidadeNaoEncontradaException e) {
            throw new NotFoundException(e.getMessage());
        }
    }

    // CADASTRAR - o pacienteId vem do front-end (do AuthContext)
    @POST
    public Response adicionar(@Valid CadastroAgendamentoDto dto, @Context UriInfo uriInfo) throws SQLException {
        Agendamentos agendamento = fromDto(dto);
        agendamentosDao.cadastrar(agendamento);

        URI uri = uriInfo.getAbsolutePathBuilder()
                .path(String.valueOf(agendamento.getId()))
                .build();

        return Response.created(uri)
                .entity(toDetalhesDto(agendamento))
                .build();
    }

    // ATUALIZAR - o pacienteId vem do front-end
    @PUT
    @Path("/{id}")
    public Response atualizar(@PathParam("id") int id, @Valid CadastroAgendamentoDto dto) throws SQLException {
        try {
            Agendamentos agendamento = fromDto(dto);
            agendamento.setId(id);
            agendamentosDao.atualizar(agendamento);
            return Response.ok().build();
        } catch (EntidadeNaoEncontradaException e) {
            throw new NotFoundException(e.getMessage());
        }
    }

    // DELETAR
    @DELETE
    @Path("/{id}")
    public Response deletar(@PathParam("id") int id) throws SQLException {
        try {
            agendamentosDao.deletar(id);
            return Response.noContent().build();
        } catch (EntidadeNaoEncontradaException e) {
            throw new NotFoundException(e.getMessage());
        }
    }

    // ... métodos fromDto e toDetalhesDto permanecem iguais
}

    private Agendamentos fromDto(CadastroAgendamentosDto dto) throws SQLException {
        Paciente paciente = pacienteDao.buscar(dto.getPacienteId());
        if (paciente == null) {
            throw new NotFoundException("Paciente não encontrado");
        }

        Agendamentos agendamento;
        switch (dto.getTipo().toUpperCase()) {
            case "ONLINE":
                AgendamentoOnline online = new AgendamentoOnline();
                online.setLinkReuniao(dto.getLink());
                agendamento = online;
                break;
            case "PRESENCIAL":
                AgendamentoPresencial presencial = new AgendamentoPresencial();
                presencial.setEndereco(dto.getEndereco());
                agendamento = presencial;
                break;
            case "EXAME":
                Exame exame = new Exame();
                exame.setTipo(dto.getTipoExame());
                exame.setResultado(dto.getResultadoExame());
                agendamento = exame;
                break;
            default:
                throw new IllegalArgumentException("Tipo de agendamento inválido: " + dto.getTipo());
        }

        agendamento.setPaciente(paciente);
        agendamento.setDataHora(dto.getDataHora());
        agendamento.setNomeConsulta(dto.getNomeConsulta());
        agendamento.setNomeProfissional(dto.getNomeProfissional());
        agendamento.setMedico(dto.getMedico());

        return agendamento;
    }

    private DetalhesAgendamentosDto toDetalhesDto(Agendamentos agendamento) {
        DetalhesAgendamentosDto dto = new DetalhesAgendamentosDto();
        dto.setId(agendamento.getId());
        dto.setTipo(agendamento.getClass().getSimpleName().toUpperCase());
        dto.setPacienteId(agendamento.getPaciente().getId());
        dto.setDataHora(agendamento.getDataHora());
        dto.setNomeConsulta(agendamento.getNomeConsulta());
        dto.setNomeProfissional(agendamento.getNomeProfissional());
        dto.setMedico(agendamento.getMedico());

        if (agendamento instanceof AgendamentoOnline) {
            dto.setLink(((AgendamentoOnline) agendamento).getLinkReuniao());
        } else if (agendamento instanceof AgendamentoPresencial) {
            dto.setEndereco(((AgendamentoPresencial) agendamento).getEndereco());
        } else if (agendamento instanceof Exame) {
            dto.setTipoExame(((Exame) agendamento).getTipo());
            dto.setResultadoExame(((Exame) agendamento).getResultado());
        }

        return dto;
    }
}