package Sesion;

import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class Email 
{
    public final String from = "correopruebaweb1234@gmail.com";
    public final String subject= "Reestablecimiento de contraseña";
    public final String login = "correopruebaweb1234@gmail.com";    //cuenta de la que se envia
    public final String password = "titancho";  //contraseña de la cuenta de la que se envia
    
    private class SMTPAuthenticator extends Authenticator 
    {
        private final PasswordAuthentication authentication;

        public SMTPAuthenticator(String login, String password) 
        {
            authentication = new PasswordAuthentication(login, password);
        }

        protected PasswordAuthentication getPasswordAuthentication() {
            return authentication;
        }
    }
    
    public boolean enviar(String toEmail, String message)
    {
        boolean b = false;
        try
        {
            Properties props = new Properties();
            props.setProperty("mail.host", "smtp.gmail.com");
            props.setProperty("mail.smtp.port", "587");
            props.setProperty("mail.smtp.auth", "true");
            props.setProperty("mail.smtp.starttls.enable", "true");

            Authenticator auth = new SMTPAuthenticator(login, password);

            Session session = Session.getInstance(props, auth);

            MimeMessage msg = new MimeMessage(session);
            msg.setText(message);
            msg.setSubject(subject);
            msg.setFrom(new InternetAddress(from));
            msg.addRecipient(Message.RecipientType.TO, new InternetAddress(toEmail));
            Transport.send(msg);
            
            b = true;
        }
        catch(MessagingException e) { System.out.println("Error al mandar el email D:\n" + e); }
        
        return b;
    }
}
