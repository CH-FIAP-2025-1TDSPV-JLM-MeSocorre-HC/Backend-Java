    package br.com.hc.model.agendamento;

    import br.com.hc.model.paciente.Paciente;
    import java.time.LocalDateTime;

    public class AgendamentoOnline extends Agendamentos {
        private String link;

        public AgendamentoOnline() {
        }

        public AgendamentoOnline(Integer id, String medico, LocalDateTime dataHora,
                                 String nomeConsulta, String nomeProfissional,
                                 Paciente paciente, String link) {
            super(id, medico, dataHora, nomeConsulta, nomeProfissional, paciente);
            this.link = link;
        }

        public String getLinkReuniao() {
            return link;
        }

        public void setLinkReuniao(String link) {
            this.link = link;
        }
    }
