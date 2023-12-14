public class Ficha {
    
    char color;
    char letra;
    Boolean grupo;
    int fila;
    int columna;

    public Ficha (char color, char letra, boolean grupo, int fila, int columna){
        this.color = color;
        this.letra = letra;
        this.grupo = grupo;
        this.fila = fila;
        this.columna = columna;
    }
    
    public Ficha (){
        this.color = ' ';
        this.letra = ' ';
        this.grupo = false;
        this.fila = 0;
        this.columna = 0;
    }

    public char getColor(){
        return color;
    }
    
    public Boolean getGrupo(){
        return grupo;
    }

    public char getLetra(){
        return letra;
    }

    public int getFila(){
        return fila;
    }

    public int getColumna(){
        return columna;
    }

    public void setColor(char color){
        this.color = color;
    }

    public void setLetra (char letra){
        this.letra = letra;
    }

    public void setGrupo(boolean grupo){
        this.grupo = grupo;
    }

    public void setFila(int fila){
        this.fila = fila;
    }

    public void serColumna(int columna){
        this.columna = columna;
    }

}
