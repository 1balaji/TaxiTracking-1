package Beans;

import java.sql.Date;

public class Taxi 
{
    int idTaxista;
    String nombre;
    String apellido_paterno;
    String apellido_materno;
    String rfc;
    int matricula;
    Date vigencia;
    int folio;
    int status;
    
    public Taxi()
    {
        idTaxista = 0;
        nombre = "";
        apellido_paterno = "";
        apellido_materno = "";
        rfc = "";
        matricula = 0;
        vigencia = null;
        folio = 0;
        status = 0;
    }
    
    public Taxi(String rfc)
    {
        this.rfc = rfc;
    }
    
    public void setNombre(String nombre)
    {
        this.nombre = nombre;
    }
    
    public String getNombre()
    {
        return nombre;
    }
    
    public void setApellidoPaterno(String apellido_paterno)
    {
        this.apellido_paterno = apellido_paterno;
    }
    
    public String getApellidoPaterno()
    {
        return apellido_paterno;
    }

    public void setApellidoMaterno(String apellido_materno)
    {
        this.apellido_materno = apellido_materno;
    }
    
    public String getApellidoMaterno()
    {
        return apellido_materno;
    }

    public void setRFC(String rfc)
    {
        this.rfc = rfc;
    }
    
    public String getRFC()
    {
        return rfc;
    }
    
    public void setMatricula(int matricula)
    {
        this.matricula = matricula;
    }
    
    public int getMatricula()
    {
        return matricula;
    }

    public void setVigencia(Date vigencia)
    {
        this.vigencia = vigencia;
    }
    
    public Date getVigencia()
    {
        return vigencia;
    }

    public void setFolio(int folio)
    {
        this.folio = folio;
    }
    
    public int getFolio()
    {
        return folio;
    }
    
    public void setStatus(int status)
    {
        this.status = status;
    }
    
    public int getStatus()
    {
        return status;
    }
}
