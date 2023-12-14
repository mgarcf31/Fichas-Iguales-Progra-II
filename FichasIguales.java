import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class FichasIguales {

    private static Tablero  tablero;
    public static int nJuegos;
    public static int filas;
    public static int columnas;

    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        nJuegos = sc.nextInt();
        sc.nextLine();// salto el salto de linea
        for (int juego = 1; juego <= nJuegos; juego++){
            filas = 0;
            columnas = 0;
            ArrayList<String> filasTablero = new ArrayList<>();
            while(true){
                String linea = sc.nextLine();
                if (linea.isEmpty()) {
                    break; // si hay una linea vacía salgo del while
                }
                filasTablero.add(linea);
                filas++;
                columnas = linea.length();
            }
            Ficha ficha = new Ficha(' ', ' ', false, 0, 0);
            tablero = new Tablero(ficha, filas, columnas);
            for (int i = 0; i < filas; i++) {
                for (int j = 0; j < columnas; j++) {
                    tablero.addFicha(new Ficha(filasTablero.get(i).charAt(j), filasTablero.get(i).charAt(j), false, i, j), i, j);
                }
            }    
        }
        sc.close();
        tablero.invertirTablero(filas, columnas);
        tablero.imprimirTablero();
    }
}
