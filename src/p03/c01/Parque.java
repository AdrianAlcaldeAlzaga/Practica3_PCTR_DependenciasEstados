package p03.c01;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Clase Parque
 * 
 * @author Adrián Alcalde Alzaga
 *
 */
public class Parque implements IParque{

	private int contadorPersonasTotales;
	private static final int AFOROMAXIMO = 50;
	private Hashtable<String, Integer> contadoresPersonasPuertaEntrada;
	private Hashtable<String, Integer> contadoresPersonasPuertaSalida;
	int contadorEntradas=0;
	int contadorSalidas=0;
	
	/**
	 * Constructor de la clase
	 */
	public Parque() {
		contadorPersonasTotales = 0;
		contadoresPersonasPuertaEntrada = new Hashtable<String, Integer>();
		contadoresPersonasPuertaSalida = new Hashtable<String, Integer>();
	}

	@Override
	public synchronized void entrarAlParque(String puerta){

		// Si no hay entradas por esa puerta, inicializamos
		if (contadoresPersonasPuertaEntrada.get(puerta) == null){
			contadoresPersonasPuertaEntrada.put(puerta, 0);
		}
		
		comprobarAntesDeEntrar();
		
		try {
			TimeUnit.MILLISECONDS.sleep(3000);
		}catch(InterruptedException e) {
			Logger.getGlobal().log(Level.INFO, "Hilo interrumpido");
			return;
		}
		
		contadorEntradas++;
				
		// Aumentamos el contador total y el individual
		contadorPersonasTotales++;		
		contadoresPersonasPuertaEntrada.put(puerta, contadoresPersonasPuertaEntrada.get(puerta)+1);
		
		// Imprimimos el estado del parque
		imprimirInfo(puerta, "Entrada");
		
		checkInvariante();
		
	}
	
	@Override
	public synchronized void salirDelParque(String puerta) {
		
		//Si no hay salidas por esa puerta, inicializamos
		if (contadoresPersonasPuertaSalida.get(puerta) == null){
			contadoresPersonasPuertaSalida.put(puerta, 0);
		}
		
		comprobarAntesDeSalir();
		
		try {
			TimeUnit.MILLISECONDS.sleep(3000);
		}catch(InterruptedException e) {
			Logger.getGlobal().log(Level.INFO, "Hilo interrumpido");
			return;
		}
		
		contadorSalidas++;
				
		// Disminuimos el contador total y el individual
		contadorPersonasTotales--;		
		contadoresPersonasPuertaSalida.put(puerta, contadoresPersonasPuertaSalida.get(puerta)+1);
		
		// Imprimimos el estado del parque
		imprimirInfo(puerta, "Salida");
		
		checkInvariante();
	}
	
	/**
	 * Informacion mostrada por consola
	 * 
	 * @param puerta
	 * @param movimiento
	 */
	private void imprimirInfo (String puerta, String movimiento){
		System.out.println(movimiento + " por puerta " + puerta);
		System.out.println("--> Personas en el parque " + contadorPersonasTotales); //+ " tiempo medio de estancia: "  + tmedio);
		
		if(movimiento=="Entrada") {
			for(String p: contadoresPersonasPuertaEntrada.keySet()){
				System.out.println("----> Entrada por puerta " + p + " " + contadoresPersonasPuertaEntrada.get(p));
			}
			System.out.println("------RESUMEN------");
			System.out.println("----> Entradas totales: " + contadorEntradas);
			System.out.println("----> Salidas totales: " + contadorSalidas);
			System.out.println(" ");
			System.out.println(" ");
		}else if(movimiento=="Salida"){
			for(String p: contadoresPersonasPuertaSalida.keySet()){
				System.out.println("----> Salida por puerta " + p + " " + contadoresPersonasPuertaSalida.get(p));
			}
			System.out.println("------RESUMEN------");
			System.out.println("----> Entradas totales: " + contadorEntradas);
			System.out.println("----> Salidas totales: " + contadorSalidas);
			System.out.println(" ");
			System.out.println(" ");
		}
		
	}
	
	/**
	 * Contador con entradas y salidas de las puertas
	 * 
	 * @return El contador de los que entran menos los que salen
	 */
	private int sumarContadoresPuerta() {
		int sumaContadoresPuertaEntrada = 0;
			Enumeration<Integer> iterPuertasEntrada = contadoresPersonasPuertaEntrada.elements();
			while (iterPuertasEntrada.hasMoreElements()) {
				sumaContadoresPuertaEntrada += iterPuertasEntrada.nextElement();
			}
			
		int sumaContadoresPuertaSalida = 0;
			Enumeration<Integer> iterPuertasSalida = contadoresPersonasPuertaSalida.elements();
			while (iterPuertasSalida.hasMoreElements()) {
				sumaContadoresPuertaSalida += iterPuertasSalida.nextElement();
			}
			
		return sumaContadoresPuertaEntrada - sumaContadoresPuertaSalida;
	}
	
	/**
	 * Errores que pueden hacer que el programa no funcione
	 */
	protected void checkInvariante() {
		assert sumarContadoresPuerta() == contadorPersonasTotales : "INV: La suma de "
				+ "contadores de las puertas debe ser igual al valor del contador del parte";
		
		assert sumarContadoresPuerta() >= 0 : "INV: La suma de "
				+ "contadores de las puertas debe ser positivo";
		
		assert sumarContadoresPuerta() <= AFOROMAXIMO : "INV: La suma de "
				+ "contadores de las puertas es mayor al aforo maximo del parque";
	}

	/**
	 * Comprobacion para poder entrar al parque
	 */
	protected void comprobarAntesDeEntrar(){
		while(contadorPersonasTotales==AFOROMAXIMO) {
			try {
				System.err.println("Aforo lleno, esperando...");
				wait();
			}catch(InterruptedException e) {
				e.printStackTrace();
			}
		}
		notify();
	}

	protected void comprobarAntesDeSalir(){
		while(contadorPersonasTotales==0) {
			try {
				//Este mensaje solo saldra en caso de que no haya nadie en el parque y alguien quiera salir, lo cual es imposible.
				System.err.println("No existe la persona, esperando...");
				wait();
			}catch(InterruptedException e) {
				e.printStackTrace();
			}
		}
		notify();
	}


}
