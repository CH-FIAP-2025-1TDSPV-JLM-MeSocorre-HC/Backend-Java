package br.com.hc.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

public class CadastroAgendamentoDto {

    @NotBlank(message = "O tipo é obrigatório")
    private String tipo; // ONLINE, PRESENCIAL, EXAME

    @NotNull(message = "O paciente é obrigatório")
    private Integer pacienteId;

    @NotNull(message = "A data e hora são obrigatórias")
    private LocalDateTime dataHora;

    private String nomeConsulta;
    private String nomeProfissional;
    private String medico;

    // Campos específicos para cada tipo
    private String link;
    private String endereco;
    private String tipoExame;
    private String resultadoExame;

    // Getters e Setters
    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }

    public Integer getPacienteId() { return pacienteId; }
    public void setPacienteId(Integer pacienteId) { this.pacienteId = pacienteId; }

    public LocalDateTime getDataHora() { return dataHora; }
    public void setDataHora(LocalDateTime dataHora) { this.dataHora = dataHora; }

    public String getNomeConsulta() { return nomeConsulta; }
    public void setNomeConsulta(String nomeConsulta) { this.nomeConsulta = nomeConsulta; }

    public String getNomeProfissional() { return nomeProfissional; }
    public void setNomeProfissional(String nomeProfissional) { this.nomeProfissional = nomeProfissional; }

    public String getMedico() { return medico; }
    public void setMedico(String medico) { this.medico = medico; }

    public String getLink() { return link; }
    public void setLink(String link) { this.link = link; }

    public String getEndereco() { return endereco; }
    public void setEndereco(String endereco) { this.endereco = endereco; }

    public String getTipoExame() { return tipoExame; }
    public void setTipoExame(String tipoExame) { this.tipoExame = tipoExame; }

    public String getResultadoExame() { return resultadoExame; }
    public void setResultadoExame(String resultadoExame) { this.resultadoExame = resultadoExame; }
}