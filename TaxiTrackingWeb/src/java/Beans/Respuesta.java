/*
 * Clase para mandar el json de respuesta a la app movil
 */

package Beans;

public class Respuesta 
{
    //Vale 1 si es valido y 0 si no es valido
    private String logstatus;
    
    /**
     *
     * @param logstatus
     */
    public Respuesta(String logstatus)
    {
        this.logstatus = logstatus;
    }
}
