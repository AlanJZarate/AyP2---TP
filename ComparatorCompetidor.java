package Tp3;

import java.util.Comparator;

import Tp3.Competidor.Caracteristica;

public class ComparatorCompetidor implements Comparator<Competidor> {
	private final Caracteristica[] list = { Caracteristica.VELOCIDAD, Caracteristica.FUERZA, Caracteristica.RESISTENCIA,
			Caracteristica.DESTREZA, Caracteristica.VELOCIDAD, Caracteristica.FUERZA, Caracteristica.RESISTENCIA };
	
	public int compare(Competidor competidor1, Competidor competidor2, Caracteristica c) {
		int aux = c.ordinal();
		for (int i = 0; i<Caracteristica.values().length; i++,aux++) {
			int compare = competidor1.caracteristicaAValor.get(list[aux]).compareTo(competidor2.caracteristicaAValor.get(list[aux]));
			if (compare != 0)
				return compare;
		}
		return 0;
	}
	public int compare(Competidor competidor1, Competidor competidor2) {
		return compare(competidor1,competidor2,Caracteristica.VELOCIDAD);
	}

}
