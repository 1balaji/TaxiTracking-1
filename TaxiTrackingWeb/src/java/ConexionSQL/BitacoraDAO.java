package ConexionSQL;

import Beans.Comentario;
import Beans.Taxi;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BitacoraDAO 
{
    //Variables para la conexion con la base de datos
    private Connection con;
    private ResultSet rs;
    private PreparedStatement pst;
    
    //Variable que contendra la consulta
    private String consulta;
    
    private Comentario objComentario;
    private Comentario objComentarios[];
    
    public BitacoraDAO()
    {
        con = null;
        rs = null;
        pst = null;
        consulta = "";
    }
    
    /*
    * Metodo para buscar los comentarios asociados a un taxista. 
    * Recibe un objeto taxi con el id del taxi del que se buscaran los comentarios.
    * Retorna un arreglo de comentarios.
    */
    public Comentario[] getComentariosTaxi(Taxi objTaxi)
    {
        //Iniciamos el arreglo
        objComentarios = new Comentario[5];
        for(int i = 0; i < 5; i++)
            objComentarios[i] = new Comentario();
            
        consulta = "SELECT descripcion, idComentario FROM comentario WHERE idTaxista = ? ORDER BY idComentario DESC LIMIT 5";

        try
        {
            con = Conexion.getConexion();
            pst = con.prepareStatement(consulta);
            pst.setString(1, objTaxi.getIdTaxista());
            rs = pst.executeQuery();
            
            //Llenamos el arreglo
            for(int i = 0; rs.next(); i++)
                objComentarios[i].setComentario(rs.getString("descripcion"));
            
            pst.close();            
        }
        catch (SQLException e) { System.out.println("Error al buscar los comentarios D:\n" + e); }
        finally { Conexion.closeConexion(); }
        
        return objComentarios;
    }
}
