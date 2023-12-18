import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;

public class Tablero {
    
    private ArrayList<ArrayList<Ficha>> tablero;
    

    private Ficha ficha;
    private Movimientos movimientos;
    private int filas;
    private int columnas;
    private double puntuacion;

    public Movimientos getMovimientos() {
        return movimientos;
    }

    public void setMovimientos(Movimientos movimientos) {
        this.movimientos = movimientos;
    }
    public ArrayList<ArrayList<Ficha>> getTablero() {
        return tablero;
    }

    public void setTablero(ArrayList<ArrayList<Ficha>> tablero) {
        this.tablero = tablero;
    }
    public double getPuntuacion() {
        return puntuacion;
    }

    public void setPuntuacion(double d) {
        this.puntuacion = d;
    }

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



    public Tablero copiarTablero() {
        Tablero newtab = new Tablero(this.getFilas(), this.getColumnas());
        ArrayList<ArrayList<Ficha>> nuevoTablero = new ArrayList<ArrayList<Ficha>>();
        
        for (int i = 0; i < this.getFilas(); i++) {
            ArrayList<Ficha> aux = new ArrayList<Ficha>();
            for (int j = 0; j < this.getColumnas(); j++) {
                Ficha fichaVacia = new Ficha();
                aux.add(fichaVacia);
            }
            nuevoTablero.add(aux);

        }
        // System.out.println(filas);
        // System.out.println(columnas);
    
        for (int i = 0; i < this.getFilas(); i++) {
            for (int j = 0; j < this.getColumnas(); j++) {
                Ficha fichaOriginal = this.getTablero().get(i).get(j);
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
        newtab.setTablero(nuevoTablero);
        newtab.setMovimientos(this.getMovimientos());
        return newtab;
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
            // Devuelve una ficha especial o maneja el caso fuera de los lÃ­mites segÃºn tus necesidades
            return new Ficha(' ', ' ', false, 0, 0, false);
        }
    }

    public ArrayList<ArrayList<int[]>> getMovsPosibles() {
        ArrayList<ArrayList<int[]>> movsPosibles = new ArrayList<>();
        int filas = this.getTablero().size();
    	if (filas == 0) {
    	    // La lista está vacía, no hay nada que hacer
    	    return movsPosibles ; // de momento está vacía
    	}
        int columnas = this.getTablero().get(0).size();
        boolean[][] visitado = new boolean[filas][columnas];
        Movimientos temp =  new Movimientos();
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                if (!visitado[i][j] && this.getTablero().get(i).get(j).getColor() != ' ') {
                    char color = this.getTablero().get(i).get(j).getColor();
                    ArrayList<int[]> grupo = new ArrayList<>();
                    getMovsRecurs(i, j, color, visitado, grupo);
                    if (grupo.size() > 1) {
                        movsPosibles.add(grupo);
                        temp.agregarMovimiento(i, j, grupo.size(), color, 0);
                    }
                }
            }
        }
        this.setMovimientos(temp);
        return movsPosibles;
    }

    private void getMovsRecurs(int fila, int columna, char color, boolean[][] visitado, ArrayList<int[]> grupo) {
        if (fila < 0 || fila >= this.filas || columna < 0 || columna >= this.columnas || visitado[fila][columna]) {
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
/* 
    public void encontrarSolucionOptima() {
        buscarSolucion(0, this.tablero, );
    }
*/

    
/*
    private void buscarSolucion(int puntuacionActual, ArrayList<ArrayList<Ficha>> tableroSol, List<Tablero> posiblesResultados) {

        int puntuacionMaxima = 0;
        boolean hayMovimientos = false;
        // Verificar si la puntuaciÃ³n actual supera la mÃ¡xima conocida
        if (puntuacionActual > puntuacionMaxima) {
            puntuacionMaxima = puntuacionActual;
        }
        

        // Generar movimientos posibles
        ArrayList<ArrayList<int[]>> movsPosibles = getMovsPosibles(tableroSol);
        
        // Verificar si no hay movimientos posibles o si la puntuaciÃ³n actual supera un lÃ­mite
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
                // Calcular la puntuaciÃ³n para el movimiento actual
            int puntuacionMovimiento = (grupo.size() - 2) * (grupo.size() - 2);
                // Llamar recursivamente con el nuevo tablero y la nueva puntuaciÃ³n
                cont++;
            buscarSolucion(puntuacionActual + puntuacionMovimiento, copiaTablero);
            imprimirTablero();
            hayMovimientos= true;
            }
        if(!hayMovimientos){
            return;
        }
    }

   */
    public void eliminarGrupo( ArrayList<int[]> grupo) {
        for (int[] posicion : grupo) {
            int fila = posicion[0];
            int columna = posicion[1];
    
            // Establecer la ficha en la posiciÃ³n actual como una nueva ficha vacÃ­a en la copia del tablero
            this.getTablero().get(fila).set(columna, new Ficha());

        }
    }

    public Tablero comprimirTablero() {
    	int filas = this.getTablero().size();
    	if (filas == 0) {
    	    // La lista está vacía, no hay nada que hacer
    	    return this; // O devolver una nueva instancia de Tablero vacía, según tus necesidades
    	}

    	int columnas = this.getTablero().get(0).size();
    	int filasborradas = tryRemoveFilaEnBlanco(this, filas, columnas);
    	filas = filas - filasborradas;

    	for (int i = 0; i < filas; i++) {
    	    if (this.getTablero().get(i).isEmpty()) {
    	        // La fila está vacía, no hay nada que hacer
    	        continue;
    	    }

    	    for (int j = 0; j < columnas; j++) {
    	        if (this.getTablero().get(i).get(j).color != ' ') {
    	            continue;
    	        }

    	        int k = i;
    	        // Verificar si la lista en la posición k tiene elementos antes de acceder a ellos
    	        while (k >= 0 && this.getTablero().size() > k && this.getTablero().get(k).size() > j &&
    	                this.getTablero().get(k).get(j) != null && this.getTablero().get(k).get(j).getColor() == ' ') {
    	            k--;
    	        }

    	        if (k >= 0 && this.getTablero().size() > k && this.getTablero().get(k).size() > j) {
    	            this.getTablero().get(i).get(j).setColor(this.getTablero().get(k).get(j).getColor());
    	            this.getTablero().get(k).get(j).setColor(' ');
    	        }
    	    }
    	}

    	return this;
    	/*int filas = this.getTablero().size();
    	if (filas == 0) {
    	    // La lista está vacía, no hay nada que hacer
    	    return this; // O devolver una nueva instancia de Tablero vacía, según tus necesidades
    	}
    	int columnas = this.getTablero().get(0).size();
    	int filasborradas = tryRemoveFilaEnBlanco(this, filas, columnas);
    	filas = filas - filasborradas;
        ///////////////////
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                // System.out.println(tablero.get(i).get(j).color);
                if (this.getTablero().get(i).get(j).color!=' ') {
                    continue;
                }
                int k = i;
                while (k >= 0 && this.getTablero().get(k).get(j).getColor() == ' ') {
                    k--;
                }
                // Si se encontrÃ³ una casilla con letra en la misma columna, intercambiar valores
                if (k >= 0) {
                    this.getTablero().get(i).get(j).setColor(this.getTablero().get(k).get(j).getColor());
                    this.getTablero().get(k).get(j).setColor(' ');
                }
                
            }
        }
        
        //tryRemoveFilaEnBlanco(tablero, filas, columnas);
        // 
        return this;*/
    }

    private static int tryRemoveFilaEnBlanco(Tablero tablero, int filas, int columnas) {
        boolean filaEnBlanco;
        int filasBorradas=0;
        //recorremos filas, si toda una fila esta en blanco, la eliminamos
        for (int i = 0; i < filas; i++) {
            int count = 0;
            filaEnBlanco = false;
            for (int j = 0; j < columnas; j++) {
                if (tablero.getTablero().get(i).get(j).color==' ') {
                    count++;
                    if (j==columnas-1 && count == columnas) {
                        filaEnBlanco=true;
                    }
                }
                if (filaEnBlanco) {
                    for (int i2 = 0; i2 < columnas; i2++) {
                        tablero.getTablero().get(i).remove(0);
                    }
                    
                }
            }
        }
        //recorremos otra vez buscando los arrays vacios, y los eliminamos
        for (int i = filas-1; i > 0; i--) {
            if (tablero.getTablero().get(i).isEmpty()) {
                tablero.getTablero().remove(i);
                filasBorradas++;
            }
        }
        return filasBorradas;
    }
    

    public void imprimirTablero() {
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                System.out.print(this.tablero.get(i).get(j).getColor()); // Puedes ajustar la forma de impresiÃ³n segÃºn tus necesidades
            }
            System.out.println(); // Nueva lÃ­nea despuÃ©s de cada fila
        }
    }

    private void aplicarMovimientos(Movimientos movimientos) {
        for (Movimiento movimiento : movimientos.getListaMovimientos()) {
            System.out.println("Movimiento " + movimiento.getCoordenadas() + ": eliminÃ³ " + movimiento.numFichas
                    + " fichas de color " + movimiento.color + " y obtuvo " + movimiento.getPuntuacion() + " puntos.");
        }
        System.out.println("PuntuaciÃ³n final: " + calcularPuntuacionTotal(movimientos.getListaMovimientos()) + ", quedando 0 fichas.");
    }

    public int calcularPuntuacionTotal(ArrayList<Movimiento> movimientos) {
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
