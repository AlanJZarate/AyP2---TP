package Tp3;

import java.util.HashMap;
import java.util.Map;

public class Competidor implements Comparable<Competidor> {

	private String nombre;
	// Boolean para determinar si es superHeroe o Villano
	protected boolean esSuperHeroe;
	// Mapa que guarda Caracteristica, Valor numerico de la caracteristica
	protected Map<Caracteristica, Integer> caracteristicasToInt = new HashMap<Caracteristica, Integer>();
	private Caracteristica[] list = { Caracteristica.VELOCIDAD, Caracteristica.FUERZA, Caracteristica.RESISTENCIA,
			Caracteristica.DESTREZA, Caracteristica.VELOCIDAD, Caracteristica.FUERZA, Caracteristica.RESISTENCIA };

	// Constructor que usa un competidor singular
	public Competidor(String nombre, boolean esSuperHeroe, int velocidad, int fuerza, int resistencia, int destreza) {
		this.nombre = nombre;
		this.esSuperHeroe = esSuperHeroe;
		caracteristicasToInt.put(Caracteristica.VELOCIDAD, velocidad);
		caracteristicasToInt.put(Caracteristica.FUERZA, fuerza);
		caracteristicasToInt.put(Caracteristica.RESISTENCIA, resistencia);
		caracteristicasToInt.put(Caracteristica.DESTREZA, destreza);
	}

	public int luchar(Competidor p) throws MismoBandoException {
		if (this.esSuperHeroe == p.esSuperHeroe)
			throw new MismoBandoException("Dos competidores del mismo bando no pueden pelear");
		return this.compareTo(p);
	}

//	private int lucharPorCaracteristica(Competidor competidor, Caracteristica c) {
//		for (int i = c.getPos(); i < Caracteristica.values().length; i++) {
//			if (this.caracteristicaToValue.get(c).compareTo(competidor.caracteristicaToValue.get(c)) != 0)
//				return this.caracteristicaToValue.get(c).compareTo(competidor.caracteristicaToValue.get(c));
//
//		}
//		return 0;
//	}
	public int lucharPorCaracteristica(Competidor competidor, Caracteristica c) {
		int aux = 0;
		for (Caracteristica carac : list) {
			if (carac == c) {
				for (int i = 0; i < Caracteristica.values().length; i++) {
					if (this.caracteristicasToInt.get(list[aux+i]).compareTo(competidor.caracteristicasToInt.get(list[aux+i])) != 0)
						return this.caracteristicasToInt.get(list[aux+i]).compareTo(competidor.caracteristicasToInt.get(list[aux+i]));
				}
			}
			aux++;
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
		VELOCIDAD(0), FUERZA(1), RESISTENCIA(2), DESTREZA(3);

		private int pos;

		Caracteristica(int i) {
			pos = i;
		}

		public static Caracteristica get(int i) {
			if (i == 0)
				return Caracteristica.VELOCIDAD;
			if (i == 1)
				return Caracteristica.FUERZA;
			if (i == 2)
				return Caracteristica.RESISTENCIA;
			if (i == 3)
				return Caracteristica.DESTREZA;
			return Caracteristica.VELOCIDAD;
		}

		public int getPos() {
			return pos;
		}

	}

}
