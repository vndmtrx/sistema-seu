package zip.fediverso.seu.diario_classe_v1.negocio.diario;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Interface responsável por mapear objetos entre DiarioEntidade e DiarioDto.
 * Esta interface é utilizada pelo MapStruct para gerar automaticamente a implementação das operações de mapeamento.
 */
@Mapper
public interface DiarioMapper {

    /**
     * Converte uma instância de DiarioEntidade em uma instância de DiarioDto.
     *
     * @param diario a instância de DiarioEntidade a ser convertida em DiarioDto
     * @return uma instância de DiarioDto contendo os dados mapeados da instância de DiarioEntidade
     */
    @Mapping(source = "diario.id", target = "id")
    @Mapping(source = "diario.criadoEm", target = "criadoEm")
    @Mapping(source = "diario.alteradoEm", target = "alteradoEm")
    @Mapping(source = "diario.versao", target = "versao")
    @Mapping(source = "diario.curso", target = "curso")
    @Mapping(source = "diario.turma", target = "turma")
    @Mapping(source = "diario.componente", target = "componente")
    @Mapping(source = "diario.turno", target = "turno")
    @Mapping(source = "diario.periodo", target = "periodo")
    DiarioDto paraDto(DiarioEntidade diario);

    /**
     * Converte uma instância de DiarioDto em uma instância de DiarioEntidade.
     *
     * @param diarioDto a instância de DiarioDto a ser convertida em DiarioEntidade
     * @return uma instância de DiarioEntidade contendo os dados mapeados da instância de DiarioDto
     */
    @Mapping(source = "id", target = "id")
    @Mapping(source = "criadoEm", target = "criadoEm")
    @Mapping(source = "alteradoEm", target = "alteradoEm")
    @Mapping(source = "versao", target = "versao")
    @Mapping(source = "curso", target = "curso")
    @Mapping(source = "turma", target = "turma")
    @Mapping(source = "componente", target = "componente")
    @Mapping(source = "turno", target = "turno")
    @Mapping(source = "periodo", target = "periodo")
    @Mapping(target = "vinculos", ignore = true)
    @Mapping(target = "encontros", ignore = true)
    DiarioEntidade paraEntidade(DiarioDto diarioDto);
}
