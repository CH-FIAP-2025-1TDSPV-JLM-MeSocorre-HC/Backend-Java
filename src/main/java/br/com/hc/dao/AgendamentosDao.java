package br.com.hc.dao;

// ... imports existentes
import br.com.hc.exception.EntidadeNaoEncontradaException;
import br.com.hc.model.agendamento.AgendamentoOnline;
import br.com.hc.model.agendamento.AgendamentoPresencial;
import br.com.hc.model.agendamento.Agendamentos;
import br.com.hc.model.agendamento.Exame;
import br.com.hc.model.paciente.Paciente;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class AgendamentosDao {

    @Inject
    private DataSource dataSource;

    @Inject
    private PacienteDao pacienteDao; // Adicionar esta injeção

    // ... manter todos os métodos existentes
    // Método existente - manter
    public List<Agendamentos> listar() throws SQLException {
        try (Connection conexao = dataSource.getConnection()) {
            PreparedStatement stmt = conexao.prepareStatement("""
                    SELECT * FROM agendamentos
                    """);
            ResultSet rs = stmt.executeQuery();

            List<Agendamentos> lista = new ArrayList<>();
            while (rs.next()) {
                lista.add(parseAgendamento(rs));
            }
            return lista;
        }
    }

    // NOVO: Listar apenas agendamentos do paciente específico
    public List<Agendamentos> listarPorPaciente(int pacienteId) throws SQLException {
        try (Connection conexao = dataSource.getConnection()) {
            PreparedStatement stmt = conexao.prepareStatement("""
                    SELECT * FROM agendamentos WHERE paciente_id = ?
                    """);
            stmt.setInt(1, pacienteId);
            ResultSet rs = stmt.executeQuery();

            List<Agendamentos> lista = new ArrayList<>();
            while (rs.next()) {
                lista.add(parseAgendamento(rs));
            }
            return lista;
        }
    }

    // NOVO: Buscar agendamento específico do paciente (segurança)
    public Agendamentos buscarPorPacienteEId(int pacienteId, int agendamentoId)
            throws SQLException, EntidadeNaoEncontradaException {
        try (Connection conexao = dataSource.getConnection()) {
            PreparedStatement stmt = conexao.prepareStatement("""
                    SELECT * FROM agendamentos WHERE id = ? AND paciente_id = ?
                    """);
            stmt.setInt(1, agendamentoId);
            stmt.setInt(2, pacienteId);

            ResultSet rs = stmt.executeQuery();
            if (!rs.next())
                throw new EntidadeNaoEncontradaException("Agendamento não encontrado.");

            return parseAgendamento(rs);
        }
    }


    private Agendamentos parseAgendamento(ResultSet rs) throws SQLException {
        String tipo = rs.getString("tipo_agendamento");
        Agendamentos ag;

        switch (tipo.toUpperCase()) {
            case "AGENDAMENTOONLINE" -> {
                ag = new AgendamentoOnline();
                ((AgendamentoOnline) ag).setLinkReuniao(rs.getString("link"));
            }
            case "EXAME" -> {
                ag = new Exame();
                ((Exame) ag).setTipo(rs.getString("tipo_exame"));
                ((Exame) ag).setResultado(rs.getString("resultado_exame"));
            }
            case "AGENDAMENTOPRESENCIAL" -> {
                ag = new AgendamentoPresencial();
                ((AgendamentoPresencial) ag).setEndereco(rs.getString("endereco"));
            }
            default -> throw new SQLException("Tipo de agendamento desconhecido: " + tipo);
        }

        ag.setId(rs.getInt("id"));
        ag.setDataHora(rs.getObject("data_hora", LocalDateTime.class));
        ag.setNomeConsulta(rs.getString("nome_consulta"));
        ag.setNomeProfissional(rs.getString("nome_profissional"));
        ag.setMedico(rs.getString("medico"));

        // ✅ CORRIGIDO: Agora carrega o paciente usando PacienteDao
        int pacienteId = rs.getInt("paciente_id");
        Paciente paciente = pacienteDao.buscar(pacienteId);
        if (paciente == null) {
            throw new SQLException("Paciente com ID " + pacienteId + " não encontrado");
        }
        ag.setPaciente(paciente);

        return ag;
    }
}