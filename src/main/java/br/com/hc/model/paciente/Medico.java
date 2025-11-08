package br.com.hc.model.paciente;

public class Medico {
    private String crm;
    private String nome;
    private String especialidade;

    public Medico(String crm, String nome, String especialidade) {
        this.crm = crm;
        this.nome = nome;
        this.especialidade = especialidade;
    }

    public String getCrm() { return crm; }
    public String getNome() { return nome; }
    public String getEspecialidade() { return especialidade; }


    public String toString() {
        return nome + " (" + especialidade + ") - CRM: " + crm;
    }
}
