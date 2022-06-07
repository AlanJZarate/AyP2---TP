package Tp3;


public class Personaje extends Competidor {
	
	String nombrePersonaje;

	public Personaje(String nombreReal, String nombrePersonaje, boolean esSuperHeroe, int velocidad, int fuerza, int resistencia, int destreza) {
		super(nombrePersonaje, esSuperHeroe, velocidad, fuerza, resistencia, destreza);
		this.nombrePersonaje = nombrePersonaje;
	}
	public String getNombrePersonaje() {
		return nombrePersonaje;
	}


	
}
