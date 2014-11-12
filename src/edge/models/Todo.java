package edge.models;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Type;

@Entity
@Table
public class Todo extends BaseModel implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	@Column(name="id")
	private Long id;

	private String title;
	
	@Column
	@Type(type="text")
	private String content;
	
	private boolean isClosed = false;

	private Date deadLine;
	
	@Column
	@Type(type="timestamp")
	private Date created;
	
	@Column
	@Type(type="timestamp")
	private Date modified;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "project_id")
	private Project project;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "todogroup_id", nullable = false)
	private TodoGroup todoGroup;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="user_id", nullable=false)
	private User user;
	
	@OneToMany(fetch = FetchType.EAGER)
	private Set<Comment> comments = new HashSet<Comment>(0);
	
	/**
	 * returns a hashset containing all attached comments to this todo
	 * @return hashset with all comments for this todo
	 */
	public Set<Comment> getComments(){
		return this.comments;
	}
	
	/**
	 * sets a hashset containing all attached comments to this todo
	 * @param comments hashset with all comments for this todo
	 */
	public void setComments(Set<Comment> comments){
		this.comments = comments;
	}
	
	
	
	/**
	 * gets the belonging user of this todo
	 * @return a User object of the user which belongs to this todo
	 */
	public User getUser() {
		return this.user;
	}
	
	/**
	 * sets the user of this todo
	 * @param user any user model
	 */
	public void setUser(User user) {
		this.user = user;
	}
	
	/**
	 * sets the todoGroup of the object.
	 * @param todoGroup a TodoGroup which this todo should belong to
	 */
	public void setTodoGroup(TodoGroup todoGroup) {
		this.todoGroup = todoGroup;
	}

	
	/**
	 * receives the project where this todo is attached to.
	 * @return a Project instance of the belonging project
	 */
    public Project getProject()  
    {  
        return project;  
    }  
	
    /**
     * Gets the todoGroup of the current todo
     * @return a todoGroup instance of the belonging todoGroup
     */
    public TodoGroup getTodoGroup()  
    {  
        return todoGroup;
    }  

	/**
	 * sets the project of the current todo
	 * @param project a project instance.
	 */
	public void setProject(Project project){
		this.project = project;
	}
	
	
	@PrePersist
	protected void onCreate(){
		created = new Date();
	}
	
	@PreUpdate
	protected void onUpdate(){
		modified = new Date();
	}
	
	public Todo(){}
	
	/**
	 * get the date when the todo object was created
	 * @return the date when the Todo Object was originally created.
	 */
	public Date getCreated() {
		return created;
	}
	
	/**
	 * FIXME: We should remove this, shouldn't we?
	 * @param created
	 */
	public void setCreated(Date created) {
		this.created = created;
	}
	
	/**
	 * get the date when the todo object was last modified
	 * @return
	 */
	public Date getModified() {
		return modified;
	}
	
	/**
	 * FIXME: das passiert automatisch. siehe @onUpdate
	 * @param modified
	 */
	public void setModified(Date modified) {
		this.modified = modified;
	}
	
	/**
	 * gets the title of the todo
	 * @return the title of the todo as string
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * sets the title of the todo
	 * FIXME: rename this to setTitle()
	 * @param title
	 */
	public void setTitleName(String title) {
		this.title = title;
	}

	/**
	 * returns the HTML content of this todo
	 * @return a string containing the HTML description of this todo
	 */
	public String getContent(){
			return content;
	}
	
	/**
	 * sets the HTML content of this todo
	 * @param content any string (accepts HTML as well)
	 */
	public void setContent(String content){
		this.content = content;
	}
	
	
	/**
	 * gets the ID of the current Todo
	 * @return the ID as Long
	 */
	public Long getId() {
		return id;
	}

	/**
	 * sets the ID of the current todo.
	 * @param id
	 */
	public void setId(Long id) {
		this.id = id;
	}
	
	
	/**
	 * sets the deadline of the todo.
	 * @param value any date object (should be in the future)
	 */
	public void setDeadline(Date value) {
		this.deadLine = value;
	}
	
	/**
	 * gets the deadline of the todo.
	 * @return
	 */
	public Date getDeadline()
	{
		return deadLine;
	}

	public boolean isValid(){
		// TODO: add validation here
		return true;
	}
	
	/**
	 * checks whether the todo is closed or not
	 * @return true if the todo is closed and false if not.
	 */
	public boolean isClosed() {
		return this.isClosed;
	}

	/**
	 * sets the state of the todo
	 * @param isClosed true if closed, false if not.
	 */
	public void setClosed(boolean isClosed) {
		this.isClosed = isClosed;
	}
}
