package Beans;

public class Taxi
{
    private String idTaxista;
    private String nombre;
    private String apellido_paterno;
    private String apellido_materno;
    private String curp;
    private String matricula;
    private long folio;
    private long numeroLicencia;
    private String vigencia;
    private String fechaExpedicion;
    private String horaExpedicion;
    private int status;
    private int tipo;
    
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
        fechaExpedicion = "";
        horaExpedicion = "";
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

    public void setFolio(long folio)
    {
        this.folio = folio;
    }
    
    public long getFolio()
    {
        return folio;
    }
    
    public void setNumeroLicencia(long numeroLicencia)
    {
        this.numeroLicencia = numeroLicencia;
    }
    
    public long getNumeroLicencia()
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

    public void setFechaExpedicion(String fechaExpedicion)
    {
        this.fechaExpedicion = fechaExpedicion;
    }
    
    public String getFechaExpedicion()
    {
        return fechaExpedicion;
    }
    
    public void setHoraExpedicion(String horaExpedicion)
    {
        this.horaExpedicion = horaExpedicion;
    }
    
    public String getHoraExpedicion()
    {
        return horaExpedicion;
    }
    
    public void setStatus(int status)
    {
        this.status = status;
    }
    
    public int getStatus()
    {
        return status;
    }
    
    public void setTipo(int tipo)
    {
        this.tipo = tipo;
    }
    
    public int getTipo()
    {
        return tipo;
    }
}
