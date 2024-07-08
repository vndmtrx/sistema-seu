package zip.fediverso.seu.diario_classe_v1.negocio.aluno;

/**
 * Uma exceção personalizada para representar erros relacionados a operações com alunos.
 * Esta exceção estende RuntimeException, o que significa que é uma exceção não verificada.
 */
public class AlunoExcecao extends RuntimeException {

    /**
     * Constrói uma nova exceção com a mensagem de erro e a causa fornecidas.
     *
     * @param mensagem a mensagem de erro que descreve a exceção
     * @param causa a causa da exceção, pode ser nulo se a causa não for conhecida ou relevante
     */
    public AlunoExcecao(String mensagem, Throwable causa) {
        super(mensagem, causa);
    }
}
