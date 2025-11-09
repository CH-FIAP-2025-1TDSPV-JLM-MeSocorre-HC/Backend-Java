package br.com.hc.model.agendamento;

import br.com.hc.model.paciente.Paciente;
import java.time.LocalDateTime;

public class AgendamentoPresencial extends Agendamentos {
    private String endereco;

    public AgendamentoPresencial() {
        setTipo("PRESENCIAL");
    }

    public AgendamentoPresencial(Integer id, String medico, LocalDateTime dataHora,
                                 String nomeConsulta, String nomeProfissional,
                                 Paciente paciente, String endereco) {
        super(id, medico, dataHora, nomeConsulta, nomeProfissional, paciente);
        setTipo("PRESENCIAL");
        this.endereco = endereco;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }
}
