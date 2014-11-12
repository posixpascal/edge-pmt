package edge.models;

import java.util.Date;
import javax.persistence.*;

@Entity
@Table
public class Comment extends BaseModel {
	
	@Id
	@GeneratedValue
	@Column(name="comment_id")
	private Integer id;
	
	private String message;
	
	private Date created;
	private Date modified;
	
	@ManyToOne
	@JoinColumn(name="user_id")
	private User user;
	
	@ManyToOne
	@JoinColumn(name="project_id")
	private Project project;
	
	@ManyToOne
	@JoinColumn(name="todo_id")
	private Todo todo;
	
	/**
	 * gets the user object of the user who wrote this comment
	 * @return User object
	 */
	public User getUser() {
		return user;
	}

	/**
	 * sets the user who writes the comment
	 * @param user any valid user object
	 */
	public void setUser(User user) {
		this.user = user;
	}

	/**
	 * gets the project where this comment belongs to
	 * @return Project object representing the project this comment belongs to.
	 */
	public Project getProject() {
		return project;
	}

	/**
	 * sets the projects where this comment belongs to
	 * @param project any valid project object
	 */
	public void setProject(Project project) {
		this.project = project;
	}
	
	/**
	 * sets the associated todo for this comment
	 * @param todo any valid todo object
	 */
	public void setTodo(Todo todo){
		this.todo = todo;
	}
	
	/**
	 * gets the associated todo for this comment
	 * @return a Todo object
	 */
	public Todo getTodo(){
		return this.todo;
	}


	@PrePersist
	protected void onCreate(){
		this.created = new Date();
	}
	
	
	public Comment(){}
	
	/**
	 * constructs a new comment given the <tt>message</tt>
	 * @param message any valid string comment
	 */
	public Comment(String message){
		this.message = message;
	}

	
	@PreUpdate
	protected void onUpdate(){
		this.modified = new Date();
	}
	
	/**
	 * gets the ID of the comment
	 * @return the ID as an integer
	 */
	public Integer getId() {
		return id;
	}
	
	/**
	 * sets the ID of the comment
	 * @param id the ID of the comment as integer
	 */
	public void setId(Integer id) {
		this.id = id;
	}


	/**
	 * gets the date when this comment was modified
	 * @return the date when this comment was last modified
	 */
	public Date getModified() {
		return modified;
	}

	/**
	 * gets the date when this comment was created
	 * @return the date when this comment was created
	 */
	public Date getCreated() {
		return created;
	}
	
	
	/**
	 * gets the comments message
	 * @return a string representing the comment's message
	 */
	public String getMessage() {
		return message;
	}


	/**
	 * sets the comments message
	 * @param message a string representing the comment's message
	 */
	public void setMessage(String message) {
		this.message = message;
	}
	
}
