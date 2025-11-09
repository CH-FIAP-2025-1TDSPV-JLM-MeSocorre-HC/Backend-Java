package br.com.hc.dao;

import br.com.hc.model.paciente.LoginSenha;
import br.com.hc.model.paciente.Paciente;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class PacienteDao {

    @Inject
    DataSource dataSource;

    public Paciente buscar(int id) throws SQLException {
        try (Connection conexao = dataSource.getConnection()) {
            PreparedStatement stmt = conexao.prepareStatement("""
                    SELECT id, nome, cpf, idade, usuario, senha 
                    FROM paciente WHERE id = ?
                    """);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                LoginSenha loginSenha = new LoginSenha(
                        rs.getInt("id"),
                        rs.getString("usuario"),
                        rs.getString("senha")
                );
                return new Paciente(
                        rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getString("cpf"),
                        rs.getInt("idade"),
                        loginSenha
                );
            }
            return null;
        }
    }

    public List<Paciente> listar() throws SQLException {
        try (Connection conexao = dataSource.getConnection()) {
            PreparedStatement stmt = conexao.prepareStatement("""
                SELECT id, nome, cpf, idade, usuario, senha FROM paciente
                """);
            ResultSet rs = stmt.executeQuery();

            List<Paciente> pacientes = new ArrayList<>();
            while (rs.next()) {
                LoginSenha loginSenha = new LoginSenha(
                        rs.getInt("id"),
                        rs.getString("usuario"),
                        rs.getString("senha")
                );
                pacientes.add(new Paciente(
                        rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getString("cpf"),
                        rs.getInt("idade"),
                        loginSenha
                ));
            }
            return pacientes;
        }
    }

    public void cadastrar(Paciente paciente) throws SQLException {
        String sql = """
        INSERT INTO paciente (id, nome, cpf, idade, usuario, senha)
        VALUES (paciente_seq.NEXTVAL, ?, ?, ?, ?, ?)
    """;

        try (Connection conexao = dataSource.getConnection();
             PreparedStatement stmt = conexao.prepareStatement(sql, new String[]{"id"})) {

            stmt.setString(1, paciente.getNome());
            stmt.setString(2, paciente.getCpf());
            stmt.setInt(3, paciente.getIdade());
            stmt.setString(4, paciente.getLoginSenha().getUsuario());
            stmt.setString(5, paciente.getLoginSenha().getSenha());

            stmt.executeUpdate();

            // Recupera o ID gerado
            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    paciente.setId(rs.getInt(1));
                }
            }
        }
    }

    public void atualizar(Paciente paciente) throws SQLException {
        try (Connection conexao = dataSource.getConnection()) {
            PreparedStatement stmt = conexao.prepareStatement("""
            UPDATE paciente
            SET nome = ?, cpf = ?, idade = ?, usuario = ?, senha = ?
            WHERE id = ?
        """);
            stmt.setString(1, paciente.getNome());
            stmt.setString(2, paciente.getCpf());
            stmt.setInt(3, paciente.getIdade());
            stmt.setString(4, paciente.getLoginSenha().getUsuario());
            stmt.setString(5, paciente.getLoginSenha().getSenha());
            stmt.setInt(6, paciente.getId());
            stmt.executeUpdate();
        }
    }


    public void deletar(int id) throws SQLException {
        try (Connection conexao = dataSource.getConnection()) {
            PreparedStatement stmt = conexao.prepareStatement("DELETE FROM paciente WHERE id = ?");
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

}