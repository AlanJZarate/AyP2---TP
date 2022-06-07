package Tp3;

import Tp3.Competidor.Caracteristica;

public class Demo {
    public static void main(String[] args) {
        Competidor uno = new Competidor("pepito", true, 15, 23, 546, 7);
        Competidor dos = new Competidor("jorge", true, 15, 23, 550, 7);

        Competidor tres = new Competidor("villano3", false, 15, 23, 546, 7);
        Competidor cuatro = new Competidor("heroe3", true, 15, 23, 550, 7);
        try {
            System.out.println(uno.lucharPorCaracteristica(dos, Caracteristica.DESTREZA));
            System.out.println(tres.lucharPorCaracteristica(cuatro, Caracteristica.DESTREZA));
        } catch (MismoBandoException ex) {
            System.out.println(ex.getMessage());
        }
    }
}
