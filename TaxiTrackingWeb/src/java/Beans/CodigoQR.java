package Beans;

import java.io.Serializable;

public class CodigoQR implements Serializable
{
    private String datos;
    
    public CodigoQR()
    {
        datos = "";
    }
    
    public CodigoQR(String datos)
    {
        this.datos = datos;
    }
    
    public void setDatos(String datos)
    {
        this.datos = datos;
    }
    
    public String getDatos()
    {
        return datos;
    }
}
