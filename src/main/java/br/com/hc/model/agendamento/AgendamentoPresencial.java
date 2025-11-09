package br.com.hc.model.agendamento;

import br.com.hc.model.paciente.Paciente;
import java.time.LocalDateTime;

public class AgendamentoPresencial extends Agendamentos {
    private final String endereco = "Rua Dr. Ovídio Pires de Campos, 471";
    private Integer sala;
    private Integer andar;

    public AgendamentoPresencial() {
        setTipo("PRESENCIAL");
    }

    public AgendamentoPresencial(Integer id, String medico, LocalDateTime dataHora,
                                 String nomeConsulta, String nomeProfissional,
                                 Paciente paciente, Integer sala, Integer andar) {
        super(id, medico, dataHora, nomeConsulta, nomeProfissional, paciente);
        setTipo("PRESENCIAL");
        this.sala = sala;
        this.andar = andar;
    }

    public String getEndereco() {
        return endereco;
    }

    public Integer getSala() {
        return sala;
    }

    public void setSala(Integer sala) {
        this.sala = sala;
    }

    public Integer getAndar() {
        return andar;
    }

    public void setAndar(Integer andar) {
        this.andar = andar;
    }
}
