package br.com.hc.model.agendamento;

import br.com.hc.model.paciente.Paciente;
import java.time.LocalDateTime;

public class AgendamentoOnline extends Agendamentos {
    private String link;

    public AgendamentoOnline(Integer id, String medico, LocalDateTime dataHora,
                             String nomeConsulta, String nomeProfissional,
                             Paciente paciente, String link) {
        super(id, medico, dataHora, nomeConsulta, nomeProfissional, paciente);
        if (link == null || link.isEmpty())
            throw new IllegalArgumentException("Link é obrigatório.");
        this.link = link;
    }

    public String getLink() {
        return link;
    }
}

