package br.com.hc.model.agendamento;

import br.com.hc.model.paciente.Paciente;
import java.time.LocalDateTime;

public class Exame extends Agendamentos {
    private String resultadoExame;
    private String tipoExame;

    public Exame() {
        setTipo("EXAME");
    }

    public Exame(Integer id, String medico, LocalDateTime dataHora,
                 String nomeConsulta, String nomeProfissional,
                 Paciente paciente, String tipoExame, String resultadoExame) {
        super(id, medico, dataHora, nomeConsulta, nomeProfissional, paciente);
        setTipo("EXAME");
        this.tipoExame = tipoExame;
        this.resultadoExame = resultadoExame;
    }

    public String getResultadoExame() {
        return resultadoExame;
    }

    public void setResultadoExame(String resultadoExame) {
        this.resultadoExame = resultadoExame;
    }

    public String getTipoExame() {
        return tipoExame;
    }

    public void setTipoExame(String tipoExame) {
        this.tipoExame = tipoExame;
    }
}
