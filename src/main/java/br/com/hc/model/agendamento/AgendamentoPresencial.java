package br.com.hc.model.agendamento;

public class AgendamentoPresencial extends Agendamentos {
    private String endereco;

    public AgendamentoPresencial(String endereco) {
        if (endereco == null || endereco.isEmpty())
            throw new IllegalArgumentException("Endereço é obrigatório.");
        this.endereco = endereco;
    }

    public String getEndereco() {
        return endereco;
    }
}
