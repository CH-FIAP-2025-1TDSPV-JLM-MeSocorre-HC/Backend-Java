package br.com.hc.dao;

import br.com.hc.model.agendamento.Agendamentos;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import javax.sql.DataSource;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class ConsultaDao {

    @Inject
    DataSource dataSource;

    // 🔹 Lista todos os agendamentos (consultas, teleconsultas e exames)
    public List<Agendamentos> listar() {
        String sql = "SELECT id, paciente_id, medico, data_hora, nome_consulta, nome_profissional, tipo_agendamento FROM agendamentos";
        List<Agendamentos> lista = new ArrayList<>();

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Agendamentos agendamento = new Agendamentos();
                agendamento.setId(rs.getInt("id"));
                agendamento.setPacienteId(rs.getInt("paciente_id"));
                agendamento.setMedico(rs.getString("medico"));
                agendamento.setDataHora(rs.getTimestamp("data_hora").toLocalDateTime());
                agendamento.setNomeConsulta(rs.getString("nome_consulta"));
                agendamento.setNomeProfissional(rs.getString("nome_profissional"));
                agendamento.setTipoAgendamento(rs.getString("tipo_agendamento"));

                lista.add(agendamento);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar agendamentos: " + e.getMessage(), e);
        }

        return lista;
    }

    // 🔹 Busca um agendamento específico pelo ID
    public Agendamentos buscar(int id) {
        String sql = "SELECT id, paciente_id, medico, data_hora, nome_consulta, nome_profissional, tipo_agendamento FROM agendamentos WHERE id = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Agendamentos agendamento = new Agendamentos();
                agendamento.setId(rs.getInt("id"));
                agendamento.setPacienteId(rs.getInt("paciente_id"));
                agendamento.setMedico(rs.getString("medico"));
                agendamento.setDataHora(rs.getTimestamp("data_hora").toLocalDateTime());
                agendamento.setNomeConsulta(rs.getString("nome_consulta"));
                agendamento.setNomeProfissional(rs.getString("nome_profissional"));
                agendamento.setTipoAgendamento(rs.getString("tipo_agendamento"));

                return agendamento;
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar agendamento por ID: " + e.getMessage(), e);
        }

        return null;
    }

    // 🔹 Cria um novo agendamento
    public void agendar(Agendamentos agendamento) {
        String sql = "INSERT INTO agendamentos (paciente_id, medico, data_hora, nome_consulta, nome_profissional, tipo_agendamento) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, agendamento.getPacienteId());
            stmt.setString(2, agendamento.getMedico());
            stmt.setTimestamp(3, Timestamp.valueOf(agendamento.getDataHora()));
            stmt.setString(4, agendamento.getNomeConsulta());
            stmt.setString(5, agendamento.getNomeProfissional());
            stmt.setString(6, agendamento.getTipoAgendamento());

            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                agendamento.setId(rs.getInt(1));
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao agendar: " + e.getMessage(), e);
        }
    }

    // 🔹 Exclui um agendamento
    public void deletar(int id) {
        String sql = "DELETE FROM agendamentos WHERE id = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao deletar agendamento: " + e.getMessage(), e);
        }
    }
}
