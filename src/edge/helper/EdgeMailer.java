package edge.helper;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.mail.*;
import javax.mail.internet.*;

/**
 * EdgeMailer is used to send mails to any email address.
 * This classes uses javax.mail package.
 * 
 * Be aware that a SMTP server <b>has to be running</b> otherwise
 * EdgeMailer won't deliver any mails at all.
 */
public class EdgeMailer {
	private List<String> receivers;
	private String message = "";
	private String subject = "";
	
	/**
	 * the eMail address used to send emails.
	 * Any email is possible but be aware of
	 * the fact that your clients won't receive mails
	 * from some unauthorized addresses (like \@facebook or \@non.standard.smtp servers
	 */
	private final String from = "default@edge.mailer";
	
	Properties props = new Properties();
    Session session = Session.getDefaultInstance(props, null);

	/**
	 * Constructs a new instance of EdgeMailer with a List of receivers
	 * @param receivers a list containing emails  
	 */
	public EdgeMailer(List<String> receivers){
		this.receivers = receivers;
	}
	
	/**
	 * Constructs a new instance of EdgeMailer with a single user as the receiver
	 * @param receiver any valid email address
	 */
	public EdgeMailer(String receiver){
		this.receivers = new ArrayList<String>();
		this.receivers.add(receiver);
	}
	
	
	/**
	 * Sends the email to the default SMTP server (localhost:25)
	 * the email will be send to any email in the <tt>receivers</tt> list.
	 */
	public void sendMail(){
		try {
			
            Message msg = new MimeMessage(session);
            try {
				msg.setFrom(new InternetAddress(from, from));
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
            
            receivers.forEach( (receiver) -> {
            	try {
					msg.addRecipient(Message.RecipientType.TO,
					new InternetAddress(receiver, receiver));
				} catch (Exception e) {
					e.printStackTrace();
				}
            });
                       
            msg.setSubject(subject);
            msg.setText(message);
            Transport.send(msg);
            
        } catch (MessagingException e) {
            e.printStackTrace();
            EdgeError.alert("Fehler beim Senden", "EDGE PMT konnte die E-Mail nicht verschicken.");
        } 
	}

	/**
	 * Returns the message which will be used in the mail context
	 * @return String the current message of the mail
	 */
	public String getMessage() {
		return message;
	}
	
	/**
	 * Sets the message which will be used in the mail context
	 * @param message a string (html allowed) which will be send as an email
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * Returns the subject of the mail.
	 * The subject is the "sender" of the email
	 * @return string which email address sends the email to the receivers
	 */
	public String getSubject() {
		return subject;
	}
	
	
	/**
	 * Sets the subject of the mail.
	 * @param subject any valid email address as string
	 */
	public void setSubject(String subject) {
		this.subject = subject;
	}
}
