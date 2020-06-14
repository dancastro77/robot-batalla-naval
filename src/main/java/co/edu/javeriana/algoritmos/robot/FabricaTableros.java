/**
 * 
 */
package co.edu.javeriana.algoritmos.robot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import co.edu.javeriana.algoritmos.proyecto.Casilla;
import co.edu.javeriana.algoritmos.proyecto.Tablero;

/**
 * @author danilo
 *
 */
public class FabricaTableros {

	public static Tablero obtenerTablero( ConfiguracionJuego config, Tablero tableroInicial ) {
	    try {
            validarLargoBarcos( config, tableroInicial );
            validarBarcosNoPegados( config, tableroInicial );
            validarBarcosRectosYContinuos( config, tableroInicial );
            return new TableroRobot( config, tableroInicial );
        }
        catch ( Exception e ) {
            System.out.println( "Error al validar: " + e.getMessage() );
            return null;
        }
	}

    private static void validarBarcosRectosYContinuos( ConfiguracionJuego config, Tablero tablero ) {
        for ( int i = 0; i < config.getBarcos().length ;i++ ) {
            List<Casilla> casillasBarco = tablero.obtenerCasillasOcupadasPorBarco( i );
            if ( !esValidoBarco( casillasBarco ) ) {
                throw new IllegalArgumentException( "Al menos un barco no es recto o continuo" );
            }
        }
    }

    static boolean esValidoBarco( List<Casilla> casillasBarco ) {
        if ( casillasBarco.size() >= 2 ) {
            if ( barcoEnFila( casillasBarco ) ) 
                return true;
            if ( barcoEnColumna( casillasBarco ) ) 
                return true;
            if ( barcoEnDiagonal( casillasBarco ) ) 
                return true;
            if ( barcoEnContraDiagonal( casillasBarco ) )
                return true;
            return false;
        }
        return true;
    }

    private static boolean barcoEnContraDiagonal( List<Casilla> casillasBarco ) {
        List<Casilla> casillasCopia = new ArrayList<>( casillasBarco );
        Collections.sort( casillasCopia, ( c1, c2 ) -> c1.getColumna() - c2.getColumna() );
        for ( int i = 1; i < casillasCopia.size() ;i++ ) {
            Casilla c1 = casillasCopia.get( i-1 ), c2 = casillasCopia.get( i );
            if ( c1.getFila() - 1 != c2.getFila() || c1.getColumna() + 1 != c2.getColumna() ) {
                return false;
            }
        }
        return true;
    }

    private static boolean barcoEnDiagonal( List<Casilla> casillasBarco ) {
        List<Casilla> casillasCopia = new ArrayList<>( casillasBarco );
        Collections.sort( casillasCopia, ( c1, c2 ) -> c1.getColumna() - c2.getColumna() );
        for ( int i = 1; i < casillasCopia.size() ;i++ ) {
            Casilla c1 = casillasCopia.get( i-1 ), c2 = casillasCopia.get( i );
            if ( c1.getFila() + 1 != c2.getFila() || c1.getColumna() + 1 != c2.getColumna() ) {
                return false;
            }
        }
        return true;
    }

    private static boolean barcoEnColumna( List<Casilla> casillasBarco ) {
        List<Casilla> casillasCopia = new ArrayList<>( casillasBarco );
        Collections.sort( casillasCopia, ( c1, c2 ) -> c1.getFila() - c2.getFila() );
        for ( int i = 1; i < casillasCopia.size() ;i++ ) {
            Casilla c1 = casillasCopia.get( i-1 ), c2 = casillasCopia.get( i );
            if ( c1.getColumna() != c2.getColumna() || c1.getFila() + 1 != c2.getFila() ) {
                return false;
            }
        }
        return true;
    }

    private static boolean barcoEnFila( List<Casilla> casillasBarco ) {
        List<Casilla> casillasCopia = new ArrayList<>( casillasBarco );
        Collections.sort( casillasCopia, ( c1, c2 ) -> c1.getColumna() - c2.getColumna() );
        for ( int i = 1; i < casillasCopia.size() ;i++ ) {
            Casilla c1 = casillasCopia.get( i-1 ), c2 = casillasCopia.get( i );
            if ( c1.getFila() != c2.getFila() || c1.getColumna() + 1 != c2.getColumna() ) {
                return false;
            }
        }
        return true;
    }

