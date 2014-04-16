package Beans;

import java.io.Serializable;

public class Taxi implements Serializable
{
    private String idTaxista;
    transient private String nombre;
    transient private String apellido_paterno;
    transient private String apellido_materno;
    transient private String curp;
    transient private String matricula;
    transient private int folio;
    transient private int numeroLicencia;
    transient private String vigencia;
    transient private String fechaHoraExpedicion;
    transient private int status;
    
    public Taxi()
    {
        idTaxista = "";
        nombre = "";
        apellido_paterno = "";
        apellido_materno = "";
        curp = "";
        matricula = "";
        folio = 0;
        numeroLicencia = 0;
        vigencia = null;
        fechaHoraExpedicion = null;
        status = 0;
    }
    
    public Taxi(String idTaxista)
    {
        this.idTaxista = idTaxista;
    }
    
    public void setIdTaxista(String idTaxista)
    {
        this.idTaxista = idTaxista;
    }
    
    public String getIdTaxista()
    {
        return idTaxista;
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

    public void setCURP(String curp)
    {
        this.curp = curp;
    }
    
    public String getCURP()
    {
        return curp;
    }
    
    public void setMatricula(String matricula)
    {
        this.matricula = matricula;
    }
    
    public String getMatricula()
    {
        return matricula;
    }

    public void setFolio(int folio)
    {
        this.folio = folio;
    }
    
    public int getFolio()
    {
        return folio;
    }
    
    public void setNumeroLicencia(int numeroLicencia)
    {
        this.numeroLicencia = numeroLicencia;
    }
    
    public int getNumeroLicencia()
    {
        return numeroLicencia;
    }
    
    public void setVigencia(String vigencia)
    {
        this.vigencia = vigencia;
    }
    
    public String getVigencia()
    {
        return vigencia;
    }

    public void setFechaHoraExpedicion(String fechaExpedicion)
    {
        this.fechaHoraExpedicion = fechaExpedicion;
    }
    
    public String getFechaHoraExpedicion()
    {
        return fechaHoraExpedicion;
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
