package br.com.hc.dao;

import br.com.hc.model.paciente.LoginSenha;
import br.com.hc.model.paciente.Paciente;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import javax.sql.DataSource;
import java.sql.*;

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
}