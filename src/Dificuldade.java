/**
 * Enumeração que representa os níveis de dificuldade do jogo da memória.
 * Cada dificuldade define o número de linhas, colunas e tempo disponível para completar o jogo.
 */
public enum Dificuldade {
    /**
     * Nível fácil: tabuleiro 4x4, 120 segundos.
     */
    FACIL(4, 4, 100),

    /**
     * Nível médio: tabuleiro 5x4, 90 segundos.
     */
    MEDIO(5, 4, 80),

    /**
     * Nível difícil: tabuleiro 6x6, 60 segundos.
     */
    DIFICIL(6, 6, 60);

    /**
     * Número de linhas do tabuleiro para esta dificuldade.
     */
    public final int linhas;

    /**
     * Número de colunas do tabuleiro para esta dificuldade.
     */
    public final int colunas;

    /**
     * Tempo limite em segundos para esta dificuldade.
     */
    public final int tempoSegundos;

    /**
     * Construtor da enumeração que define as configurações para cada dificuldade.
     *
     * @param linhas        Número de linhas do tabuleiro.
     * @param colunas       Número de colunas do tabuleiro.
     * @param tempoSegundos Tempo disponível para completar o jogo (em segundos).
     */
    Dificuldade(int linhas, int colunas, int tempoSegundos) {
        this.linhas = linhas;
        this.colunas = colunas;
        this.tempoSegundos = tempoSegundos;
    }

    /**
     * Retorna a próxima dificuldade em ordem de progressão.
     *
     * @param atual A dificuldade atual.
     * @return A próxima dificuldade, ou {@code null} se não houver mais níveis.
     */
    public static Dificuldade proximaDificuldade(Dificuldade atual) {
        switch (atual) {
            case FACIL:
                return Dificuldade.MEDIO;
            case MEDIO:
                return Dificuldade.DIFICIL;
            default:
                return null;
        }
    }
}
