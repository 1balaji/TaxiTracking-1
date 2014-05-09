package Validacion;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validacion
{
    private static final Pattern esCadena = Pattern.compile("[a-zA-Záéíóúñ][a-zA-Záéíóúñ\\s]*");
    private static final Pattern esNumero = Pattern.compile("\\d+");
    private static final Pattern esFecha = Pattern.compile("\\d{4}/(0[1-9]|1[0-2])/(0[1-9]|[1-2][0-9]|3[0-1])");
    private static final Pattern esHora = Pattern.compile("([0-1][0-9]|2[0-4]):[0-5][0-9]:[0-5][0-9]");
    private static final Pattern esAlfanumerico = Pattern.compile("[a-zA-Z0-9-_]+");
    private static final Pattern esCURP = Pattern.compile("[A-Z]{1}[AEIOU]{1}[A-Z]{2}[0-9]{2}" + 
                                            "(0[1-9]|1[0-2])(0[1-9]|1[0-9]|2[0-9]|3[0-1])" +
                                            "[HM]{1}" +
                                            "(AS|BC|BS|CC|CS|CH|CL|CM|DF|DG|GT|GR|HG|JC|MC|MN|MS|NT|NL|OC|PL|QT|QR|SP|SL|SR|TC|TS|TL|VZ|YN|ZS|NE)" +
                                            "[B-DF-HJ-NP-TV-Z]{3}" +
                                            "[0-9A-Z]{1}[0-9]{1}$");
    private static final Pattern esMatricula = Pattern.compile("[AB]-\\d{2}-\\d{3}");
    private static final Pattern esEmail = Pattern.compile("^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:\\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*$");
    static private Matcher matcher;
    
    public static boolean esCadena(String entrada)
    {
        if(entrada != null)
        {
            matcher = esCadena.matcher(entrada);
            return matcher.matches();
        }
        else
            return false;
    }
    
    public static boolean esNumero(String entrada)
    {
        if(entrada != null)
        {
            matcher = esNumero.matcher(entrada);
            return matcher.matches();
        }
        else
            return false;
    }
    
    public static boolean esFecha(String entrada)
    {
        if(entrada != null)
        {
            matcher = esFecha.matcher(entrada);
            return matcher.matches();
        }
        else
            return false;
    }
    
    public static boolean esHora(String entrada)
    {
        if(entrada != null)
        {
            matcher = esHora.matcher(entrada);
            return matcher.matches();
        }
        else
            return false;
    }
    
    public static boolean esAlfanumerico(String entrada)
    {
        if(entrada != null)
        {
            matcher = esAlfanumerico.matcher(entrada);
            return matcher.matches();
        }
        else
            return false;
    }
    
    public static boolean esCURP(String entrada)
    {
        if(entrada != null)
        {
            matcher = esCURP.matcher(entrada);
            return matcher.matches();
        }
        else
            return false;
    }
    
    public static boolean esMatricula(String entrada)
    {
        if(entrada != null)
        {
            matcher = esMatricula.matcher(entrada);
            return matcher.matches();
        }
        else
            return false;
    }
    
    public static boolean esEmail(String entrada)
    {
        if(entrada != null)
        {
            matcher = esEmail.matcher(entrada);
            return matcher.matches();
        }
        else
            return false;
    }
}
