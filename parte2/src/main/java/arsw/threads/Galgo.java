package arsw.threads;

/**
 * Un galgo que puede correr en un carril
 * 
 * @author rlopez
 * 
 */
public class Galgo extends Thread {
	private int paso;
	private Carril carril;
	RegistroLlegada regl;
	private boolean stop;

	/**
	 * Constructor
	 * @param carril Carril
	 * @param name nombre del galgo
	 * @param reg registro de llegada
	 */
	public Galgo(Carril carril, String name, RegistroLlegada reg) {
		super(name);
		this.carril = carril;
		paso = 0;
		this.regl=reg;
		this.stop = false;
	}

	/**
	 * Funcion que pone a correr el galgo
	 * @throws InterruptedException
	 */
	public void corra() throws InterruptedException {
		while (paso < carril.size()) {			
			Thread.sleep(100);

			//Usamos esta funcion para pausar el juego
			synchronized(this) {
				while(stop) {
					this.wait();
				}
			}

			carril.setPasoOn(paso++);
			carril.displayPasos(paso);
			
			if (paso == carril.size()) {						
				carril.finish();
				int ubicacion=regl.getUltimaPosicionAlcanzada();
				regl.setUltimaPosicionAlcanzada(ubicacion+1);
				System.out.println("El galgo "+this.getName()+" llego en la posicion "+ubicacion);
				if (ubicacion==1){
					regl.setGanador(this.getName());
				}
				
			}
		}
	}

	/**
	 * Metodo que pausa el galgo
	 * @param stop boolean
	 */
	public synchronized void stop(boolean stop) {
		this.stop = stop;

		if(!stop) {
			this.notifyAll();
		}
	}


	@Override
	public void run() {
		
		try {
			corra();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

}
