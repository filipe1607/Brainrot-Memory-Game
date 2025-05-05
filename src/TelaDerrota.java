import javax.swing.*;
import java.awt.*;

/**
 * Tela que exibe a mensagem de derrota do jogador, mostrando sua pontuação e tentativas,
 * com opções para recomeçar o jogo ou voltar ao menu inicial.
 */
public class TelaDerrota extends JFrame {
    private String nomeJogador;
    private int pontuacao;
    private int tentativas;
    private Dificuldade dificuldadeAtual;

    /**
     * Construtor da TelaDerrota, inicializa a interface gráfica.
     * @param nomeJogador O nome do jogador.
     * @param pontuacao A pontuação do jogador.
     * @param tentativas O número de tentativas do jogador.
     * @param dificuldadeAtual A dificuldade que o jogador estava jogando.
     */
    public TelaDerrota(String nomeJogador, int pontuacao, int tentativas, Dificuldade dificuldadeAtual) {
        this.nomeJogador = nomeJogador;
        this.pontuacao = pontuacao;
        this.tentativas = tentativas;
        this.dificuldadeAtual = dificuldadeAtual;

        // Configurações da janela
        setTitle("Jogo da Memória - Derrota");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Define o painel com fundo de imagem
        setContentPane(new PainelComImagemFundo("/assets/fundos/lose.png"));
        setLayout(null);

        // Carrega a fonte personalizada
        Font boldstromGrande = carregarFonte("/assets/fonts/Boldstrom.otf", 34f);
        Font boldstromMedia = carregarFonte("/assets/fonts/Boldstrom.otf", 20f);

        // Cria e configura o label de mensagem de derrota
        JLabel mensagemLabel = new JLabel("Você Perdeu!", SwingConstants.CENTER);
        mensagemLabel.setFont(boldstromGrande != null ? boldstromGrande : new Font("SansSerif", Font.BOLD, 45));
        mensagemLabel.setBounds(0, 20, 400, 30);
        mensagemLabel.setForeground(Color.RED);
        add(mensagemLabel);

        // Cria e configura o label com as informações do jogador
        JLabel infoLabel = new JLabel("<html>" + nomeJogador + ", sua pontuação: " + pontuacao +
                "<br/>Tentativas: " + tentativas + "</html>", SwingConstants.CENTER);
        infoLabel.setFont(boldstromMedia != null ? boldstromMedia : new Font("SansSerif", Font.PLAIN, 25));
        infoLabel.setBounds(50, 75, 300, 60);
        infoLabel.setForeground(Color.WHITE);
        add(infoLabel);

        // Botão para recomeçar o jogo
        JButton botaoRecomecar = new JButton("Recomeçar");
        botaoRecomecar.setBounds(50, 160, 120, 30);
        botaoRecomecar.addActionListener(e -> {
            new PainelDeJogo(dificuldadeAtual);  // Inicia um novo painel de jogo com a dificuldade atual
            dispose();  // Fecha a tela de derrota
        });
        add(botaoRecomecar);

        // Botão para voltar ao menu inicial
        JButton botaoMenu = new JButton("Voltar ao Menu");
        botaoMenu.setBounds(230, 160, 120, 30);
        botaoMenu.addActionListener(e -> {
            new MenuInicial().setVisible(true);  // Exibe o menu inicial
            dispose();  // Fecha a tela de derrota
        });
        add(botaoMenu);

        setVisible(true);  // Torna a tela visível
    }

    /**
     * Classe interna para criar um painel com uma imagem de fundo.
     */
    private static class PainelComImagemFundo extends JPanel {
        private Image imagem;

        /**
         * Construtor para carregar a imagem de fundo.
         * @param caminho O caminho da imagem de fundo.
         */
        public PainelComImagemFundo(String caminho) {
            try {
                imagem = new ImageIcon(getClass().getResource(caminho)).getImage();
            } catch (Exception e) {
                System.err.println("Imagem de fundo não encontrada: " + caminho);
            }
            setLayout(null);
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (imagem != null) {
                g.drawImage(imagem, 0, 0, getWidth(), getHeight(), this);  // Desenha a imagem de fundo
            }
        }
    }

    /**
     * Método para carregar uma fonte personalizada do tipo .otf.
     * @param caminho O caminho do arquivo da fonte.
     * @param tamanho O tamanho da fonte.
     * @return A fonte carregada ou null em caso de erro.
     */
    private Font carregarFonte(String caminho, float tamanho) {
        try {
            Font font = Font.createFont(Font.TRUETYPE_FONT, getClass().getResourceAsStream(caminho));
            return font.deriveFont(tamanho);  // Retorna a fonte com o tamanho especificado
        } catch (Exception e) {
            System.err.println("Erro ao carregar a fonte personalizada: " + caminho);
            return null;  // Retorna null se não for possível carregar a fonte
        }
    }

    /**
     * Método principal para testar a TelaDerrota.
     * @param args Argumentos da linha de comando.
     */
    public static void main(String[] args) {
        new TelaDerrota("Jogador", 300, 10, Dificuldade.FACIL);  // Exibe a tela de derrota com exemplo
    }
}
