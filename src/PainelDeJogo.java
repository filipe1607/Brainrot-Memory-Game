import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Classe responsável por gerenciar a interface gráfica do jogo da memória,
 * controlando a exibição das cartas, o tempo, a pontuação, tentativas, a interação do jogador
 * e as funcionalidades de pausa e término do jogo.
 */
public class PainelDeJogo extends JPanel {

    /**
     * Lista de cartas no jogo.
     */
    private ArrayList<Carta> cartas;

    /**
     * Nível de dificuldade do jogo.
     */
    private Dificuldade dificuldade;

    /**
     * Timer para controlar a contagem regressiva do tempo.
     */
    private javax.swing.Timer timer;

    /**
     * Tempo restante no jogo, em segundos.
     */
    private int tempoRestante;

    /**
     * Largura de cada carta.
     */
    private final int cartaLargura = 90;

    /**
     * Altura de cada carta.
     */
    private final int cartaAltura = 90;

    /**
     * Espaçamento entre as cartas.
     */
    private final int espacamento = 10;

    /**
     * Margem superior para os elementos na interface.
     */
    private final int margemTopo = 60;

    /**
     * Rótulo que exibe a quantidade de tentativas realizadas pelo jogador.
     */
    private JLabel tentativasLabel;

    /**
     * Rótulo que exibe a pontuação do jogador.
     */
    private JLabel pontuacaoLabel;

    /**
     * Botão que permite pausar e continuar o jogo.
     */
    private JButton pauseButton;

    /**
     * Indicador de que o jogo está pausado ou não.
     */
    private boolean jogoPausado;

    /**
     * Indicador de que o jogo está no estado de visualização das cartas (preview).
     */
    private boolean emPreview = true;

    /**
     * Timer para o preview das cartas no início do jogo.
     */
    private Timer timerPreview;

    /**
     * Controles que gerenciam a lógica do jogo, como as interações com as cartas.
     */
    private Controles controles;

    /**
     * Nome do jogador.
     */
    private String nomeJogador;

