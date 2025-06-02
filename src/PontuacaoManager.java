import java.io.*;

/**
 * Classe responsável por gerenciar o arquivo de recordes,
 * incluindo a leitura e escrita do recorde de pontos em arquivo.
 */
public class PontuacaoManager {
    /**
     * Caminho do arquivo onde o recorde de pontos é armazenado.
     */
    private static final String ARQUIVO_RECORDES = "recorde.txt";

    /**
     * Método para salvar o recorde de pontos no arquivo.
     * Se houver um novo recorde, ele é escrito no arquivo de recordes.
     * @param pontos A pontuação a ser salva como recorde.
     */
    public static void salvarRecorde(int pontos) {
        try (PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(ARQUIVO_RECORDES)))) {
            writer.println(pontos);  // Escreve a pontuação no arquivo
        } catch (IOException e) {
            e.printStackTrace();  // Em caso de erro de IO, imprime o erro
        }
    }

    /**
     * Método para carregar o recorde de pontos do arquivo.
     * Caso o arquivo não exista ou haja algum erro, o método retorna 0.
     * @return O recorde de pontos ou 0 se o arquivo não existir ou houver erro.
     */
    public static int carregarRecorde() {
        File arquivo = new File(ARQUIVO_RECORDES);

        // Verifica se o arquivo de recorde existe
        if (!arquivo.exists()) {
            try {
                arquivo.createNewFile();  // Cria o arquivo se não existir
            } catch (IOException e) {
                e.printStackTrace();  // Em caso de erro ao criar o arquivo
            }
            return 0;  // Retorna 0 se o arquivo foi criado ou não existia
        }

        // Tenta ler o conteúdo do arquivo de recorde
        try (BufferedReader reader = new BufferedReader(new FileReader(ARQUIVO_RECORDES))) {
            String linha = reader.readLine();  // Lê a primeira linha do arquivo
            return linha != null ? Integer.parseInt(linha) : 0;  // Retorna o valor lido ou 0 se vazio
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();  // Em caso de erro de IO ou formato inválido
            return 0;  // Retorna 0 caso ocorra um erro de leitura
        }
    }
}
