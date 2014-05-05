package Beans;

public class Usuario 
{
    private String nombre;
    private String contrasena;
    private String apellido_paterno;
    private String apellido_materno;
    private String email;
    private String nombre_usuario;
    private int status;
    private Peticion peticion;
    
    public Usuario()
    {
        nombre = "";
        contrasena = "";
        apellido_paterno = "";
        apellido_materno = "";
        email = "";
        nombre_usuario = "";
        status = 0;
        peticion = null;
    }
    
    public Usuario(String nombre_usuario)
    {
        this.nombre_usuario = nombre_usuario;
    }
    
    public void setNombre(String nombre)
    {
        this.nombre = nombre;
    }
    
    public String getNombre()
    {
        return nombre;
    }
    
    public void setContrasena(String contrasena)
    {
        this.contrasena = contrasena;
    }
    
    public String getContrasena()
    {
        return contrasena;
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

    public void setEmail(String email)
    {
        this.email = email;
    }
    
    public String getEmail()
    {
        return email;
    }

    public void setNombreUsuario(String nombre_usuario)
    {
        this.nombre_usuario = nombre_usuario;
    }
    
    public String getNombreUsuario()
    {
        return nombre_usuario;
    }

    public void setStatus(int status)
    {
        this.status = status;
    }
    
    public int getStatus()
    {
        return status;
    }
    
    public void setPeticion(Peticion peticion)
    {
        this.peticion = peticion;
    }
    
    public Peticion getPeticion()
    {
        return peticion;
    }
}
