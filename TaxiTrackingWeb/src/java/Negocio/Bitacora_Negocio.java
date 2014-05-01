package Negocio;

import Beans.Respuesta;
import Beans.Taxi;
import Beans.Usuario;
import ConexionSQL.TaxiDAO;
import ConexionSQL.UsuarioDAO;
import Serializacion.Serializacion;
import com.google.gson.Gson;
import java.io.IOException;
import java.io.PrintWriter;
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
    //private Usuario objUsuario;
    
    //Variable para la conexion con la BD
    private TaxiDAO taxiDAO;
    //private UsuarioDAO usuarioDAO;
    
    //Salida html
    private PrintWriter out;
    
    //Respuesta
    private List respuesta;
    
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
        
        respuesta = new ArrayList<Taxi>();
                
        switch(query)
        {
            case 1: //Buscar taxi
                //objTaxi = buscarTaxi(request);
                
                //Si no hubo resultados mandamos el objeto vacio
                //if(objTaxi == null)
                    objTaxi = new Taxi();
                                
                respuesta.add(objTaxi);
                
                out.println(gson.toJson(respuesta));
                break;
            default:
        }
        out.close();
    }
    
    private Taxi buscarTaxi(HttpServletRequest request)
    {
        String qrLeido = request.getParameter("qrLeido");
        
        if(qrLeido != null)
        {
            objTaxi = Serializacion.deserialize(qrLeido, Taxi.class);

            if(objTaxi != null)
            {
                taxiDAO = new TaxiDAO();
                
                //Hacemos la consulta a la BD
                objTaxi = taxiDAO.buscarTaxi(objTaxi);
            }
        }
        
        return objTaxi;
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
