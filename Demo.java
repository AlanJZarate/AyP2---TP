package Tp3;

import java.io.FileNotFoundException;
import java.io.IOException;

public class Demo {
	public static void main(String[] args) {
		try {
			// LectorLiga.leerLiga("E:\\UNTREF\\AyP2\\TP\\ligas.in",
			// LectorCompetidor.leerCompetidor("E:\\UNTREF\\AyP2\\TP\\personajes.in"));
			// LectorLiga.leerLiga("E:\\UNTREF\\AyP2\\TP\\ligas_changed.in",
			// LectorCompetidor.leerCompetidor("E:\\UNTREF\\AyP2\\TP\\personajes_original.in"));

			JuegoGUI.generarMenu(); // portal hacia la GUI del juego

		} catch (FileNotFoundException fNFE) {
			System.err.println(fNFE.getMessage());
		} catch (BandoErroneoException bEE) {
			System.err.println(bEE.getMessage());
		} catch (PeleaAliadaException pAE) {
			System.err.println(pAE.getMessage());
		} catch (IOException iOE) {
			System.err.println(iOE.getMessage());
		}
	}
}
