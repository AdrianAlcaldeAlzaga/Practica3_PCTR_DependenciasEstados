package p03.c01;

import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

/***
 * Clase ActividadSalidaPuerta
 * 
 * @author Adrián Alcalde Alzaga
 *
 */
public class ActividadSalidaPuerta implements Runnable{

	private static final int NUMSALIDAS = 20;
	private String puerta;
	private IParque parque;

	/**
	 * Constructor
	 * 
	 * @param puerta
	 * @param parque
	 */
	public ActividadSalidaPuerta(String puerta, IParque parque) {
		this.puerta = puerta;
		this.parque = parque;
	}

	@Override
	public void run() {
		for (int i = 0; i < NUMSALIDAS; i ++) {
			try {
				parque.salirDelParque(puerta);
				TimeUnit.MILLISECONDS.sleep(new Random().nextInt(5)*1000);
			} catch (InterruptedException e) {
				Logger.getGlobal().log(Level.INFO, "Salida interrumpida");
				Logger.getGlobal().log(Level.INFO, e.toString());
				return;
			}
		}
	}
}
