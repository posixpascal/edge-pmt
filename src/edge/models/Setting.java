package edge.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;



@Entity
@Table
public class Setting extends BaseModel {
	@Id
	@GeneratedValue
	@Column(name="id")
	private Long id;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="user_id", nullable=false)
	private User user;
	
	
	public User getUser() {
		return this.user;
	}
	
	
	public void setUser(User user) {
		this.user = user;
	}
	
	public String getKey() {
		return key;
	}


	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}


	public void setValue(String value) {
		this.value = value;
	}

	private String key;
	private String value;
}
