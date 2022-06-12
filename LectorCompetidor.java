package Tp3;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;



public class LectorCompetidor {
	//Para el lector de liga la idea es crear la liga con su nombre y tipo y despues pasarle los personajes de la lista de este main.

	public static List<Personaje> leer(String fileName) throws FileNotFoundException,NumberFormatException,PeleaAliadaException {
		ArrayList<Personaje> aux = new ArrayList<Personaje>();
		try {
			
			FileReader archivo = new FileReader(fileName);
			BufferedReader lector = new BufferedReader(archivo);
			String[] datos;
			String line = lector.readLine();

			while (line != null) {

				datos = line.split(",");
					aux.add(new Personaje(datos[1], datos[2],datos[0].equals("Heroe"), Integer.parseInt(datos[3]), Integer.parseInt(datos[4]),
							Integer.parseInt(datos[5]), Integer.parseInt(datos[6])));
				line = lector.readLine();

			}
			lector.close();

		}catch (FileNotFoundException e) {
			System.out.println("pasalo bien flaco "+ e);
		}catch(NumberFormatException ex){
            System.out.println("Se han introducido caracteres no numericos " + ex);
        } catch (IOException e) {
				e.printStackTrace();
		}
		
		return aux;
	}

}

