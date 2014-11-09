package edge.models;

import java.util.Date;

import javax.persistence.*;

import org.hibernate.*;
import org.hibernate.Query;

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
	
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}

	public String getKeyName() {
		return keyName;
	}

	public void setKeyName(String keyName) {
		this.keyName = keyName;
	}

	public String getStringValue() {
		return stringValue;
	}

	public void setStringValue(String stringValue) {
		this.stringValue = stringValue;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
}
