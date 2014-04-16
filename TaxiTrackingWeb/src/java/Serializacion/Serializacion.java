package Serializacion;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class Serializacion 
{
    public static String serialize(Object obj)
    {
        try
        {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(obj);
            oos.flush();
            return baos.toString("ISO-8859-1");
        } 
        catch (IOException e) {System.out.println("Error al serializar el objeto D:\n" + e);}
        return null;
    }
    
    public static <T> T deserialize(String str, Class<T> cls)
    {
        try 
        {
            // This encoding induces a bijection between byte[] and String (unlike UTF-8)
            byte b[] = str.getBytes("ISO-8859-1");
            ByteArrayInputStream bi = new ByteArrayInputStream(b);
            ObjectInputStream si = new ObjectInputStream(bi);
            return cls.cast(si.readObject());
        } 
        catch (IOException e) {System.out.println("Error al deserializar la cadena D:\n" + e);}
        catch (ClassNotFoundException e) {System.out.println("Error al deserializar la cadena D:\n" + e);}
        return null;
    }
}
