package zip.fediverso.seu.diario_classe_v1.negocio.aluno;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Interface responsável por mapear objetos entre AlunoEntidade e AlunoDto.
 * Esta interface é utilizada pelo MapStruct para gerar automaticamente a implementação das operações de mapeamento.
 */
@Mapper
public interface AlunoMapper {
    /**
     * Converte uma instância de AlunoEntidade em uma instância de AlunoDto.
     *
     * @param aluno a instância de AlunoEntidade a ser convertida em AlunoDto
     * @return uma instância de AlunoDto contendo os dados mapeados da instância de AlunoEntidade
     */
    @Mapping(source = "aluno.id", target = "id")
    @Mapping(source = "aluno.criadoEm", target = "criadoEm")
    @Mapping(source = "aluno.alteradoEm", target = "alteradoEm")
    @Mapping(source = "aluno.versao", target = "versao")
    @Mapping(source = "aluno.matricula", target = "matricula")
    @Mapping(source = "aluno.nome", target = "nome")
    @Mapping(source = "aluno.status", target = "status")
    AlunoDto paraDto(AlunoEntidade aluno);

    /**
     * Converte uma instância de AlunoDto em uma instância de AlunoEntidade.
     *
     * @param alunoDto a instância de AlunoDto a ser convertida em AlunoEntidade
     * @return uma instância de AlunoEntidade contendo os dados mapeados da instância de AlunoDto
     */
    @Mapping(source = "id", target = "id")
    @Mapping(source = "criadoEm", target = "criadoEm")
    @Mapping(source = "alteradoEm", target = "alteradoEm")
    @Mapping(source = "versao", target = "versao")
    @Mapping(source = "matricula", target = "matricula")
    @Mapping(source = "nome", target = "nome")
    @Mapping(source = "status", target = "status")
    @Mapping(target = "vinculos", ignore = true)
    AlunoEntidade paraEntidade(AlunoDto alunoDto);
}
