package Tp3;

import Tp3.Competidor.Caracteristica;

public class Demo {
	public static void main(String[] args) {
		Competidor uno = new Competidor("pepito", true, 15,23,546,7);
		Competidor dos = new Competidor("jorge", true, 15,23,542,7);
		
		System.out.println(uno.lucharPorCaracteristica(dos, Caracteristica.DESTREZA));
	}

}
