import java.io.*;

/**
 * Classe responsável por gerenciar a pontuação do jogo, incluindo a adição de pontos,
 * o carregamento do recorde salvo em arquivo e a atualização do recorde.
 */
public class Pontuacao {
    /**
     * A pontuação atual do jogador.
     */
    private static int pontos;

    /**
     * Caminho do arquivo onde o recorde é armazenado.
     */
    private static final String ARQUIVO_RECORD = "recorde.txt";

    /**
     * Construtor da classe, que inicializa a pontuação em zero.
     */
    public Pontuacao() {
        pontos = 0; // Garantir que os pontos começam em 0
    }

    /**
     * Método para adicionar uma quantidade de pontos à pontuação atual.
     * @param valor A quantidade de pontos a ser adicionada.
     * @return A pontuação atual após a adição.
     */
    public static int adicionarPontos(int valor) {
        pontos += valor;
        return pontos;
    }

    /**
     * Método para obter a pontuação atual.
     * @return A pontuação atual.
     */
    public int getPontos() {
        return pontos;
    }

    /**
     * Método para carregar o recorde armazenado em arquivo.
     * Se o arquivo não existir ou houver um erro de leitura, retorna 0.
     * @return O valor do recorde, ou 0 se não houver recorde salvo.
     */
    public static int carregarRecorde() {
        try (BufferedReader br = new BufferedReader(new FileReader(ARQUIVO_RECORD))) {
            String linha = br.readLine();
            return linha != null ? Integer.parseInt(linha) : 0;
        } catch (Exception e) {
            return 0; // Nenhum recorde salvo ainda
        }
    }

    /**
     * Método para salvar o recorde, caso a pontuação atual seja maior que o recorde anterior.
     * Caso a pontuação atual ultrapasse o recorde, o valor é salvo no arquivo de recorde.
     */
    public void salvarSeForRecorde() {
        int recordeAtual = carregarRecorde();
        if (pontos > recordeAtual) {
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(ARQUIVO_RECORD))) {
                bw.write(String.valueOf(pontos));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
