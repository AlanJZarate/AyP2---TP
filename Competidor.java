package Tp3;

import java.util.HashMap;
import java.util.Map;

public abstract class Competidor implements Comparable<Competidor> {
	
	private ComparatorCompetidor comparator = new ComparatorCompetidor();
	private String nombre;
	// Boolean para determinar si es superHeroe o Villano
	protected boolean esSuperHeroe;
	// Mapa que guarda Caracteristica, Valor numerico de la caracteristica
	protected Map<Caracteristica, Integer> caracteristicaAValor = new HashMap<Caracteristica, Integer>();

	// Constructor que usa un competidor singular
	public Competidor(String nombre, boolean esSuperHeroe, int velocidad, int fuerza, int resistencia, int destreza) {
		this.nombre = nombre;
		this.esSuperHeroe = esSuperHeroe;
		caracteristicaAValor.put(Caracteristica.VELOCIDAD, velocidad);
		caracteristicaAValor.put(Caracteristica.FUERZA, fuerza);
		caracteristicaAValor.put(Caracteristica.RESISTENCIA, resistencia);
		caracteristicaAValor.put(Caracteristica.DESTREZA, destreza);
	}

	public int luchar(Competidor p) throws PeleaAliadaException {
		if (this.esSuperHeroe == p.esSuperHeroe)
			throw new PeleaAliadaException("Dos competidores del mismo bando no pueden pelear");
		return this.compareTo(p);
	}
	public int lucharPorCaracteristica(Competidor competidor, Caracteristica c) {
		return comparator.compare(this, competidor, c);
	}
	
	public boolean getBando(){
		return this.esSuperHeroe;
	}
	

	@Override
	public int compareTo(Competidor competidor) {
		return comparator.compare(this,competidor);
	}

	public String getNombre() {
		return nombre;
	}

	enum Caracteristica {
		VELOCIDAD, FUERZA, RESISTENCIA, DESTREZA;

	}

}
