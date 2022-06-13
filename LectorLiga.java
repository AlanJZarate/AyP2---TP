package Tp3;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LectorLiga {
    // la lista de competidores per viene del LectorCompetidor
    public static List<Liga> leerLiga(String fileName, List<Competidor> competidoresDisponibles) throws PeleaAliadaException, BandoErroneoException {
        ArrayList<Liga> ligaResultado = new ArrayList<Liga>();

        try {
            FileReader archivo = new FileReader(fileName);
            BufferedReader lector = new BufferedReader(archivo);
            String[] datos;
            String line = lector.readLine();

            while (line != null) {
                datos = line.split(",");
                boolean esHeroe = false;

                for (Competidor comp : competidoresDisponibles) {
                    if (comp.getNombre().trim().equals(datos[1].trim())) {
                        esHeroe = comp.esHeroe();
                    }
                }
                Liga liga = new Liga(datos[0], esHeroe);
                for (int i = 1; i < datos.length; i++) {
                    for (Competidor comp : competidoresDisponibles) {
                        if (comp.getNombre().trim().equals(datos[i].trim())) {
                            liga.agregarCompetidor(comp);
                        }
                    }
                }
                competidoresDisponibles.add(liga);
                ligaResultado.add(liga);
                line = lector.readLine();
            }
            lector.close();

        } catch (FileNotFoundException e) {
            System.out.println("No se encontro el archivo " + fileName + ". " + e);
        } catch (NumberFormatException e1) {
            System.out.println("Se han introducido caracteres no numericos. " + e1);
        } catch (IOException e3) {
            e3.printStackTrace();
        }
        return ligaResultado;
    }
}
