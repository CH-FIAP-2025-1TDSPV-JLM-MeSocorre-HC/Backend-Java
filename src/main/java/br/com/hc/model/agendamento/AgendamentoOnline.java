package br.com.hc.model.agendamento;

public class AgendamentoOnline extends Agendamentos {
    private String link;

    public AgendamentoOnline(String link) {
        if (link == null || link.isEmpty())
            throw new IllegalArgumentException("Link é obrigatório.");
        this.link = link;
    }

    public String getLink() {
        return link;
    }
}
