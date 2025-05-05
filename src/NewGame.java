import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * Classe que representa a janela de seleção de novo jogo ou carregamento de progresso.
 * Permite ao usuário iniciar o jogo diretamente ou escolher um nível de dificuldade.
 */
public class NewGame extends JFrame {

    private JButton btnNovoJogo;
    private JButton btnCarregarJogo;
    private JButton btnNivel1;
    private JButton btnNivel2;
    private JButton btnNivel3;
    private JButton btnNivel4;
    private JButton btnNivel5;
    private JLabel background;

    /**
     * Construtor que inicializa a interface da tela de novo jogo.
     */
    public NewGame() {
        setTitle("Novo Jogo");
        setSize(800, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setResizable(false);

        // Carrega a imagem de fundo
        ImageIcon icon = new ImageIcon(getClass().getResource("/assets/fundos/1.png"));
        Image img = icon.getImage().getScaledInstance(800, 700, Image.SCALE_SMOOTH);
        background = new JLabel(new ImageIcon(img));
        background.setBounds(0, 0, 800, 700);
        setContentPane(background);
        background.setLayout(null);

        criarBotoes();
        setVisible(true);
    }

    /**
     * Cria e posiciona os botões na tela.
     */
    private void criarBotoes() {
        btnNovoJogo = criarBotao("Novo Jogo", 300, 110, e -> iniciarJogoComDificuldade(Dificuldade.FACIL));
        btnCarregarJogo = criarBotao("Carregar Jogo", 300, 160, e -> carregarJogo());

        btnNivel1 = criarBotao("Nível Fácil", 300, 280, e -> iniciarJogoComDificuldade(Dificuldade.FACIL));
        btnNivel2 = criarBotao("Nível Médio", 300, 350, e -> iniciarJogoComDificuldade(Dificuldade.MEDIO));
        btnNivel3 = criarBotao("Nível Difícil", 300, 420, e -> iniciarJogoComDificuldade(Dificuldade.DIFICIL));

        // Inicialmente desabilita níveis mais altos
        btnNivel2.setEnabled(false);
        btnNivel3.setEnabled(false);

        background.add(btnNovoJogo);
        background.add(btnCarregarJogo);
        background.add(btnNivel1);
        background.add(btnNivel2);
        background.add(btnNivel3);
    }

    /**
     * Cria um botão com estilo e comportamento personalizados.
     *
     * @param texto Texto do botão.
     * @param x Posição horizontal.
     * @param y Posição vertical.
     * @param action Ação executada ao clicar no botão.
     * @return O botão configurado.
     */
    private JButton criarBotao(String texto, int x, int y, ActionListener action) {
        JButton botao = new JButton(texto);
        botao.setFont(new Font("Arial", Font.BOLD, 18));
        botao.setBackground(new Color(0, 0, 0, 180));
        botao.setForeground(Color.WHITE);
        botao.setFocusPainted(false);
        botao.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
        botao.setBounds(x, y, 200, 50);
        botao.addActionListener(action);
        return botao;
    }

    /**
     * Inicia o jogo com a dificuldade especificada e abre uma nova janela do jogo.
     *
     * @param dificuldade Dificuldade selecionada para iniciar o jogo.
     */
    private void iniciarJogoComDificuldade(Dificuldade dificuldade) {
        JFrame janelaJogo = new JFrame("Jogo da Memória - " + dificuldade.name());
        janelaJogo.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        janelaJogo.setResizable(false);

        PainelDeJogo painel = new PainelDeJogo(dificuldade);
        janelaJogo.add(painel);
        janelaJogo.pack();
        janelaJogo.setLocationRelativeTo(null);
        janelaJogo.setVisible(true);

        liberarProximoNivel(dificuldade);
    }

    /**
     * Simula o carregamento de um jogo salvo e libera o segundo nível.
     */
    private void carregarJogo() {
        JOptionPane.showMessageDialog(this, "Carregando Jogo...", "Carregar Jogo", JOptionPane.INFORMATION_MESSAGE);
        iniciarJogoComDificuldade(Dificuldade.FACIL);
        liberarNivel(2);
    }

    /**
     * Libera o botão de acesso ao nível especificado.
     *
     * @param nivel Número do nível a ser liberado.
     */
    private void liberarNivel(int nivel) {
        switch (nivel) {
            case 2 -> btnNivel2.setEnabled(true);
            case 3 -> btnNivel3.setEnabled(true);
        }
    }

    /**
     * Libera o próximo nível de dificuldade com base no nível atual jogado.
     *
     * @param atual Dificuldade atual completada.
     */
    private void liberarProximoNivel(Dificuldade atual) {
        if (atual == Dificuldade.FACIL) liberarNivel(2);
        else if (atual == Dificuldade.MEDIO) liberarNivel(3);
        else if (atual == Dificuldade.DIFICIL) liberarNivel(4); // ou 5 futuramente
    }
}
