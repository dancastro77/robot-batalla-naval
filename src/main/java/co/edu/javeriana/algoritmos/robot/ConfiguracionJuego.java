/**
 * 
 */
package co.edu.javeriana.algoritmos.robot;

import java.security.SecureRandom;

/**
 * @author danilo
 *
 */
public class ConfiguracionJuego 
{
    int dimension = 0;
    int[] barcos = null;
    static SecureRandom rnd = new SecureRandom(); 
    
	public ConfiguracionJuego( int dimension ) 
	{
	    this.dimension = dimension;
	    int numBarcos = dimension/2;
	    int tamBarco = numBarcos - 1;
	    barcos = new int[numBarcos];
	    for ( int i = 0; i < barcos.length ;i++ ) {
	        barcos[i] = tamBarco;
	        tamBarco = tamBarco - rnd.nextInt( 2 );
	    }
    }

    public ConfiguracionJuego( int dimension, int[] barcos ) 
    {
        super();
        this.dimension = dimension;
        this.barcos = barcos;
    }

    public static ConfiguracionJuego crearConfiguracion() 
    {
		return new ConfiguracionJuego( rnd.nextInt( 11 ) + 10 );
	}

	public int[] getBarcos() 
	{
		return barcos;
	}

	public int getDimension() 
	{
		return dimension;
	}
	
	public String toString() 
	{
	    StringBuilder sBuilder = new StringBuilder();
	    sBuilder.append( "ConfiguracionJuego(" + dimension + ", [" );
	    for ( int i = 0; i < barcos.length ;i++ ) {
	        if ( i > 0 ) 
	            sBuilder.append( ", " );
	        sBuilder.append( barcos[i] );
	    }
	    sBuilder.append( "])" );
	    return sBuilder.toString();
	}

}
