package br.com.hc.dao;

import br.com.hc.exception.EntidadeNaoEncontradaException;
import br.com.hc.model.agendamento.AgendamentoOnline;
import br.com.hc.model.agendamento.AgendamentoPresencial;
import br.com.hc.model.agendamento.Agendamentos;
import br.com.hc.model.agendamento.Exame;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import javax.sql.DataSource;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class AgendamentosDao {

    @Inject
    private DataSource dataSource;

    public void cadastrar(Agendamentos agendamento) throws SQLException {
        try (Connection conexao = dataSource.getConnection()) {

            PreparedStatement stmt = conexao.prepareStatement("""
                    INSERT INTO agendamentos (
                        id, paciente_id, data_hora, tipo_agendamento,
                        nome_consulta, nome_profissional, medico
                    ) VALUES (
                        agendamentos_seq.NEXTVAL, ?, ?, ?, ?, ?, ?
                    )
                    """, new String[]{"id"});

            setarParametros(agendamento, stmt);
            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                agendamento.setId(rs.getInt(1));
            }
        }
    }

    public Agendamentos buscar(int id) throws SQLException, EntidadeNaoEncontradaException {
        try (Connection conexao = dataSource.getConnection()) {
            PreparedStatement stmt = conexao.prepareStatement("""
                    SELECT * FROM agendamentos WHERE id = ?
                    """);
            stmt.setInt(1, id);

            ResultSet rs = stmt.executeQuery();
            if (!rs.next())
                throw new EntidadeNaoEncontradaException("Agendamento não encontrado.");

            return parseAgendamento(rs);
        }
    }

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

    public void atualizar(Agendamentos agendamento) throws SQLException, EntidadeNaoEncontradaException {
        try (Connection conexao = dataSource.getConnection()) {
            PreparedStatement stmt = conexao.prepareStatement("""
                    UPDATE agendamentos SET
                        paciente_id = ?, data_hora = ?, tipo_agendamento = ?,
                        nome_consulta = ?, nome_profissional = ?, medico = ?
                    WHERE id = ?
                    """);
            setarParametros(agendamento, stmt);
            stmt.setInt(7, agendamento.getId());

            if (stmt.executeUpdate() == 0)
                throw new EntidadeNaoEncontradaException("Agendamento não existe para ser atualizado.");
        }
    }

    public void deletar(int id) throws SQLException, EntidadeNaoEncontradaException {
        try (Connection conexao = dataSource.getConnection()) {
            PreparedStatement stmt = conexao.prepareStatement("""
                    DELETE FROM agendamentos WHERE id = ?
                    """);
            stmt.setInt(1, id);

            if (stmt.executeUpdate() == 0)
                throw new EntidadeNaoEncontradaException("Agendamento não encontrado para exclusão.");
        }
    }

    private void setarParametros(Agendamentos agendamento, PreparedStatement stmt) throws SQLException {
        stmt.setInt(1, agendamento.getPaciente().getId());
        stmt.setObject(2, agendamento.getDataHora());
        stmt.setString(3, agendamento.getClass().getSimpleName().toUpperCase()); // tipo_agendamento
        stmt.setString(4, agendamento.getNomeConsulta());
        stmt.setString(5, agendamento.getNomeProfissional());
        stmt.setString(6, agendamento.getMedico());
    }

    private Agendamentos parseAgendamento(ResultSet rs) throws SQLException {
        String tipo = rs.getString("tipo_agendamento");
        Agendamentos ag;

        // decide qual classe concreta criar
        switch (tipo.toUpperCase()) {
            case "ONLINE" -> ag = new AgendamentoOnline();
            case "EXAME" -> ag = new Exame();
            case "PRESENCIAL" -> ag = new AgendamentoPresencial();
            default -> throw new SQLException("Tipo de agendamento desconhecido: " + tipo);
        }

        ag.setId(rs.getInt("id"));
        ag.setDataHora(rs.getObject("data_hora", LocalDateTime.class));
        ag.setNomeConsulta(rs.getString("nome_consulta"));
        ag.setNomeProfissional(rs.getString("nome_profissional"));
        ag.setMedico(rs.getString("medico"));
        return ag;
    }
}
