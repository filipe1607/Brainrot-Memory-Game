import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import javax.swing.*;
import java.lang.reflect.Field;
import static org.junit.Assert.*;

public class NewGameTest {

    private NewGame newGame;

    @Before
    public void setUp() {
        newGame = new NewGame();
    }

    @After
    public void tearDown() {
        newGame.dispose(); // fecha a janela após cada teste
    }

    @Test
    public void testBotoesExistem() throws Exception {
        JButton btnNovoJogo = getBotao("btnNovoJogo");
        JButton btnNivel1 = getBotao("btnNivel1");

        assertNotNull("Botão Novo Jogo deve existir", btnNovoJogo);
        assertNotNull("Botão Nível 1 deve existir", btnNivel1);
    }

    @Test
    public void testNiveisIniciamDesabilitadosCorretamente() throws Exception {
        JButton btnNivel2 = getBotao("btnNivel2");
        JButton btnNivel3 = getBotao("btnNivel3");

        assertFalse("Nível 2 deve começar desabilitado", btnNivel2.isEnabled());
        assertFalse("Nível 3 deve começar desabilitado", btnNivel3.isEnabled());
    }

    @Test
    public void testLiberarNivel2HabilitaBotaoCorreto() throws Exception {
        // Libera nível 2 via método privado
        var method = NewGame.class.getDeclaredMethod("liberarNivel", int.class);
        method.setAccessible(true);
        method.invoke(newGame, 2);

        JButton btnNivel2 = getBotao("btnNivel2");
        assertTrue("Nível 2 deve estar habilitado após liberação", btnNivel2.isEnabled());
    }

    /**
     * Helper para acessar os botões privados por reflexão.
     */
    private JButton getBotao(String nomeCampo) throws Exception {
        Field field = NewGame.class.getDeclaredField(nomeCampo);
        field.setAccessible(true);
        return (JButton) field.get(newGame);
    }
}
