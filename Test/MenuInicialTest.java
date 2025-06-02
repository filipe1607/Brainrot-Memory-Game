import org.junit.Before;
import org.junit.Test;

import javax.swing.*;

import static org.junit.Assert.*;

public class MenuInicialTest {

    private MenuInicial menu;

    @Before
    public void setUp() {
        menu = new MenuInicial();
    }

    @Test
    public void testToggleMusicaDesligaEMLiga() {
        boolean estadoOriginal = menu.musicatocando;
        menu.toggleMusica();
        assertNotEquals("O estado da música deveria ter mudado", estadoOriginal, menu.musicatocando);
        menu.toggleMusica(); // volta ao original
