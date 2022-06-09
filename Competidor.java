package Tp3;

import java.util.HashMap;
import java.util.Map;

public abstract class Competidor implements Comparable<Competidor> {

	private String nombre;
	// Boolean para determinar si es superHeroe o Villano
	protected boolean esSuperHeroe;
	// Mapa que guarda Caracteristica, Valor numerico de la caracteristica
	protected Map<Caracteristica, Integer> caracteristicaAValor = new HashMap<Caracteristica, Integer>();
	private Caracteristica[] list = { Caracteristica.VELOCIDAD, Caracteristica.FUERZA, Caracteristica.RESISTENCIA,
			Caracteristica.DESTREZA, Caracteristica.VELOCIDAD, Caracteristica.FUERZA, Caracteristica.RESISTENCIA };

	// Constructor que usa un competidor singular
	public Competidor(String nombre, boolean esSuperHeroe, int velocidad, int fuerza, int resistencia, int destreza) {
		this.nombre = nombre;
		this.esSuperHeroe = esSuperHeroe;
		caracteristicaAValor.put(Caracteristica.VELOCIDAD, velocidad);
		caracteristicaAValor.put(Caracteristica.FUERZA, fuerza);
		caracteristicaAValor.put(Caracteristica.RESISTENCIA, resistencia);
		caracteristicaAValor.put(Caracteristica.DESTREZA, destreza);
	}

	public int luchar(Competidor p) throws MismoBandoException {
		if (this.esSuperHeroe == p.esSuperHeroe)
			throw new MismoBandoException("Dos competidores del mismo bando no pueden pelear");
		return this.compareTo(p);
	}


	public int lucharPorCaracteristica(Competidor competidor, Caracteristica c) {
		int aux = c.ordinal();
		for (int i = 0; i<Caracteristica.values().length; i++,aux++) {
			int compare = this.caracteristicaAValor.get(list[aux]).compareTo(competidor.caracteristicaAValor.get(list[aux]));
			if (compare != 0)
				return compare;
		}
				
		return 0;

	}
	

	@Override
	public int compareTo(Competidor competidor) {

		return this.lucharPorCaracteristica(competidor, Caracteristica.VELOCIDAD);
	}

	public String getNombre() {
		return nombre;
	}

	enum Caracteristica {
		VELOCIDAD, FUERZA, RESISTENCIA, DESTREZA;

	}

}
