package Negocio;

import Beans.Taxi;
import ConexionSQL.TaxiDAO;
import com.itextpdf.text.pdf.BarcodeQRCode;
import java.awt.Color;
import java.awt.Image;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.Date;
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
    
    //Salida
    PrintWriter out;
    
    //Sesion
    HttpSession session = null;
    
    //Respuesta
    boolean respuesta;
    
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
        
        response.setContentType("text/html;charset=UTF-8");
        out = response.getWriter();
        
        //Nos dice que metodo hay que invocar
        int query = Integer.parseInt(request.getParameter("q"));
        
        switch(query)
        {
            case 1: //Buscar taxi
                break;
            case 2: //Agregar taxi
                respuesta = agregarTaxi(request);
                break;
            case 3:
                out.close();
                generarCodigoQR(largoQR, altoQR, request, response);
                break;
            default:
                response.sendRedirect("index.jsp");
        }
    }
    
    private Taxi buscarTaxi(HttpServletRequest request)
    {
        String RFC = request.getParameter("TBRFC");
        objTaxi = new Taxi(RFC);
        taxiDAO = new TaxiDAO();
        
        //Hacemos la consulta a la BD
        objTaxi = taxiDAO.buscarTaxi(objTaxi);
        
        return objTaxi;
    }
    
    private boolean agregarTaxi(HttpServletRequest request)
    {
        //Variable de respuesta
        boolean b;
        
        //Obtenemos los datos del formulario
        String nombre = request.getParameter("TBNombre");
        String apellido_paterno = request.getParameter("TBApellidoPaterno");
        String apellido_materno = request.getParameter("TBApellidoMaterno");
        String rfc = request.getParameter("TBRFC");
        int matricula = Integer.parseInt(request.getParameter("TBMatricula"));
        
        //Obtenemos la fecha en formato yyyy-mm-dd y la spliteamos
        String date[] = request.getParameter("TBVigencia").split("-");
        
        //Creamos un objeto tipo date especial de sql
        Date vigencia = new Date(Integer.parseInt(date[0]),Integer.parseInt(date[1]), Integer.parseInt(date[2]));
        
        int folio = Integer.parseInt(request.getParameter("TBFolio"));
        
        //Llenamos el objeto con los datos
        objTaxi = new Taxi();
        objTaxi.setNombre(nombre);
        objTaxi.setApellidoPaterno(apellido_paterno);
        objTaxi.setApellidoMaterno(apellido_materno);
        objTaxi.setRFC(rfc);
        objTaxi.setMatricula(matricula);
        objTaxi.setVigencia(vigencia);
        objTaxi.setFolio(folio);
        
        taxiDAO = new TaxiDAO();
        
        //Hacemos la consulta a la BD
        b = taxiDAO.agregarTaxi(objTaxi);
        
        return b;
    }
    
    private void generarCodigoQR(int largo, int alto, HttpServletRequest request, HttpServletResponse response)
    {
        //Objeto para imprimir la salida
        OutputStream outQR;
        
        int idTaxista = Integer.parseInt(request.getParameter("idTaxista"));
        
        try
        {
            //Creamos el QR
            BarcodeQRCode codigoQR = new BarcodeQRCode(Integer.toString(idTaxista), largo, alto, null);
            
            //Obtenemos y configuramos la salida
            outQR = response.getOutputStream();
            response.setContentType("image/png");
            
            //Creamos una imagen tipo awt
            Image imagenQR = codigoQR.createAwtImage(Color.BLACK, Color.WHITE);
            
            //Imprimimos la salida
            ImageIO.write(AWTImageDescriptor.create(imagenQR, null), "png", outQR);
            
            outQR.close();
        }
        catch (IOException e){ System.out.println("Error al generar el codigo QR D:\n" + e);}
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
