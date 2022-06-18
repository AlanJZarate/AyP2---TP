package Tp3;


public class Personaje extends Competidor {
	
	String nombreReal;

	public Personaje(String nombreReal, String nombrePersonaje, boolean esSuperHeroe, int velocidad, int fuerza, int resistencia, int destreza) {
		super(nombrePersonaje, esSuperHeroe, velocidad, fuerza, resistencia, destreza);
		this.nombreReal = nombreReal;
	}
	public String getNombreReal() {
		return nombreReal;
	}

	@Override
	public String toString() {
		return "Nombre real: " + getNombreReal() + ", Nombre de superHeroe/Villano: " + super.getNombre() + ", Liga: " + (esHeroe() == true ? "Heroe." : "Villano.");
	}

	/**
	 * Genera un string formateado para poder escribirse a un archivo con el formato:
	 * "Bando, NombreReal, Nombre SuperHeroe/Villano, VELOCIDAD.value, FUERZA.value, RESISTENCIA.value, DESTREZA.value"
	 * para matchear con el formato solicitado, como en personajes.in brindado
	 * @return String formateado para poder escribirse a un archivo con el formato solicitado, como en personajes.in brindado
	 */
	public String toStringParaArchivo() {
		return  (esHeroe() == true ? "Heroe, " : "Villano, ") + getNombreReal() + ", " + super.getNombre() + ", "
				+ caracteristicaAValor.get(Caracteristica.VELOCIDAD) + ", "
				+ caracteristicaAValor.get(Caracteristica.FUERZA) + ", "
				+ caracteristicaAValor.get(Caracteristica.RESISTENCIA) + ", "
				+ caracteristicaAValor.get(Caracteristica.DESTREZA);
	}
}
