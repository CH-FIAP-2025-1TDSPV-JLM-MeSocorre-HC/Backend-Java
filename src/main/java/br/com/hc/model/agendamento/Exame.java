package br.com.hc.model.agendamento;

import java.time.LocalDateTime;

public class Exame {
    private String resultadosexame;
    private String tipo;
    private int id;
    private LocalDateTime diaHora;

    public Exame(String resultadosexame, String fazerexame) {
        this.resultadosexame = resultadosexame;

    }

    public String getResultadosexame() {
        return resultadosexame;
    }

    public void setResultadosexame(String resultadosexame) {
        this.resultadosexame = resultadosexame;
    }



    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getTipo() {
        return tipo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDateTime getDiaHora() {
        return diaHora;
    }

    public void setDiaHora(LocalDateTime diaHora) {
        this.diaHora = diaHora;
    }
}

