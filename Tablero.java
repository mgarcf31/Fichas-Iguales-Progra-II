import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;

public class Tablero {
    
    private ArrayList<ArrayList<Ficha>> tablero;
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
        tablero = new ArrayList<ArrayList<Ficha>>();
        
        for (int i = 0; i < filas; i++) {
            ArrayList<Ficha> aux = new ArrayList<Ficha>();
            for (int j = 0; j < columnas; j++) {
                Ficha fichaVacia = new Ficha();
                aux.add(fichaVacia);
            }
            tablero.add(aux);
        }

    }



    public ArrayList<ArrayList<Ficha>> copiarTablero() {
        ArrayList<ArrayList<Ficha>> nuevoTablero = new ArrayList<ArrayList<Ficha>>();
        
        for (int i = 0; i < filas; i++) {
            ArrayList<Ficha> aux = new ArrayList<Ficha>();
            for (int j = 0; j < columnas; j++) {
                Ficha fichaVacia = new Ficha();
                aux.add(fichaVacia);
            }
            nuevoTablero.add(aux);

        }
        System.out.println(filas);
        System.out.println(columnas);
    
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                Ficha fichaOriginal = tablero.get(i).get(j);
                Ficha nuevaFicha = new Ficha(
                    fichaOriginal.getColor(),
                    fichaOriginal.getLetra(),
                    fichaOriginal.getGrupo(),
                    fichaOriginal.getFila(),
                    fichaOriginal.getColumna(),
                    fichaOriginal.getVisitado()
                );
                nuevoTablero.get(i).add(j, nuevaFicha);
            }
        }
    
        return nuevoTablero;
    }

    public void invertirTablero() {
        ArrayList<ArrayList<Ficha>> tableroInvertido = new ArrayList<ArrayList<Ficha>>();
        for (int i = 0; i < filas; i++) { // Relleno el array con fichas en blanco
            ArrayList<Ficha> aux = new ArrayList<Ficha>();
            for (int j = 0; j < columnas; j++) {
                Ficha fichaVacia = new Ficha();
                aux.add(fichaVacia);
            }
            tableroInvertido.add(aux);
        }
        for (int i = 0; i < this.filas; i++) {
            for (int j = 0; j < this.columnas; j++) {
                tableroInvertido.get(i).add(j, tablero.get(filas-1-i).get(j)); 
            }
        }

        // Actualizar el tablero original con el tablero invertido
        tablero = tableroInvertido;
    }

    public void addFicha(Ficha ficha, int fila, int columna) {
        if (fila >= 0 && fila < filas && columna >= 0 && columna < columnas) {
            tablero.get(fila).get(columna).setColor(ficha.getColor());
            tablero.get(fila).get(columna).setLetra(ficha.getLetra()); 
            tablero.get(fila).get(columna).setGrupo(ficha.getGrupo());
            tablero.get(fila).get(columna).setFila(ficha.getFila());
            tablero.get(fila).get(columna).setColumna(ficha.getColumna());
            tablero.get(fila).get(columna).setVisitado(ficha.getVisitado());

        }
    }

    public Ficha getFicha(int fila, int columna) {
        if (fila >= 0 && fila < filas && columna >= 0 && columna < columnas) {
            return tablero.get(fila).get(columna);
        } else {
            // Devuelve una ficha especial o maneja el caso fuera de los límites según tus necesidades
            return new Ficha(' ', ' ', false, 0, 0, false);
        }
    }

    public ArrayList<ArrayList<int[]>> getMovsPosibles(ArrayList<ArrayList<Ficha>> tablero) {
        ArrayList<ArrayList<int[]>> movsPosibles = new ArrayList<>();
        int filas = tablero.size();
        int columnas = tablero.get(0).size();
        boolean[][] visitado = new boolean[filas][columnas];

        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                if (!visitado[i][j] && tablero.get(i).get(j).getColor() != ' ') {
                    char color = tablero.get(i).get(j).getColor();
                    ArrayList<int[]> grupo = new ArrayList<>();
                    getMovsRecurs(i, j, color, visitado, grupo);
                    if (grupo.size() > 1) {
                        movsPosibles.add(grupo);
                    }
                }
            }
        }
        return movsPosibles;
    }

    private void getMovsRecurs(int fila, int columna, char color, boolean[][] visitado, ArrayList<int[]> grupo) {
        if (fila < 0 || fila >= filas || columna < 0 || columna >= columnas || visitado[fila][columna]) {
            return;
        }

        char fichaColor = tablero.get(fila).get(columna).getColor();
        if (fichaColor == ' ' || fichaColor != color) {
            return;
        }

        visitado[fila][columna] = true;
        grupo.add(new int[] { fila, columna });

        getMovsRecurs(fila - 1, columna, color, visitado, grupo); // miro las fichas de encima
        getMovsRecurs(fila + 1, columna, color, visitado, grupo); // "" las de abajo
        getMovsRecurs(fila, columna - 1, color, visitado, grupo); // "" las de izquierda
        getMovsRecurs(fila, columna + 1, color, visitado, grupo); // "" las de derecha
    }

    public void encontrarSolucionOptima() {
        List<Tablero> posibles = new List<Tablero>();
        buscarSolucion(0, this.tablero, posibles);
    }

    private void buscarSolucion(int puntuacionActual, ArrayList<ArrayList<Ficha>> tableroSol, List<Tablero> posiblesResultados) {
        
        int puntuacionMaxima = 0;
        boolean hayMovimientos = false;
        // Verificar si la puntuación actual supera la máxima conocida
        if (puntuacionActual > puntuacionMaxima) {
            puntuacionMaxima = puntuacionActual;
        }
        

        // Generar movimientos posibles
        ArrayList<ArrayList<int[]>> movsPosibles = getMovsPosibles(tableroSol);
        
        // Verificar si no hay movimientos posibles o si la puntuación actual supera un límite
        if (movsPosibles.isEmpty() ) {
            System.out.println("vacio");
            return;
        }
            // Iterar sobre movimientos posibles
            int cont=0;
        for (ArrayList<int[]> grupo : movsPosibles) {
            ArrayList<ArrayList<Ficha>> copiaTablero = copiarTablero();
            // Eliminar el grupo en la copia del tablero
            System.out.println(cont);
            eliminarGrupo(copiaTablero, grupo);
            comprimirTablero(copiaTablero);
                // Calcular la puntuación para el movimiento actual
            int puntuacionMovimiento = (grupo.size() - 2) * (grupo.size() - 2);
                // Llamar recursivamente con el nuevo tablero y la nueva puntuación
                cont++;
            buscarSolucion(puntuacionActual + puntuacionMovimiento, copiaTablero);
            imprimirTablero();
            hayMovimientos= true;
            }
        if(!hayMovimientos){
            return;
        }
    }

   
    public void eliminarGrupo(ArrayList<ArrayList<Ficha>> tablero, ArrayList<int[]> grupo) {
        for (int[] posicion : grupo) {
            int fila = posicion[0];
            int columna = posicion[1];
    
            // Establecer la ficha en la posición actual como una nueva ficha vacía en la copia del tablero
            tablero.get(fila).set(columna, new Ficha());

        }
    }

    private static ArrayList<ArrayList<Ficha>> comprimirTablero(ArrayList<ArrayList<Ficha>> tablero) {
        int filas = tablero.size();
        int columnas = tablero.get(0).size();
        ///////////////////
        int filasborradas = tryRemoveFilaEnBlanco(tablero, filas, columnas);
        filas = filas - filasborradas;
        ///////////////////
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                // System.out.println(tablero.get(i).get(j).color);
                if (tablero.get(i).get(j).color!=' ') {
                    continue;
                }
                int k = i;
                while (k >= 0 && tablero.get(k).get(j).getColor() == ' ') {
                    k--;
                }
                // Si se encontró una casilla con letra en la misma columna, intercambiar valores
                if (k >= 0) {
                    tablero.get(i).get(j).setColor(tablero.get(k).get(j).getColor());
                    tablero.get(k).get(j).setColor(' ');
                }
                
            }
        }
        
        //tryRemoveFilaEnBlanco(tablero, filas, columnas);
        // 
        return tablero;
    }

    private static int tryRemoveFilaEnBlanco(ArrayList<ArrayList<Ficha>> tablero, int filas, int columnas) {
        boolean filaEnBlanco;
        int filasBorradas=0;
        //recorremos filas, si toda una fila esta en blanco, la eliminamos
        for (int i = 0; i < filas; i++) {
            int count = 0;
            filaEnBlanco = false;
            for (int j = 0; j < columnas; j++) {
                if (tablero.get(i).get(j).color==' ') {
                    count++;
                    if (j==columnas-1 && count == columnas) {
                        filaEnBlanco=true;
                    }
                }
                if (filaEnBlanco) {
                    for (int i2 = 0; i2 < columnas; i2++) {
                        tablero.get(i).remove(0);
                    }
                    
                }
            }
        }
        //recorremos otra vez buscando los arrays vacios, y los eliminamos
        for (int i = filas-1; i > 0; i--) {
            if (tablero.get(i).isEmpty()) {
                tablero.remove(i);
                filasBorradas++;
            }
        }
        return filasBorradas;
    }
    

    public void imprimirTablero() {
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                System.out.print(tablero.get(i).get(j).getColor()); // Puedes ajustar la forma de impresión según tus necesidades
            }
            System.out.println(); // Nueva línea después de cada fila
        }
    }

    private void aplicarMovimientos(ArrayList<Movimiento> movimientos) {
        for (Movimiento movimiento : movimientos) {
            System.out.println("Movimiento " + movimiento.getCoordenadas() + ": eliminó " + movimiento.numFichas
                    + " fichas de color " + movimiento.color + " y obtuvo " + movimiento.getPuntuacion() + " puntos.");
        }
        System.out.println("Puntuación final: " + calcularPuntuacionTotal(movimientos) + ", quedando 0 fichas.");
    }

    private int calcularPuntuacionTotal(ArrayList<Movimiento> movimientos) {
        int puntuacionTotal = 0;
        for (Movimiento movimiento : movimientos) {
            puntuacionTotal += movimiento.getPuntuacion();
        }
        return puntuacionTotal;
    }

    public void setFilas (int filas){
        this.filas = filas;
    }

    public void setColumnas (int columnas){
        this.columnas = columnas;
    }
}
