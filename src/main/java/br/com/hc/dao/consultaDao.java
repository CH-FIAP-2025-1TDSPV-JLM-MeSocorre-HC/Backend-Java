package br.com.hc.dao;

import br.com.hc.model.agendamento.Agendamentos;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class ConsultaDao {

    @Inject
    DataSource dataSource;

    public List<Agendamentos> listar() {
        String sql = "SELECT * FROM consultas";
        List<Agendamentos> consultas = new ArrayList<>();

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Agendamentos consulta = new Agendamentos(
                        rs.getInt("id"),
                        rs.getString("paciente"),
                        rs.getString("medico"),
                        rs.getString("data_hora"),
                        rs.getString("motivo")
                );
                consultas.add(consulta);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar consultas: " + e.getMessage(), e);
        }
        return consultas;
    }
    public Agendamentos buscar(int id) {
        String sql = "SELECT * FROM consultas WHERE id = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Agendamentos(
                        rs.getInt("id"),
                        rs.getString("paciente"),
                        rs.getString("medico"),
                        rs.getString("data_hora"),
                        rs.getString("motivo")
                );
            } else {
                return null;
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar consulta por ID: " + e.getMessage(), e);
        }
    }

    public void agendar(Agendamentos consulta) {
        String sql = "INSERT INTO consultas (paciente, medico, data_hora, motivo) VALUES (?, ?, ?, ?)";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, consulta.getPaciente());
            stmt.setString(2, consulta.getMedico());
            stmt.setString(3, consulta.getDataHora());
            stmt.setString(4, consulta.getMotivo());

            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                consulta.setId(rs.getInt(1));
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao agendar consulta: " + e.getMessage(), e);
        }
    }


}