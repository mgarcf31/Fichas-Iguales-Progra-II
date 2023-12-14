import java.util.ArrayList;

public class Tablero {
    
    private ArrayList<ArrayList<Ficha>> tablero ;
    
    /*public Tablero(int filas, int columnas){
        tablero = new ArrayList<>(filas);
        for(int i = 0; i < filas; i++){
            ArrayList<Ficha> fila = new ArrayList<>(columnas);
            for(int j = 0; j < columnas; j++){
                Ficha ficha = new Ficha(' ', ' ', false, i, j);
                fila.add(j, ficha);
            }
            tablero.add(i, fila);
        }
    }*/

    public Tablero(Ficha ficha, int filas, int columnas){
        tablero = new ArrayList<>(filas);
        for(int i = 0; i < filas; i++){
            ArrayList<Ficha> fila = new ArrayList<>(columnas);
            for(int j = 0; j < columnas; j++){
                fila.add(j, ficha);
            }
            tablero.add(i, fila);
        }
    }

    public void invertirTablero(int filas, int columnas){
    
        ArrayList<ArrayList<Ficha>> tableroInvertido = new ArrayList<>(filas);

        for (int i = 0; i < filas; i++) {
            ArrayList<Ficha> filaInvertida = new ArrayList<>(columnas);
            for (int j = 0; j < columnas; j++) {
                filaInvertida.add(j, tablero.get(filas - 1 - i).get(j));
            }
            tableroInvertido.add(i, filaInvertida);
        }

        // Actualizar el tablero original con el tablero invertido
        tablero = tableroInvertido;
    }

    public void addFicha(Ficha ficha, int fila, int columna){
        if (fila < tablero.size() && columna < tablero.get(0).size()) {
            tablero.get(fila).set(columna, ficha);
        }
    
    }

    public void imprimirTablero() {
        for (ArrayList<Ficha> fila : tablero) {
            for (Ficha ficha : fila) {
                System.out.print(ficha.getColor()); // Puedes ajustar la forma de impresión según tus necesidades
            }
            System.out.println(); // Nueva línea después de cada fila
        }
    }
}
