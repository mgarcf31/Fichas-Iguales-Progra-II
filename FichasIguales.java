import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class FichasIguales {

    private Ficha[] ficha;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int numJuegos = scanner.nextInt();
        scanner.nextLine(); // Consumir la línea en blanco

        for (int juego = 1; juego <= numJuegos; juego++) {
            System.out.println("Juego " + juego + ":");
            Ficha[][] tablero = leerTablero(scanner);
            juego(scanner, numJuegos, juego, tablero);
            
        }
    }

    private static void juego(Scanner scanner, int numJuegos, int juego, Ficha[][] tablero) {
        List<Movimiento> movimientos = generarMovimientos(tablero);
        int puntuacionTotal = 0;
        int numMovimientos = 1;

        while (!movimientos.isEmpty()) {
        
            Movimiento movimiento = encontrarMejorMovimiento(tablero);
            if (movimiento == null) {
                break;
            }
            ejecutarMovimiento(tablero, movimiento);
            int puntuacion = calcularPuntuacionMovimiento(movimiento.numFichas);
            puntuacionTotal += puntuacion;
            System.out.println("Movimiento " + numMovimientos + " en (" + movimiento.fila + ", " + movimiento.columna + "): eliminó "
                    + movimiento.numFichas + " ficha" + (movimiento.numFichas == 1 ? "" : "s") + " de color " + movimiento.color + " y obtuvo " + puntuacion + " punto" + (puntuacion == 1 ? "" : "s") + ".");
            numMovimientos++;
            movimientos = generarMovimientos(tablero);
        }


        // Verificar si quedan fichas después de todos los movimientos
        if (contarFichas(tablero) == 0) {
            puntuacionTotal += 1000;
        }

        System.out.println("Puntuación final: " + puntuacionTotal + ", quedando " + contarFichas(tablero) + " ficha" + (contarFichas(tablero) == 1 ? "" : "s") + ".");
        System.out.println();

        if (juego < numJuegos) {
            // Consumir la línea en blanco entre juegos
            scanner.nextLine();
        }
    }

    private static Movimiento encontrarMejorMovimiento(Ficha[][] tablero) {
        int mejorPuntuacion = 0;
        Movimiento mejorMovimiento = null;
    
        List<Movimiento> movimientos = generarMovimientos(tablero);
    
        // Itera sobre los movimientos generados
        for (Movimiento movimiento : movimientos) {
            Ficha[][] copiaTablero = copiarTablero(tablero);
            
        System.exit(0);
            ejecutarMovimiento(copiaTablero, movimiento);
            int puntuacion = calcularPuntuacionMovimiento(movimiento.numFichas);
    
            // Llama a la función recursiva para evaluar los posibles movimientos futuros
            puntuacion += evaluarMovimientosRecursivo(copiaTablero);
    
            // Actualiza si este movimiento es el mejor hasta ahora
            if (puntuacion > mejorPuntuacion || (puntuacion == mejorPuntuacion && movimiento.compareTo(mejorMovimiento) < 0)) {
                mejorPuntuacion = puntuacion;
                mejorMovimiento = movimiento;
            }
        }
    
        return mejorMovimiento;
    }
    

    private static int evaluarMovimientosRecursivo(Ficha[][] tablero) {
        Movimiento mejorMovimiento = encontrarMejorMovimiento(tablero);
    
        // Caso base: no hay más movimientos posibles
        if (mejorMovimiento == null) {
            return 0;
        }
    
        // Realizar una copia del tablero antes de ejecutar el movimiento
        Ficha[][] copiaTablero = copiarTablero(tablero);
    
        // Realizar el mejor movimiento en la copia del tablero
        ejecutarMovimiento(copiaTablero, mejorMovimiento);
    
        // Calcular la puntuación del movimiento actual
        int puntuacion = calcularPuntuacionMovimiento(mejorMovimiento.numFichas);
    
        // Llamada recursiva para evaluar los movimientos futuros en la copia del tablero
        puntuacion += evaluarMovimientosRecursivo(copiaTablero);
    
        return puntuacion;
    }
    
    private static Ficha[][] copiarTablero(Ficha[][] tablero) { // esto funciona bien
        Ficha[][] copia = new Ficha[tablero.length][tablero[0].length];
        for (int i = 0; i < tablero.length; i++) {
            for (int j = 0; j < tablero[i].length; j++) {
                copia[i][j] = tablero[i][j];
            }
        }
        return copia;
    }

    private static List<Movimiento> generarMovimientos(Ficha[][] tablero) {
        ArrayList<Movimiento> movimientos = new ArrayList<>();

        for (int columna = 0; columna < tablero[0].length; columna++) {
            for (int fila = tablero.length - 1; fila >= 0; fila--) {
                char color = tablero[fila][columna].getColor();
                if (color != ' ') {
                    int numFichas = contarFichasGrupo(tablero, fila, columna, color);
                    if (numFichas >= 2) {
                        movimientos.add(new Movimiento(tablero.length -fila, columna + 1, numFichas, color));
                    }
                }
            }
        }

        // Ordenar los movimientos lexicográficamente
        movimientos.sort(Comparator.comparing(Movimiento::getCoordenadas).reversed());

        return movimientos;
    }
    
    // Función auxiliar para calcular la puntuación de un movimiento
    private static int calcularPuntuacionMovimiento(int numFichas) {
        return (numFichas - 2) * (numFichas - 2);
    }
    
    

    private static void ejecutarMovimiento(Ficha[][] tablero, Movimiento movimiento) { //está eliminando todos los de color, no los que tenga en las 4 direcciones
        char color = movimiento.color;
        for (int i = 0; i < tablero.length; i++) {
            for (int j = 0; j < tablero[i].length; j++) {
                if (tablero[i][j].getColor() == color && tablero[i][j].getGrupo()) {
                    tablero[i][j].setColor(color);;
                }
            }
        }
        comprimirTablero(tablero);
    }

    private static void comprimirTablero(Ficha[][] tablero) {
        for (int j = 0; j < tablero[0].length; j++) {
            int destino = tablero.length - 1;
            for (int i = tablero.length - 1; i >= 0; i--) {
                if (tablero[i][j].getColor()!= ' ') {
                    tablero[destino][j] = tablero[i][j];
                    destino--;
                }
            }
            while (destino >= 0) {
                tablero[destino][j].setColor(' ');
                destino--;
            }
        }
    }

    private static int contarFichasGrupo(Ficha[][] tablero, int fila, int columna, char color) {
        boolean[][] visitado = new boolean[tablero.length][tablero[0].length];
        return dfsContar(tablero, fila, columna, color, visitado);
    }
    
    private static int dfsContar(Ficha[][] tablero, int fila, int columna, char color, boolean[][] visitado) {
        if (fila < 0 || fila >= tablero.length || columna < 0 || columna >= tablero[0].length || visitado[fila][columna] || tablero[fila][columna].getColor() != color) {
            return 0;
        }
    
        visitado[fila][columna] = true; // la pone visitada
        int count = 1; //inicializa count a 1
        tablero[fila][columna].setGrupo(true);
    
        count += dfsContar(tablero, fila + 1, columna, color, visitado);
        count += dfsContar(tablero, fila - 1, columna, color, visitado);
        count += dfsContar(tablero, fila, columna + 1, color, visitado);
        count += dfsContar(tablero, fila, columna - 1, color, visitado);
    
        return count;
    }
    
    private static int contarFichas(Ficha[][] tablero) {
        int count = 0;
        for (int j = 0; j < tablero[0].length; j++) {
            for (int i = tablero.length - 1; i >= 0; i--) {
                if (tablero[i][j].getColor()== ' ') {
                    count ++;
                }
            }
        }
        System.out.println(count);
        return count;
    }



    private static Ficha[][] leerTablero(Scanner scanner) {
        int filas = 0;
        int columnas = 0;
        ArrayList<String> filasTablero = new ArrayList<>();

        while (true) {
            String linea = scanner.nextLine();
            if (linea.isEmpty()) {
                break;
            }
            filasTablero.add(linea);
            filas++;
            columnas = linea.length();
        }
        Ficha[][] tablero = new Ficha[filas][columnas];


        for (int i = 0; i < filas; i++) {
            int posicionReal = (i-(filas-1)) * (-1);//
            for (int j = 0; j < columnas; j++) {
                Ficha ficha = new Ficha(filasTablero.get(i).charAt(j), filasTablero.get(i).charAt(j), false);
                tablero[posicionReal][j] = ficha; // 
            }
        }
        return tablero;
    }
}