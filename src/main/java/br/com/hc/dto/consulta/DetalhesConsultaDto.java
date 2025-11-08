package br.com.hc.dto.consulta;

public class DetalhesConsultaDto {
    private Integer id;
    private String paciente;
    private String medico;
    private String dataHora;
    private String motivo;

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getPaciente() { return paciente; }
    public void setPaciente(String paciente) { this.paciente = paciente; }

    public String getMedico() { return medico; }
    public void setMedico(String medico) { this.medico = medico; }

    public String getDataHora() { return dataHora; }
    public void setDataHora(String dataHora) { this.dataHora = dataHora; }

    public String getMotivo() { return motivo; }
    public void setMotivo(String motivo) { this.motivo = motivo; }
}