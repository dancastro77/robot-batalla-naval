/**
 * 
 */
package co.edu.javeriana.algoritmos.robot;

import co.edu.javeriana.algoritmos.proyecto.Jugador;

/**
 * @author danilo
 *
 */
public class CargadorJugadores 
{
	public JugadorRobot[] cargarJugadores() 
	{
		JugadorRobot[] jugadores = new JugadorRobot[2];
		
		jugadores[0] = cargarJugador( LectorPropiedades.instancia().claseJugadorUno() );
		jugadores[1] = cargarJugador( LectorPropiedades.instancia().claseJugadorDos() );

		return jugadores;
	}

	private JugadorRobot cargarJugador( String claseJugador ) 
	{
		try {
			return new JugadorRobot( ( Jugador ) Class.forName( claseJugador ).newInstance() );
		} 
		catch ( Exception e ) {
			e.printStackTrace();
			return new JugadorRobot( null );
		}
	}
	
}
