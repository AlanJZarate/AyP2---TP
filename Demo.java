package Tp3;

import java.io.FileNotFoundException;

public class Demo {
    public static void main(String[] args) {
        try {
            //LectorLiga.leerLiga("E:\\UNTREF\\AyP2\\TP\\ligas.in", LectorCompetidor.leerCompetidor("E:\\UNTREF\\AyP2\\TP\\personajes.in"));
            //LectorLiga.leerLiga("E:\\UNTREF\\AyP2\\TP\\ligas_changed.in", LectorCompetidor.leerCompetidor("E:\\UNTREF\\AyP2\\TP\\personajes_original.in"));

            JuegoGUI.generarMenu(); // portal hacia la GUI del juego

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (BandoErroneoException e) {
            throw new RuntimeException(e);
        } catch (PeleaAliadaException e) {
            throw new RuntimeException(e);
        }
    }
}
