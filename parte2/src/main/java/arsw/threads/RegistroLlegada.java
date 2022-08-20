package arsw.threads;

public class RegistroLlegada {

	private int ultimaPosicionAlcanzada=1;

	private String ganador=null;

	/**
	 * Retorna el ganador
	 * @return galgo ganador
	 */
	public String getGanador() {
		return ganador;
	}

	/**
	 * Metodo que cambia el ganador
	 * @param ganador galgo nuevo ganador
	 */
	public synchronized void setGanador(String ganador) {
		this.ganador = ganador;
	}

	/**
	 * Metodo que retora la ultima posicion alcanzada
	 * @return ultima posicion
	 */
	public synchronized int getUltimaPosicionAlcanzada() {
		return ultimaPosicionAlcanzada;
	}

	/**
	 * Metodo que cambia la ultima posicion alcanzada
	 * @param ultimaPosicionAlcanzada nueva ultima posicion
	 */
	public synchronized void setUltimaPosicionAlcanzada(int ultimaPosicionAlcanzada) {
		this.ultimaPosicionAlcanzada = ultimaPosicionAlcanzada;
	}
	
}
