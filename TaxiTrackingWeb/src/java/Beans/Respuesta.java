package Beans;

/*
 * Clase para mandar respuesta a la app movil como Nombre:Valor
 */

public class Respuesta 
{
    //Vale 1 si es valido y 0 si no es valido
    private String logstatus;
    
    public Respuesta(String logstatus)
    {
        this.logstatus = logstatus;
    }
}
