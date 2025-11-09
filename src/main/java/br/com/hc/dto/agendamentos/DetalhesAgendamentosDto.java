package br.com.hc.dto.agendamentos;

import java.time.LocalDateTime;

public class DetalhesAgendamentosDto {

    private Integer id;
    private String tipo;
    private Integer pacienteId;
    private LocalDateTime dataHora;
    private String nomeConsulta;
    private String nomeProfissional;
    private String medico;
    private String link;
    private String endereco;
    private String tipoExame;
    private String resultadoExame;
    private Integer sala;
    private Integer andar;

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


    // Getters e Setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

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