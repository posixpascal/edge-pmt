package edge.models;



import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.codec.binary.Hex;

import edge.logic.HibernateUtil;

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
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = User.hashPassword(password);
	}
	
	/**
	 * Searches DB for a user with the specified username
	 * @param username String containing the username which Hibernate should look for
	 * @return User returns user as UserObject if found otherwise null.
	 * @author pr
	 */
	public static Object findByUsername(String username){
		Session session = HibernateUtil.getSession();
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
