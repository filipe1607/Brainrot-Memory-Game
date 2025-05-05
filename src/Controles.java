import javax.sound.sampled.*;
import java.net.URL;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Classe responsável pelo controle da lógica de interação do jogador com as cartas,
 * gerenciamento de tentativas, pares encontrados e reprodução de sons.
 */
public class Controles {
    private Carta primeiraCarta = null;
    private Carta segundaCarta = null;
    private boolean aguardando = false;
    private Timer timer = new Timer();
    private PainelDeJogo painel;
    private int tentativas = 0;

    /**
     * Construtor que associa os controles a um painel de jogo.
     *
     * @param painel O painel de jogo onde a lógica será aplicada.
     */
    public Controles(PainelDeJogo painel) {
        this.painel = painel;
    }

    /**
     * Método chamado ao clicar em uma carta. Controla o processo de virada e verificação de par.
     *
     * @param carta A carta que foi clicada.
     */
    public void clicarCarta(Carta carta) {
        if (aguardando || carta.isVirada() || carta.isEncontrada()) return;
        aguardando = true;

        carta.virarComAnimacao(() -> {
            tocarSom("/assets/audios/ping.wav");

            if (primeiraCarta == null) {
                primeiraCarta = carta;
                aguardando = false;
            } else if (segundaCarta == null) {
                segundaCarta = carta;
                tentativas++;
                verificarPar();
            }
        });
    }

    /**
     * Verifica se as duas cartas selecionadas formam um par. Caso afirmativo, marca como encontradas.
     * Caso contrário, realiza a animação de desvirar as cartas. Sons são reproduzidos conforme o resultado.
     */
    private void verificarPar() {
        aguardando = true;

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (primeiraCarta.getId() == segundaCarta.getId()) {
                    primeiraCarta.setEncontrada(true);
                    segundaCarta.setEncontrada(true);
                    tocarSom("/assets/audios/not.wav");
                    painel.atualizarPontuacao();

                    painel.verificarFimDeJogo();
                } else {
                    primeiraCarta.virarComAnimacao(null);
                    segundaCarta.virarComAnimacao(null);
                    tocarSom("/assets/audios/error.wav");
                }

                primeiraCarta = null;
                segundaCarta = null;
                aguardando = false;
            }
        }, 500); // tempo de espera para o jogador visualizar as cartas
    }

    /**
     * Reproduz um efeito sonoro a partir do caminho do recurso de áudio.
     *
     * @param caminhoM Caminho relativo do arquivo de som dentro do projeto.
     */
    private void tocarSom(String caminhoM) {
        try {
            URL url = getClass().getResource(caminhoM);
            if (url != null) {
                AudioInputStream audioIn = AudioSystem.getAudioInputStream(url);
                Clip clip = AudioSystem.getClip();
                clip.open(audioIn);
                clip.start();
            } else {
                System.err.println("Som não encontrado: " + caminhoM);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Retorna o número de tentativas feitas pelo jogador.
     *
     * @return Número de tentativas.
     */
    public int getTentativas() {
        return tentativas;
    }
}
