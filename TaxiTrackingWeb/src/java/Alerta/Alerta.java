package Alerta;

import edu.stanford.ejalbert.BrowserLauncher;
import edu.stanford.ejalbert.exception.BrowserLaunchingInitializingException;
import edu.stanford.ejalbert.exception.UnsupportedOperatingSystemException;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;
import twitter4j.conf.ConfigurationBuilder;

public class Alerta 
{
    private final String CONSUMERKEY = "Cv9rHmBtAnxl3ESWsP5pJ97Po";
    private final String CONSUMERSECRET = "mDj5yk4yOZgR8Ane4xgZKNu8QKGGwgpjOdQi0csJODIAbq97aB";
    private String AUTHFILE = "/autenticacion/auth_file.txt";
    
    private final ConfigurationBuilder configBuilder;
    private Twitter twitter;
    
    //Token para hacer la peticion de un nuevo PIN para la autorizacion del programa
    private RequestToken requestToken = null;
    
    //Token que tiene la autorizacion del programa para interactuar con la cuenta de twitter
    private AccessToken accessToken = null;
    
    //URL que se accesa para pedir la autorizacion del programa
    private String url = null;
    
    //Buffer para leer el PIN del teclado o para leer del archivo de autenticacion
    private BufferedReader br;
    
    //Variables para manipular el archivo de autenticacion
    private File archivo;
    private FileReader fileReader;
    
    //Variable para guardar el OAuthAccessToken que este en el archivo
    private String token;
    
    //Variable para guardar el OAuthAccessTokenSecret que este en el archivo
    private String tokenSecret;
    
    public Alerta(String rutaFichero) throws IOException, TwitterException 
    {
        AUTHFILE = rutaFichero + AUTHFILE;
        System.out.println(AUTHFILE);
        
        configBuilder = new ConfigurationBuilder();
        configBuilder.setDebugEnabled(true)
            .setOAuthConsumerKey(CONSUMERKEY)
            .setOAuthConsumerSecret(CONSUMERSECRET);
 
        //Checamos si ya hay un archivo de autenticado para no tener que pedir un nuevo codigo
        try 
        {
            archivo = new File(AUTHFILE);
            fileReader = new FileReader(archivo);
            br = new BufferedReader(fileReader);
            
            String linea;
            int n = 1;
            
            //Comenzamos a leer el archivo
            while ((linea = br.readLine()) != null) 
            {
                if (n == 1) //La primera línea es el Access Token
                    token = linea;
                else if (n == 2)    //La segunda línea es el Access Token Secret
                    tokenSecret = linea;
                n++;
            }
            
            configBuilder.setOAuthAccessToken(token)
                .setOAuthAccessTokenSecret(tokenSecret);
            
            System.out.println("Access Tokens obtenidos con éxito.");
            
        twitter = new TwitterFactory(configBuilder.build()).getInstance();
        }
        //Si no hay un archivo, tenemos que pedir un nuevo codigo
        catch (IOException e) 
        {
            twitter = new TwitterFactory(configBuilder.build()).getInstance();
    
            do 
            {
                try 
                {
                    requestToken = twitter.getOAuthRequestToken();
                    System.out.println("Request Tokens obtenidos con éxito.");
                    url = requestToken.getAuthorizationURL();
                    System.out.println("URL: " + requestToken.getAuthorizationURL());
                }
                catch (TwitterException te) { System.out.println("Error al obtener los tokens D:\n" + te); }

                br = new BufferedReader(new InputStreamReader(System.in));

                //Abrimos el navegador
                try
                {
                    BrowserLauncher launcher = new BrowserLauncher();
                    launcher.openURLinBrowser(url);
                } 
                catch (BrowserLaunchingInitializingException be) { System.out.println("Error al abrir el navegador D:\n" + be); }
                catch(UnsupportedOperatingSystemException be) { System.out.println("Error al abrir el navegador D:\n" + be); }

                System.out.print("Introduce el PIN del navegador: ");
                String pin = br.readLine();

                if (pin.length() > 0)
                    accessToken = twitter.getOAuthAccessToken(requestToken, pin);
                else
                    accessToken = twitter.getOAuthAccessToken(requestToken);
            } while (accessToken == null);

            System.out.println("Access Tokens obtenidos con éxito.");
            
            //Ahora vamos a guardar los tokens en el archivo para ya no tener que volver a pedirlos
            
            FileOutputStream fos = null;
            
            //Contenido del archivo
            String content = accessToken.getToken() + "\n" + accessToken.getTokenSecret();
            try 
            {
                archivo = new File(AUTHFILE);
                fos = new FileOutputStream(archivo);
                archivo.createNewFile();
                
                //Se obtiene el contenido en Bytes
                byte[] contentInBytes = content.getBytes();
                fos.write(contentInBytes);
                fos.flush();
                fos.close();
                System.out.println("El archivo de autenticacion se creó con éxito.");
            } 
            catch (IOException ioe) { System.out.println("Error al guardar el archivo D:\n" + ioe); }
            finally 
            {
                try 
                {
                    if (fos != null)
                        fos.close();
                } 
                catch (IOException ioe){ System.out.println("Error al cerrar el archivo D:\n" + ioe); }
            }
        }
        finally
        {
            try 
            {
                if (null != fileReader)
                    fileReader.close();
            } catch (IOException e) { System.out.println("Error al cerrar el archivo D:\n"+ e); }
        }
    }
    
    public boolean enviarMensajeAlerta(String destinatario, String mensaje)
    {
        boolean b;
        
        try 
        {
            twitter.sendDirectMessage(destinatario, mensaje);
            b = true;
        } 
        catch (TwitterException e) 
        {
            System.out.println("Error al enviar el mensaje directo a " + destinatario + "D:\n" + e);
            b = false;
        }
        return b;
    }
    
    public boolean publicarMensajeAlerta(String mensaje)
    {
        boolean b;
        
        try 
        {
            twitter.updateStatus(mensaje);
            b = true;
        } 
        catch (TwitterException e) 
        {
            System.out.println("Error al publicar el mensaje de alerta D:\n" + e);
            b = false;
        }
        return b;
    }
}