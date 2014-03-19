/*
 * Clase para mandar el json de respuesta a la app movil
 */

package Negocio;

public class Respuesta 
{
    //Vale 1 si es valido y 0 si no es valido
    String logstatus;
    
    Respuesta(String logstatus)
    {
        this.logstatus = logstatus;
    }
}
