import javax.swing.*;
import java.awt.*;

/**
 * Tela de pausa do jogo, com opções para continuar, salvar o jogo ou voltar ao menu.
 */
public class TelaPause extends JFrame {

    private JButton btnContinuar;
    private JButton btnSalvar;
    private JButton btnVoltarMenu;

    /**
     * Construtor da TelaPause, inicializa a interface gráfica com os botões e ações.
     */
    public TelaPause() {
        // Define o título e configurações da janela
        setTitle("Pausa");
        setSize(300, 250);  // Define o tamanho da janela
        setLocationRelativeTo(null); // Centraliza a janela
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);  // Fecha a janela de pausa ao invés de encerrar o programa
        setResizable(false);  // Impede que a janela seja redimensionada

        // Criação do painel principal
        JPanel painelPrincipal = new JPanel();
        painelPrincipal.setLayout(new GridLayout(3, 1, 10, 10)); // Layout com 3 linhas e 1 coluna
        painelPrincipal.setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50)); // Adiciona espaçamento ao redor do painel

        // Define o conteúdo da janela como o painel principal
        setContentPane(painelPrincipal);

        // Criação dos botões
        btnContinuar = new JButton("Continuar");
        btnSalvar = new JButton("Salvar Jogo");
        btnVoltarMenu = new JButton("Voltar ao Menu");

        // Adiciona os botões no painel principal
        painelPrincipal.add(btnContinuar);
        painelPrincipal.add(btnSalvar);
        painelPrincipal.add(btnVoltarMenu);

        // Ações dos botões com lambda expressions
        btnContinuar.addActionListener(e -> continuarJogo());  // Ação de continuar o jogo
        btnSalvar.addActionListener(e -> salvarJogo());        // Ação de salvar o jogo
        btnVoltarMenu.addActionListener(e -> voltarParaMenu());  // Ação de voltar para o menu principal

        // Torna a janela visível
        setVisible(true);
    }

    /**
     * Método para continuar o jogo, fechando a janela de pausa.
     */
    private void continuarJogo() {
        this.dispose();  // Fecha a janela de pausa
    }

    /**
     * Método para salvar o jogo, mostrando uma mensagem de sucesso.
     */
    private void salvarJogo() {
        JOptionPane.showMessageDialog(this, "Jogo salvo com sucesso!");  // Exibe mensagem de sucesso
    }

    /**
     * Método para voltar ao menu principal, com confirmação do usuário.
     */
    private void voltarParaMenu() {
        int confirmacao = JOptionPane.showConfirmDialog(this, "Deseja voltar para o menu?", "Confirmação", JOptionPane.YES_NO_OPTION);
        if (confirmacao == JOptionPane.YES_OPTION) {
            this.dispose();  // Fecha a janela de pausa
            new MenuInicial();  // Abre o menu inicial (supondo que MenuInicial esteja implementado)
        }
    }
}
