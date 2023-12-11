public class Ficha {
    
    char color;
    char letra;
    Boolean grupo;

    public Ficha (char color, char letra, boolean grupo){
        this.color = color;
        this.letra = letra;
        this.grupo = grupo;
    }
    
    public Ficha (){
        this.color = ' ';
        this.letra = ' ';
        this.grupo = false;
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

    public void setColor(char letra){
        this.color = letra;
    }

    public void setGrupo(boolean grupo){
        this.grupo = grupo;
    }
}
