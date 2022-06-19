package Tp3;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class JuegoGUI {

    private static ArrayList<Competidor> competidoresParaGuardarAArchivo = new ArrayList<Competidor>(); // aca van los personajes que luego se escribiran
    // en el archivo
    private static List<Competidor> competidoresLeidosDesdeArchivo = null; // lista que se va llenando de competidores que entran desde
    // archivo. No puse ArrayList porque no queria tocar el tipo de dato de la Liga

    private static List<Competidor> todosLosCompetidores = new ArrayList<Competidor>();
    // Lista que contiene todos los competidores, tanto los ingresados por archivo como manualmente

    /**
     * Genera el menu en consola. Contiene opciones que se utilizan interactivamente en tiempo real.
     *
     * @throws BandoErroneoException
     * @throws FileNotFoundException
     * @throws PeleaAliadaException
     */
    public static void generarMenu() throws BandoErroneoException, IOException, PeleaAliadaException {
        Scanner sc = new Scanner(System.in);
        boolean salir = false; // flag para terminar ejecucion del juego
        System.out.println("Bienvenido/a a Luchardo(TM), por favor, indique su nombre:\n");
        String nombre = sc.nextLine();


        while (!salir) {
            System.out.println("Hola " + nombre + ", por favor, elija una opción:\t");
            System.out.println("1. Administrar personajes\n" +
                    "2. Administrar Ligas\n" +
                    "3. Realizacion de combates\n" +
                    "9. Salir\n");

            int opcion = Integer.parseInt(sc.nextLine()); // leo la opcion por teclado

            switch (opcion) {
                case 1:
                    lanzarSubMenuAdministrarCompetidores(false);
                    break;
                case 2:
                    lanzarSubMenuAdministrarCompetidores(true);
                    break;
                case 9:
                    salir = true;
                    System.out.println("Saliendo del juego... Vuelva prontos!");
                    break;
            }
        }
        sc.close();
    }

    /**
     * Se lanza cuando la opcion "Administrar personajes" fue elegida. Trata a las Ligas como Competidores.
     *
     * @throws BandoErroneoException
     * @throws FileNotFoundException
     * @throws PeleaAliadaException
     */
    private static void lanzarSubMenuAdministrarCompetidores(boolean esLiga) throws BandoErroneoException, IOException, PeleaAliadaException {
        Scanner sc = new Scanner(System.in);

        System.out.println("1. Carga de " + (esLiga ? "Liga " : "Personaje ") + "desde archivo\n" +
                "2. Crear " + (esLiga ? "Liga " : "Personaje ") + "manualmente\n" +
                "3. Listar " + (esLiga ? "Ligas " : "Personajes ") + "\n" +
                "4. Guardar " + (esLiga ? "Liga " : "Personaje") + "s " + " creados a un archivo\n" +
                (esLiga && competidoresLeidosDesdeArchivo != null ? "5. Agregar Personaje a liga existente" : ""));

        switch (Integer.parseInt(sc.nextLine())) {

            case 1: // 1. Carga de Personaje/Liga desde archivo
                String path = obtenerRutaDeArchivo(esLiga);
                if (esLiga) {
                    if (competidoresLeidosDesdeArchivo != null) {
                        competidoresLeidosDesdeArchivo.addAll(LectorLiga.obtenerLigasDesdeArchivo(path, competidoresLeidosDesdeArchivo));
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
            case 5:
                System.out.println("Ingrese el ID de la Liga, de los siguientes Competidores listados: ");
                listarCompetidores();

                int idPersonaje = Integer.parseInt(sc.nextLine());

                Competidor liga = null;

                while (obtenerCompetidor(idPersonaje).getClass() == Liga.class)
                    System.err.println("El id seleccionado no corresponde a una Liga. Seleccione el ID correcto.");

                liga = obtenerCompetidor(idPersonaje);
                break;
        }
    }

    /**
     * @return el path ingresado por teclado
     */
    public static String obtenerRutaDeArchivo(boolean esLiga) {
        Scanner scanner = new Scanner(System.in);

        String pathResultado = "";

        if (!esLiga) { // ruta para un Personaje
            System.out.println("Ingrese la ruta del archivo personajes.in a leer con el siguiente formato: " +
                    "\"C:\\Usuarios\\UsuarioBross\\Documentos\\personajes.in o \"Archivos/personajes_original.in para path relativo");
            pathResultado = scanner.nextLine();
            while (!pathValido(pathResultado)) // mientras el path sea invalido, lo sigo pidiendo (y pathValido muestra un error)
                pathResultado = scanner.nextLine();
        } else { // ruta para una Liga
            System.out.println("Ingrese la ruta del archivo ligas.in a leer con el siguiente formato: \"C:\\Usuarios\\" +
                    "UsuarioBross\\Documentos\\ligas.in o \"Archivos/ligas_changed.in para path relativo");
            pathResultado = scanner.nextLine();
            while (!pathValido(pathResultado)) // mientras el path sea invalido, lo sigo pidiendo (y pathValido muestra un error)
                pathResultado = scanner.nextLine();
        }
        return pathResultado;
    }

    /**
     * valida un path pasado como parametro si un 'File' fake creado en tiempo de ejecucion existe, sino, tira error
     * via System.err.println
     *
     * @param path
     */
    private static boolean pathValido(String path) {
        boolean resultado = true; // pathResultado arranca como si fuera valido. No hice todo sobre 'path' para no
        // pisar datos que vienen por parametro
        if (!new File(path).exists()) {
            System.err.println("El archivo \"" + path + "\" especificado no existe. Por favor, compruebe la ruta y vuelva a intentar a continuacion.");
            resultado = false; // si el path no es valido, tiro false para que no funcione
        }
        return resultado;
    }

    private static void crearCompetidorParaGuardarAArchivo(boolean esLiga) {
        Scanner sc = new Scanner(System.in);
        Competidor nuevo = null;
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

            nuevo = new Personaje(nombreReal, nombreCompetidor, bando == 'h' ? true : false, velocidad, fuerza, resistencia, destreza);
        } else {
            System.out.println("Ingrese el ID del personaje de los siguientes Personajes listados: ");
            listarCompetidores();
            int idPersonaje = Integer.parseInt(sc.nextLine());
            nuevo = obtenerCompetidor(idPersonaje);
        }
        competidoresParaGuardarAArchivo.add(nuevo);
        todosLosCompetidores.add(nuevo);
    }

    /**
     * Sirve para obtener un competidor de la lista competidoresDesdeArchivo dado un idCompetidor
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
     * Lista todos los competidores que se hayan tenido en cuenta al crearse/importarse
     * - Nota: toma las ligas como Competidores que son. ToDo ver como hacer para separar Ligas de Personajes
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
     * Guarda a un archivo elegido por el usuario (sino crea un archivo con un nombre del estilo "D:\archivo_1655509117824.in")
     * Por ahora usa D:/ para guardar por default
     *
     * @param competidores Lista de personajes a usar para guardar a archivo
     * @param filePath     Ruta completa (por ej. "D:\personajes.out") que es la ruta + nombredefile a guardar
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
     * intento de limpiar la consola para que no se vea todo el historico, parece funcionar en algunos contextos
     */
    private static void limpiarConsola() { // ToDo borrar si no se usa
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    // region Dibujado de bordes. ToDo: ver si se queda o lo borramos
    // para despues:


    /**
     * Dibuja el relleno de una columna de un ancho dado (sin los bordes)
     *
     * @param anchoTotal         ancho de la columna a dibujar
     * @param columnaInicio      columna por la que comienza a dibujar
     * @param caracterIntermedio (String) "caracter" intermedio con el cual se imprime la columna
     */
    private static void dibujarColumnaRecursivo(int anchoTotal, int columnaInicio, String caracterIntermedio) {
        if (columnaInicio == anchoTotal)
            return;
        System.out.print(caracterIntermedio);
        columnaInicio++;
        dibujarColumnaRecursivo(anchoTotal, columnaInicio, caracterIntermedio);
    }

    /**
     * Dibuja filas llamando al metodo recursivo dibujarColumnaRecursivo y a si mismo
     *
     * @param alto               cuantas filas va a dibujar
     * @param ancho              ancho de la fila, se lo paso a dibujarColumnaRecursivo
     * @param fila               fila por la que comienza dibujarFilaRecursivo, se lo paso a dibujarFilaRecursivo
     * @param columna            columna por la que comienza dibujarColumnaRecursivo, se lo paso a dibujarColumnaRecursivo
     * @param caracterIntermedio
     */
    private static void dibujarFilasRecursivo(int alto, int ancho, int fila, int columna, String caracterIntermedio) {
        if (fila == alto)
            return;
        dibujarColumnaRecursivo(ancho, columna, caracterIntermedio);
        System.out.print(caracterIntermedio);
        fila++;

        dibujarFilasRecursivo(alto, ancho, fila, columna, caracterIntermedio);
    }

    /**
     * Dibuja el rectangulo, utilizando los metodos recursivos dibujarFilasRecursivo() para dibujar una fila compuesta
     * de llamadas a dibujarColumnaRecursivo
     *
     * @param ancho
     * @param alto
     */
    public static void dibujarRectangulo(int ancho, int alto) {
        // primera linea
        System.out.print("╔"); // esquina superior izquierda
        dibujarFilasRecursivo(1, alto, 0, 1, "= = ");
        System.out.println("╗"); // esquina superior derecha

        System.out.print("║"); // borde izquierdo
        dibujarFilasRecursivo(1, alto, 1, 1, "    ");
        System.out.println("║"); // borde derecho
    }

    public static void printHeader() {
        int alto = 5;
        int ancho = 100;

        System.out.print("╔"); // esquina superior izquierda

        for (int fila = 0; fila < alto; fila++) { // filas
            if (fila > 0 && fila < alto - 1)
                System.out.print("║"); // borde izquierdo

            if (fila == alto - 1)
                System.out.print("╚"); // esquina inferior izquierda

            for (int columna = 0; columna < ancho; columna++) { // columnas
                if (fila == 0 || fila == alto - 1) {
                    System.out.print("="); // borde superior/inferior
                } else {
                    if (fila == alto / 2 && (columna == ancho / 2 - "Hola romi, choose your destiny:".length())) {
                        System.out.print("Hola player, choose your destiny:");
                        columna = ancho / 2 + 1;
                    } else
                        System.out.print(" "); // relleno interior
                }
            }
            if (fila == 0)
                System.out.print("╗"); // esquina superior derecha
            else if (fila > 0 && fila < alto - 1)
                System.out.print("║"); // borde derecho

            if (fila == alto - 1)
                System.out.print("╝");
            System.out.println();
        }
        System.out.println();
    }
    //endregion
}
