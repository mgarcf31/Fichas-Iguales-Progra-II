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

            if (copiaTablero.getMovsPosibles().size() == 0) {// si ya he acabado
                // anado a lista de tableros para comparar al final
                if (copiaTablero.getTablero().isEmpty()) {
                    double punt = copiaTablero.calcularPuntuacionTotal(copiaTablero.getMovimientos().getListaMovimientos()); //calcuka ountuacion
                    copiaTablero.setPuntuacion(punt + 1000);
                } else {
                    copiaTablero.setPuntuacion(copiaTablero.calcularPuntuacionTotal(copiaTablero.getMovimientos().getListaMovimientos()));
                }
                tableros.add(copiaTablero);
                
            } else {
                resuelveTablero(copiaTablero, tableros);
            }
            
        }
    }
	public Tablero getTablero() {
		return tablero;
	}
	public void setTablero(Tablero tablero) {
		this.tablero = tablero;
	}
	
	private void aplicarMovimientos(Movimientos movimientos) {
        for (Movimiento movimiento : movimientos.getListaMovimientos()) {
            System.out.println("Movimiento " + movimiento.getCoordenadas() + ": eliminÃ³ " + movimiento.numFichas
                    + " fichas de color " + movimiento.color + " y obtuvo " + movimiento.getPuntuacion() + " puntos.");
        }
        //System.out.println("PuntuaciÃ³n final: " + calcularPuntuacionTotal(movimientos.getListaMovimientos()) + ", quedando 0 fichas.");
    }
}

