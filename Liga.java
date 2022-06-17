package Tp3;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Liga extends Competidor {
    private List<Competidor> competidoresDeLaLiga = new ArrayList<Competidor>();

    public Liga(String nombre, boolean formadaPorSuperHeroes) {
        super(nombre, formadaPorSuperHeroes, 0, 0, 0, 0);
    }

    public boolean agregarCompetidor(Competidor competidor) throws BandoErroneoException {
        if (this.esHeroe() != competidor.esHeroe()) {
            throw new BandoErroneoException("Dos personajes de distintos bandos no pueden estar en la misma liga \n" + this.getNombre() + " " + competidor.getNombre());
        }
        if (competidoresDeLaLiga.add(competidor)) {
            this.recalcularDatos();
            return true;
        }
        return false;
    }

    private void recalcularDatos() {
        for (Map.Entry<Caracteristica, Integer> entry : caracteristicaAValor.entrySet()) {
            caracteristicaAValor.replace(entry.getKey(), this.getPromedioCaracteristica(entry.getKey()));
        }
    }

    private int getPromedioCaracteristica(Caracteristica c) {
        int totalCaracteristicas = 0;
        for (Competidor competidor : competidoresDeLaLiga) {
            totalCaracteristicas += competidor.caracteristicaAValor.get(c);
        }
        return totalCaracteristicas / competidoresDeLaLiga.size();
    }

    /**
     * Metodo overrideado con el fin de mostrar coherentemente la liga y por quienes esta compuesta
     * @return
     */
    @Override
    public String toString() {
        String competidoresConcatenados = "";
        for(Competidor c :competidoresDeLaLiga){
            if(competidoresDeLaLiga.indexOf(c) < competidoresDeLaLiga.size()) // si el competidor no es el ultimo
                                                                                // lo separo con coma
                competidoresConcatenados += c + "\n";
            else
                competidoresConcatenados += c + ".";                           // si es el ultimo, punto final
        }
        return "Liga de " + (esHeroe() == true ? "heroes " : "villanos ") + this.getNombre() + " conformada por: \n" +
                "==========================================================\n"
                + competidoresConcatenados;
    }
}

