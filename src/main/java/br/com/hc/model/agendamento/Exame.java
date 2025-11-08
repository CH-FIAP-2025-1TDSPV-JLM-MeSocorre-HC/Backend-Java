package br.com.hc.model.agendamento;

import java.time.LocalDateTime;

public class Exame {
    private int id;
    private String resultadoExame;
    private String tipo;
    private LocalDateTime diaHora;

    public Exame(int id, String resultadoExame, String tipo, LocalDateTime diaHora) {
        this.id = id;
        this.resultadoExame = resultadoExame;
        this.tipo = tipo;
        this.diaHora = diaHora;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getResultadoExame() {
        return resultadoExame;
    }

    public void setResultadoExame(String resultadoExame) {
        this.resultadoExame = resultadoExame;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public LocalDateTime getDiaHora() {
        return diaHora;
    }

    public void setDiaHora(LocalDateTime diaHora) {
        this.diaHora = diaHora;
    }
}