    static void validarBarcosNoPegados( ConfiguracionJuego config, Tablero tablero ) {
        int[][] planoTablero = new int[config.getDimension()][config.getDimension()];
        
        for ( int i = 0; i < config.getBarcos().length ;i++ ) {
            List<Casilla> casillas = tablero.obtenerCasillasOcupadasPorBarco( i );
            if ( casillas == null || casillas.isEmpty() ) {
                throw new IllegalArgumentException( "Al menos un barco tiene longitud 0" );
            }
            else {
                for ( Casilla casilla: casillas ) {
                    if ( casilla.getFila() < 0 || casilla.getFila() >= planoTablero.length ) {
                        throw new IllegalArgumentException( "Al menos un barco contiene una fila ilegal" );
                    }
                    if ( casilla.getColumna() < 0 || casilla.getColumna() >= planoTablero.length ) {
                        throw new IllegalArgumentException( "Al menos un barco contiene una columna ilegal" );
                    }
                    if ( planoTablero[casilla.getFila()][casilla.getColumna()] != 0 ) {
                        throw new IllegalArgumentException( "Dos barcos caen en la misma casilla" );
                    }
                    else {
                        planoTablero[casilla.getFila()][casilla.getColumna()] = i + 1;
                    }
                }
            }
        }
        
        for ( int i = 0; i < config.getDimension() ;i++ ) {
            for ( int j = 0; j < config.getDimension() ;j++ ) {
                if ( barcosPegadosEnCasilla( planoTablero, i, j ) ) {
                    throw new IllegalArgumentException( "Al menos dos barcos no cumplen el requisito de distancia" );
                }
            }
        }
    }

    private static boolean barcosPegadosEnCasilla( int[][] tablero, int i, int j ) 
    {
        if ( tablero[i][j] == 0 ) {
            return false;
        }
        if ( i >= 1 && j >= 1 && tablero[i-1][j-1] != 0 && tablero[i-1][j-1] != tablero[i][j] ) {
            return true;
        }
        if ( i >= 1 && j < tablero.length-1 && tablero[i-1][j+1] != 0 && tablero[i-1][j+1] != tablero[i][j] ) {
            return true;
        }
        if ( i < tablero.length-1 && j >= 1 && tablero[i+1][j-1] != 0 && tablero[i+1][j-1] != tablero[i][j] ) {
            return true;
        }
        if ( i < tablero.length-1 && j < tablero.length-1 && tablero[i+1][j+1] != 0 && tablero[i+1][j+1] != tablero[i][j] ) {
            return true;
        }
        if ( i >= 1 && tablero[i-1][j] != 0 && tablero[i-1][j] != tablero[i][j] ) {
            return true;
        }
        if ( i < tablero.length-1 && tablero[i+1][j] != 0 && tablero[i+1][j] != tablero[i][j] ) {
            return true;
        }
        if ( j >= 1 && tablero[i][j-1] != 0 && tablero[i][j-1] != tablero[i][j] ) {
            return true;
        }
        if ( j < tablero.length-1 && tablero[i][j+1] != 0 && tablero[i][j+1] != tablero[i][j] ) {
            return true;
        }
        return false;
    }

    private static void validarLargoBarcos( ConfiguracionJuego config, Tablero tablero ) {
        Set<Casilla> casillasDiferentes;
        
        for ( int i = 0; i < config.getBarcos().length ;i++ ) {
            casillasDiferentes = new HashSet<>();
            List<Casilla> casillas = tablero.obtenerCasillasOcupadasPorBarco( i ); 
            if ( casillas == null || casillas.size() != config.getBarcos()[i] ) {
                throw new IllegalArgumentException( "Al menos un barco no cumple con el requisito de eslora" );
            }
            for ( Casilla casilla: casillas ) {
                if ( casilla != null ) 
                    casillasDiferentes.add( casilla );
            }
            if ( casillasDiferentes.size() != casillas.size() ) {
                throw new IllegalArgumentException( "Al menos un barco tiene posiciones repetidas o nulas" );
            }
        }
    }

}
