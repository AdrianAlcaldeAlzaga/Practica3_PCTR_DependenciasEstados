package src.p03.c01;

/**
 * Clase ActividadEntradaPuerta
 * 
 * @author Adrián Alcalde Alzaga
 *
 */
public class ActividadSalidaPuerta implements Runnable{

	private static final int NUMENTRADAS = 20;
		private String puerta;
		private IParque parque;

		/**
		 * Constructor de la clase
		 * 
		 * @param puerta
		 * @param parque
		 */
		public ActividadEntradaPuerta(String puerta, IParque parque) {
			this.puerta = puerta;
			this.parque = parque;
		}

		@Override
		public void run() {
			for (int i = 0; i < NUMENTRADAS; i ++) {
				try {
					parque.entrarAlParque(puerta);
					TimeUnit.MILLISECONDS.sleep(new Random().nextInt(5)*1000);
				} catch (InterruptedException e) {
					Logger.getGlobal().log(Level.INFO, "Entrada interrumpida");
					Logger.getGlobal().log(Level.INFO, e.toString());
					return;
				}
			}
		}
}
