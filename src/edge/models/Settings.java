package edge.models;

import javax.persistence.*;


/**
 * This JPA model represents a Windows-Registry-Like interface
 * for dealing with various configurations for the user. 
 */
@Entity
@Table
public class Settings extends BaseModel {
	@Id
	@GeneratedValue
	private Integer id;
	
	private String keyName;
	private String stringValue;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="user_id")
	private User user;
	
	/**
	 * A basic constructor for the settings class.
	 */
	public Settings(){}
	
	/**
	 * Constructs a new Settings object with the user, key and default value already
	 * present. You can quickly create a setting the for active user for example:
	 * 
	 * <tt>Settings s = Settings(EdgeSession.getActiveUser(), "yourSetting", "yourInitialValue")</tt>
	 * 
	 * and save it afterwards with ease:
	 * 
	 * <tt>Database.saveAndCommit(s);</tt>
	 * 
	 * @param user a user object
	 * @param key the key of the setting as a string
	 * @param defaultValue 
	 */
	public Settings(User user, String key, String defaultValue) {
		this.setUser(user);
		this.setKeyName(key);
		this.setStringValue(defaultValue);
	}
	
	/**
	 * gets the ID of the current settings object
	 * @return the ID as an integer
	 * TODO: maybe use long for IDs?
	 */
	public Integer getId() {
		return id;
	}
	
	/**
	 * sets the ID of the current settings object
	 * be aware of the fact that IDs must be unique!
	 * if not, Hibernate will <b>UPDATE</b> a different setting instead.
	 * @param id the ID as an integer
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * gets the keyName of the setting
	 * it has to be keyName because of the way hibernate's mapping works.
	 * @return a string representing the key of the setting
	 */
	public String getKeyName() {
		return keyName;
	}

	/**
	 * sets the keyName of the setting
	 * @param keyName a string representing the key (should be unique for the
	 * user to avoid issues when dealing with settings
	 */
	public void setKeyName(String keyName) {
		this.keyName = keyName;
	}

	/**
	 * gets the value of the setting as string.
	 * there is no class supported other than string
	 * because we decided to keep the parsing
	 * to the external classes. we can't really
	 * predict the type of the setting without offering
	 * a huge amount of columns (which would go against the third normal form)
	 * @return the value of the setting as string
	 */
	public String getStringValue() {
		return stringValue;
	}

	/**
	 * sets the value of the setting
	 * @param stringValue a string value for the setting (key => value)
	 */
	public void setStringValue(String stringValue) {
		this.stringValue = stringValue;
	}

	/**
	 * return the user which belongs to the setting
	 * @return any user object
	 */
	public User getUser() {
		return user;
	}

	/**
	 * sets the user for the specific setting
	 * @param user any user object
	 */
	public void setUser(User user) {
		this.user = user;
	}
}
