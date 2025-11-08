package br.com.hc.model.agendamento;

import br.com.hc.model.paciente.Paciente;

import java.time.LocalDateTime;

public class Exame extends Agendamentos {
    private String resultadoExame;
    private String tipo;

    public Exame() {
    }

    public Exame(Integer id, String medico, LocalDateTime dataHora, String nomeConsulta, String nomeProfissional, Paciente paciente, String resultadoExame, String tipo) {
        super(id, medico, dataHora, nomeConsulta, nomeProfissional, paciente);
        this.resultadoExame = resultadoExame;
        this.tipo = tipo;
    }

    public String getResultadoExame() {
        return resultadoExame;
    }

    public void setResultadoExame(String resultadoExame) {
        this.resultadoExame = resultadoExame;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}

