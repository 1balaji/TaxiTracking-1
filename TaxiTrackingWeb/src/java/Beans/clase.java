package Beans;

import java.io.IOException;
import Serializacion.Serializacion;

public class clase 
{
    public static void main(String[] args) throws IOException, ClassNotFoundException 
    {
        Taxi taxi = new Taxi("TX201404151700");
        taxi.setCURP("JILC910413HDFMPR01");
        
        String s = Serializacion.serialize(taxi);
        System.out.println("Esto va en el QR");
        System.out.println("***********************");
        System.out.println(s);
        System.out.println("***********************");        
        Taxi objTaxi = Serializacion.deserialize(s, Taxi.class);
        System.out.println("Esto es lo recuperado");
        System.out.println(objTaxi.getIdTaxista());
    }
}
