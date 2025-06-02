// CartaTest.java
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CartaTest {
    private Carta carta;

    @BeforeEach
    void setUp() {
        carta = new Carta(1);
    }

    @Test
    void testCartaInicialmenteDesvirada() {
        assertFalse(carta.isVirada());
    }

    @Test
    void testVirarCarta() {
        carta.virar();
        assertTrue(carta.isVirada());
    }

    @Test
    void testSetEncontrada() {
        carta.setEncontrada(true);
        assertTrue(carta.isEncontrada());
    }

    @Test
    void testIdDaCarta() {
        assertEquals(1, carta.getId());
    }
}

// ControlesTest.java
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ControlesTest {
    private Controles controles;
    private Carta carta1, carta2;
    private FakePainel painel;

    @BeforeEach
    void setUp() {
        painel = new FakePainel();
        controles = new Controles(painel);
        carta1 = new Carta(1);
        carta2 = new Carta(1);
    }

    @Test
    void testInicialmenteSemTentativas() {
        assertEquals(0, controles.getTentativas());
    }

    @Test
    void testCliqueNaCarta() {
        controles.clicarCarta(carta1);
        assertTrue(carta1.isVirada());
    }

    @Test
    void testIncrementaTentativas() {
        controles.clicarCarta(carta1);
        controles.clicarCarta(carta2);
        assertEquals(1, controles.getTentativas());
    }
}

// DificuldadeTest.java
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class DificuldadeTest {

    @Test
    void testProximaDificuldade() {
        assertEquals(Dificuldade.MEDIO, Dificuldade.proximaDificuldade(Dificuldade.FACIL));
        assertEquals(Dificuldade.DIFICIL, Dificuldade.proximaDificuldade(Dificuldade.MEDIO));
        assertNull(Dificuldade.proximaDificuldade(Dificuldade.DIFICIL));
    }

    @Test
    void testTempoDificuldade() {
        assertEquals(120, Dificuldade.FACIL.tempoSegundos);
    }
}

// PontuacaoManagerTest.java
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PontuacaoManagerTest {

    @Test
    void testSalvarECarregarRecorde() {
        PontuacaoManager.salvarRecorde(1234);
        int recorde = PontuacaoManager.carregarRecorde();
        assertEquals(1234, recorde);
    }

    @Test
    void testNaoUltrapassaRecordeMenor() {
        PontuacaoManager.salvarRecorde(2000);
        PontuacaoManager.salvarRecorde(1500);
        int recorde = PontuacaoManager.carregarRecorde();
        assertEquals(2000, recorde);
    }
}

// FakePainel.java (classe auxiliar para testes)
public class FakePainel extends PainelDeJogo {
    public FakePainel() {
        super(Dificuldade.FACIL);
    }

    @Override
    public void atualizarPontuacao() {
        // Sem efeito para os testes
    }
}
