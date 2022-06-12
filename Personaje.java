package Tp3;

public class Personaje extends Competidor {
	
	String nombreReal;

	public Personaje(String nombreReal, String nombrePersonaje, boolean esSuperHeroe, int velocidad, int fuerza, int resistencia, int destreza) {
		super(nombrePersonaje, esSuperHeroe, velocidad, fuerza, resistencia, destreza);
		this.nombreReal = nombreReal;
	}
	public String getNombrePersonaje() {
		return nombreReal;
	}	
}
