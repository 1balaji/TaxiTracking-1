package Negocio;

import Alerta.Alerta;
import Beans.CodigoQR;
import Beans.Respuesta;
import Beans.Taxi;
import Beans.Usuario;
import ConexionSQL.BitacoraDAO;
import ConexionSQL.TaxiDAO;
import ConexionSQL.UsuarioDAO;
import Serializacion.Serializacion;
import Sesion.Email;
import Validacion.Validacion;
import com.google.gson.Gson;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import twitter4j.TwitterException;

public class Bitacora_Negocio extends HttpServlet 
{
    //Beans
    private Taxi objTaxi;
    private Usuario objUsuario;
    private CodigoQR objCodigoQR;
    private Respuesta objRespuesta;
    
    //Variable para la conexion con la BD
    private TaxiDAO taxiDAO;
    private UsuarioDAO usuarioDAO;
    private BitacoraDAO bitacoraDAO;
    
    //Variable para el envio de alertas.
    private Alerta objAlerta;
    
    //Salida html
    private PrintWriter out;
    
    //Respuesta
    private List respuestaALaPeticion;
    
    //Resultado de las operaciones
    private boolean resultado;
    
    //Nos dice que metodo hay que invocar
    private int query;
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        //Configuramos el tipo de respuesta
        response.setContentType("application/json");
        
        out = response.getWriter();
        Gson gson = new Gson();
        respuestaALaPeticion = new ArrayList();
        
        try {query = Integer.parseInt(request.getParameter("q"));}
        catch (NumberFormatException e) {query = -1;}
                
