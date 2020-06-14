/**
 * 
 */
package co.edu.javeriana.algoritmos.robot;

import java.security.SecureRandom;

import co.edu.javeriana.algoritmos.proyecto.Casilla;
import co.edu.javeriana.algoritmos.proyecto.Jugador;
import co.edu.javeriana.algoritmos.proyecto.RespuestaJugada;
import co.edu.javeriana.algoritmos.proyecto.Tablero;

/**
 * @author danilo
 *
 */
public class Chico 
{
	Jugador[] jugadores = null;
	int primerJugador = 0, segundoJugador = 1;
	boolean puedeHaberEmpate = true;
	ConfiguracionJuego configJuego = null;
	int jugadorGanador = -1; // -1: Empate
	Tablero[] tablerosJugadores = new Tablero[2];
	
	public Chico( Jugador[] jugadores, int chicosJugados ) 
	{
		this.jugadores = jugadores;
		puedeHaberEmpate = chicosJugados < 2 * LectorPropiedades.instancia().numeroChicosParaGanar() - 1;
		if ( puedeHaberEmpate ) {
			primerJugador = chicosJugados % 2;
		}
		else {
			primerJugador = new SecureRandom().nextInt( 2 );
		}
		segundoJugador = (primerJugador + 1) % 2;
	}

	public void jugar() 
	{
		jugadorGanador = prepararJuego();
		if ( jugadorGanador != -1 ) return;
		
		intercambiarResumenes();
        if ( jugadorGanador != -1 ) return;
		
        int turno = 1;
		while ( alMenosUnBarcoEnCadaTablero() ) {
		    System.out.println( "Turno no. " + turno );
			jugadorGanador = realizarDisparo( primerJugador, segundoJugador );
			if ( jugadorGanador != -1 ) return;
			jugadorGanador = realizarDisparo( segundoJugador, primerJugador );
			if ( jugadorGanador != -1 ) return;
		}
		
		determinarGanador();
	}

	private void determinarGanador() 
	{
		if ( tablerosJugadores[0].numeroBarcosNoHundidos() < tablerosJugadores[1].numeroBarcosNoHundidos() ) {
			jugadorGanador = 1;
		}
		else if ( tablerosJugadores[0].numeroBarcosNoHundidos() > tablerosJugadores[1].numeroBarcosNoHundidos() ) {
			jugadorGanador = 0;
		}
		else {
			if ( !puedeHaberEmpate ) {
				jugadorGanador = new SecureRandom().nextInt( 2 );
				System.out.println( "El ganador ha sido determinado por sorteo" );
			}
		}
	}

	private void intercambiarResumenes() 
	{
		for ( int i = 0; i < 2 ;i++ ) {
			try {
                jugadores[i].recibirResumenRival( tablerosJugadores[(i+1)%2].obtenerResumen() );
            }
            catch ( Exception e ) {
                System.out.println( 
                        "El jugador " + i + " falló al recibir resumen: " + e.getClass().getName() 
                        + ": " + e.getMessage() );
                jugadorGanador = (i+1)%2;
            }
		}
	}

	private int prepararJuego() 
	{
		configJuego = ConfiguracionJuego.crearConfiguracion();
		System.out.println( "Configuración juego: " + configJuego );
		for ( int i = 0; i < 2 ;i++ ) {
			tablerosJugadores[i] = obtenerTableroValido( jugadores[i] );
			if ( tablerosJugadores[i] == null ) {
			    System.out.println( "El jugador " + jugadores[i].obtenerNombre() + " no logró devolver tablero válido." );
				jugadorGanador = (i+1) % 2;
				return jugadorGanador;
			}
		}
		return jugadorGanador;
	}

