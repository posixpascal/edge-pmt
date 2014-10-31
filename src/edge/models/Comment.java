package edge.models;

import java.util.Date;

import javax.persistence.*;

import org.hibernate.*;
import org.hibernate.Query;

@Entity
@Table
public class Comment {
	@Id
	@GeneratedValue
	private Integer id;
	
	private String comment;
	
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
	
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
}
