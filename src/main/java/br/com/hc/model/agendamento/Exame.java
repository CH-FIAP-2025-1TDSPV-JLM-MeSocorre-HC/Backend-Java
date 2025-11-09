package br.com.hc.model.agendamento;

import br.com.hc.model.paciente.Paciente;

import java.time.LocalDateTime;

public class Exame extends Agendamentos {
    private String resultado;
    private String tipo;

    public Exame() {
    }

    public Exame(Integer id, String medico, LocalDateTime dataHora, String nomeConsulta, String nomeProfissional, Paciente paciente, String resultado, String tipo) {
        super(id, medico, dataHora, nomeConsulta, nomeProfissional, paciente);
        this.resultado = resultado;
        this.tipo = tipo;
    }

    public String getResultado() {
        return resultado;
    }

    public void setResultado(String resultado) {
        this.resultado = resultado;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}