	private Tablero obtenerTableroValido( Jugador jugador ) 
	{
		for ( int i = 1; i <= 3 ;i++ ) {
			Tablero tableroInterno;
            System.out.println( "Solicitando tablero a jugador: " + jugador.obtenerNombre() + ".  Intento " + i );
            try {
                tableroInterno = FabricaTableros.obtenerTablero( 
                		configJuego, jugador.iniciarTablero( configJuego.getDimension(), configJuego.getBarcos() ) );
            }
            catch ( Exception e ) {
                System.out.println( "Error al obtener tablero: " + e.getClass().getName() + ": " + e.getMessage() );
                tableroInterno = null;
            }
			if ( tableroInterno != null ) {
			    System.out.println( "Tablero para jugador " + jugador.obtenerNombre() + ": " + tableroInterno );
				return tableroInterno;
			}
		}
		return null;
	}

	private int realizarDisparo( int jugadorQueDispara, int jugadorQueRecibe ) 
	{
		Casilla disparo = null;

		try {
            disparo = jugadores[jugadorQueDispara].realizarDisparo();
            System.out.println( "El jugador " + jugadorQueDispara + " dispara: " + disparo );
            System.out.println( "El jugador " + jugadores[jugadorQueDispara].obtenerNombre() + " dispara: " + disparo );
        }
        catch ( Exception e ) {
            System.out.println( "Disparo no realizado correctamente: " + e.getClass().getName() + ":" + e.getMessage() );
            jugadorGanador = jugadorQueRecibe;
            return jugadorGanador;
        }
		
		if ( !disparo.validaParaDimension( configJuego.getDimension() ) ) {
		    System.out.println( "Disparo no válido.  Jugador " + jugadorQueDispara + " descalificado." );
            jugadorGanador = jugadorQueRecibe;
            return jugadorGanador;
		}
		
		RespuestaJugada respuestaInterna = tablerosJugadores[jugadorQueRecibe].dispararACasilla( disparo );
		RespuestaJugada respuestaJugador = null;

		try {
            respuestaJugador = jugadores[jugadorQueRecibe].registrarDisparoAPosicion( disparo );
        }
        catch ( Exception e ) {
            System.out.println( 
                    "Error al recibir disparo.  Jugador " + jugadorQueRecibe + " descalificado: " 
                    + e.getClass().getName() + ": " + e.getMessage() );
            jugadorGanador = jugadorQueDispara;
            return jugadorGanador;
        }
        
		if ( respuestaInterna != respuestaJugador ) {
		    System.out.println( 
		            "Respuesta incorrecta del receptor del disparo.  Jugador " 
		            + jugadorQueRecibe + " descalificado." );
			jugadorGanador = jugadorQueDispara;
			return jugadorGanador;
		}
		
		System.out.println( "Respuesta a disparo: " + respuestaJugador.getLetrero() );
		
		try {
            jugadores[jugadorQueDispara].procesarResultadoDisparo( respuestaJugador );
        }
        catch ( Exception e ) {
            System.out.println( 
                    "Error al procesar disparo.  Jugador " + jugadorQueDispara + " descalificado: " 
                    + e.getClass().getName() + ": " + e.getMessage() );
            jugadorGanador = jugadorQueRecibe;
            return jugadorGanador;
        }
		return jugadorGanador;
	}

	private boolean alMenosUnBarcoEnCadaTablero() 
	{
	    System.out.println( 
	            "Barcos de cada jugador: " + jugadores[0].obtenerNombre() + " " 
	            + (configJuego.getBarcos().length - tablerosJugadores[1].numeroBarcosNoHundidos()) + " - "
	            + (configJuego.getBarcos().length - tablerosJugadores[0].numeroBarcosNoHundidos())
	            + jugadores[1].obtenerNombre() );
		return tablerosJugadores[0].numeroBarcosNoHundidos() > 0 && tablerosJugadores[1].numeroBarcosNoHundidos() > 0;
	}

	public float obtenerPuntajeParaJugador( int numeroJugador ) 
	{
		if ( numeroJugador != 0 && numeroJugador != 1 ) {
			return 0F;
		}
		if ( jugadorGanador == -1 ) {
			return 0.5F;
		}
		if ( numeroJugador == jugadorGanador ) {
		    return 1F;
		}
		else {
		    return 0F;
		}
	}

}
