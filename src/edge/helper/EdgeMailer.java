package edge.helper;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.mail.*;
import javax.mail.internet.*;

public class EdgeMailer {
	private List<String> receivers;
	private String message = "";
	private String subject = "";
	
	private final String from = "edge-pmt@is.just.mothafucking.cool";
	private final String host = "localhost";
	
	 Properties props = new Properties();
     Session session = Session.getDefaultInstance(props, null);

	
	public EdgeMailer(List<String> receivers){
		this.receivers = receivers;
	}
	
	public EdgeMailer(String receiver){
		this.receivers = new ArrayList<String>();
		this.receivers.add(receiver);
	}
	
	
	
	public boolean sendMail(){
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

        } catch (AddressException e) {
            e.printStackTrace();
        } catch (MessagingException e) {
            e.printStackTrace();
        }
		return false;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}
}
