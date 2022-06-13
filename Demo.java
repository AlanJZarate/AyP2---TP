package Tp3;

import java.io.FileNotFoundException;

public class Demo {
    public static void main(String[] args) {
        try {
            //LectorLiga.leerLiga("E:\\UNTREF\\AyP2\\TP\\ligas.in", LectorCompetidor.leerCompetidor("E:\\UNTREF\\AyP2\\TP\\personajes.in"));
            LectorLiga.leerLiga("E:\\UNTREF\\AyP2\\TP\\ligas_changed.in", LectorCompetidor.leerCompetidor("E:\\UNTREF\\AyP2\\TP\\personajes_original.in"));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (BandoErroneoException e) {
            throw new RuntimeException(e);
        } catch (PeleaAliadaException e) {
            throw new RuntimeException(e);
        }

        /*try {
            System.out.println(LectorCompetidor.print(LectorCompetidor.leerCompetidor("E:\\UNTREF\\AyP2\\TP\\personajes.in")));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }*/
    }
}
