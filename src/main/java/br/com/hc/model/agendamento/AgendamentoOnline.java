package br.com.hc.model.agendamento;

import br.com.hc.model.paciente.Paciente;
import java.time.LocalDateTime;

public class AgendamentoOnline extends Agendamentos {
    private String linkReuniao;

    public AgendamentoOnline() {
    }

    public AgendamentoOnline(Integer id, String medico, LocalDateTime dataHora,
                             String nomeConsulta, String nomeProfissional,
                             Paciente paciente, String linkReuniao) {
        super(id, medico, dataHora, nomeConsulta, nomeProfissional, paciente);
        this.linkReuniao = linkReuniao;
    }

    public String getLinkReuniao() {
        return linkReuniao;
    }

    public void setLinkReuniao(String linkReuniao) {
        this.linkReuniao = linkReuniao;
    }
}
