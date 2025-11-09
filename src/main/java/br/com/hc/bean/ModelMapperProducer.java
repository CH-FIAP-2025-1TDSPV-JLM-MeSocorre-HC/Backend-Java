package br.com.hc.bean;

import br.com.hc.dto.agendamentos.CadastroAgendamentoDto;
import br.com.hc.dto.agendamentos.DetalhesAgendamentosDto;
import br.com.hc.model.agendamento.*;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;

@ApplicationScoped
public class ModelMapperProducer {

    @Produces
    @ApplicationScoped
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();

        // Configuração mais rígida (evita mapeamentos automáticos errados)
        modelMapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.STRICT)
                .setSkipNullEnabled(true);

        // ================================================
        // DTO -> ENTIDADE (CadastroAgendamentoDto → modelos)
        // ================================================

        // Mapeamento específico para agendamento online
        modelMapper.typeMap(CadastroAgendamentoDto.class, AgendamentoOnline.class)
                .addMapping(CadastroAgendamentoDto::getLink, AgendamentoOnline::setLinkReuniao);

        // Mapeamento para agendamento presencial
        modelMapper.typeMap(CadastroAgendamentoDto.class, AgendamentoPresencial.class)
                .addMappings(mapper -> {
                    mapper.map(CadastroAgendamentoDto::getSala, AgendamentoPresencial::setSala);
                    mapper.map(CadastroAgendamentoDto::getAndar, AgendamentoPresencial::setAndar);
                });

        // Mapeamento para exame
        modelMapper.typeMap(CadastroAgendamentoDto.class, Exame.class)
                .addMappings(mapper -> {
                    mapper.map(CadastroAgendamentoDto::getTipoExame, Exame::setTipo);
                    mapper.map(CadastroAgendamentoDto::getResultadoExame, Exame::setResultadoExame);
                });

        // ================================================
        // ENTIDADE -> DTO (Agendamentos → DetalhesAgendamentosDto)
        // ================================================

        modelMapper.typeMap(AgendamentoOnline.class, DetalhesAgendamentosDto.class)
                .addMapping(AgendamentoOnline::getLinkReuniao, DetalhesAgendamentosDto::setLink);

        modelMapper.typeMap(AgendamentoPresencial.class, DetalhesAgendamentosDto.class)
                .addMappings(mapper -> {
                    mapper.map(AgendamentoPresencial::getEndereco, DetalhesAgendamentosDto::setEndereco);
                    mapper.map(AgendamentoPresencial::getSala, DetalhesAgendamentosDto::setSala);
                    mapper.map(AgendamentoPresencial::getAndar, DetalhesAgendamentosDto::setAndar);
                });

        modelMapper.typeMap(Exame.class, DetalhesAgendamentosDto.class)
                .addMappings(mapper -> {
                    mapper.map(Exame::getTipo, DetalhesAgendamentosDto::setTipoExame);
                    mapper.map(Exame::getResultadoExame, DetalhesAgendamentosDto::setResultadoExame);
                });

        return modelMapper;
    }
}
