/**
 * 
 */
package co.edu.javeriana.algoritmos.robot;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * @author danilo
 *
 */
public class LectorPropiedades 
{
	private Properties propiedades = null;
	private static LectorPropiedades instancia = null;
	
	private LectorPropiedades( String propertiesPath ) throws IOException 
	{
		propiedades = new Properties();
		propiedades.load( new FileInputStream( new File( propertiesPath ) ) );
	}
	
	public static LectorPropiedades instancia( String propertiesPath ) throws IOException 
	{
		if ( instancia == null ) {
			instancia = new LectorPropiedades( propertiesPath );
		}
		return instancia;
	}

	public static LectorPropiedades instancia() 
	{
		return instancia;
	}

	public String claseJugadorUno() 
	{
		return propiedades.getProperty( "clase.jugador.uno" );
	}

	public String claseJugadorDos() 
	{
		return propiedades.getProperty( "clase.jugador.dos" );
	}
	
	public int numeroChicosParaGanar() 
	{
		return new Integer( propiedades.getProperty( "numero.chicos.para.ganar" ) );
	}

}
