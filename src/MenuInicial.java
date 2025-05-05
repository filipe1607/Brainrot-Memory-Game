import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.sound.sampled.*;
import java.io.IOException;
import java.net.URL;

/**
 * Classe {@code MenuInicial} representa a tela principal do jogo da memória.
 * Contém os botões para iniciar o jogo, ver placares, sair, ativar/desativar música
 * e visualizar os créditos. Também é responsável por tocar música de fundo e
 * exibir o fundo animado (GIF).
 *
 * Esta interface gráfica utiliza {@code JFrame} como base e {@code JPanel} com layout nulo
 * para posicionamento absoluto dos componentes.
 *
 * @author João Pedro
 */
public class MenuInicial extends JFrame {

    // Declaração dos componentes da interface
    private JButton btnIniciarJogo;
    private JButton btnVerPlacares;
    private JButton btnSair;
    private JButton btnMusica;
    private JButton btnCreditos;
    private JLabel lblMembros;

    private Image backgroundImage;
    private boolean musicatocando = true;
    private Clip clip;

    /**
     * Construtor que inicializa e configura os componentes da interface do menu.
     * Define tamanho da janela, título, fundo, botões e eventos associados.
     */
    public MenuInicial() {
        // Configurações iniciais da janela
        setTitle("TRALALERO-MEMORY");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);

        // Carrega imagem de fundo animada
        backgroundImage = new ImageIcon(getClass().getResource("/assets/fundos/fundoMenu.gif")).getImage();

        // Painel personalizado com fundo animado
        JPanel painelPrincipal = new JPanel() {
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
            }
        };
        painelPrincipal.setLayout(null);
        setContentPane(painelPrincipal);

        // Título do jogo
        JLabel titulo = new JLabel("Memória De BrainRot", JLabel.CENTER);
        titulo.setFont(new Font("Verdana", Font.BOLD, 40));
        titulo.setForeground(Color.WHITE);
        titulo.setBounds(150, 50, 500, 100);
        painelPrincipal.add(titulo);

        // Botão Iniciar Jogo
        btnIniciarJogo = criarBotao("Iniciar Jogo");
        btnIniciarJogo.setBounds(300, 150, 200, 50);
        btnIniciarJogo.addActionListener(e -> {
            iniciarJogo();
            dispose();
        });

        // Botão Ver Placares
        btnVerPlacares = criarBotao("Ver Placares");
        btnVerPlacares.setBounds(300, 220, 200, 50);
        btnVerPlacares.addActionListener(e -> RankingManager.mostrarPlacar());

        // Botão Sair
        btnSair = criarBotao("Sair");
        btnSair.setBounds(300, 290, 200, 50);
        btnSair.addActionListener(e -> sair());

        // Botão Música
        btnMusica = new JButton("Música: ON");
        btnMusica.setFont(new Font("Arial", Font.PLAIN, 12));
        btnMusica.setFocusPainted(false);
        btnMusica.setBackground(new Color(0, 102, 204));
        btnMusica.setForeground(Color.WHITE);
        btnMusica.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
        btnMusica.setBounds(670, 500, 100, 40);
        btnMusica.addActionListener(e -> toggleMusica());

        // Botão Créditos
        btnCreditos = new JButton("Créditos");
        btnCreditos.setFont(new Font("Arial", Font.PLAIN, 12));
        btnCreditos.setFocusPainted(false);
        btnCreditos.setBackground(new Color(0, 102, 204));
        btnCreditos.setForeground(Color.WHITE);
        btnCreditos.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
        btnCreditos.setBounds(20, 500, 100, 40);
        btnCreditos.addActionListener(e -> mostrarCreditos());

        // Adiciona todos os botões ao painel
        painelPrincipal.add(btnIniciarJogo);
        painelPrincipal.add(btnVerPlacares);
        painelPrincipal.add(btnSair);
        painelPrincipal.add(btnMusica);
        painelPrincipal.add(btnCreditos);

        // Tocar música de fundo
        tocarMusica("/assets/fundos/fundo.wav");
    }

    /**
     * Inicia a reprodução da música de fundo em loop.
     * @param caminhoMusica Caminho do arquivo WAV no classpath.
     */
    private void tocarMusica(String caminhoMusica) {
        try {
            URL url = getClass().getResource(caminhoMusica);
            if (url == null) {
                JOptionPane.showMessageDialog(this, "Arquivo de música não encontrado!", "Erro de Música", JOptionPane.ERROR_MESSAGE);
                return;
            }
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(url);
            clip = AudioSystem.getClip();
            clip.open(audioIn);
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Erro ao carregar a música!", "Erro de Música", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Alterna o estado da música de fundo entre tocar e parar.
     */
    private void toggleMusica() {
        if (musicatocando) {
            clip.stop();
            btnMusica.setText("Música: OFF");
        } else {
            clip.loop(Clip.LOOP_CONTINUOUSLY);
            btnMusica.setText("Música: ON");
        }
        musicatocando = !musicatocando;
    }

    /**
     * Exibe uma janela com os nomes dos autores do projeto.
     */
    private void mostrarCreditos() {
        JOptionPane.showMessageDialog(this, "Créditos:\nJoão Pedro da Costa Souza RA: 114202411025\n" +
                "Edson Paulo da Silva Pinto Filho RA: 120093511025\n" +
                "Filipe Ribeiro De Sousa RA: 335322911025", "Créditos", JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Cria um botão estilizado com texto especificado.
     * @param texto Texto exibido no botão.
     * @return JButton com estilo pré-definido.
     */
    private JButton criarBotao(String texto) {
        JButton botao = new JButton(texto);
        botao.setFocusPainted(false);
        botao.setBackground(new Color(0, 0, 0, 200));
        botao.setForeground(Color.WHITE);
        botao.setFont(new Font("Arial", Font.BOLD, 18));
        botao.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
        return botao;
    }

    /**
     * Inicia o jogo chamando a classe {@code NewGame}.
     */
    private void iniciarJogo() {
        JOptionPane.showMessageDialog(this, "Iniciando o jogo...");
        new NewGame();
    }

    /**
     * Exibe a pontuação recorde atual em um dialog.
     */
    private void verPlacares() {
        int recorde = PontuacaoManager.carregarRecorde();
        JOptionPane.showMessageDialog(this, "Recorde atual: " + recorde + " pontos", "Placares", JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Fecha o jogo após confirmação do usuário e para a música de fundo.
     */
    private void sair() {
        int confirmacao = JOptionPane.showConfirmDialog(this, "Deseja realmente sair?", "Sair", JOptionPane.YES_NO_OPTION);
        if (confirmacao == JOptionPane.YES_OPTION) {
            if (clip != null && clip.isRunning()) {
                clip.stop();
            }
            System.exit(0);
        }
    }

    /**
     * Método principal para executar o menu inicial do jogo.
     * @param args Argumentos de linha de comando (não utilizados).
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MenuInicial().setVisible(true));
    }
}
