package twitterjavagt;

import java.io.IOException;
import twitter4j.TwitterException;

/**
 *
 * @author krlos18
 */
public class TwitterJavaGT 
{

    public static void main(String args[]) throws TwitterException, IOException 
    {
        ConexionTwitter aut = new ConexionTwitter();
        aut.enviarMensajeAlerta("krlosyop", "listo");
        aut.publicarMensajeAlerta("Mira @krlosyop");
    }
}