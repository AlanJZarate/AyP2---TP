package Tp3;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LectorLiga {
// la lista de competidores per viene del LectorCompetidor
	public static List<Liga> main(String fileName, List<Competidor> per) throws MismoBandoException {
		ArrayList<Liga> aux = new ArrayList<Liga>();
try {
			
			FileReader archivo = new FileReader(fileName);
			BufferedReader lector = new BufferedReader(archivo);
			String[] datos;
			String line = lector.readLine();

			while (line != null) {

				datos = line.split(",");
				boolean esHeroe = true;
				if (datos[0].equals("Heroe"))
					esHeroe = true;
				if (datos[0].equals("Villano"))
					esHeroe = false;
				Liga liga = new Liga(datos[1],esHeroe);
				for(int i = 2; i<datos.length;i++) {
					for (Competidor comp : per) {
						if(comp.getNombre()==datos[i])
							liga.agregarCompetidor(comp);
					}
					
				}
				aux.add(liga);
					
				line = lector.readLine();

			}
			lector.close();

		}catch (FileNotFoundException e) {
			System.out.println("pasalo bien flaco "+ e);
		}catch(NumberFormatException e1){
            System.out.println("Se han introducido caracteres no num�ricos " + e1);
		}catch (MismoBandoException e2) {
			
        } catch (IOException e3) {
				e3.printStackTrace();
		}
		
		return aux;
	}

}
