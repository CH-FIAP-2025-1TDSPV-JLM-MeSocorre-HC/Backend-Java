package br.com.hc.model.paciente;

public class Paciente {
    private int id;
    private String nome;
    private String cpf;
    private int idade;
    private LoginSenha loginSenha;

    public Paciente(int id, String nome, String cpf, int idade, LoginSenha loginSenha) {
        this.id = id;
        this.nome = nome;
        this.cpf = cpf;
        this.idade = idade;
        this.loginSenha = loginSenha;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public int getIdade() {
        return idade;
    }

    public void setIdade(int idade) {
        this.idade = idade;
    }

    public LoginSenha getLoginSenha() {
        return loginSenha;
    }

    public void setLoginSenha(LoginSenha loginSenha) {
        this.loginSenha = loginSenha;
    }
}
