import java.util.ArrayList;

public class Juego {
	private Tablero tablero;
    public Juego(Tablero tablero) {
    	this.tablero = tablero; 
    }
    
    public void resuelveTablero(Tablero tablero, ArrayList<Tablero> tableros) {
        ArrayList<ArrayList<int[]>> movsPosibles = tablero.getMovsPosibles();
        for (ArrayList<int[]> movs : movsPosibles) {
            Tablero copiaTablero = tablero.copiarTablero();
            copiaTablero.eliminarGrupo(movs);
            copiaTablero.comprimirTablero();

            if (copiaTablero.getMovsPosibles().size() == 0) {
                if (copiaTablero.getTablero().isEmpty()) {
                    double punt = copiaTablero.calcularPuntuacionTotal(copiaTablero.getMovimientos().getListaMovimientos());
                    copiaTablero.setPuntuacion(punt + 1000);
                } else {
                    copiaTablero.setPuntuacion(copiaTablero.calcularPuntuacionTotal(copiaTablero.getMovimientos().getListaMovimientos()));
                }
                tableros.add(copiaTablero);
            } else {
                resuelveTablero(copiaTablero, tableros);
            }
        }
        // Mover la aplicación de movimientos fuera del bucle para evitar impresiones múltiples
        aplicarMovimientos(tablero);
    }
	public Tablero getTablero() {
		return tablero;
	}
	public void setTablero(Tablero tablero) {
		this.tablero = tablero;
	}
	
	private void aplicarMovimientos(Tablero tablero) {
	    Movimientos movimientos = tablero.getMovimientos();
	    for (Movimiento movimiento : movimientos.getListaMovimientos()) {
	        System.out.println("Movimiento " + (movimiento.getCoordenadas()) + ": eliminó " + movimiento.numFichas
	                + " fichas de color " + movimiento.color + " y obtuvo " + (movimiento.numFichas - 2)*(movimiento.numFichas - 2) + " puntos.");
	    }
	    System.out.println("Puntuación final: " + tablero.calcularPuntuacionTotal(movimientos.getListaMovimientos()) + ", quedando 0 fichas.");
	}
	
}

