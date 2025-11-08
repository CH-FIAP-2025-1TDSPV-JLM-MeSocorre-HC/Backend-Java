package br.com.hc.model.agendamento;

import br.com.hc.model.paciente.Paciente;
import java.time.LocalDateTime;

public class AgendamentoPresencial extends Agendamentos {
    private String endereco;
    private String sala;
    private String andar;

    // ✅ Construtor vazio (necessário para frameworks)
    public AgendamentoPresencial() {
    }

    // ✅ Construtor completo
    public AgendamentoPresencial(Integer id, String medico, LocalDateTime dataHora,
                                 String nomeConsulta, String nomeProfissional,
                                 Paciente paciente, String sala, String andar, String endereco) {
        super(id, medico, dataHora, nomeConsulta, nomeProfissional, paciente);
        this.sala = sala;
        this.andar = andar;
        this.endereco = endereco;
    }

    public String getSala() {
        return sala;
    }

    public void setSala(String sala) {
        this.sala = sala;
    }

    public String getAndar() {
        return andar;
    }

    public void setAndar(String andar) {
        this.andar = andar;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }
}
