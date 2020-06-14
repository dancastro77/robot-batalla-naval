package co.edu.javeriana.algoritmos.robot;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import co.edu.javeriana.algoritmos.proyecto.Casilla;
import co.edu.javeriana.algoritmos.proyecto.Tablero;

public class FabricaTablerosTest
{

   @Before
   public void setUp() throws Exception
   {
   }

   @Test
   public void testBarcoEnFilaEsValido()
   {
      List<Casilla> barco = new ArrayList<>();
      barco.add( new Casilla( 0, 1 ) );
      barco.add( new Casilla( 0, 2 ) );
      barco.add( new Casilla( 0, 3 ) );
      barco.add( new Casilla( 0, 4 ) );
      
      assertTrue( FabricaTableros.esValidoBarco( barco ) );
   }
   
   @Test
   public void testBarcoEnFilaDesordenadoEsValido() 
   {
      List<Casilla> barco = new ArrayList<>();
      barco.add( new Casilla( 4, 10 ) );
      barco.add( new Casilla( 4, 9 ) );
      barco.add( new Casilla( 4, 8 ) );
      barco.add( new Casilla( 4, 11 ) );
      
      assertTrue( FabricaTableros.esValidoBarco( barco ) );
   }
   
   @Test
   public void testBarcoEnFilaDiscontinuoNoEsValido() 
   {
      List<Casilla> barco = new ArrayList<>();
      barco.add( new Casilla( 4, 10 ) );
      barco.add( new Casilla( 4, 9 ) );
      barco.add( new Casilla( 4, 7 ) );
      barco.add( new Casilla( 4, 11 ) );
      
      assertFalse( FabricaTableros.esValidoBarco( barco ) );
   }

   @Test
   public void testBarcoEnColumnaEsValido()
   {
      List<Casilla> barco = new ArrayList<>();
      barco.add( new Casilla( 1, 1 ) );
      barco.add( new Casilla( 2, 1 ) );
      barco.add( new Casilla( 3, 1 ) );
      barco.add( new Casilla( 4, 1 ) );
      
      assertTrue( FabricaTableros.esValidoBarco( barco ) );
   }
   
   @Test
   public void testBarcoEnColumnaDesordenadoEsValido() 
   {
      List<Casilla> barco = new ArrayList<>();
      barco.add( new Casilla( 16, 5 ) );
      barco.add( new Casilla( 18, 5 ) );
      barco.add( new Casilla( 15, 5 ) );
      barco.add( new Casilla( 17, 5 ) );
      
      assertTrue( FabricaTableros.esValidoBarco( barco ) );
   }
   
   @Test
   public void testBarcoEnColumnaDiscontinuoNoEsValido() 
   {
      List<Casilla> barco = new ArrayList<>();
      barco.add( new Casilla( 1, 8 ) );
      barco.add( new Casilla( 3, 8 ) );
      barco.add( new Casilla( 2, 8 ) );
      barco.add( new Casilla( 5, 8 ) );
      
      assertFalse( FabricaTableros.esValidoBarco( barco ) );
   }
   
   @Test
   public void testBarcoEnDiagonalEsValido()
   {
      List<Casilla> barco = new ArrayList<>();
      barco.add( new Casilla( 1, 1 ) );
      barco.add( new Casilla( 2, 2 ) );
      barco.add( new Casilla( 3, 3 ) );
      barco.add( new Casilla( 4, 4 ) );
      
      assertTrue( FabricaTableros.esValidoBarco( barco ) );
   }

   @Test
   public void testBarcoEnContraDiagonalEsValido()
   {
      List<Casilla> barco = new ArrayList<>();
      barco.add( new Casilla( 3, 1 ) );
      barco.add( new Casilla( 2, 2 ) );
      barco.add( new Casilla( 1, 3 ) );
      
      assertTrue( FabricaTableros.esValidoBarco( barco ) );
   }
   
   @Test
   public void testBarcoEnDiagonalDiscontinuaNoEsValido()
   {
      List<Casilla> barco = new ArrayList<>();
      barco.add( new Casilla( 3, 5 ) );
      barco.add( new Casilla( 4, 6 ) );
      barco.add( new Casilla( 5, 7 ) );
      barco.add( new Casilla( 7, 9 ) );
      
      assertFalse( FabricaTableros.esValidoBarco( barco ) );
   }

   @Test
   public void testBarcoEnContraDiagonalDiscontinuaNoEsValido()
   {
      List<Casilla> barco = new ArrayList<>();
      barco.add( new Casilla( 5, 5 ) );
      barco.add( new Casilla( 4, 6 ) );
      barco.add( new Casilla( 3, 7 ) );
      barco.add( new Casilla( 1, 9 ) );
      
      assertFalse( FabricaTableros.esValidoBarco( barco ) );
   }

   @Test
   public void testBarcoEslora2ContiguasEsValido()
   {
      List<Casilla> barco = new ArrayList<>();
      barco.add( new Casilla( 3, 5 ) );
      barco.add( new Casilla( 4, 6 ) );
      
      assertTrue( FabricaTableros.esValidoBarco( barco ) );
   }

