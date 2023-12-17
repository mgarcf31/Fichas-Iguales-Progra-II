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

    public static void main(String[] args){
        tableros = new ArrayList<Tablero>();
        Scanner sc = new Scanner(System.in);
        nJuegos = sc.nextInt();
        sc.nextLine();// salto el salto de linea
        for (int juego = 1; juego <= nJuegos; juego++){
            filas = 0;
            columnas = 0;
            ArrayList<String> filasTablero = new ArrayList<String>();
            while(true){
                String linea = sc.nextLine();
                if (linea.isEmpty()) {
                    break; // si hay una linea vacÃ­a salgo del while
                }
                filasTablero.add(linea);
                filas++;
                columnas = linea.length();
            }
            tablero = new Tablero(filas, columnas);
            for (int i = 0; i < filas; i++) {
                for (int j = 0; j < columnas; j++) {
                    Ficha aux = new Ficha(filasTablero.get(i).charAt(j), filasTablero.get(i).charAt(j), false, i, j, false);
                    tablero.addFicha(aux, i, j);
                }
            }
            tableros.add(tablero);
        }
        sc.close();

        for (int i = 0; i < tableros.size(); i++) {
            System.out.println("Juego " + (i + 1) + ":");
            Tablero tablero = tableros.get(i);
            tablero.invertirTablero();
            tablero.encontrarSolucionOptima();
        }
    }
}
