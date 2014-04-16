package Negocio;

import Beans.Taxi;
import ConexionSQL.TaxiDAO;
import Serializacion.Serializacion;
import Validacion.Validacion;
import com.itextpdf.text.pdf.BarcodeQRCode;
import java.awt.Color;
import java.awt.Image;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import javax.imageio.ImageIO;
import javax.media.jai.operator.AWTImageDescriptor;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class Taxi_Negocio extends HttpServlet 
{
    //Beans
    Taxi objTaxi;
    Taxi objTaxis[];
    
    //Variable para la conexion con la BD
    private TaxiDAO taxiDAO;
    
    //Salida html
    PrintWriter out;
    
    //Salida png
    OutputStream outQR;
    
    //Sesion
    HttpSession session = null;
    
    //Respuesta
    boolean respuesta;
    
    //Nos dice que metodo hay que invocar
    int query;
    
    //Medidas del codigoQR
    int largoQR = 300;
    int altoQR = 300;
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException 
    {
        session = request.getSession(false);
        
        //Para que no se pueda llamar al servlet con la URL
        if(session == null)
            response.sendRedirect("index.jsp");
        
        //Para que el usuario normal no pueda llamar el servlet
        else if((Integer)session.getAttribute("rol") != 1)
            response.sendRedirect("index.jsp");
        
        try {query = Integer.parseInt(request.getParameter("q"));}
        catch (NumberFormatException e) {query = -1;}
        
        if(query == 3)
        {
            outQR = response.getOutputStream();
            response.setContentType("image/png");
        }
        else
        {
            out = response.getWriter();
            response.setContentType("text/html;charset=UTF-8");
        }
        
        switch(query)
        {
            case 1: //Buscar taxi
                objTaxi = buscarTaxi(request);
                session.setAttribute("objTaxi", objTaxi);
                response.sendRedirect("busquedaTaxi.jsp");
                break;
            case 2: //Agregar taxi
                respuesta = agregarTaxi(request);
                
                //Si el registro fue correcto
                if(respuesta)
                {
                    session.setAttribute("objTaxi", objTaxi);
                    response.sendRedirect("busquedaTaxista.jsp");
                }
                else
                    response.sendRedirect("gestionTaxis.jsp");
                break;
            case 3: //Generar QR                
                Image imagenQR = generarCodigoQR(largoQR, altoQR, request);
            
                //Pintamos el QR
                ImageIO.write(AWTImageDescriptor.create(imagenQR, null), "png", outQR);                
                break;
            default:
                response.sendRedirect("index.jsp");
        }
        if(query == 3)
            outQR.close();
        else
            out.close();
    }
    
    private boolean agregarTaxi(HttpServletRequest request)
    {
        //Variable de respuesta
        boolean b = true;
        
        //Obtenemos los datos del formulario
        String idTaxista = request.getParameter("TBIdTaxista").trim();
        
        //Validamos cada entrada con una expresion regular
        if(!Validacion.esAlfanumerico(idTaxista))
        {
            //En caso de que no sea valida la entrada, subimos a sesion un mensaje indicando el error
            session.setAttribute("errorIdTaxista", "Error la clave de operador es incorrecta");
            b = false;
        }
        
        String nombre = request.getParameter("TBNombre").trim();
        if(!Validacion.esCadena(nombre))
        {
            session.setAttribute("errorNombre", "Error el nombre es incorrecto");
            b = false;
        }
        
        String apellido_paterno = request.getParameter("TBApellidoPaterno").trim();
        if(!Validacion.esCadena(apellido_paterno))
        {
            session.setAttribute("errorApellidoPaterno", "Error el apellido paterno es incorrecto");
            b = false;
        }
        
        String apellido_materno = request.getParameter("TBApellidoMaterno").trim();
        if(!Validacion.esCadena(apellido_materno))
        {
            session.setAttribute("errorApellidoMaterno", "Error el apellido materno es incorrecto");
            b = false;
        }
        
        String curp = request.getParameter("TBCURP").trim();
        if(!Validacion.esCURP(curp))
        {
            session.setAttribute("errorCurp", "Error el curp es incorrecto");
            b = false;
        }
        
        String matricula = request.getParameter("TBMatricula").trim();
        if(!Validacion.esMatricula(matricula))
        {
            session.setAttribute("errorMatricula", "Error la matricula es incorrecta");
            b = false;
        }
        
        int folio = 0;
        try {folio = Integer.parseInt(request.getParameter("TBFolio").trim());}
        catch(NumberFormatException e)
        {
            session.setAttribute("errorFolio", "Error el folio es incorrecto");
            b = false;
        }
        
        int numeroLicencia = 0;
        try {numeroLicencia = Integer.parseInt(request.getParameter("TBNumeroLicencia").trim());}
        catch(NumberFormatException e)
        {
            session.setAttribute("errorNumeroLicencia", "Error el numero de licencia es incorrecto");
            b = false;
        }
        
        String vigencia = request.getParameter("TBVigencia").trim();
        if(!Validacion.esFecha(vigencia))
        {
            session.setAttribute("errorVigencia", "Error la vigencia es incorrecta");
            b = false;
        }
        
        String fechaExpedicion = request.getParameter("TBFechaExpedicion").trim();
        if(!Validacion.esFecha(fechaExpedicion))
        {
            session.setAttribute("errorFechaExpedicion", "Error la fecha de expedición es incorrecta");
            b = false;
        }
        
        String horaExpedicion = request.getParameter("TBHoraExpedicion").trim();
        if(!Validacion.esHora(horaExpedicion))
        {
            session.setAttribute("errorHoraExpedicion", "Error la hora de expedición es incorrecta");
            b = false;
        }
        
        //Si no hubo error y los datos son validos
        if(b)
        {
            taxiDAO = new TaxiDAO();
            
            //Llenamos el objeto solo con los datos que no se deben duplicar
            objTaxi = new Taxi();
            objTaxi.setIdTaxista(idTaxista);
            objTaxi.setCURP(curp);
            objTaxi.setFolio(folio);
            objTaxi.setNumeroLicencia(numeroLicencia);
            
            //Buscamos el taxi para ver si ya esta registrado algun dato
            objTaxi = taxiDAO.buscarTaxi(objTaxi);

            //No hay datos repetidos
            if(objTaxi == null)
            {
                //Llenamos el objeto con todos los datos
                objTaxi = new Taxi();
                objTaxi.setIdTaxista(idTaxista);
                objTaxi.setNombre(nombre);
                objTaxi.setApellidoPaterno(apellido_paterno);
                objTaxi.setApellidoMaterno(apellido_materno);
                objTaxi.setCURP(curp);
                objTaxi.setMatricula(matricula);
                objTaxi.setFolio(folio);
                objTaxi.setNumeroLicencia(numeroLicencia);
                objTaxi.setVigencia(vigencia);
                objTaxi.setFechaHoraExpedicion(fechaExpedicion + " " + horaExpedicion);
                objTaxi.setStatus(1);
                
                //Hacemos la consulta a la BD
                b = taxiDAO.agregarTaxi(objTaxi);
            }
            //Hay datos repetidos
            else
            {
                //Verificamos que dato esta repetido y subimos el error a sesion
                if(objTaxi.getIdTaxista().equals(idTaxista))
                    session.setAttribute("errorIdTaxista", "Error la clave de operador ya esta registrada");
                if(objTaxi.getCURP().equals(curp))
                    session.setAttribute("errorCurp", "Error el CURP ya esta registrado");
                if(objTaxi.getFolio() == folio)
                    session.setAttribute("errorFolio", "Error el folio ya esta registrado");
                if(objTaxi.getNumeroLicencia() == numeroLicencia)
                    session.setAttribute("errorNumeroLicencia", "Error el numero de licencia ya esta registrado");
                
                b = false;
            }
        }
        return b;
    }
    
    private Taxi buscarTaxi(HttpServletRequest request)
    {
        String idTaxista = request.getParameter("TBIdTaxista");
        objTaxi = new Taxi(idTaxista);
        taxiDAO = new TaxiDAO();
        
        //Hacemos la consulta a la BD
        objTaxi = taxiDAO.buscarTaxi(objTaxi);
        
        return objTaxi;
    }
    
    private Image generarCodigoQR(int largo, int alto, HttpServletRequest request)
    {
        String idTaxista = request.getParameter("idTaxista");
        objTaxi = new Taxi(idTaxista);
        String datos = Serializacion.serialize(objTaxi);
        
        //Creamos el QR
        BarcodeQRCode codigoQR = new BarcodeQRCode(datos, largo, alto, null);
        
        //Creamos una imagen tipo awt con el color de las barras en negro y el fondo blanco
        return codigoQR.createAwtImage(Color.BLACK, Color.WHITE);
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
