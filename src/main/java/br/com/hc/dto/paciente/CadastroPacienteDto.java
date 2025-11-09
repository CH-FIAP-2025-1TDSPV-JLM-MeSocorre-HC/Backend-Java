package br.com.hc.dto.paciente;

import jakarta.validation.constraints.*;

public class CadastroPacienteDto {

    @NotBlank
    private String nome;

    @NotBlank
    private String cpf;

    @Min(0)
    private int idade;

    @NotBlank
    private String usuario;

    @NotBlank
    private String senha;

    // Getters e Setters
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getCpf() { return cpf; }
    public void setCpf(String cpf) { this.cpf = cpf; }

    public int getIdade() { return idade; }
    public void setIdade(int idade) { this.idade = idade; }

    public String getUsuario() { return usuario; }
    public void setUsuario(String usuario) { this.usuario = usuario; }

    public String getSenha() { return senha; }
    public void setSenha(String senha) { this.senha = senha; }
}
