package Beans;

public class Evaluacion 
{
    private String comentario;
    private float calificacion;

    public Evaluacion()
    {
        comentario = "";
        calificacion = 0.0f;
    }
    
    public Evaluacion(String comentario, float calificacion)
    {
        this.comentario = comentario;
        this.calificacion = calificacion;
    }
    
    public void setComentario(String comentario)
    {
        this.comentario = comentario;
    }
    
    public String getComentario()
    {
        return comentario;
    }
    
    public void setCalificacion(float calificacion)
    {
        this.calificacion = calificacion;
    }
    
    public float getCalificacion()
    {
        return calificacion;
    }
}