        switch(query)
        {
            case 1: //Consultar QR
                objTaxi = consultarQR(request);
                
                //Si no hubo resultados
                if(objTaxi == null)
                {
                    objRespuesta = new Respuesta(0);
                    respuestaALaPeticion.add(objRespuesta);
                }
                //Si el taxista esta bloqueado
                else if(objTaxi.getStatus() == 0)
                {
                    objRespuesta = new Respuesta(1);
                    respuestaALaPeticion.add(objRespuesta);
                }
                //Busqueda correcta y no esta bloqueado
                else
                {
                    objRespuesta = new Respuesta(2);
                    respuestaALaPeticion.add(objRespuesta);
                    
                    //Agregamos los datos del taxi a la respuesta
                    respuestaALaPeticion.add(objTaxi);
                    
                    //Recuperamos los comentarios asociados al taxista
                    bitacoraDAO = new BitacoraDAO();
                    respuestaALaPeticion.add(bitacoraDAO.getEvaluacionesTaxi(objTaxi));
                }
                
                //Mandamos la respuesta
                out.println(gson.toJson(respuestaALaPeticion));
                break;
            case 2: //Registrar usuario
                resultado = agregarUsuario(request);
                
                //Si el registro fue correcto
                if(resultado)
                {
                    //Mandamos un 1 como respuesta
                    objRespuesta = new Respuesta(1);
                    respuestaALaPeticion.add(objRespuesta);
                    out.println(gson.toJson(respuestaALaPeticion));
                }
                else
                {
                    //Mandamos un 0 como respuesta
                    objRespuesta = new Respuesta(0);
                    respuestaALaPeticion.add(objRespuesta);
                    
                    //Agregamos despues los errores
                    respuestaALaPeticion.add(objUsuario);
                    
                    //Mandamos la respuesta
                    out.println(gson.toJson(respuestaALaPeticion));
                }
                break;
            case 3: //Mandar alerta
                resultado = enviarAlerta(request);
                
                //Si el envio fue correcto
                if(resultado)
                    //Mandamos un 1 como respuesta
                    objRespuesta = new Respuesta(1);
                else
                    //Mandamos un 0 como respuesta
                    objRespuesta = new Respuesta(0);
                respuestaALaPeticion.add(objRespuesta);
                out.println(gson.toJson(respuestaALaPeticion));
                break;
        }
        out.close();
    }
    
    private Taxi consultarQR(HttpServletRequest request)
    {
        String qrLeido = request.getParameter("qrLeido");
        
        if(qrLeido != null)
        {
            //objCodigoQR = Serializacion.deserialize(qrLeido, CodigoQR.class);
            objCodigoQR = new CodigoQR(qrLeido);

            if(objCodigoQR != null)
            {
                taxiDAO = new TaxiDAO();
                
                //Hacemos la consulta a la BD
                objTaxi = taxiDAO.buscarTaxi(new Taxi(objCodigoQR.getDatos()));
            }
        }
        
        return objTaxi;
    }
    
    private boolean agregarUsuario(HttpServletRequest request) throws UnsupportedEncodingException
    {
        //Variable de respuesta
        boolean b = true;
        
        objUsuario = new Usuario();
        
        //Obtenemos los datos del formulario
        String nombreUsuario = request.getParameter("TBNombreUsuario");
        
        //Validamos cada entrada con una expresion regular
        if(!Validacion.esAlfanumerico(nombreUsuario))
        {
            //En caso de que no sea valida la entrada, asignamos el atributo como error
            objUsuario.setNombreUsuario("error");
            b = false;
        }
        
        String contrasena = request.getParameter("TBContrasena");
        if(!Validacion.esAlfanumerico(contrasena))
        {
            objUsuario.setContrasena("error");
            b = false;
        }
        
        String nombre = new String(request.getParameter("TBNombre").getBytes("ISO-8859-1"),"UTF-8");
        if(!Validacion.esCadena(nombre))
        {
            objUsuario.setNombre("error");
            b = false;
        }
        
        String apellido_paterno = new String(request.getParameter("TBApellidoPaterno").getBytes("ISO-8859-1"),"UTF-8");
        if(!Validacion.esCadena(apellido_paterno))
        {
            objUsuario.setApellidoPaterno("error");
            b = false;
        }
        
        String apellido_materno = new String(request.getParameter("TBApellidoMaterno").getBytes("ISO-8859-1"),"UTF-8");
        if(!Validacion.esCadena(apellido_materno))
        {
            objUsuario.setApellidoMaterno("error");
            b = false;
        }
        
        String email = request.getParameter("TBEmail");
        if(!Validacion.esEmail(email))
        {
            objUsuario.setEmail("error");
            b = false;
        }
        
        //Si no hubo error y los datos son validos
        if(b)
        {
            usuarioDAO = new UsuarioDAO();
            
            //Llenamos el objeto solo con los datos que no se deben duplicar
            objUsuario.setNombreUsuario(nombreUsuario);
            objUsuario.setEmail(email);
            
            //Buscamos el usuario para ver si ya esta registrado el nombre del usuario
            objUsuario = usuarioDAO.buscarUsuario(objUsuario);

            //No hay datos repetidos
            if(objUsuario.getNombreUsuario().equals("") && objUsuario.getEmail().equals(""))
            {
                //Llenamos el objeto con todos los datos
                objUsuario.setNombreUsuario(nombreUsuario);
                objUsuario.setContrasena(contrasena);
                objUsuario.setApellidoPaterno(apellido_paterno);
                objUsuario.setApellidoMaterno(apellido_materno);
                objUsuario.setEmail(email);
                objUsuario.setStatus(-1);
                
                //Hacemos la consulta a la BD
                b = usuarioDAO.agregarUsuario(objUsuario);
                
                //Si se creo correctamente la cuenta enviamos un email para que la active
                if(b)
                {
                    Email serverEmail = new Email();
                    b = serverEmail.enviar(email, "Activación de cuenta", "Por favor ingrese al siguiente link para activar su cuenta: http://192.168.0.8:8084/TaxiTrackingWeb/ManejoSesion?q=5&nombreUsuario=" + nombreUsuario);
                }
            }
            //Hay datos repetidos
            else
            {
                //Verificamos que dato esta repetido y subimos el error a sesion
                if(objUsuario.getNombreUsuario().equals(nombreUsuario))
                    objUsuario.setNombreUsuario("repetido");
                else
                    objUsuario.setNombreUsuario("");
                if(objUsuario.getEmail().equals(email))
                    objUsuario.setEmail("repetido");
                else
                    objUsuario.setEmail("");
                
                //Regresamos el objeto con los valores introducidos
                objUsuario.setNombre("");
                objUsuario.setApellidoPaterno("");
                objUsuario.setApellidoMaterno("");
                objUsuario.setContrasena("");
                objUsuario.setStatus(0);
                
                b = false;
            }
        }
        return b;
    }
    
    private boolean enviarAlerta(HttpServletRequest request) throws IOException
    {
        //Variable de respuesta
        boolean b = true;
        
        objUsuario = new Usuario();
        
        //Obtenemos los datos del formulario
        String datos = request.getParameter("TBDatos");
        
        //Validamos cada entrada con una expresion regular
        /*if(!Validacion.esAlfanumerico(nombreUsuario))
        {
            //En caso de que no sea valida la entrada, asignamos el atributo como error
            objUsuario.setNombreUsuario("error");
            b = false;
        }*/
        
        //Si no hubo error y los datos son validos
        if(b)
        {
            try { objAlerta = new Alerta(request.getServletContext().getRealPath("/") + "\\autenticacion\\"); } 
            catch (TwitterException e) { System.out.println("Error al enviar la alerta D:\n" + e); }
            
            //Publicamos el mensaje
            b = objAlerta.publicarMensajeAlerta(datos);
            
            if(b)
                b = objAlerta.enviarMensajeAlerta("krlosyop", datos);
        }
        return b;
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
