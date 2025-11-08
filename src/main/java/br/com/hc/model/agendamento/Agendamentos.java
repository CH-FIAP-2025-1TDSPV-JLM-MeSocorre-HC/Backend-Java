package br.com.hc.model.agendamento;

import java.time.LocalDateTime;
import br.com.hc.model.paciente.Paciente;

public abstract class Agendamentos {
    private Integer id;
    private String medico;
    private LocalDateTime dataHora;
    private String nomeConsulta;
    private String nomeProfissional;
    private Paciente paciente;

    public Agendamentos() {
        // construtor padrão (necessário para herança funcionar)
    }

    public Agendamentos(Integer id, String medico, LocalDateTime dataHora, String nomeConsulta, String nomeProfissional, Paciente paciente) {
        this.id = id;
        this.medico = medico;
        this.dataHora = dataHora;
        this.nomeConsulta = nomeConsulta;
        this.nomeProfissional = nomeProfissional;
        this.paciente = paciente;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMedico() {
        return medico;
    }

    public void setMedico(String medico) {
        this.medico = medico;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public void setDataHora(LocalDateTime dataHora) {
        this.dataHora = dataHora;
    }

    public String getNomeConsulta() {
        return nomeConsulta;
    }

    public void setNomeConsulta(String nomeConsulta) {
        this.nomeConsulta = nomeConsulta;
    }

    public String getNomeProfissional() {
        return nomeProfissional;
    }

    public void setNomeProfissional(String nomeProfissional) {
        this.nomeProfissional = nomeProfissional;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }
}

