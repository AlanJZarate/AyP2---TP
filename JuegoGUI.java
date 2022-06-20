package Tp3;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class JuegoGUI {

	private static ArrayList<Competidor> competidoresParaGuardarAArchivo = new ArrayList<Competidor>(); // aca van los
	static Scanner sc = new Scanner(System.in);																// personajes
																										// que luego se
																										// escribiran
	// en el archivo
	private static List<Competidor> competidoresLeidosDesdeArchivo = null; // lista que se va llenando de competidores
																			// que entran desde
	// archivo. No puse ArrayList porque no queria tocar el tipo de dato de la Liga

	private static List<Competidor> todosLosCompetidores = new ArrayList<Competidor>();
	// Lista que contiene todos los competidores, tanto los ingresados por archivo
	// como manualmente

	/**
	 * Genera el menu en consola. Contiene opciones que se utilizan interactivamente
	 * en tiempo real.
	 *
	 * @throws BandoErroneoException
	 * @throws FileNotFoundException
	 * @throws PeleaAliadaException
	 */
	public static void generarMenu() throws BandoErroneoException, IOException, PeleaAliadaException {
		
		boolean salir = false; // flag para terminar ejecucion del juego
		System.out.println("Bienvenido/a a Luchardo™, por favor, indique su nombre:\n");
		String nombre = sc.nextLine();

		
		while (!salir) {
			System.out.println("Hola " + nombre + ", por favor, elija una opción:\t");
			System.out.println("1. Administrar personajes\n" + "2. Administrar Ligas\n" + "3. Realizacion de combates\n"
					+ "9. Salir\n");

			int opcion = Integer.parseInt(sc.nextLine()); // leo la opcion por teclado

			switch (opcion) {
			case 1:
				try {
					lanzarSubMenuAdministrarCompetidores(false);
					break;
				} catch (FileNotFoundException fNFE) {
					System.err.println(fNFE.getMessage());
				} catch (BandoErroneoException bEE) {
					System.err.println(bEE.getMessage());
				} catch (PeleaAliadaException pAE) {
					System.err.println(pAE.getMessage());
				} catch (IOException iOE) {
					System.err.println(iOE.getMessage());
				}
			case 2:
				try {
					lanzarSubMenuAdministrarCompetidores(true);
					break;
				} catch (FileNotFoundException fNFE) {
					System.err.println(fNFE.getMessage());
				} catch (BandoErroneoException bEE) {
					System.err.println(bEE.getMessage());
				} catch (PeleaAliadaException pAE) {
					System.err.println(pAE.getMessage());
				} catch (IOException iOE) {
					System.err.println(iOE.getMessage());
				}
				break;
			case 3:
				try {
					lanzarSubMenuGenerarCombate();
					break;
				} catch (FileNotFoundException fNFE) {
					System.err.println(fNFE.getMessage());
				} catch (PeleaAliadaException pAE) {
					System.err.println(pAE.getMessage());
				} catch (IOException iOE) {
					System.err.println(iOE.getMessage());
				}
				break;
			case 9:
				salir = true;
				System.out.println("Saliendo del juego... Vuelva prontos!");
				sc.close();
				break;
			}			
		}
	}

	/**
	 * Se lanza cuando la opcion "Administrar personajes" fue elegida. Trata a las
	 * Ligas como Competidores.
	 *
	 * @throws BandoErroneoException
	 * @throws FileNotFoundException
	 * @throws PeleaAliadaException
	 */
	private static void lanzarSubMenuAdministrarCompetidores(boolean esLiga)
			throws BandoErroneoException, IOException, PeleaAliadaException {

		System.out.println("1. Carga de " + (esLiga ? "Liga " : "Personaje ") + "desde archivo\n" + "2. Crear "
				+ (esLiga ? "Liga " : "Personaje ") + "manualmente\n" + "3. Listar "
				+ (esLiga ? "Ligas " : "Personajes ") + "\n" + "4. Guardar " + (esLiga ? "Liga " : "Personaje") + "s "
				+ " creados a un archivo\n");

		switch (Integer.parseInt(sc.nextLine())) {

		case 1: // 1. Carga de Personaje/Liga desde archivo
			String path = obtenerRutaDeArchivo(esLiga);
			if (esLiga) {
				if (competidoresLeidosDesdeArchivo != null) {
					competidoresLeidosDesdeArchivo
							.addAll(LectorLiga.obtenerLigasDesdeArchivo(path, competidoresLeidosDesdeArchivo));
				} else
					System.err.println("No hay Personajes creados para crear una Liga, primero debe crear uno.");
			} else {
				competidoresLeidosDesdeArchivo = LectorCompetidor.obtenerCompetidoresDesdeArchivo(path);
				todosLosCompetidores.addAll(competidoresLeidosDesdeArchivo);
			}

			break;

		case 2: // 2. Crear Personaje/Liga manualmente
			if (esLiga)
				if (competidoresLeidosDesdeArchivo != null)
					crearCompetidorParaGuardarAArchivo(true); // crea una Liga
				else
					System.err.println("No hay Personajes creados para crear una Liga, primero debe crear uno.");
			else
				crearCompetidorParaGuardarAArchivo(false); // crea un Personaje

			break;
		case 3:
			listarCompetidores();
			break;
		case 4:
			String nombreDeArchivo = "";
			System.out.println("Ingrese la ruta donde desea guardar el archivo:");
			if ((nombreDeArchivo = sc.nextLine()) == "") {
				nombreDeArchivo = String.format("D:\\archivo_" + System.currentTimeMillis() + ".in");
			}
			guardarAArchivo(competidoresParaGuardarAArchivo, nombreDeArchivo);
			break;		
		}
	}

	public static void lanzarSubMenuGenerarCombate() throws PeleaAliadaException, IOException {
//		System.out.println("¿Desea combatir por una caracteristica particular?");
//		boolean peleanPorCaracteristica = 
		System.out.println("Ingrese el ID de los competidores listados que van a combatir (separados por coma): ");
		listarCompetidores();
		String idCompetidores = (sc.nextLine());
		String[] stringSpliteados = idCompetidores.split(",");
		int idCompetidor1 = Integer.parseInt(stringSpliteados[0].trim());
		int idCompetidor2 = Integer.parseInt(stringSpliteados[1].trim());
		Competidor competidor1 = obtenerCompetidor(idCompetidor1);
		Competidor competidor2 = obtenerCompetidor(idCompetidor2);
		int resultado = competidor1.luchar(competidor2);

		switch (resultado) {
		case 1: {
			System.out.println("¡GANADOR!: " + competidor1.getNombre());
			break;
		}
		case -1:
			System.out.println("¡GANADOR!: " + competidor2.getNombre());
			break;
		case 0:
			System.out.println(
					"¡Empate! " + competidor1.getNombre() + " y " + competidor2.getNombre() + " son dos mediocres");
			break;
		}

	}

	/**
	 * @return el path ingresado por teclado
	 */
	public static String obtenerRutaDeArchivo(boolean esLiga) {
		String pathResultado = "";

		if (!esLiga) { // ruta para un Personaje
			System.out.println("Ingrese la ruta del archivo personajes.in a leer con el siguiente formato: "
					+ "\"C:\\Usuarios\\UsuarioBross\\Documentos\\personajes.in o \"Archivos/personajes_original.in para path relativo");
			pathResultado = sc.nextLine();
			while (!pathValido(pathResultado)) // mientras el path sea invalido, lo sigo pidiendo (y pathValido muestra
												// un error)
				pathResultado = sc.nextLine();
		} else { // ruta para una Liga
			System.out.println("Ingrese la ruta del archivo ligas.in a leer con el siguiente formato: \"C:\\Usuarios\\"
					+ "UsuarioBross\\Documentos\\ligas.in o \"Archivos/ligas_changed.in para path relativo");
			pathResultado = sc.nextLine();
			while (!pathValido(pathResultado)) // mientras el path sea invalido, lo sigo pidiendo (y pathValido muestra
												// un error)
				pathResultado = sc.nextLine();
		}		
		return pathResultado;
	}

	/**
	 * valida un path pasado como parametro si un 'File' fake creado en tiempo de
	 * ejecucion existe, sino, tira error via System.err.println
	 *
	 * @param path
	 */
	private static boolean pathValido(String path) {
		boolean resultado = true; // pathResultado arranca como si fuera valido. No hice todo sobre 'path' para no
		// pisar datos que vienen por parametro
		if (!new File(path).exists()) {
			System.err.println("El archivo \"" + path
					+ "\" especificado no existe. Por favor, compruebe la ruta y vuelva a intentar a continuacion.");
			resultado = false; // si el path no es valido, tiro false para que no funcione
		}
		return resultado;
	}

	private static void crearCompetidorParaGuardarAArchivo(boolean esLiga) throws BandoErroneoException {
		// una liga necesita un nombre y personajes que la contienen
		// un personaje necesita nombreReal, nombreHeroeVilllano, Caracteristicas

		System.out.println("Ingrese " + (esLiga ? "el nombre de la Liga" : " nombre real del personaje:"));
		String nombreReal = sc.nextLine();

		String nombreCompetidor = null; // para no ponerle "nombreDeSuperHeroeOVillano"
		Character bando = null; // medio flojo porque si ponen otra cosa se rompe
		int velocidad = 0;
		int fuerza = 0;
		int resistencia = 0;
		int destreza = 0;

		if (!esLiga) {
			System.out.println("Ingrese el nombre de Heroe/Villano del personaje:");
			nombreCompetidor = sc.nextLine();

			System.out.println("Ingrese el bando del personaje: ('h' para heroe, 'v' para villano):");
			bando = sc.nextLine().charAt(0);

			System.out.println("Ingrese el valor de VELOCIDAD del personaje:");
			velocidad = Integer.parseInt(sc.nextLine());

			System.out.println("Ingrese el valor de FUERZA del personaje:");
			fuerza = Integer.parseInt(sc.nextLine());

			System.out.println("Ingrese el valor de RESISTENCIA del personaje:");
			resistencia = Integer.parseInt(sc.nextLine());

			System.out.println("Ingrese el valor de DESTREZA del personaje:");
			destreza = Integer.parseInt(sc.nextLine());

			Competidor nuevo = new Personaje(nombreReal, nombreCompetidor, bando == 'h' ? true : false, velocidad, fuerza,
					resistencia, destreza);
			
			competidoresParaGuardarAArchivo.add(nuevo);
			todosLosCompetidores.add(nuevo);
		} else {			
			System.out.println("Ingrese el bando de la Liga: ('h' para heroe, 'v' para villano):");
			bando = sc.nextLine().charAt(0);
			Liga nuevaLiga = new Liga(nombreReal, bando == 'h' ? true : false);
			System.out.println("Ingrese el ID de los competidores listados que van a combatir (separados por coma): ");
			listarCompetidores();
			String idCompetidores = (sc.nextLine());			
			String[] stringSpliteados = idCompetidores.split(",");
			for(int i = 0; i < stringSpliteados.length; i++) {
				Competidor competidorTemporal = obtenerCompetidor(Integer.parseInt(stringSpliteados[i]));
				nuevaLiga.agregarCompetidor(competidorTemporal);
				competidoresParaGuardarAArchivo.add(nuevaLiga);
				todosLosCompetidores.add(nuevaLiga);
			}			
		}
		
	}

	/**
	 * Sirve para obtener un competidor de la lista competidoresDesdeArchivo dado un
	 * idCompetidor
	 *
	 * @param idCompetidor competidor que se necesita obtener
	 * @return
	 */
	private static Competidor obtenerCompetidor(int idCompetidor) { // 2 competidores
		if (idCompetidor >= 0 && idCompetidor < todosLosCompetidores.size()) {
			return todosLosCompetidores.get(idCompetidor);
		}
		return null;
	}

	/**
	 * Lista todos los competidores que se hayan tenido en cuenta al
	 * crearse/importarse - Nota: toma las ligas como Competidores que son. ToDo ver
	 * como hacer para separar Ligas de Personajes
	 */
	public static void listarCompetidores() {
		if (todosLosCompetidores != null) {
			for (Competidor c : todosLosCompetidores) {
				System.out.println("Competidor " + todosLosCompetidores.indexOf(c) + ": " + c.toString());
				// index of para mostrar el numero de personaje
			}
		} else {
			System.err.println("No hay competidores que mostrar");
		}
	}

	/**
	 * Guarda a un archivo elegido por el usuario (sino crea un archivo con un
	 * nombre del estilo "D:\archivo_1655509117824.in") Por ahora usa D:/ para
	 * guardar por default
	 *
	 * @param competidores Lista de personajes a usar para guardar a archivo
	 * @param filePath     Ruta completa (por ej. "D:\personajes.out") que es la
	 *                     ruta + nombredefile a guardar
	 * @throws IOException
	 */
	public static void guardarAArchivo(List<Competidor> competidores, String filePath) throws IOException {
		FileWriter fw = new FileWriter(filePath);
		for (Competidor c : competidores) {
			fw.append(((Personaje) c).toStringParaArchivo() + "\n");
		}
		if (pathValido(filePath))
			System.out.println(filePath + " guardado con exito!");
		else
			System.err.println("Error al guardar " + filePath);
		fw.close();
	}

	/**
	 * intento de limpiar la consola para que no se vea todo el historico, parece
	 * funcionar en algunos contextos
	 */
//	private static void limpiarConsola() { // ToDo borrar si no se usa
//		System.out.print("\033[H\033[2J");
//		System.out.flush();
//	}
	
}
