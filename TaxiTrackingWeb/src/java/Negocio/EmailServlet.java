package Negocio;

import java.io.*;
import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.*;
import javax.servlet.http.*;

public class EmailServlet extends HttpServlet 
{
    HttpServletRequest request;
    HttpServletResponse response;

    public static String from;//de 
    public static String to;//para direccion
    public static String subject;///asunto
    public static String message;
    public static String login;// cuenta de la qie se envia
    public static String password;// contraseña de ña que se envia

    protected void processRequest(HttpServletRequest request,
            HttpServletResponse response)
            throws IOException, ServletException {

    }

    public void enviar(String user, String name, String lastname, String pass, String email, String tipo)
            throws IOException, ServletException, MessagingException {

        final String err = "/error.jsp";
        final String succ = "/success.jsp";

        from = "correopruebaweb1234@gmail.com";//de 
        to = email;//para direccion
        subject = "";///asunto
        message = "";
        login = "correopruebaweb1234@gmail.com";// cuenta de la qie se envia
        password = "titancho";// contraseña de ña que se envia

        if (tipo.equals("registro")) 
        {
            subject = "bienvenido al sistema ";///asunto
            message = "bienvenido   " + name + "  " + lastname + "   al sistema  " + "\n" + "con el nombre de usuario:" + user + "\nsu contraseña es \n:" + pass;

        } 
        else if (tipo.equals("cambio")) 
        {
            subject = "Cambios realizados ";///asunto
            message = "Sus cambios fueron realizados \n"
                    + "  " + name + "  " + lastname + "   " + "\n" + "s nuevo nombre de  usuario:" + user + "\nsu  nueva contraseña es \n:" + pass;
        }
        else if (tipo.equals("contra")) 
        {
            subject = "recuperar contraseña ";///asunto
            message = "los datos de su cuenta son : \n"
                    + "  " + name + "  " + lastname + "   " + "\n" + " nombre de  usuario:" + user + "\n su  nueva contraseña es \n:" + pass + "\n"
                    + "se le sugiere cambiar esta nueva contraseña al ingresar de nuevo al sistema ";
        }

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
        msg.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
        Transport.send(msg);
    }

    private class SMTPAuthenticator extends Authenticator 
    {
        private PasswordAuthentication authentication;

        public SMTPAuthenticator(String login, String password) 
        {
            authentication = new PasswordAuthentication(login, password);
        }

        protected PasswordAuthentication getPasswordAuthentication() {
            return authentication;
        }
    }

    protected void doGet(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    protected void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
}
