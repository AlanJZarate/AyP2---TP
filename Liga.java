package Tp3;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Liga extends Competidor {
	private List<Competidor> competidoresDeLaLiga = new ArrayList<Competidor>();

	public Liga(String nombre, boolean formadaPorSuperHeroes) {
		super(nombre, formadaPorSuperHeroes, 0, 0, 0, 0);
	}

	public boolean agregarCompetidor(Competidor competidor) throws MismoBandoException {
		if (this.esSuperHeroe == competidor.esSuperHeroe) {
			throw new MismoBandoException("Dos personajes del mismo bando no pueden pelear");
		}
		if (competidoresDeLaLiga.add(competidor)) {
			this.recalcularDatos();
			return true;
		}
		return false;
	}

	private void recalcularDatos() {
		for (Map.Entry<Caracteristica, Integer> entry : caracteristicasToInt.entrySet()) {
			caracteristicasToInt.replace(entry.getKey(), this.getPromedioCaracteristica(entry.getKey()));
		}
	}

	private int getPromedioCaracteristica(Caracteristica c) {
		int totalCaracteristicas = 0;
		for (Competidor competidor : competidoresDeLaLiga) {
			totalCaracteristicas += competidor.caracteristicasToInt.get(c);
		}
		return totalCaracteristicas / competidoresDeLaLiga.size();
	}
}
