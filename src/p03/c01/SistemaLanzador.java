package p03.c01;

/**
 * Clase del Sistema Lanzador
 * 
 * @author Adrián Alcalde Alzaga
 *
 */
public class SistemaLanzador {
	
	public static void main(String[] args) {
		
		final int Puertas= 5;
		IParque parque = new Parque();
		char letra_puerta = 'A';
		
		System.out.println("¡Parque abierto!");
		
		for (int i = 0; i < Puertas; i++) {
			
			String puerta = ""+((char) (letra_puerta++));
			
			// Creación de hilos de entrada
			ActividadEntradaPuerta entradas = new ActividadEntradaPuerta(puerta, parque);
			new Thread (entradas).start();
			
			//Creacion de hilos de salida
			ActividadSalidaPuerta salidas = new ActividadSalidaPuerta(puerta, parque);
			new Thread (salidas).start();
			
			
		}
	}	
}
