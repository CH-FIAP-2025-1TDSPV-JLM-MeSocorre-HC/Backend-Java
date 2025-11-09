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

        // Mapeamento mais rígido (para evitar confusões de nomes)
        modelMapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.STRICT)
                .setSkipNullEnabled(true);

        // ================================================
        // DTO -> ENTIDADE (CadastroAgendamentoDto → modelos)
        // ================================================

        modelMapper.typeMap(CadastroAgendamentoDto.class, AgendamentoOnline.class)
                .addMapping(CadastroAgendamentoDto::getLink, AgendamentoOnline::setLinkReuniao);

        modelMapper.typeMap(CadastroAgendamentoDto.class, AgendamentoPresencial.class)
                .addMapping(CadastroAgendamentoDto::getEndereco, AgendamentoPresencial::setEndereco);

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
                .addMapping(AgendamentoPresencial::getEndereco, DetalhesAgendamentosDto::setEndereco);

        modelMapper.typeMap(Exame.class, DetalhesAgendamentosDto.class)
                .addMappings(mapper -> {
                    mapper.map(Exame::getTipo, DetalhesAgendamentosDto::setTipoExame);
                    mapper.map(Exame::getResultadoExame, DetalhesAgendamentosDto::setResultadoExame);
                });

        return modelMapper;
    }
}
