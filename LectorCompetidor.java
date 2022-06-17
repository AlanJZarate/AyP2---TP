package Tp3;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LectorCompetidor {
    //Para el lector de liga la idea es crear la liga con su nombre y tipo y despues pasarle los personajes de la lista de este main.

    public static List<Competidor> obtenerCompetidoresDesdeArchivo(String fileName) throws FileNotFoundException, NumberFormatException, BandoErroneoException {
        ArrayList<Competidor> competidoresResultado = new ArrayList<Competidor>();
        try {
            FileReader archivo = new FileReader(fileName);
            BufferedReader lector = new BufferedReader(archivo);
            String[] datos;
            String line = lector.readLine();

            while (line != null) {
                datos = line.split(",");
                competidoresResultado.add(new Personaje(datos[1], datos[2], datos[0].trim().equals("Heroe"), Integer.parseInt(datos[3].trim()), Integer.parseInt(datos[4].trim()),
                        Integer.parseInt(datos[5].trim()), Integer.parseInt(datos[6].trim())));
                line = lector.readLine();
            }
            lector.close(); // ver si lo metemos dentro del finally

        } catch (FileNotFoundException e) {
            System.out.println("No se encontr el archivo " + fileName +". " +  e);
        } catch (NumberFormatException ex) {
            System.out.println("Se han introducido caracteres no numericos " + ex);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return competidoresResultado;
    }

    /*
     * ToDo: solo para testeo, luego hay que borrarlo (no nos mates, Lucas)
     * */
    public String print(List<Personaje> personajes) {
        String aux = "";
        for (Personaje p : personajes) {
            aux += p.nombreReal + " ";
            aux += p.esHeroe() + " ";
            aux += p.getNombre();
            aux += "\n";
        }
        return aux;
    }
}

