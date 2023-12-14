import java.util.ArrayList;
import java.util.List;

public class Tablero {
    
    private ArrayList<ArrayList<Ficha>> tablero ;
    private Ficha ficha;
    private Movimientos movimientos;
    private int filas;
    private int columnas;


    public int getFilas() {
        return filas;
    }

    public int getColumnas() {
        return columnas;
    }

    public Tablero(int filas, int columnas){
        this.filas = filas;
        this.columnas = columnas;
    }

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

    public Ficha getFicha(int fila, int columna) {
        if (fila >= 0 && fila < tablero.size() && columna >= 0 && columna < tablero.get(fila).size()) {
            return tablero.get(fila).get(columna);
        } else {
            // Devuelve una ficha especial o maneja el caso fuera de los límites según tus necesidades
            return new Ficha(' ', ' ', false, 0, 0);
        }
    }

    public ArrayList<ArrayList<int[]>> getMovsPosibles() {
        ArrayList<ArrayList<int[]>> movsPosib = new ArrayList<>();
        boolean[][] visitado = new boolean[tablero.getFilas()][tablero.getColumnas()];
        
        for (int i = 0; i < tablero.getFilas(); i++) {
            for (int j = 0; j < tablero.getColumnas(); j++) {
                Ficha ficha = tablero.getFicha(i, j);
                if (!visitado[i][j] && ficha.getColor() != ' ') {
                    char color = ficha.getColor();
                    ArrayList<int[]> grupo = new ArrayList<>();
                    getMovsRecurs(i, j, color, visitado, grupo);
                    if (grupo.size() > 1) {
                        movsPosib.add(grupo);
                    }
                }
            }
        }
        
        return movsPosib;
    }

    private void getMovsRecurs(int fila, int columna, char color, boolean[][] visitado, ArrayList<int[]> grupo) {
        if (fila < 0 || fila >= tablero.getFilas() || columna < 0 || columna >= tablero.getColumnas() || visitado[fila][columna]) {
            return;
        }

        Ficha ficha = tablero.getFicha(fila, columna);
        if (ficha.getColor() == ' ' || ficha.getColor() != color) {
            return;
        }

        visitado[fila][columna] = true;
        grupo.add(new int[] { fila, columna });

        getMovsRecurs(fila - 1, columna, color, visitado, grupo); // Miro las fichas de encima
        getMovsRecurs(fila + 1, columna, color, visitado, grupo); // "" las de abajo
        getMovsRecurs(fila, columna - 1, color, visitado, grupo); // "" las de izquierda
        getMovsRecurs(fila, columna + 1, color, visitado, grupo); // "" las de derecha
    }

    public void imprimirTablero() {
        for (ArrayList<Ficha> fila : tablero) {
            for (Ficha ficha : fila) {
                System.out.print(ficha.getColor()); // Puedes ajustar la forma de impresión según tus necesidades
            }
            System.out.println(); // Nueva línea después de cada fila
        }
    }

    public void setFilas (int filas){
        this.filas = filas;
    }

    public void setColumnas (int columnas){
        this.columnas = columnas;
    }
}
