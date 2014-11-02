package edge.models;



import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import org.apache.commons.codec.binary.Hex;

import edge.logic.Database;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import javax.persistence.*;

import org.hibernate.*;
import org.hibernate.Query;
import org.hibernate.annotations.GenericGenerator;
import java.util.Set;


@Entity
@Table
public class User extends BaseModel {
	@Id
	@Column(name="user_id")
	@GeneratedValue
	private Long id;
	
	private String username;
	private String password;
	private String firstname;
	private String lastname;
	private String eMail;
	
	private Date created;
	private Date modified;
	
	@ManyToMany(cascade = {CascadeType.ALL})
	@JoinTable(name="Project_User", joinColumns={@JoinColumn(name="user_id")}, inverseJoinColumns={@JoinColumn(name="project_id")})
	private Set<Project> projects = new HashSet<Project>();
	
	/**
	 * @constructor
	 */
	public User(){}
	
	/**
	 * @constructor
	 * @param username The username for the new user
	 * @param password The password of the new user
	 */
	public User(String username, String password){
		this.username = username;
		this.password = User.hashPassword(password);
	}
	
	
	/**
	 * gets the database ID of the user object
	 * @return database id as integer
	 */
	public Long getId() {
		return id;
	}
	
	/**
	 * sets the ID of the user object
	 * FIXME: We should remove this.
	 * @param id a non-negative integer for the database ID.
	 */
	public void setId(Long id) {
		this.id = id;
	}
	
	/**
	 * gets the username of the user object
	 * @return username as string
	 * @author pr
	 */
	public String getUsername() {
		return username;
	}
	
	/**
	 * gets the firstname of the user object
	 * @return firstname as string
	 * @author pr
	 */
	public String getFirstname() {
		return firstname;
	}
	
	/**
	 * sets the firstname of the user object
	 * @param firstname a string containing the firstname of the user
	 * @author pr
	 */
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	
	/**
	 * gets the last name of the user object
	 * @return lastname as string 
	 * @author pr
	 */
	public String getLastname() {
		return lastname;
	}
	
	/**
	 * Sets the lastname of the user object
	 * @param lastname a string containing the lastname of the user
	 * @author pr
	 */
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	
	/**
	 * Sets the username of the user object
	 * @param username a string containing the username of the user
	 * @author pr
	 */
	public void setUsername(String username) {
		this.username = username;
	}
	
	/**
	 * Gets the MD5 password of the user object
	 * @return the password as MD5 string
	 * @author pr
	 */
	public String getPassword() {
		return password;
	}
	
	/**
	 * Sets the password of the user object
	 * This method takes the password and transforms it into an MD5 hash.
	 * @param password
	 * @author pr
	 */
	public void setPassword(String password) {
		this.password = User.hashPassword(password);
	}
	
	/**
	 * Sets the email of the user
	 * @author nahom
	 * @param eMail
	 */
	public void setEMail(String eMail) {
		this.eMail = eMail;
	}
	
	/**
	 * Gets the email of user
	 * @return the email of the current user. returns null if not available.
	 * @author nahom
	 */
	public String getEMail() {
		return eMail;
	}
	
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
	
	/**
	 * Transform any password into a MD5 hash
	 * @param password
	 * @author pr
	 * @return the MD5 hash of the given password
	 */
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
