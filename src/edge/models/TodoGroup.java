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
public class TodoGroup extends BaseModel {
	@Id
	@Column(name="todogroup_id")
	@GeneratedValue
	private Long id;
	
	private String name;
	
	private Date created;
	private Date modified;
	
	private String title;
	
	public String getTitle(){
		return this.title;
	}
	
	public void setTitle(String title){
		this.title = title;
	}


	
	/**
	 * @constructor
	 */
	public TodoGroup(){}
	
	private Project project;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "project_id", nullable = false)
    public Project getProject()  
    {  
        return project;  
    }  
	
	@OneToMany(fetch = FetchType.EAGER)
	private Set<Todo> todos = new HashSet<Todo>();
	
	/**
	 * returns a hashset containing all attached todos to this project
	 * @return
	 */
	public Set<Todo> getTodos(){
		return this.todos;
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
	
	public static List<TodoGroup> getAll() {
		Session session = Database.getSession();
		session.beginTransaction();
		
		Query query = session.createQuery("from TodoGroup");
		
		@SuppressWarnings("unchecked")
		List<TodoGroup> result = (List<TodoGroup>) query.list();
		

		session.close();
		
		return result;
	}
	
	
	@PrePersist
	protected void onCreate(){
		created = new Date();
	}
	
	@PreUpdate
	protected void onUpdate(){
		modified = new Date();
	}
	
}
