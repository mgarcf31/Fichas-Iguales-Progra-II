

public class Movimiento implements Comparable<Movimiento> {
        int fila;
        int columna;
        int numFichas;
        char color;

        public Movimiento(int fila, int columna, int numFichas2, char color) {
            this.fila = fila;
            this.columna = columna;
            this.numFichas = numFichas2;
            this.color = color;
        }

            @Override
        public int compareTo(Movimiento otroMovimiento) {
            // Comparar primero por fila
            int comparacionFila = Integer.compare(this.fila, otroMovimiento.fila);
            if (comparacionFila != 0) {
                return comparacionFila;
            }


            // Si las filas son iguales, comparar por columna
            int comparacionColumna = Integer.compare(this.columna, otroMovimiento.columna);
            if (comparacionColumna != 0) {
                return comparacionColumna;
            }

            // Si las filas y columnas son iguales, comparar por color
            return Character.compare(this.color, otroMovimiento.color);
        }

        public String getCoordenadas() {
            return "(" + fila + ", " + columna + ")";
        }
    }

