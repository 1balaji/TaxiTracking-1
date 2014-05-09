package Beans;

public class Comentario 
{
    private String comentario;

    public Comentario()
    {
        comentario = "";
    }
    
    public Comentario(String comentario)
    {
        this.comentario = comentario;
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
