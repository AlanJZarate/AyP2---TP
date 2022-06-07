package Tp3;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;



public class LectorCompetidor {
	//Para el lector de liga la idea es crear la liga con su nombre y tipo y despues pasarle los personajes de la lista de este main.

	public static List<Competidor> main(String fileName) throws FileNotFoundException,NumberFormatException,MismoBandoException {
		ArrayList<Competidor> aux = new ArrayList<Competidor>();
		try {
			
			FileReader archivo = new FileReader(fileName);
			BufferedReader lector = new BufferedReader(archivo);
			String[] datos;
			String line = lector.readLine();

			while (line != null) {

				datos = line.split(",");
				if (datos[0].equals("Heroe"))
					aux.add(new Personaje(datos[1], datos[2],true, Integer.parseInt(datos[3]), Integer.parseInt(datos[4]),
							Integer.parseInt(datos[5]), Integer.parseInt(datos[6])));
				if (datos[0].equals("Villano"))
					aux.add(new Personaje(datos[1], datos[2],false, Integer.parseInt(datos[3]), Integer.parseInt(datos[4]),
							Integer.parseInt(datos[5]), Integer.parseInt(datos[6])));
				line = lector.readLine();

			}
			lector.close();

		}catch (FileNotFoundException e) {
			System.out.println("pasalo bien flaco "+ e);
		}catch(NumberFormatException ex){
            System.out.println("Se han introducido caracteres no numéricos " + ex);
        } catch (IOException e) {
				e.printStackTrace();
		}
		
		return aux;
	}

}
