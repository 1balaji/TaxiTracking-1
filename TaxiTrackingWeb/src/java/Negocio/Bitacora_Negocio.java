package Negocio;

import Beans.CodigoQR;
import Beans.Respuesta;
import Beans.Taxi;
import Beans.Usuario;
import ConexionSQL.TaxiDAO;
import ConexionSQL.UsuarioDAO;
import Serializacion.Serializacion;
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
        
        try {query = Integer.parseInt(request.getParameter("q"));}
        catch (NumberFormatException e) {query = -1;}
                
        switch(query)
        {
            case 1: //Consultar QR
                objTaxi = consultarQR(request);
                
                //Si no hubo resultados mandamos el objeto null
                if(objTaxi == null)
                    objTaxi = new Taxi(null);
                
                respuestaALaPeticion = new ArrayList<Taxi>();
                respuestaALaPeticion.add(objTaxi);
                
                out.println(gson.toJson(respuestaALaPeticion));
                break;
            case 2: //Registrar usuario
                resultado = agregarUsuario(request);
                
                //Si el registro fue correcto
                if(resultado)
                {
                    //Mandamos un 1 como respuesta
                    objRespuesta = new Respuesta(1);
                    respuestaALaPeticion = new ArrayList<Respuesta>();
                    respuestaALaPeticion.add(objRespuesta);
                    out.println(gson.toJson(respuestaALaPeticion));
                }
                else
                {
                    //Mandamos un objeto usuario indicando los errores dentro de los atributos
                    respuestaALaPeticion = new ArrayList<Usuario>();
                    respuestaALaPeticion.add(objUsuario);
                    out.println(gson.toJson(respuestaALaPeticion));
                }
                break;
        }
        out.close();
    }
    
    private Taxi consultarQR(HttpServletRequest request)
    {
        String qrLeido = request.getParameter("qrLeido");
        
        if(qrLeido != null)
        {
            objCodigoQR = Serializacion.deserialize(qrLeido, CodigoQR.class);

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
        String nombreUsuario = request.getParameter("TBNombreUsuario").trim();
        
        //Validamos cada entrada con una expresion regular
        if(!Validacion.esAlfanumerico(nombreUsuario))
        {
            //En caso de que no sea valida la entrada, asignamos el atributo como error
            objUsuario.setNombreUsuario("error");
            b = false;
        }
        
        String contrasena = request.getParameter("TBContrasena").trim();
        if(!Validacion.esAlfanumerico(contrasena))
        {
            objUsuario.setContrasena("error");
            b = false;
        }
        
        String confirmarContrasena = request.getParameter("TBConfirmarContrasena").trim();
        
        //Comparamos que coincidan las contrasenas
        if(!contrasena.equals(confirmarContrasena))
        {
            objUsuario.setContrasena("noCoinciden");
            b = false;
        }
        
        String nombre = new String(request.getParameter("TBNombre").trim().getBytes("ISO-8859-1"),"UTF-8");
        if(!Validacion.esCadena(nombre))
        {
            objUsuario.setNombre("error");
            b = false;
        }
        
        String apellido_paterno = new String(request.getParameter("TBApellidoPaterno").trim().getBytes("ISO-8859-1"),"UTF-8");
        if(!Validacion.esCadena(apellido_paterno))
        {
            objUsuario.setApellidoPaterno("error");
            b = false;
        }
        
        String apellido_materno = new String(request.getParameter("TBApellidoMaterno").trim().getBytes("ISO-8859-1"),"UTF-8");
        if(!Validacion.esCadena(apellido_materno))
        {
            objUsuario.setApellidoMaterno("error");
            b = false;
        }
        
        String email = request.getParameter("TBEmail").trim();
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
            
            //Buscamos el usuario para ver si ya esta registrado el nombre del usuario
            objUsuario = usuarioDAO.buscarUsuario(objUsuario);

            //No hay datos repetidos
            if(objUsuario.getNombreUsuario().equals("") && !usuarioDAO.existeEmail(email))
            {
                //Llenamos el objeto con todos los datos
                objUsuario.setNombreUsuario(nombreUsuario);
                objUsuario.setContrasena(contrasena);
                objUsuario.setApellidoPaterno(apellido_paterno);
                objUsuario.setApellidoMaterno(apellido_materno);
                objUsuario.setEmail(email);
                objUsuario.setStatus(1);
                
                //Hacemos la consulta a la BD
                b = usuarioDAO.agregarUsuario(objUsuario);
            }
            //Hay datos repetidos
            else
            {
                //Verificamos que dato esta repetido y subimos el error a sesion
                if(objUsuario.getNombreUsuario().equals(nombreUsuario))
                    objUsuario.setNombreUsuario("repetido");
                if(objUsuario.getEmail().equals(email))
                    objUsuario.setEmail("repetido");
                
                b = false;
            }
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
