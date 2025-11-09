package br.com.hc.resource;

import br.com.hc.dao.AgendamentosDao;
import br.com.hc.dto.DetalhesAgendamentosDto;
import br.com.hc.dto.DetalhesAgendamentosDto;
import br.com.hc.model.agendamento.Exame;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

@Path("/resultados")
@Produces(MediaType.APPLICATION_JSON)
public class ResultadoResource {

    @Inject
    AgendamentosDao agendamentosDao;

    @GET
    @Path("/paciente/{pacienteId}")
    public List<DetalhesAgendamentosDto> getResultadosPorPaciente(@PathParam("pacienteId") int pacienteId) throws SQLException {
        return agendamentosDao.listar()
                .stream()
                .filter(ag -> ag instanceof Exame)
                .filter(ag -> ag.getPaciente().getId() == pacienteId) // Filtra pelo paciente
                .filter(ag -> ((Exame) ag).getResultado() != null && !((Exame) ag).getResultado().isEmpty())
                .map(ag -> {
                    DetalhesAgendamentosDto dto = new DetalhesAgendamentosDto();
                    dto.setId(ag.getId());
                    dto.setTipo("EXAME");
                    dto.setPacienteId(ag.getPaciente().getId());
                    dto.setDataHora(ag.getDataHora());
                    dto.setNomeConsulta(ag.getNomeConsulta());
                    dto.setNomeProfissional(ag.getNomeProfissional());
                    dto.setMedico(ag.getMedico());
                    dto.setTipoExame(((Exame) ag).getTipo());
                    dto.setResultadoExame(((Exame) ag).getResultado());
                    return dto;
                })
                .collect(Collectors.toList());
    }
}