import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

public class FichasIguales {

    private static Tablero  tablero;
    private static ArrayList<Tablero> tableros;
    public static int nJuegos;
    public static int filas;
    public static int columnas;

    

        public static void main(String[] args) {
            tableros = new ArrayList<>();
            Scanner sc = new Scanner(System.in);
            nJuegos = sc.nextInt();
            sc.nextLine(); // Salto el salto de l�nea

            for (int juego = 1; juego <= nJuegos; juego++) {
                filas = 0;
                columnas = 0;
                ArrayList<String> filasTablero = new ArrayList<>();
                
                try {
                    while (true) {
                        String linea = sc.nextLine();
                        if (linea.isEmpty()) {
                            break; // Si hay una l�nea vac�a, salgo del while
                        }
                        filasTablero.add(linea);
                        filas++;
                        columnas = linea.length();

                        // Verificar que la fila no exceda la longitud m�xima permitida
                        if (columnas > 20) {
                            throw new RuntimeException("Error: La fila excede la longitud m�xima permitida.");
                        }
                    }

                    // Verificar que las filas y columnas est�n en el rango permitido
                    if (filas < 1 || filas > 20 || columnas < 1 || columnas > 20) {
                        throw new RuntimeException("Error: Filas o columnas fuera del rango permitido.");
                    }

                    tablero = new Tablero(filas, columnas);

                    for (int i = 0; i < filas; i++) {
                        for (int j = 0; j < columnas; j++) {
                            Ficha aux = new Ficha(filasTablero.get(i).charAt(j), filasTablero.get(i).charAt(j), false, i, j, false);
                            tablero.addFicha(aux, i, j);
                        }
                    }

                    tableros.add(tablero);
                } catch (Exception e) {
                    // Manejar errores en la entrada y salir del programa si se encuentra un juego mal formado
                    System.out.println(e.getMessage());
                    break;
                }
            }

            sc.close();

        for (int i = 0; i < tableros.size(); i++) {
            System.out.println("Juego " + (i + 1) + ":");
            Tablero tablero = tableros.get(i);
            tablero.invertirTablero();
            Juego juego = new Juego(tablero);
            ArrayList<Tablero> posibilidades =  new ArrayList<Tablero>();
            juego.resuelveTablero(tablero, posibilidades);
            //tablero.encontrarSolucionOptima();
        }
    }
}