    /**
     * Construtor que inicializa o painel de jogo com o nível de dificuldade escolhido.
     * Configura o layout, as cartas, os rótulos, o botão de pausa e inicia o timer.
     * @param dificuldade Nível de dificuldade do jogo.
     */
    public PainelDeJogo(Dificuldade dificuldade) {
        this.dificuldade = dificuldade;
        this.tempoRestante = dificuldade.tempoSegundos;
        this.jogoPausado = false;

        // Solicita o nome do jogador
        nomeJogador = JOptionPane.showInputDialog(null, "Digite seu nome:", "Jogador");

        // Configura o tamanho do painel de acordo com a dificuldade (linhas e colunas)
        setPreferredSize(new Dimension(
                dificuldade.colunas * cartaLargura + (dificuldade.colunas - 1) * espacamento,
                dificuldade.linhas * cartaAltura + (dificuldade.linhas - 1) * espacamento + margemTopo
        ));

        setLayout(null);

        // Gera as cartas do jogo com base na dificuldade e configura o painel
        cartas = GeradorDeCartas.gerarCartasPequenasComPainel(this, dificuldade.linhas, dificuldade.colunas);
        for (Carta carta : cartas) carta.setPainel(this);

        // Inicializa os controles
        controles = new Controles(this);

        // Criação do rótulo de pontuação
        pontuacaoLabel = new JLabel("Pontuação: ");
        pontuacaoLabel.setFont(new Font("Arial", Font.BOLD, 14));
        pontuacaoLabel.setBounds(10, 10, 200, 30);
        add(pontuacaoLabel);

        // Criação do rótulo de tentativas
        tentativasLabel = new JLabel("Tentativas: 0", SwingConstants.CENTER);
        tentativasLabel.setFont(new Font("Arial", Font.BOLD, 14));
        tentativasLabel.setBounds((getPreferredSize().width / 2) - 60, 10, 120, 30);
        add(tentativasLabel);

        // Botão de pausa e continuação do jogo
        pauseButton = new JButton("⏸ Pause");
        pauseButton.setFont(new Font("Arial", Font.BOLD, 14));
        pauseButton.setBounds(getPreferredSize().width - 120, 10, 110, 30);
        pauseButton.addActionListener(e -> pausarJogo());
        add(pauseButton);

        // Adiciona o evento de clique do mouse para interagir com as cartas
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (jogoPausado || emPreview) return;

                int x = e.getX();
                int y = e.getY() - margemTopo;

                // Verifica em qual carta o jogador clicou
                for (int i = 0; i < cartas.size(); i++) {
                    int linha = i / dificuldade.colunas;
                    int coluna = i % dificuldade.colunas;

                    int cartaX = coluna * (cartaLargura + espacamento);
                    int cartaY = linha * (cartaAltura + espacamento);

                    // Se o clique for dentro dos limites da carta
                    if (x >= cartaX && x <= cartaX + cartaLargura &&
                            y >= cartaY && y <= cartaY + cartaAltura) {
                        controles.clicarCarta(cartas.get(i));
                        tentativasLabel.setText("Tentativas: " + controles.getTentativas());
                        break;
                    }
                }

                // Verifica se o jogo foi finalizado e exibe o resultado
                if (isJogoFinalizado()) {
                    timer.stop();
                    int pontos = tempoRestante * 10;
                    int recorde = PontuacaoManager.carregarRecorde();

                    if (pontos > recorde) {
                        PontuacaoManager.salvarRecorde(pontos);
                    } else {
                        JOptionPane.showMessageDialog(null,
                                nomeJogador + ", sua pontuação: " + pontos +
                                        "\nTentativas: " + controles.getTentativas());
                    }

                    // Fecha a janela de jogo e abre o menu inicial
                    SwingUtilities.getWindowAncestor(PainelDeJogo.this).dispose();
                    new MenuInicial().setVisible(true);
                }
            }
        });

        // Inicia o timer do jogo
        iniciarTimer();

        // Inicia o preview das cartas
        iniciarPreview();

        // Salva as tentativas no ranking
        RankingManager.salvar(nomeJogador, controles.getTentativas());
    }

    /**
     * Sobrescreve o método de pintura do painel. Desenha as cartas, a pontuação, o tempo e o botão de pausa.
     * @param g Objeto Graphics usado para desenhar na tela.
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        pauseButton.setBounds(getWidth() - 120, 10, 110, 30);

        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.BOLD, 16));
        g.drawString("Tempo: " + tempoRestante + "s", 10, 50);
        tentativasLabel.setBounds((getWidth() / 2) - 60, 10, 120, 30);

        // Desenha as cartas na tela
        for (int i = 0; i < cartas.size(); i++) {
            int linha = i / dificuldade.colunas;
            int coluna = i % dificuldade.colunas;

            int x = coluna * (cartaLargura + espacamento);
            int y = linha * (cartaAltura + espacamento) + margemTopo;

            cartas.get(i).desenhar(g, x, y, cartaLargura, cartaAltura);
        }
    }

    /**
     * Inicia a contagem regressiva do timer do jogo. Cada segundo reduz o tempo restante.
     */
    private void iniciarTimer() {
        timer = new javax.swing.Timer(1000, e -> {
            if (!jogoPausado) {
                tempoRestante--;
                if (tempoRestante <= 0) {
                    timer.stop();
                    new TelaDerrota(nomeJogador, tempoRestante * 10, controles.getTentativas(), dificuldade);
                    SwingUtilities.getWindowAncestor(PainelDeJogo.this).dispose();
                }
                repaint();
            }
        });
        timer.start();
    }

    /**
     * Pausa ou retoma o jogo ao clicar no botão de pausa.
     */
    private void pausarJogo() {
        jogoPausado = !jogoPausado;
        pauseButton.setText(jogoPausado ? "▶ Continuar" : "⏸ Pause");
        if (jogoPausado) timer.stop();
        else iniciarTimer();
    }

    /**
     * Verifica se o jogo foi finalizado. O jogo é considerado finalizado se todas as cartas forem encontradas.
     * @return true se o jogo terminou, false caso contrário.
     */
    public boolean isJogoFinalizado() {
        for (Carta c : cartas) if (!c.isEncontrada()) return false;
        return true;
    }

    /**
     * Atualiza a pontuação exibida na interface.
     */
    public void atualizarPontuacao() {
        pontuacaoLabel.setText("Pontuação: " + Pontuacao.adicionarPontos(100));
    }

    /**
     * Retorna o nível de dificuldade atual do jogo.
     * @return O nível de dificuldade atual.
     */
    public Dificuldade getDificuldade() {
        return dificuldade;
    }

    /**
     * Método principal para testar o painel de jogo em uma janela.
     * @param args Argumentos da linha de comando (não utilizados).
     */
    public static void main(String[] args) {
        Dificuldade dificuldade = Dificuldade.FACIL;

        JFrame frame = new JFrame("Jogo da Memória");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        PainelDeJogo painel = new PainelDeJogo(dificuldade);
        frame.add(painel);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    /**
     * Inicia o preview das cartas no início do jogo. Durante o preview, todas as cartas ficam visíveis por um curto período.
     */
    private void iniciarPreview() {
        emPreview = true;

        // Vira todas as cartas para mostrar seu conteúdo no início
        for (Carta carta : cartas) {
            if (!carta.isEncontrada()) {
                carta.virarComAnimacao(null);
            }
        }

        // Cria um timer que irá reverter as cartas após 2 segundos
        timerPreview = new Timer();
        timerPreview.schedule(new TimerTask() {
            @Override
            public void run() {
                for (Carta carta : cartas) {
                    if (!carta.isEncontrada()) {
                        carta.virarComAnimacao(null);
                    }
                }
                emPreview = false;
                repaint();
            }
        }, 2000);
    }

    /**
     * Verifica o fim do jogo e exibe a tela de vitória ou derrota, com base na pontuação e tentativas do jogador.
     */
    public void verificarFimDeJogo() {
        // Verifica se o jogo foi finalizado
        if (isJogoFinalizado()) {
            // Calcula a pontuação
            int pontos = tempoRestante * 10;
            int tentativas = controles.getTentativas();

            // Verifica se o jogador alcançou um novo recorde
            int recorde = PontuacaoManager.carregarRecorde();

            if (pontos > recorde) {
                PontuacaoManager.salvarRecorde(pontos);
                JOptionPane.showMessageDialog(null,
                        "🎉 Parabéns, " + nomeJogador + "! Novo Recorde: " + pontos +
                                "\nTentativas: " + tentativas);
            }

            // Fecha a janela do painel de jogo
            SwingUtilities.getWindowAncestor(PainelDeJogo.this).dispose();

            // Abre a tela de vitória
            TelaVitoria vitoria = new TelaVitoria(nomeJogador, pontos, tentativas, dificuldade);
            vitoria.setVisible(true);
        } else if (tempoRestante <= 0) {
            // Se o tempo acabou, o jogador perde
            int pontos = tempoRestante * 10;
            int tentativas = controles.getTentativas();

            // Fecha a janela do painel de jogo
            SwingUtilities.getWindowAncestor(PainelDeJogo.this).dispose();

            // Exibe a tela de derrota
            new TelaDerrota(nomeJogador, pontos, tentativas, dificuldade).setVisible(true);
        }
    }
}
