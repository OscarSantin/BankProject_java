package org.example;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
public class AppTest {
    private Client client;
    private Bank bank;

    @BeforeEach
    void setUp() {
        client = new Client("Mario", "Rossi", "password123");
        bank = new Bank();
    }

    @Test
    void testDepositaFondiSufficienti() {
        client.deposita(50.0);
        assertEquals(50.0, client.getSaldo(), 0.01);
        assertEquals(50.0, client.getPortafoglio(), 0.01);
    }

    @Test
    void testDepositaFondiInsufficienti() {
        client.deposita(200.0);
        assertEquals(0.0, client.getSaldo(), 0.01);
        assertEquals(100.0, client.getPortafoglio(), 0.01);
    }

    @Test
    void testPrelevaFondiSufficienti() {
        client.deposita(50.0);
        client.preleva(20.0);
        assertEquals(30.0, client.getSaldo(), 0.01);
        assertEquals(70.0, client.getPortafoglio(), 0.01);
    }

    @Test
    void testPrelevaFondiInsufficienti() {
        client.preleva(20.0);
        assertEquals(0.0, client.getSaldo(), 0.01);
        assertEquals(100.0, client.getPortafoglio(), 0.01);
    }

    @Test
    void testAggiungiInvestimento() {
        client.deposita(52);
        Investiment investimento = new Investiment("basso", 2.0, 3);
        assertTrue(client.aggiungiInvestimento(investimento));
        assertEquals(50.0, client.getSaldo(), 0.01);
        assertEquals(1, client.getInvestimenti().size());
    }

    @Test
    void testAggiungiInvestimentoFondiInsufficienti() {
        Investiment investimento = new Investiment("medio", 2.0, 3);
        assertFalse(client.aggiungiInvestimento(investimento));
        assertEquals(0.0, client.getSaldo(), 0.01);
        assertEquals(0, client.getInvestimenti().size());
    }

    @Test
    void testAvanzaTempo() {
        Investiment investimento = new Investiment("basso", 25.0, 1);
        client.deposita(100);
        client.aggiungiInvestimento(investimento);
        client.avanzaTempo(1);
        assertEquals(100.0, client.getPortafoglio(), 0.01);
        assertEquals(122.60416666666667, client.getSaldo()); // 50 + 1 di guadagno
        assertEquals(0, client.getInvestimenti().size());
    }

    @Test
    void testToString() {
        String expected = "Client{nome='Mario', cognome='Rossi', password='**', saldo=0.0, debito=0.0, portafoglio=100.0, investimenti attivi=, investimentsHistory=}";
        assertEquals(expected, client.toString());
    }

    @Test
    void testAggiungiCliente() {
        bank.aggiungiCliente(client);


        boolean trovato = bank.findClient(new Client("Mario", "Rossi", "password123"));
        assertTrue(trovato, "Il cliente dovrebbe essere stato aggiunto correttamente.");
    }

    @Test
    void testFindClient() {
        bank.aggiungiCliente(client);
        boolean trovato = bank.findClient(new Client("Mario", "Rossi", "password123"));

        assertTrue(trovato, "Il cliente dovrebbe essere stato trovato.");
    }

    @Test
    void testAvanzareTempo() {
        bank.aggiungiCliente(client);
        double saldoPrima = client.getSaldo();
        bank.avanzareTempo(client, 2);
        client.deposita(1.0);
        double saldoDopo = client.getSaldo();
        assertNotEquals(saldoPrima, saldoDopo, "Il saldo dovrebbe cambiare dopo l'avanzamento del tempo.");
    }

    @Test
    void testMostraStatiClienti() {
        bank.aggiungiCliente(client);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        bank.mostraStatiClienti();

        String output = outputStream.toString();
        assertTrue(output.contains("MarioRossi"), "L'output dovrebbe contenere il nome del cliente.");
    }

    @AfterEach
    void tearDown() {

        System.setOut(System.out);
    }
}
