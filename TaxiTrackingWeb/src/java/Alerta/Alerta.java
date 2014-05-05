package Alerta;

import edu.stanford.ejalbert.BrowserLauncher;
import edu.stanford.ejalbert.exception.BrowserLaunchingInitializingException;
import edu.stanford.ejalbert.exception.UnsupportedOperatingSystemException;
import java.io.BufferedReader;
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
    final String CONSUMERKEY = "gKRYZvEqq1Zhs2QTW6VPimayc";
    final String CONSUMERSECRET = "NbXMtvWA0TRujkgn6UgvRgom6dGl07Xn0sFWYsptD8VRZxbPOP";
    
    ConfigurationBuilder configBuilder;
    Twitter twitter;
    RequestToken requestToken = null;
    AccessToken accessToken = null;
    String url = null;
    
    Alerta() throws IOException, TwitterException 
    {
        configBuilder = new ConfigurationBuilder();
        configBuilder.setDebugEnabled(true)
            .setOAuthConsumerKey(CONSUMERKEY)
            .setOAuthConsumerSecret(CONSUMERSECRET);
        
        twitter = new TwitterFactory(configBuilder.build()).getInstance();
        requestToken = null;
        accessToken = null;
        url = null;
        
        do 
        {
            try 
            {
                requestToken = twitter.getOAuthRequestToken();
                System.out.println("Request Tokens obtenidos con éxito.");
                url = requestToken.getAuthorizationURL();
                System.out.println("URL: " + requestToken.getAuthorizationURL());
            }
            catch (TwitterException e) { System.out.println("Error al obtener los tokens D:\n" + e); }
            
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            
            //Abrimos el navegador
            try
            {
                BrowserLauncher launcher = new BrowserLauncher();
                launcher.openURLinBrowser(url);
            } 
            catch (BrowserLaunchingInitializingException e){ System.out.println("Error al abrir el navegador D:\n" + e); }
            catch(UnsupportedOperatingSystemException e) { System.out.println("Error al abrir el navegador D:\n" + e); }
            
            System.out.print("Introduce el PIN del navegador: ");
            String pin = br.readLine();
            
            if (pin.length() > 0)
                accessToken = twitter.getOAuthAccessToken(requestToken, pin);
            else
                accessToken = twitter.getOAuthAccessToken(requestToken);
        } while (accessToken == null);
        
        System.out.println("Access Tokens obtenidos con éxito.");
    }
    
    boolean enviarMensajeAlerta(String destinatario, String mensaje)
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
    
    boolean publicarMensajeAlerta(String mensaje)
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