package br.com.hc.dao;

import br.com.hc.exception.EntidadeNaoEncontradaException;
import br.com.hc.model.agendamento.*;
import br.com.hc.model.paciente.Paciente;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import br.com.hc.model.agendamento.AgendamentoPresencial;

import javax.sql.DataSource;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class AgendamentosDao {

    @Inject
    private DataSource dataSource;

    @Inject
    private PacienteDao pacienteDao;

    // =========================
    // MÉTODO CADASTRAR
    // =========================
    public void cadastrar(Agendamentos agendamento) throws SQLException {
        try (Connection conexao = dataSource.getConnection()) {

            PreparedStatement stmt = conexao.prepareStatement("""
                INSERT INTO agendamentos (
                    id, paciente_id, data_hora, tipo_agendamento,
                    nome_consulta, nome_profissional, medico,
                    link, endereco, tipo_exame, resultado_exame
                ) VALUES (agendamentos_seq.NEXTVAL, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
            """, new String[] {"id"});


            setarParametros(agendamento, stmt);

            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                agendamento.setId(rs.getInt(1));
            }
        }
    }

    // =========================
    // MÉTODO LISTAR
    // =========================
    public List<Agendamentos> listar() throws SQLException {
        try (Connection conexao = dataSource.getConnection()) {
            PreparedStatement stmt = conexao.prepareStatement("SELECT * FROM agendamentos");
            ResultSet rs = stmt.executeQuery();

            List<Agendamentos> lista = new ArrayList<>();
            while (rs.next()) {
                lista.add(parseAgendamento(rs));
            }
            return lista;
        }
    }

    // =========================
    // MÉTODO BUSCAR
    // =========================
    public Agendamentos buscar(int id) throws SQLException, EntidadeNaoEncontradaException {
        try (Connection conexao = dataSource.getConnection()) {
            PreparedStatement stmt = conexao.prepareStatement("SELECT * FROM agendamentos WHERE id = ?");
            stmt.setInt(1, id);

            ResultSet rs = stmt.executeQuery();

            if (!rs.next()) {
                throw new EntidadeNaoEncontradaException("Agendamento não encontrado");
            }

            return parseAgendamento(rs);
        }
    }

    // =========================
    // MÉTODO ATUALIZAR
    // =========================
    public void atualizar(Agendamentos agendamento) throws SQLException, EntidadeNaoEncontradaException {
        try (Connection conexao = dataSource.getConnection()) {
            PreparedStatement stmt = conexao.prepareStatement("""
                UPDATE agendamentos SET
                    paciente_id = ?, data_hora = ?, tipo_agendamento = ?,
                    nome_consulta = ?, nome_profissional = ?, medico = ?,
                    link = ?, endereco = ?, tipo_exame = ?, resultado_exame = ?
                WHERE id = ?
            """);

            setarParametros(agendamento, stmt);
            stmt.setInt(11, agendamento.getId());

            if (stmt.executeUpdate() == 0) {
                throw new EntidadeNaoEncontradaException("Agendamento não existe para ser atualizado");
            }
        }
    }

    // =========================
    // MÉTODO DELETAR
    // =========================
    public void deletar(int id) throws SQLException, EntidadeNaoEncontradaException {
        try (Connection conexao = dataSource.getConnection()) {
            PreparedStatement stmt = conexao.prepareStatement("DELETE FROM agendamentos WHERE id = ?");
            stmt.setInt(1, id);

            if (stmt.executeUpdate() == 0) {
                throw new EntidadeNaoEncontradaException("Agendamento não encontrado para exclusão");
            }
        }
    }

    // =========================
    // LISTAR POR PACIENTE
    // =========================
    public List<Agendamentos> listarPorPaciente(int pacienteId) throws SQLException {
        try (Connection conexao = dataSource.getConnection()) {
            PreparedStatement stmt = conexao.prepareStatement("SELECT * FROM agendamentos WHERE paciente_id = ?");
            stmt.setInt(1, pacienteId);

            ResultSet rs = stmt.executeQuery();
            List<Agendamentos> lista = new ArrayList<>();
            while (rs.next()) {
                lista.add(parseAgendamento(rs));
            }
            return lista;
        }
    }

    // =========================
    // BUSCAR POR PACIENTE E ID
    // =========================
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

    // =========================
    // MÉTODOS AUXILIARES
    // =========================

    private static void setarParametros(Agendamentos agendamento, PreparedStatement stmt) throws SQLException {
        stmt.setInt(1, agendamento.getPaciente().getId());
        stmt.setObject(2, agendamento.getDataHora());
        stmt.setString(3, agendamento.getTipo().toUpperCase());
        stmt.setString(4, agendamento.getNomeConsulta());
        stmt.setString(5, agendamento.getNomeProfissional());
        stmt.setString(6, agendamento.getMedico());

        // Campos específicos por tipo
        if (agendamento instanceof AgendamentoOnline online) {
            stmt.setString(7, online.getLinkReuniao());
            stmt.setNull(8, Types.VARCHAR);
            stmt.setNull(9, Types.VARCHAR);
            stmt.setNull(10, Types.VARCHAR);
        } else if (agendamento instanceof AgendamentoPresencial pres) {
            stmt.setNull(7, Types.VARCHAR);
            stmt.setString(8, pres.getEndereco());
            stmt.setNull(9, Types.VARCHAR);
            stmt.setNull(10, Types.VARCHAR);
        } else if (agendamento instanceof Exame exame) {
            stmt.setNull(7, Types.VARCHAR);
            stmt.setNull(8, Types.VARCHAR);
            stmt.setString(9, exame.getTipo());
            stmt.setString(10, exame.getResultadoExame());
        } else {
            stmt.setNull(7, Types.VARCHAR);
            stmt.setNull(8, Types.VARCHAR);
            stmt.setNull(9, Types.VARCHAR);
            stmt.setNull(10, Types.VARCHAR);
        }
    }

    private Agendamentos parseAgendamento(ResultSet rs) throws SQLException {
        String tipo = rs.getString("tipo_agendamento");
        Agendamentos ag;

        switch (tipo.toUpperCase()) {
            case "ONLINE" -> {
                ag = new AgendamentoOnline();
                ((AgendamentoOnline) ag).setLinkReuniao(rs.getString("link"));
            }
            case "PRESENCIAL" -> {
                AgendamentoPresencial pres = new AgendamentoPresencial();
                // O endereço é fixo (definido na classe)
                pres.setSala(rs.getInt("sala"));
                pres.setAndar(rs.getInt("andar"));
                ag = pres;
            }
            case "EXAME" -> {
                ag = new Exame();
                ((Exame) ag).setTipo(rs.getString("tipo_exame"));
                ((Exame) ag).setResultadoExame(rs.getString("resultado_exame"));
            }
            default -> throw new SQLException("Tipo de agendamento desconhecido: " + tipo);
        }


        ag.setId(rs.getInt("id"));
        ag.setDataHora(rs.getObject("data_hora", LocalDateTime.class));
        ag.setNomeConsulta(rs.getString("nome_consulta"));
        ag.setNomeProfissional(rs.getString("nome_profissional"));
        ag.setMedico(rs.getString("medico"));

        int pacienteId = rs.getInt("paciente_id");
        try {
            Paciente paciente = pacienteDao.buscar(pacienteId);
            ag.setPaciente(paciente);
        } catch (Exception e) {
            throw new SQLException("Paciente não encontrado para ID: " + pacienteId);
        }

        return ag;
    }
}
