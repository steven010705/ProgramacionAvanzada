import co.edu.udistrital.modelo.Cronometro;
import co.edu.udistrital.modelo.Jugador;
import co.edu.udistrital.modelo.Maquina;
import co.edu.udistrital.modelo.Partida;
import co.edu.udistrital.modelo.Tablero;
import co.edu.udistrital.modelo.Barco;
import co.edu.udistrital.modelo.ServicioJugador;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import javax.swing.JLabel;
import java.util.List;

public class PruebasUnitarias {

    private Jugador jugador;
    private Maquina maquina;
    private Partida partida;
    private Tablero tablero;
    private Barco barco;
    private ServicioJugador servicioJugador;
    private Cronometro cronometro;
    private JLabel etiquetaTiempo;

    @BeforeEach
    public void setUp() {
        // Inicialización de objetos para las pruebas
        etiquetaTiempo = new JLabel();
        cronometro = new Cronometro(etiquetaTiempo);
        jugador = new Jugador("testUser", "testPassword");
        maquina = new Maquina();
        partida = new Partida();
        tablero = new Tablero(10, 10);
        barco = new Barco("Fragata", 2, false);
        servicioJugador = new ServicioJugador();
    }

    // Pruebas para Cronometro
    @Test
    public void testCronometroIniciarYDetener() {
        cronometro.iniciar();
        assertTrue(cronometro.isRunning());  // Asumiendo un método isRunning() para verificar
        cronometro.detener();
        assertFalse(cronometro.isRunning());  // Verificar que se detenga correctamente
    }

    @Test
    public void testCronometroTiempoTranscurrido() {
        cronometro.iniciar();
        // Simular tiempo transcurrido (esto es aproximado en pruebas)
        try { Thread.sleep(2000); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
        cronometro.detener();
        String tiempo = etiquetaTiempo.getText();  // Verificar el texto actualizado
        assertTrue(tiempo.contains("Tiempo: 2s") || tiempo.contains("Tiempo: 1s"));  // Aproximación
    }

    // Pruebas para Jugador
    @Test
    public void testJugadorConstructorYGetters() {
        assertEquals("testUser", jugador.getUsuario());
        assertEquals("testPassword", jugador.getContrasena());
        List<Barco> barcos = jugador.getBarcos();
        assertNotNull(barcos);
        assertFalse(barcos.isEmpty());  // Verificar que se inicialicen barcos
    }

    @Test
    public void testJugadorAgregarYEliminarBarco() {
        Barco nuevoBarco = new Barco("Submarino", 3, true);
        jugador.agregarBarco(nuevoBarco);
        List<Barco> barcos = jugador.getBarcos();
        assertTrue(barcos.contains(nuevoBarco));
        
        jugador.eliminarBarco(nuevoBarco);
        assertFalse(barcos.contains(nuevoBarco));
    }

    // Pruebas para Maquina
    @Test
    public void testMaquinaDecidirMovimiento() {
        int[] movimiento = maquina.decidirMovimiento(10, 10);
        assertNotNull(movimiento);
        assertEquals(2, movimiento.length);  // Verificar que sea un array de 2 elementos
        assertTrue(movimiento[0] >= 0 && movimiento[0] < 10);  // Dentro de límites
        assertTrue(movimiento[1] >= 0 && movimiento[1] < 10);
    }

    @Test
    public void testMaquinaAgregarObjetivos() {
        maquina.agregarObjetivos(1, 1, 10, 10);
        // Verificar que se agreguen objetivos (esto es indirecto)
        assertFalse(maquina.getObjetivos().isEmpty());  // Asumiendo acceso a getObjetivos()
    }

    // Pruebas para Partida
    @Test
    public void testPartidaIniciarPartida() {
        partida.iniciarPartida(jugador, maquina);
        assertNotNull(partida.getTableroJugador());
        assertNotNull(partida.getTableroMaquina());
    }

    @Test
    public void testPartidaMovimientos() {
        boolean acierto = partida.movimientos(jugador, 0, 0);
        assertFalse(acierto);  // Asumiendo que no hay barco en (0,0) inicialmente
    }

    // Pruebas para Tablero
    @Test
    public void testTableroColocarBarco() {
        boolean colocado = tablero.colocarBarco(0, 0, barco.getTamanio(), true);
        assertTrue(colocado);  // Debería colocarse si está vacío
    }

    @Test
    public void testTableroRecibirAtaque() {
        tablero.colocarBarco(0, 0, 2, true);  // Colocar un barco
        boolean acierto = tablero.recibirAtaque(0, 0);
        assertTrue(acierto);  // Debería ser un acierto
    }

    // Pruebas para Barco
    @Test
    public void testBarcoRecibirAtaque() {
        barco.recibirAtaque();
        assertEquals(barco.getTamanio() - 1, barco.getVida());  // Vida debería reducirse
    }

    @Test
    public void testBarcoEstaHundido() {
        for (int i = 0; i < barco.getTamanio(); i++) {
            barco.recibirAtaque();
        }
        assertTrue(barco.estaHundido());
    }

    // Pruebas para ServicioJugador
    @Test
    public void testServicioJugadorRegistrar() {
        boolean registrado = servicioJugador.registrarJugador(jugador);
        assertTrue(registrado);  // Debería registrarse si no existe
    }

    @Test
    public void testServicioJugadorIniciarSesion() {
        servicioJugador.registrarJugador(jugador);  // Registrar primero
        boolean login = servicioJugador.iniciarSesion("testUser", "testPassword");
        assertTrue(login);
    }
}
