package email;

import java.util.List;
import java.util.Properties;
import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
 
public class JavaMailApp
{
      public void enviarEmail(List<String> bairros) {
            
    	  Properties props = new Properties();
            /** Parâmetros de conexão com servidor Gmail */
            props.put("mail.smtp.host", "smtp.gmail.com");
            props.put("mail.smtp.socketFactory.port", "465");
            props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.port", "465");
 
            Session session = Session.getDefaultInstance(props,
                        new javax.mail.Authenticator() {
                             protected PasswordAuthentication getPasswordAuthentication() 
                             {
                                   return new PasswordAuthentication("alerta.saidengue@gmail.com", "sossaidengue123");
                             }
                        });
 
            /** Ativa Debug para sessão */
            session.setDebug(true);
 
            try {
 
                  Message message = new MimeMessage(session);
                  message.setFrom(new InternetAddress("alerta.saidengue@gmail.com")); //Remetente
 
                  Address[] toUser = InternetAddress //Destinatário(s)
                             .parse("danillo.fr@gmail.com");  
 
                  message.setRecipients(Message.RecipientType.TO, toUser);
                  message.setSubject("Alerta Secretaria Serviços Publicos");//Assunto
                  message.setText("Presença do mosquito localizada nos bairros " + bairros +". Favor efetuar serviços de limpeza!");
                  
                  /**Método para enviar a mensagem criada*/
                  Transport.send(message);
 
                  System.out.println("E-mail enviado a Secretaria de Serviços Publicos.");
 
             } catch (MessagingException e) {
                  throw new RuntimeException(e);
            }
      }
}
