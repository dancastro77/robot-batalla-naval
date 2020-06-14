/**
 * 
 */
package co.edu.javeriana.algoritmos.robot;

import java.io.IOException;
import java.util.Arrays;

import co.edu.javeriana.algoritmos.proyecto.Jugador;

/**
 * @author danilo
 *
 */
public class Main 
{
    JugadorRobot[] jugadores;
    LectorPropiedades lector;
    
	/**
	 * @param args
	 */
	public static void main( String[] args ) 
	{
		new Main().ejecutarRobot( args );
	}

	void ejecutarRobot( String[] args ) 
	{
		try {
			lector = LectorPropiedades.instancia( args[0] );
			int chicosJugados = 0;
			jugadores = new CargadorJugadores().cargarJugadores();
			
			validarCargaJugadores();
			while ( seDebeSeguirJugando() ) {
				Chico chico = new Chico( jugadores, chicosJugados );
				chico.jugar();
				actualizarMarcadoresConChico( chico );
				
				chicosJugados++;
			}
			// Marcador final
			Arrays.stream( jugadores ).forEach( JugadorRobot::toString );
		} 
		catch ( IOException e ) {
			System.out.println( "Error al cargar propiedades: " + e.getMessage() );
		}
	}

    void validarCargaJugadores() 
    {
        if ( jugadores[0] == null && jugadores[1] == null ) {
            System.err.println( "Ninguno de los dos jugadores pudo cargarse.  Termina el partido." );
            System.exit( 1 );
        }
        if ( jugadores[0] == null ) {
            System.err.println( "Jugador 0 no pudo ser cargado y queda descalificado" );
        	jugadores[1].setMarcador( lector.numeroChicosParaGanar() );
        }
        else if ( jugadores[1] == null ) {
            System.err.println( "Jugador 1 no pudo ser cargado y queda descalificado" );
            jugadores[0].setMarcador( lector.numeroChicosParaGanar() );
        }
    }
	
	private void actualizarMarcadoresConChico( Chico chico ) 
	{
	    for ( int i = 0; i < 2 ;i++ ) {
	        jugadores[i].sumarAMarcador( chico.obtenerPuntajeParaJugador( i ) );
	    }
    }

    boolean seDebeSeguirJugando() 
	{
	    for ( int i = 0; i < 2 ;i++ ) {
	        if ( jugadores[i].getMarcador() >= lector.numeroChicosParaGanar() )
	            return false;
	    }
	    return true;
	}

}
