import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Representa uma carta do jogo da memória, contendo imagem da frente e do verso,
 * estado de virada/encontrada e lógica de animação de virada.
 */
public class Carta {
    private Image imagemFrente;
    private Image imagemCosta;
    private boolean virada = false;
    private boolean encontrada = false;
    private int id;
    private double escalaX = 1.0;
    private PainelDeJogo painel;

    /**
     * Construtor da carta, inicializando imagens de frente e verso com base nos caminhos fornecidos.
     *
     * @param caminhoFrente Caminho do arquivo da imagem da frente da carta.
     * @param caminhoCosta  Caminho do arquivo da imagem do verso da carta.
     * @param id            Identificador único da carta.
     */
    public Carta(String caminhoFrente, String caminhoCosta, int id) {
        try {
            this.imagemFrente = ImageIO.read(new File(caminhoFrente));
            this.imagemCosta = ImageIO.read(new File(caminhoCosta));
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.id = id;
    }

    /**
     * Define o painel de jogo ao qual esta carta pertence.
     *
     * @param painel O painel de jogo responsável pelo desenho e atualizações.
     */
    public void setPainel(PainelDeJogo painel) {
        this.painel = painel;
    }

    /**
     * Desenha a carta no painel gráfico, com escala de animação.
     *
     * @param g        Contexto gráfico.
     * @param x        Coordenada X do canto superior esquerdo.
     * @param y        Coordenada Y do canto superior esquerdo.
     * @param largura  Largura da carta.
     * @param altura   Altura da carta.
     */
    public void desenhar(Graphics g, int x, int y, int largura, int altura) {
        int larguraEscalada = (int) (largura * escalaX);
        int deslocamentoX = (largura - larguraEscalada) / 2;
        Image img = (virada || encontrada) ? imagemFrente : imagemCosta;
        g.drawImage(img, x + deslocamentoX, y, larguraEscalada, altura, null);
    }

    /**
     * Executa a animação de virada da carta, com efeito de escala simulando rotação.
     *
     * @param callback Função a ser executada ao final da animação (pode ser null).
     */
    public void virarComAnimacao(Runnable callback) {
        Timer timer = new Timer();
        final int passos = 10;
        final int intervalo = 30;
        final boolean virarParaFrente = !virada;

        timer.scheduleAtFixedRate(new TimerTask() {
            int passoAtual = 0;

            @Override
            public void run() {
                passoAtual++;
                if (passoAtual <= passos / 2) {
                    escalaX = 1.0 - (passoAtual / (double)(passos / 2));
                } else if (passoAtual == (passos / 2) + 1) {
                    virada = virarParaFrente;
                } else if (passoAtual <= passos) {
                    escalaX = (passoAtual - passos / 2) / (double)(passos / 2);
                } else {
                    escalaX = 1.0;
                    timer.cancel();
                    if (callback != null) callback.run(); // Chama o callback após a animação
                }
                if (painel != null) painel.repaint(); // Atualiza o painel durante a animação
            }
        }, 0, intervalo);
    }

    /**
     * Verifica se a carta está virada para cima.
     *
     * @return true se a carta estiver virada, false caso contrário.
     */
    public boolean isVirada() {
        return virada;
    }

    /**
     * Verifica se a carta já foi encontrada (par correto).
     *
     * @return true se a carta foi encontrada, false caso contrário.
     */
    public boolean isEncontrada() {
        return encontrada;
    }

    /**
     * Define se a carta foi encontrada (formou par).
     *
     * @param encontrada true para marcar como encontrada, false caso contrário.
     */
    public void setEncontrada(boolean encontrada) {
        this.encontrada = encontrada;
    }

    /**
     * Retorna o identificador único da carta (usado para comparação de pares).
     *
     * @return ID da carta.
     */
    public int getId() {
        return id;
    }
}
