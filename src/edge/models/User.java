package edge.models;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import org.apache.commons.codec.binary.Hex;
import edge.logic.Database;
import javax.persistence.*;
import org.hibernate.*;
import org.hibernate.Query;
import org.hibernate.annotations.Type;
import java.util.Set;


@Entity
@Table
public class User extends BaseModel implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="id")
	@GeneratedValue
	private Long id;
	
	private String username;
	private String password;
	private String firstname;
	private String lastname;
	private String eMail;
	
	@Column
	@Type(type="timestamp")
	private Date created;
	
	@Column
	@Type(type="timestamp")
	private Date modified;
	
	private boolean isAdmin = false;
	

	/**
	 * checks if the user is an admin
	 * defaults to false;
	 * @return true if the user has admin rights
	 */
	public boolean isAdmin() {
		return isAdmin;
	}

	/**
	 * sets the admin level for the current user
	 * @param isAdmin true if admin, false if not.
	 */
	public void setAdmin(boolean isAdmin) {
		this.isAdmin = isAdmin;
	}

	@OneToMany(fetch = FetchType.EAGER)
	private Set<Comment> comments = new HashSet<Comment>(0);
	
	/**
	 * returns a hashset containing all attached comments to this user
	 * @return hashset with all comments for this user
	 */
	public Set<Comment> getComments(){
		return this.comments;
	}
	
	/**
	 * sets a hashset containing all attached comments to this user
	 * @param comments hashset with all comments for this user
	 */
	public void setComments(Set<Comment> comments){
		this.comments = comments;
	}
	
	
	@Column(name="profile_pic", columnDefinition="mediumblob")
	private byte[] image;
	

	@OneToMany(fetch = FetchType.EAGER)
	private Set<Todo> todos = new HashSet<Todo>(0);
	
	@OneToMany(fetch = FetchType.EAGER)
	private Set<Settings> settings = new HashSet<Settings>(0);
	
	@OneToMany(fetch = FetchType.EAGER)
	private Set<FTPFiles> ftpFiles = new HashSet<FTPFiles>(0);
	
	public Set<FTPFiles> getFtpFiles() {
		return ftpFiles;
	}

	public void setFtpFiles(Set<FTPFiles> ftpFiles) {
		this.ftpFiles = ftpFiles;
	}

	/**
	 * returns a hashset containing all attached settings to this user
	 * @return
	 */
	public Set<Settings> getSettings(){
		return this.settings;
	}
	
	public void setSettings(Set<Settings> settings){
		this.settings = settings;
	}
	
	
	/**
	 * returns a hashset containing all attached todos to this user
	 * @return
	 */
	public Set<Todo> getTodos(){
		return this.todos;
	}
	
	public void setTodos(Set<Todo> todos){
		this.todos = todos;
	}
	
	public void update(){};
	
	/**
	 * gets the image from the database as byte[]
	 * @return
	 */
	public byte[] getImage() {
		return image;
	}


	/**
	 * sets the image of the user as a mediumblob in the database
	 * @param image an byte[] which contains the images source
	 */
	public void setImage(byte[] image) {
		this.image = image;
	}

	@ManyToMany(fetch = FetchType.EAGER)
	private Set<Project> projects;
	
	public Set<Project> getProjects(){
		return projects;
	}
	
	
	public void setProjects(Set<Project> projects){
		this.projects = projects;
	}
	
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
	 */
	public String getUsername() {
		return username;
	}
	
	/**
	 * gets the firstname of the user object
	 * @return firstname as string
	 */
	public String getFirstname() {
		return firstname;
	}
	
	/**
	 * sets the firstname of the user object
	 * @param firstname a string containing the firstname of the user
	 */
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	
	/**
	 * gets the last name of the user object
	 * @return lastname as string 
	 */
	public String getLastname() {
		return lastname;
	}
	
	/**
	 * Sets the lastname of the user object
	 * @param lastname a string containing the lastname of the user
	 */
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	
	/**
	 * Sets the username of the user object
	 * @param username a string containing the username of the user
	 */
	public void setUsername(String username) {
		this.username = username;
	}
	
	/**
	 * Gets the MD5 password of the user object
	 * @return the password as MD5 string
	 */
	public String getPassword() {
		return password;
	}
	
	/**
	 * Sets the password of the user object
	 * This method takes the password and transforms it into an MD5 hash.
	 * @param password
	 */
	public void setPassword(String password) {
		this.password = User.hashPassword(password);
	}
	
	/**
	 * Sets the email of the user
	 * @param eMail
	 */
	public void setEMail(String eMail) {
		this.eMail = eMail;
	}
	
	/**
	 * Gets the email of user
	 * @return the email of the current user. returns null if not available.
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
	 * returns a list of all todos attached to this user.
	 * returns null if no todo was found.
	 * @return a List of Todos which belong to the current user.
	 */
	public List<Todo> getTodo(){
		Session session = Database.getSession();
		session.beginTransaction();
		
		Query query = session.createQuery("from Todo where user_id = :userid");
		query.setParameter("userid", this.getId());
		
		@SuppressWarnings("unchecked")
		List<Todo> result = query.list();
		
		session.close();
		
		if (result.size() == 0){ return null; }
		
		return result;
	}
	
	/**
	 * Searches DB for a user with the specified username
	 * @param username String containing the username which Hibernate should look for
	 * @return User returns user as UserObject if found otherwise null.
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
	
	/**
	 * Gets the user setting for a specific key while also
	 * providing a default value which will be instantly available to the database
	 * and other classes 
	 * @param key the key of the setting
	 * @param defaultValue the default value (also creates a new object if no setting was found)
	 * @return a Settings object for the given key.
	 */
	public Settings getSettingFor(String key, String defaultValue){
		Session session = Database.getSession();
		session.beginTransaction();
		
		Query query = session.createQuery("from Settings as settings where keyName = :key and user_id = :user_id");
		query.setParameter("key", key);
		query.setParameter("user_id", this.getId());
		
		List<?> result = query.list();
		
		session.close();
		if (result.size() == 0){ 
			Settings setting = new Settings(this, key, defaultValue);
			Database.saveAndCommit(setting);
			this.getSettings().add(setting);
			Database.saveAndCommit(this);
			return setting;
		}
		return (Settings) result.get(0);	
	}
	
	/**
	 * Saves the setting to the users settings list.
	 * @param key the settings key
	 * @param defaultValue the value to save
	 * @return the saved Settings object
	 */
	public Settings saveSetting(String key, String defaultValue){
		Settings setting = getSettingFor(key, defaultValue);
		setting.setStringValue(defaultValue);
		Database.saveAndCommit(setting);
		
		return setting;
	}
	
	/**
	 * removes the user from the database.
	 * @return
	 */
	public boolean delete(){
		Session session = Database.getSession();
		session.beginTransaction();
		
		Query query = session.createQuery("delete from User where user_id = :user_id");
		query.setParameter("user_id", this.getId());
		session.close();
		return false;
	}
	
	/**
	 * represents a nice readable name of the user
	 * the string is build using the firstname and by appending:
	 * lastname and email (email is wrapped with brackets) 
	 * @return String the string representing the current user
	 */
	public String toString(){
		return this.getFirstname() + " " + this.getLastname() + " (" + this.getEMail() + ")";
	}
	
}
