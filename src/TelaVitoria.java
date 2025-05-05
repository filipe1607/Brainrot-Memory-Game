import javax.swing.*;
import java.awt.*;

/**
 * Tela de vitória que é exibida ao jogador quando ele vence o jogo.
 */
public class TelaVitoria extends JFrame {
    /**
     * Construtor que configura a tela de vitória com as informações do jogador.
     *
     * @param nome           Nome do jogador.
     * @param pontuacao      Pontuação final do jogador.
     * @param tentativas     Número de tentativas feitas pelo jogador.
     * @param dificuldadeAtual Nível de dificuldade atual do jogador.
     */
    public TelaVitoria(String nome, int pontuacao, int tentativas, Dificuldade dificuldadeAtual) {
        setTitle("Vitória!");  // Título da janela
        setSize(400, 300);  // Tamanho da janela
        setResizable(false);  // Impede que a janela seja redimensionada
        setLocationRelativeTo(null);  // Centraliza a janela na tela
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  // Fecha o programa quando a janela é fechada

        // Define o fundo da tela com uma imagem personalizada
        setContentPane(new PainelComImagemFundo("/assets/fundos/win.png"));  // Caminho da imagem de fundo
        setLayout(null);  // Layout sem restrições de posicionamento (usando coordenadas absolutas)

        // Carrega as fontes personalizadas
        Font boldstromGrande = carregarFonte("/assets/fonts/Boldstrom.otf", 34f);
        Font boldstromMedia = carregarFonte("/assets/fonts/Boldstrom.otf", 20f);

        // Exibe a mensagem de parabéns ao jogador
        JLabel titulo = new JLabel("Parabéns, " + nome + "!");
        titulo.setFont(boldstromGrande != null ? boldstromGrande : new Font("SansSerif", Font.BOLD, 45));
        titulo.setBounds(30, 10, 340, 40);  // Define a posição e o tamanho do rótulo
        titulo.setForeground(Color.GREEN);  // Cor do texto
        add(titulo);  // Adiciona o rótulo à tela

        // Exibe a pontuação do jogador
        JLabel pontosLabel = new JLabel("Pontuação: " + pontuacao);
        pontosLabel.setFont(boldstromMedia != null ? boldstromMedia : new Font("SansSerif", Font.PLAIN, 20));
        pontosLabel.setBounds(120, 60, 200, 25);
        pontosLabel.setForeground(Color.WHITE);
        add(pontosLabel);

        // Exibe o número de tentativas feitas pelo jogador
        JLabel tentativasLabel = new JLabel("Tentativas: " + tentativas);
        tentativasLabel.setFont(boldstromMedia != null ? boldstromMedia : new Font("SansSerif", Font.PLAIN, 20));
        tentativasLabel.setBounds(120, 90, 200, 25);
        tentativasLabel.setForeground(Color.WHITE);
        add(tentativasLabel);

        // Botão para recomeçar o jogo com a mesma dificuldade
        JButton recomecarBtn = new JButton("🔁 Recomeçar");
        recomecarBtn.setBounds(50, 150, 120, 40);
        recomecarBtn.addActionListener(e -> {
            dispose();  // Fecha a janela atual
            new PainelDeJogo(dificuldadeAtual);  // Inicia um novo jogo com a dificuldade atual
        });
        add(recomecarBtn);

        // Botão para voltar ao menu inicial
        JButton menuBtn = new JButton("🏠 Menu");
        menuBtn.setBounds(220, 150, 120, 40);
        menuBtn.addActionListener(e -> {
            dispose();  // Fecha a janela atual
            new MenuInicial().setVisible(true);  // Abre o menu inicial
        });
        add(menuBtn);

        // Botão para avançar para a próxima fase do jogo
        JButton proximaFaseBtn = new JButton("➡ Próxima Fase");
        proximaFaseBtn.setBounds(110, 210, 180, 35);
        proximaFaseBtn.addActionListener(e -> {
            dispose();  // Fecha a janela atual
            Dificuldade proxima = Dificuldade.proximaDificuldade(dificuldadeAtual);  // Avança para a próxima dificuldade
            if (proxima != null) {
                // Cria uma nova janela para a próxima fase
                JFrame f = new JFrame("Nova Fase");
                f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                f.setResizable(false);
                f.add(new PainelDeJogo(proxima));  // Inicia o jogo com a nova dificuldade
                f.pack();
                f.setLocationRelativeTo(null);
                f.setVisible(true);
            } else {
                // Exibe mensagem de que o jogador completou todas as fases
                JOptionPane.showMessageDialog(null, "Você completou todas as fases!");
                new MenuInicial().setVisible(true);  // Retorna ao menu inicial
            }
        });
        add(proximaFaseBtn);

        // Torna a janela visível
        setVisible(true);
    }

    /**
     * Método para carregar fontes personalizadas.
     *
     * @param caminho Caminho da fonte.
     * @param tamanho Tamanho da fonte.
     * @return A fonte carregada ou null se houver erro.
     */
    private Font carregarFonte(String caminho, float tamanho) {
        try {
            Font fonte = Font.createFont(Font.TRUETYPE_FONT, getClass().getResourceAsStream(caminho));
            return fonte.deriveFont(tamanho);
        } catch (Exception e) {
            System.err.println("Erro ao carregar a fonte: " + caminho);
            return null;
        }
    }

    /**
     * Painel com imagem de fundo para a tela de vitória.
     */
    private static class PainelComImagemFundo extends JPanel {
        private Image imagem;

        public PainelComImagemFundo(String caminho) {
            try {
                imagem = new ImageIcon(getClass().getResource(caminho)).getImage();  // Carrega a imagem de fundo
            } catch (Exception e) {
                System.err.println("Imagem não encontrada: " + caminho);
            }
            setLayout(null);  // Layout sem restrições
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (imagem != null) {
                g.drawImage(imagem, 0, 0, getWidth(), getHeight(), this);  // Exibe a imagem de fundo
            }
        }
    }

    /**
     * Método principal para testar a TelaVitoria.
     */
    public static void main(String[] args) {
        new TelaVitoria("Jão", 1200, 5, Dificuldade.FACIL);  // Exemplo com um nome, pontuação e tentativas
    }
}
