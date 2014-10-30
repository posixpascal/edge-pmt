package edge.models;



import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.commons.codec.binary.Hex;

import edge.logic.Database;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import javax.persistence.*;

import org.hibernate.*;
import org.hibernate.Query;


@Entity
@Table
public class User {
	@Id
	@GeneratedValue
	private Long id;
	
	private String username;
	private String password;
	private String firstname;
	private String lastname;
	private String eMail;
	
	public User(){}
	public User(String username, String password){
		this.username = username;
		this.password = User.hashPassword(password);
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	
	public String getFirstname() {
		return firstname;
	}
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	public String getLastname() {
		return lastname;
	}
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = User.hashPassword(password);
	}
	
	public void setEMail(String eMail) {
		this.eMail = eMail;
	}
	public String getEMail() {
		return eMail;
	}
	
	private Date created;
	private Date modified;
	
	@PrePersist
	protected void onCreate(){
		created = new Date();
	}
	
	@PreUpdate
	protected void onUpdate(){
		modified = new Date();
	}
	
	/**
	 * Get all users from DB
	 * @author pr
	 * @return A List containing every user found in the database.
	 */
	public static List<User> getAll() {
		Session session = Database.getSession();
		session.beginTransaction();
		
		Query query = session.createQuery("from User");
		
		@SuppressWarnings("unchecked")
		List<User> result = (List<User>) query.list();
		
		session.close();
		
		return result;
	}
	
	/**
	 * Searches DB for a user with the specified username
	 * @param username String containing the username which Hibernate should look for
	 * @return User returns user as UserObject if found otherwise null.
	 * @author pr
	 */
	public static Object findByUsername(String username){
		Session session = Database.getSession();
		session.beginTransaction();
		
		Query query = session.createQuery("from User as user where user.username = :username");
		query.setParameter("username", username);
		
		List<?> result = query.list();
		
		session.close();
		
		if (result.size() == 0){ return null; }
		return result.get(0);
	}
	
	public static String hashPassword(String password){

		MessageDigest md = null;
		try {
			md = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e1) {
			e1.printStackTrace();
		}
		
		try {
			md.update(password.getBytes("UTF-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		byte[] md5password = md.digest();
		
		char[] hexPassword = Hex.encodeHex(md5password);
		
		return String.copyValueOf(hexPassword);
	}
	
	
}
