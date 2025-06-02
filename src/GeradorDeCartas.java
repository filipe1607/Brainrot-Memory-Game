import java.util.ArrayList;
import java.util.Collections;

/**
 * Classe utilitária responsável por gerar as cartas do jogo da memória.
 * As cartas são criadas em pares e embaralhadas antes de serem retornadas.
 */
public class GeradorDeCartas {

    /**
     * Gera uma lista de cartas com imagens correspondentes em pares,
     * configuradas com o painel de jogo fornecido.
     *
     * @param painel  O painel onde as cartas serão desenhadas e atualizadas.
     * @param linhas  Número de linhas do tabuleiro.
     * @param colunas Número de colunas do tabuleiro.
     * @return Uma lista embaralhada de cartas em pares.
     * @throws IllegalArgumentException Se o número total de cartas (linhas x colunas) não for par.
     */
    public static ArrayList<Carta> gerarCartasPequenasComPainel(PainelDeJogo painel, int linhas, int colunas) {
        ArrayList<Carta> cartas = new ArrayList<>();

        String caminhoCosta = "src/assets/cards/costa.png"; // imagem padrão do verso da carta
        int totalCartas = linhas * colunas;
        int totalPares = totalCartas / 2;

        // Verifica se o número total de cartas é par
        if (totalCartas % 2 != 0) {
            throw new IllegalArgumentException("O número de cartas deve ser par!");
        }

        for (int i = 1; i <= totalPares; i++) {
            String caminhoFrente = "src/assets/cards/carta" + i + ".jpg"; // frente única por ID

            // Verifica se o arquivo de imagem da frente da carta existe
            if (!new java.io.File(caminhoFrente).exists()) {
                System.err.println("Imagem não encontrada: " + caminhoFrente);
                continue;
            }

            // Cria duas cartas idênticas (par)
            Carta carta1 = new Carta(caminhoFrente, caminhoCosta, i);
            Carta carta2 = new Carta(caminhoFrente, caminhoCosta, i);

            // Define o painel para que as cartas possam ser redesenhadas quando viradas
            carta1.setPainel(painel);
            carta2.setPainel(painel);

            cartas.add(carta1);
            cartas.add(carta2);
        }

        // Embaralha as cartas antes de retornar
        Collections.shuffle(cartas);
        return cartas;
    }
}
