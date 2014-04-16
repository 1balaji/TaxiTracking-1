package Validacion;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validacion
{
    static Pattern esCadena = Pattern.compile("[a-zA-Záéíóúñ ]+");
    static Pattern esNumero = Pattern.compile("\\d+");
    static Pattern esFecha = Pattern.compile("\\d{4}/(0[1-9]|1[0-2])/(0[1-9]|[1-2][0-9]|3[0-1])");
    static Pattern esHora = Pattern.compile("([0-1][0-9]|2[0-4]):[0-5][0-9]:[0-5][0-9]");
    static Pattern esAlfanumerico = Pattern.compile("[a-zA-Z0-9]+");
    static Pattern esCURP = Pattern.compile("[A-Z]{1}[AEIOU]{1}[A-Z]{2}[0-9]{2}" + 
                                            "(0[1-9]|1[0-2])(0[1-9]|1[0-9]|2[0-9]|3[0-1])" +
                                            "[HM]{1}" +
                                            "(AS|BC|BS|CC|CS|CH|CL|CM|DF|DG|GT|GR|HG|JC|MC|MN|MS|NT|NL|OC|PL|QT|QR|SP|SL|SR|TC|TS|TL|VZ|YN|ZS|NE)" +
                                            "[B-DF-HJ-NP-TV-Z]{3}" +
                                            "[0-9A-Z]{1}[0-9]{1}$");
    static Pattern esMatricula = Pattern.compile("[A-Z]-\\d{2}-\\d{3}");
    static Matcher matcher;
    
    public static boolean esCadena(String entrada)
    {
        matcher = esCadena.matcher(entrada);
        return matcher.matches();
    }
    
    public static boolean esNumero(String entrada)
    {
        matcher = esNumero.matcher(entrada);
        return matcher.matches();
    }
    
    public static boolean esFecha(String entrada)
    {
        matcher = esFecha.matcher(entrada);
        return matcher.matches();
    }
    
    public static boolean esHora(String entrada)
    {
        matcher = esHora.matcher(entrada);
        return matcher.matches();
    }
    
    public static boolean esAlfanumerico(String entrada)
    {
        matcher = esAlfanumerico.matcher(entrada);
        return matcher.matches();
    }
    
    public static boolean esCURP(String entrada)
    {
        matcher = esCURP.matcher(entrada);
        return matcher.matches();
    }
    
    public static boolean esMatricula(String entrada)
    {
        matcher = esMatricula.matcher(entrada);
        return matcher.matches();
    }
}
