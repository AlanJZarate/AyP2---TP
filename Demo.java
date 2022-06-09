package Tp3;

import Tp3.Competidor.Caracteristica;

public class Demo {
	public static void main(String[] args) {
		Competidor uno = new Personaje("pepito","astolfito", true, 15,23,546,7);
		Competidor dos = new Personaje("jorge","astolfio", true, 15,23,542,7);
		
		System.out.println(uno.lucharPorCaracteristica(dos, Caracteristica.DESTREZA));
	}

}
