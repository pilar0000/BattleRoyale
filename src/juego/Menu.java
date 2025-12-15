package juego;

import java.util.Scanner;

// menu de la partida, se ejecuta al inicio
// se encarga de pedir al jugador si quiere jugar o salir, su nombre, la clase de personaje, el numero de jugadores humanos, de bots, y la dificultad

public class Menu {
	
	// leer texto y numeros que introduce el usuario
    private static Scanner sc = new Scanner(System.in);

    // menu principal
    public static void mostrarMenu() {
        System.out.println("BATTLE ROYALE - MENU");
        System.out.println("1. Iniciar partida");
        System.out.println("2. Salir");
        System.out.print("Elige una opcion: ");
    }

    public static int pedirOpcion() {
        return leerEntero();
    }

    // datos para el jugador humano
    public static String pedirNombre() {
        System.out.print("Introduce tu nombre: ");
        return sc.nextLine();
    }

    public static String elegirClase() {
        System.out.println("Elige tu clase de personaje:");
        System.out.println("1. Guerrero");
        System.out.println("2. Arquero");
        System.out.println("3. Mago");
        System.out.print("Opcion: ");

        int op = leerEntero();
        switch (op) {
            case 1: return "Guerrero";
            case 2: return "Arquero";
            case 3: return "Mago";
            default:
                System.out.println("Selecciona GUERRERO");
                return "Guerrero";
        }
    }

    public static int pedirNumeroHumanos() {
        System.out.print("Numero de jugadores: ");
        return leerEntero();
    }

    public static int pedirNumeroBots() {
        System.out.print("Numero de bots: ");
        return leerEntero();
    }

    public static Dificultad pedirDificultad() {
        System.out.println("Selecciona la dificultad:");
        System.out.println("1. Fácil");
        System.out.println("2. Normal");
        System.out.println("3. Difícil");
        System.out.print("Opción: ");

        int op = leerEntero();
        switch (op) {
            case 1: return Dificultad.FACIL;
            case 2: return Dificultad.NORMAL;
            case 3: return Dificultad.DIFICIL;
            default:
                System.out.println("Selecciona NORMAL.");
                return Dificultad.NORMAL;
        }
    }

    // leer numeros
    private static int leerEntero() {
        while (true) {
            try {
                return Integer.parseInt(sc.nextLine());
            } catch (Exception e) {
                System.out.print("Introduce un numero: ");
            }
        }
    }
}
