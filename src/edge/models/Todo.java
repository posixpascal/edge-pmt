package edge.models;

import java.time.LocalDate;
import java.util.Date;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.*;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.messageinterpolation.ResourceBundleMessageInterpolator;
import org.hibernate.validator.resourceloading.PlatformResourceBundleLocator;



@Entity
@Table
public class Todo extends BaseModel implements java.io.Serializable {
	
	@Id
	@GeneratedValue
	@Column(name="id")
	private Long id;

	
	//@NotNull(message = "Das Todo ben�tigt einen Titel")
	//@Min(2)
	private String title;
	
	//@NotNull(message = "Das kann aber leer sein")
	private String content;
	private boolean isClosed = false;
	
	//@NotNull(message = "Füge eine Deadline zum Projekt hinzu")
	private Date deadLine;
	
	private Date created;
	private Date modified;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "project_id")
	private Project project;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "todogroup_id", nullable = false)
	private TodoGroup todoGroup;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="user_id", nullable=false)
	private User user;
	
	
	public User getUser() {
		return this.user;
	}
	
	
	public void setTodoGroup(TodoGroup todoGroup) {
		this.todoGroup = todoGroup;
	}

	

    public Project getProject()  
    {  
        return project;  
    }  
	

    public TodoGroup getTodoGroup()  
    {  
        return todoGroup;
    }  
		
	public void setUser(User user) {
		this.user = user;
	}
	
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
	
	private void update(){}
	
	/**
	 * get the date when the todo object was created
	 * @return the date when the Todo Object was originally created.
	 * @author nahom
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
	 * @author nahom
	 */
	public void setModified(Date modified) {
		this.modified = modified;
	}
	
	/**
	 * gets the title of the todo
	 * @return the title of the todo as string
	 * @author nahom
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * sets the title of the todo
	 * @author nahom
	 * FIXME: rename this to setTitle()
	 * @param title
	 */
	public void setTitleName(String title) {
		this.title = title;
	}

	public String getContent(){
			return content;
	}
	
	public void setContent(String content){
		this.content = content;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	

	public void setDeadline(Date value) {
		this.deadLine = value;
	}
	public Date getDeadline()
	{
		return deadLine;
	}
	
	public boolean isValid(){
		// TODO: add validation here
		return true;
	}

	public boolean isClosed() {
		return this.isClosed;
	}

	public void setClosed(boolean isClosed) {
		this.isClosed = isClosed;
	}
	
	
	/* validation stuff 
	 * disabled for fucks sake. 
	 *
	 *Vankash: okay das ist echt edel!
	 * add / to the star to enable: *
	 
	

	@Transient
	private Validator validator = Validation.byDefaultProvider()
							.configure()
							.messageInterpolator(
								new ResourceBundleMessageInterpolator(
									new PlatformResourceBundleLocator("Messages")
								)
							)
							.buildValidatorFactory()
							.getValidator();
	
	
	public String getErrorMessages(){
		String errors = "";
		errors += validator.validateProperty(this, "deadline").iterator().next().getMessage();
		
		return errors;
	}/**/
}
