package edge.models;

import java.util.Date;

import javax.persistence.*;

import org.hibernate.*;
import org.hibernate.Query;

@Entity
@Table
public class Comment extends BaseModel {
	@Id
	@GeneratedValue
	@Column(name="comment_id")
	private Integer id;
	
	private String comment;
	private String title = "";
	
	private Date created;
	private Date modified;
	
	@ManyToOne
	@JoinColumn(name="user_id")
	private User user;
	
	@ManyToOne
	@JoinColumn(name="project_id")
	private Project project;
	
	public String getTitle() {
		return title;
	}


	public void setTitle(String title) {
		this.title = title;
	}


	public User getUser() {
		return user;
	}


	public void setUser(User user) {
		this.user = user;
	}


	public Project getProject() {
		return project;
	}


	public void setProject(Project project) {
		this.project = project;
	}


	@PrePersist
	protected void onCreate(){
		setCreated(new Date());
	}
	
	
	public Comment(){}
	
	public Comment(String title, String message){
		this.title = title;
		this.comment = message;
	}

	
	@PreUpdate
	protected void onUpdate(){
		setModified(new Date());
	}
	
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}



	public Date getModified() {
		return modified;
	}



	public void setModified(Date modified) {
		this.modified = modified;
	}



	public Date getCreated() {
		return created;
	}



	public void setCreated(Date created) {
		this.created = created;
	}



	public String getComment() {
		return comment;
	}



	public void setComment(String comment) {
		this.comment = comment;
	}
	
}
