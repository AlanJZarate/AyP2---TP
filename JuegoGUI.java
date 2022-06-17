package Tp3;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class JuegoGUI {

    static ArrayList<Personaje> personajesParaGuardarAArchivo = null; // aca van los personajes que luego se escribiran
    // en el archivo
    static List<Competidor> competidoresDesdeArchivo = null; // lista que se va llenando de competidores que entran desde
                                                            // archivo. No puse ArrayList porque no queria tocar el tipo
                                                            //  de dato de la Liga
    static List<Liga> ligaDesdeArchivo = null; // lista que se llena con las ligas que vienen en el archivo, para
                                               // poder imprimirse por separado. ToDo Ver si es util

    /**
     * Genera el menu en consola. Contiene opciones que se utilizan interactivamente en tiempo real.
     * @throws BandoErroneoException
     * @throws FileNotFoundException
     * @throws PeleaAliadaException
     */
    public static void generarMenu() throws BandoErroneoException, FileNotFoundException, PeleaAliadaException {
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
                    lanzarSubMenuAdministrarPersonajes();
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
     * @throws BandoErroneoException
     * @throws FileNotFoundException
     * @throws PeleaAliadaException
     */
    private static void lanzarSubMenuAdministrarPersonajes() throws BandoErroneoException, FileNotFoundException, PeleaAliadaException {
        Scanner sc = new Scanner(System.in);

        System.out.println("1. Carga de personajes desde archivo\n" +
                "2. Crear personaje manualmente\n" +
                "3. Listar competidores cargados desde archivo\n" +
                "4. Listar competidores creados aqui\n" +
                "5. Guardar personajes creados a un archivo\n"); // ToDo falta implementar guardado


        switch (Integer.parseInt(sc.nextLine())) {
            case 1:
                String[] paths = obtenerRutaDeArchivos();
                competidoresDesdeArchivo = LectorCompetidor.obtenerCompetidoresDesdeArchivo(paths[1]);
                ligaDesdeArchivo = LectorLiga.obtenerLigasDesdeArchivo(paths[0], competidoresDesdeArchivo);
                break;

            case 2:
                crearPersonajeParaGuardarAArchivo();
                break;

            case 3:
                listarCompetidores(true);
                break;

            case 4:
                listarCompetidores(false);
                break;
        }
    }

    /**
     * @return el path ingresado por teclado
     */
    public static String[] obtenerRutaDeArchivos() {
        Scanner scanner = new Scanner(System.in);

        String[] paths = new String[2]; // Array de 2 posiciones para almacenar el path de ligas.in en [0] y
                                        // el path de personajes.in en [1]

        System.out.println("Ingrese la ruta del archivo ligas.in a leer con el siguiente formato: \"C:\\Usuarios\\" +
                "UsuarioBross\\Documentos\\ligas.in o \"Archivos/ligas_changed.in para path relativo" );
        while(pathValido(scanner.nextLine())) // ToDo ver como hacer para que no se llame en esta linea y en la siguiente
            paths[0] = scanner.nextLine();

        System.out.println("Ingrese la ruta del archivo personajes.in a leer con el siguiente formato: " +
                "\"C:\\Usuarios\\UsuarioBross\\Documentos\\personajes.in o \"Archivos/personajes_original.in para path relativo");
        while(pathValido(scanner.nextLine())) // ToDo ver como hacer para que no se llame en esta linea y en la siguiente
            paths[1] = scanner.nextLine();

        return paths;
    }

    /**
     * valida un path pasado como parametro si un 'File' fake creado en tiempo de ejecucion existe, sino, tira error
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

    private static void crearPersonajeParaGuardarAArchivo() {
        personajesParaGuardarAArchivo = new ArrayList<Personaje>();
        Scanner sc = new Scanner(System.in);

        System.out.println("Ingrese el nombre real del personaje:");
        String nombreReal = sc.nextLine();

        System.out.println("Ingrese el nombre de Heroe/Villano del personaje:");
        String nombreCompetidor = sc.nextLine(); // para no ponerle "nombreDeSuperHeroeOVillano"

        System.out.println("Ingrese el bando del personaje: ('h' para heroe, 'v' para villano):");
        Character bando = sc.nextLine().charAt(0); // medio flojo porque si ponen otra cosa se rompe

        System.out.println("Ingrese el valor de VELOCIDAD del personaje:");
        int velocidad = Integer.parseInt(sc.nextLine());

        System.out.println("Ingrese el valor de FUERZA del personaje:");
        int fuerza = Integer.parseInt(sc.nextLine());

        System.out.println("Ingrese el valor de RESISTENCIA del personaje:");
        int resistencia = Integer.parseInt(sc.nextLine());

        System.out.println("Ingrese el valor de DESTREZA del personaje:");
        int destreza = Integer.parseInt(sc.nextLine());

        personajesParaGuardarAArchivo.add(new Personaje(nombreReal, nombreCompetidor, bando == 'h' ? true : false, velocidad, fuerza, resistencia, destreza));
    }

    /**
     * Lista todos los competidores que se hayan tenido en cuenta al crearse/importarse
     * - Nota: toma las ligas como Competidores que son. ToDo ver como hacer para separar Ligas de Personajes
     * @param importadosDesdeArchivo Boolean para diferenciar si vamos a listar los creados en tiempo de ejecucion o
     *                               los importados desde archivo
     */
    public static void listarCompetidores(boolean importadosDesdeArchivo) {
        if (importadosDesdeArchivo && competidoresDesdeArchivo != null) {
            for (Competidor c : competidoresDesdeArchivo) {
                System.out.println("Personaje " + competidoresDesdeArchivo.indexOf(c) + ": " + c.toString()); // index of para mostrar el numero de personaje
            }
        }
        else if (!importadosDesdeArchivo && personajesParaGuardarAArchivo != null) {
            for (Competidor p : personajesParaGuardarAArchivo) {
                System.out.println("Personaje " + personajesParaGuardarAArchivo.indexOf(p) + ": " + p.toString()); // index of para mostrar el numero de personaje
            }
        }
        else {
            System.err.println("No hay personajes que mostrar");
        }
    }

    /**
     * intento de limpiar la consola para que no se vea todo el historico, parece funcionar en algunos contextos
     */
    private static void limpiarConsola(){ // ToDo borrar si no se usa
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

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
}
