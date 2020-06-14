/**
 * 
 */
package co.edu.javeriana.algoritmos.robot;

import co.edu.javeriana.algoritmos.proyecto.Casilla;
import co.edu.javeriana.algoritmos.proyecto.Jugador;
import co.edu.javeriana.algoritmos.proyecto.RespuestaJugada;
import co.edu.javeriana.algoritmos.proyecto.ResumenTablero;
import co.edu.javeriana.algoritmos.proyecto.Tablero;

/**
 * @author danilo
 *
 */
public class JugadorRobot implements Jugador 
{
    private Jugador jugador = null;
    private double marcador = 0D;
    
    
    public JugadorRobot( Jugador jugador ) 
    {
        super();
        this.jugador = jugador;
    }
    

    public double getMarcador() 
    {
        return marcador;
    }

    public void setMarcador( double marcador ) 
    {
        this.marcador = marcador;
    }

    public void sumarAMarcador( double puntaje ) 
    {
        this.marcador += puntaje;
    }

    @Override
    public Tablero iniciarTablero( int arg0, int[] arg1 ) 
    {
        long inicio = System.currentTimeMillis();
        Tablero t = jugador.iniciarTablero( arg0, arg1 );
        if ( System.currentTimeMillis() - inicio > 10000L ) {
            throw new IllegalStateException( "El llamado supera el tiempo permitido" );
        }
        return t;
    }

    @Override
    public int numeroBarcosNoHundidos() 
    {
        long inicio = System.currentTimeMillis();
        int t = jugador.numeroBarcosNoHundidos();
        if ( System.currentTimeMillis() - inicio > 10000L ) {
            throw new IllegalStateException( "El llamado supera el tiempo permitido" );
        }
        return t;
    }

    @Override
    public String obtenerNombre() 
    {
        return jugador.obtenerNombre();
    }

    @Override
    public void procesarResultadoDisparo( RespuestaJugada arg0 ) 
    {
        long inicio = System.currentTimeMillis();
        jugador.procesarResultadoDisparo( arg0 );
        if ( System.currentTimeMillis() - inicio > 10000L ) {
            throw new IllegalStateException( "El llamado supera el tiempo permitido" );
        }
    }

    @Override
    public Casilla realizarDisparo() 
    {
        long inicio = System.currentTimeMillis();
        Casilla t = jugador.realizarDisparo();
        if ( System.currentTimeMillis() - inicio > 10000L ) {
            throw new IllegalStateException( "El llamado supera el tiempo permitido" );
        }
        return t;
    }

    @Override
    public void recibirResumenRival( ResumenTablero arg0 ) 
    {
        long inicio = System.currentTimeMillis();
        jugador.recibirResumenRival( arg0 );
        if ( System.currentTimeMillis() - inicio > 10000L ) {
            throw new IllegalStateException( "El llamado supera el tiempo permitido" );
        }
    }

    @Override
    public RespuestaJugada registrarDisparoAPosicion( Casilla arg0 ) 
    {
        long inicio = System.currentTimeMillis();
        RespuestaJugada t = jugador.registrarDisparoAPosicion( arg0 );
        if ( System.currentTimeMillis() - inicio > 10000L ) {
            throw new IllegalStateException( "El llamado supera el tiempo permitido" );
        }
        return t;
    }

    public String toString()
    {
        return ( ( jugador.obtenerNombre() == null ) ? "Null" : jugador.obtenerNombre() ) 
                + ": " + this.marcador;
    }
}
