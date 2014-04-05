package Beans;

public class Peticion 
{
    int tipo;
    String comentario;
    
    public Peticion()
    {
        tipo = 0;
        comentario = "";
    }
    
    public Peticion(int tipo, String comentario)
    {
        this.tipo = tipo;
        this.comentario = comentario;
    }
    
    public void setTipo(int tipo)
    {
        this.tipo = tipo;
    }
    
    public int getTipo()
    {
        return tipo;
    }
    
    public void setComentario(String comentario)
    {
        this.comentario = comentario;
    }
    
    public String getComentario()
    {
        return comentario;
    }
}