   @Test
   public void testBarcoEslora2NoContiguasNoEsValido()
   {
      List<Casilla> barco = new ArrayList<>();
      barco.add( new Casilla( 3, 5 ) );
      barco.add( new Casilla( 14, 16 ) );
      
      assertFalse( FabricaTableros.esValidoBarco( barco ) );
   }
   
   @Test
   public void testBarcoEnLNoEsValido() 
   {
      List<Casilla> barco = new ArrayList<>();
      barco.add( new Casilla( 3, 5 ) );
      barco.add( new Casilla( 3, 6 ) );
      barco.add( new Casilla( 3, 7 ) );
      barco.add( new Casilla( 4, 7 ) );
      
      assertFalse( FabricaTableros.esValidoBarco( barco ) );
   }

   @Test
   public void testBarcosPegadosProaConPopaNoSonValidos()
   {
      List<Casilla> listaBarco1 = new ArrayList<>(), listaBarco2 = new ArrayList<>();
      listaBarco1.add( new Casilla( 3, 5 ) );
      listaBarco1.add( new Casilla( 3, 6 ) );
      listaBarco2.add( new Casilla( 3, 4 ) );
      listaBarco2.add( new Casilla( 3, 3 ) );
      ConfiguracionJuego config = new ConfiguracionJuego( 7, new int[]{2, 2} );
      Tablero tablero = Mockito.mock( Tablero.class );
      Mockito.when( tablero.obtenerCasillasOcupadasPorBarco( 0 ) ).thenReturn( listaBarco1 );
      Mockito.when( tablero.obtenerCasillasOcupadasPorBarco( 1 ) ).thenReturn( listaBarco2 );
      
      try {
         FabricaTableros.validarBarcosNoPegados( config, tablero );
         fail();
      }
      catch ( Exception e ) {
         assertTrue( e instanceof IllegalArgumentException );
      }
   }
   
   @Test
   public void testBarcosPegadosEnLNoSonValidos()
   {
      List<Casilla> listaBarco1 = new ArrayList<>(), listaBarco2 = new ArrayList<>();
      listaBarco1.add( new Casilla( 3, 5 ) );
      listaBarco1.add( new Casilla( 3, 6 ) );
      listaBarco1.add( new Casilla( 3, 7 ) );
      listaBarco2.add( new Casilla( 4, 6 ) );
      listaBarco2.add( new Casilla( 5, 6 ) );
      ConfiguracionJuego config = new ConfiguracionJuego( 10, new int[]{3, 2} );
      Tablero tablero = Mockito.mock( Tablero.class );
      Mockito.when( tablero.obtenerCasillasOcupadasPorBarco( 0 ) ).thenReturn( listaBarco1 );
      Mockito.when( tablero.obtenerCasillasOcupadasPorBarco( 1 ) ).thenReturn( listaBarco2 );
      
      try {
         FabricaTableros.validarBarcosNoPegados( config, tablero );
         fail();
      }
      catch ( Exception e ) {
         assertTrue( e instanceof IllegalArgumentException );
      }
   }

   @Test
   public void testBarcosPegadosParalelosNoSonValidos()
   {
      List<Casilla> listaBarco1 = new ArrayList<>(), listaBarco2 = new ArrayList<>();
      listaBarco1.add( new Casilla( 3, 5 ) );
      listaBarco1.add( new Casilla( 4, 6 ) );
      listaBarco1.add( new Casilla( 5, 7 ) );
      listaBarco2.add( new Casilla( 3, 6 ) );
      listaBarco2.add( new Casilla( 4, 7 ) );
      ConfiguracionJuego config = new ConfiguracionJuego( 10, new int[]{3, 2} );
      Tablero tablero = Mockito.mock( Tablero.class );
      Mockito.when( tablero.obtenerCasillasOcupadasPorBarco( 0 ) ).thenReturn( listaBarco1 );
      Mockito.when( tablero.obtenerCasillasOcupadasPorBarco( 1 ) ).thenReturn( listaBarco2 );
      
      try {
         FabricaTableros.validarBarcosNoPegados( config, tablero );
         fail();
      }
      catch ( Exception e ) {
         assertTrue( e instanceof IllegalArgumentException );
      }
   }
   
   @Test
   public void testBarcosNoPegadosSonValidos() 
   {
      List<Casilla> listaBarco1 = new ArrayList<>(), listaBarco2 = new ArrayList<>();
      listaBarco1.add( new Casilla( 3, 5 ) );
      listaBarco1.add( new Casilla( 4, 6 ) );
      listaBarco1.add( new Casilla( 5, 7 ) );
      listaBarco2.add( new Casilla( 0, 0 ) );
      listaBarco2.add( new Casilla( 1, 1 ) );
      ConfiguracionJuego config = new ConfiguracionJuego( 10, new int[]{3, 2} );
      Tablero tablero = Mockito.mock( Tablero.class );
      Mockito.when( tablero.obtenerCasillasOcupadasPorBarco( 0 ) ).thenReturn( listaBarco1 );
      Mockito.when( tablero.obtenerCasillasOcupadasPorBarco( 1 ) ).thenReturn( listaBarco2 );
      FabricaTableros.validarBarcosNoPegados( config, tablero );
   }

}
