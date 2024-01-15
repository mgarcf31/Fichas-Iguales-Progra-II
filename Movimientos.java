import java.util.ArrayList;
import java.util.List;


public class Movimientos {
    public ArrayList<Movimiento> getListaMovimientos() {
        return listaMovimientos;
    }


    public void setListaMovimientos(ArrayList<Movimiento> listaMovimientos) {
        this.listaMovimientos = listaMovimientos;
    }

    private ArrayList<Movimiento> listaMovimientos;

    public Movimientos() {
        this.listaMovimientos = new ArrayList<>();
    }


    public void agregarMovimiento(int fila, int columna, int numFichas, char color, int puntuacion) {
        Movimiento nuevoMovimiento = new Movimiento(fila, columna, numFichas, color, puntuacion);
        listaMovimientos.add(nuevoMovimiento);
    }

    public void eliminarMovimiento(int fila, int columna, int numFichas, char color, int puntuacion){
        ArrayList<Movimiento> auxiliar = new ArrayList<>();
        for(Movimiento movimiento : listaMovimientos){
            if(!(movimiento.fila == fila && movimiento.columna == columna && movimiento.numFichas == numFichas && movimiento.color == color && movimiento.puntuacion == puntuacion)){
                auxiliar.add(movimiento);
            }
        }
        listaMovimientos = auxiliar;
    }

    public void ordenarLista(){
        int n = listaMovimientos.size();
        for(int i = 0; i < n-1; i++){
            for(int j = 0; j < n-i-1; j++){
                // estoy en un movimiento y en el que está a su lado. 
                if (listaMovimientos.get(j).getPuntuacion() < listaMovimientos.get(j+i).getPuntuacion()){
                    // intercambio los elementos si están en orden contrario
                    Movimiento  auxiliar = listaMovimientos.get(j);
                    listaMovimientos.set(j, listaMovimientos.get(j+1));
                    listaMovimientos.set(j+1, auxiliar);
                }
            }
        }
    }


	public void add(ArrayList<int[]> grupo) {
		// TODO Auto-generated method stub
		
		
	}    
}
    
