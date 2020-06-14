package co.edu.javeriana.algoritmos.robot;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import co.edu.javeriana.algoritmos.proyecto.Casilla;
import co.edu.javeriana.algoritmos.proyecto.RespuestaJugada;
import co.edu.javeriana.algoritmos.proyecto.ResumenTablero;
import co.edu.javeriana.algoritmos.proyecto.Tablero;

public class TableroRobot implements Tablero {

    List<List<Casilla>> posicionesBarcos = null;
    Set<Casilla> disparosRealizados = null;
    boolean[] barcoHundido = null;
    int dimension = 0;
    int barcosHundidos = 0;
    int barcosTotales = 0;
    
    public TableroRobot( ConfiguracionJuego config, Tablero tableroACopiar ) {
        barcosTotales = config.getBarcos().length;
        dimension = config.getDimension();
        posicionesBarcos = new ArrayList<>();
        disparosRealizados = new HashSet<>();
        barcoHundido = new boolean[config.getBarcos().length];
        for ( int i = 0; i < barcoHundido.length ;i++ )
            barcoHundido[i] = false;
        
        for ( int i = 0; i < config.getBarcos().length ;i++ ) {
            List<Casilla> posicionesBarco = tableroACopiar.obtenerCasillasOcupadasPorBarco( i );
            posicionesBarcos.add(  posicionesBarco );
        }
    }
    
    @Override
    public ResumenTablero obtenerResumen() {
        int[] resumenFila = new int[dimension], resumenColumna = new int[dimension];
        posicionesBarcos.stream()
            .flatMap( List::stream )
            .forEach( casilla -> {
                resumenFila[casilla.getFila()]++; resumenColumna[casilla.getColumna()]++;
            } );
        return new ResumenTablero( resumenFila, resumenColumna ); 
    }

    @Override
    public List<Casilla> obtenerCasillasOcupadasPorBarco( int numeroBarco ) {
        return posicionesBarcos.get( numeroBarco );
    }

    @Override
    public RespuestaJugada dispararACasilla( Casilla casilla ) {
        if ( casilla == null )
            throw new IllegalArgumentException( "Casilla nula" );
        disparosRealizados.add( casilla );
        for ( int barco = 0; barco < posicionesBarcos.size() ;barco++ ) {
            List<Casilla> posicionesBarco = posicionesBarcos.get( barco );
            if ( posicionesBarco.contains( casilla ) ) {
                if ( disparosRealizados.containsAll( posicionesBarco ) ) {
                    barcoHundido[barco] = true;
                    return RespuestaJugada.HUNDIDO; 
                }
                else {
                    return RespuestaJugada.IMPACTO;
                }
            }
        }
        return RespuestaJugada.AGUA;
    }

    @Override
    public int numeroBarcosNoHundidos() {
        barcosHundidos = 0;
        for ( boolean hundido: barcoHundido ) {
            if ( hundido ) barcosHundidos++;
        }
        return barcosTotales - barcosHundidos;
    }
    
    public String toString() {
        return "Tablero: {" + posicionesBarcos.toString() + "}";
    }

}
