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

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.validator.messageinterpolation.ResourceBundleMessageInterpolator;
import org.hibernate.validator.resourceloading.PlatformResourceBundleLocator;


import edge.logic.Database;



@Entity
@Table
public class Project extends BaseModel implements java.io.Serializable {
	
	@Id
	@GeneratedValue
	@Column(name="project_id")
	private Long id;
	
	//@NotNull(message = "Das Projekt benötigt einen Namen")
	//@Min(2)
	private String name;
	
	@Column(name="image", columnDefinition="mediumblob")
	private byte[] image;
	
	//@NotNull(message = "Ein Projekt muss einem Kunden zugewiesen sein")
	private String customerName;
	
	//@NotNull(message = "Füge eine Deadline zum Projekt hinzu")
	private Date deadLine;
	
	private Date created;
	private Date modified;
	


	
	@PrePersist
	protected void onCreate(){
		created = new Date();
	}
	
	@OneToMany(fetch = FetchType.EAGER)
	private Set<User> users = new HashSet<User>();
	
	
	@OneToMany(fetch = FetchType.EAGER)
	private Set<TodoGroup> todoGroups = new HashSet<TodoGroup>();
	
	/**
	 * returns a hashset containing all attached users to this project
	 * @return
	 */
	public Set<User> getUsers(){
		return this.users;
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
	 * Get a hashset of all closed todos for this project
	 * @return
	 */
	public Set<Todo> getClosedTodos() {
		Set<Todo> closedTodos = new HashSet<Todo>();
		this.todos.forEach( (todo) -> {
			if (todo.isClosed()){
				closedTodos.add(todo);
			}
		});
		return closedTodos;
	}
	
	/**
	 * get a hashset of all open todos for this project
	 * @return
	 */
	public Set<Todo> getOpenTodos() {
		Set<Todo> closedTodos = new HashSet<Todo>();
		this.todos.forEach( (todo) -> {
			if (!todo.isClosed()){
				closedTodos.add(todo);
			}
		});
		return closedTodos;
	}
	
	@PreUpdate
	protected void onUpdate(){
		modified = new Date();
	}
	
	
	
	public Project(){}
	
	/**
	 * gets the date when this project was created
	 * @return
	 */
	public Date getCreated() {
		return created;
	}
	
	/**
	 * sets the date when this project is created
	 * @param created
	 */
	public void setCreated(Date created) {
		this.created = created;
	}
	
	/**
	 * returns the date when the project was last modified
	 * @return
	 */
	public Date getModified() {
		return modified;
	}
	
	/**
	 * sets the date when the project was last modified
	 * @param modified
	 */
	public void setModified(Date modified) {
		this.modified = modified;
	}
	
	/**
	 * gets a list of all projects available in the database
	 * @return
	 */
	public static List<Project> getAll() {
		Session session = Database.getSession();
		session.beginTransaction();
		
		Query query = session.createQuery("from Project");
		
		@SuppressWarnings("unchecked")
		List<Project> result = (List<Project>) query.list();
		
		session.getTransaction().commit();
		Database.closeSession();
		
		return result;
	}
	
	

	/**
	 * gets the customername of the project
	 * @return
	 */
	public String getCustomerName() {
		return customerName;
	}

	/**
	 * sets the customername of the project
	 * @param customerName
	 */
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	
	/**
	 * gets the name of the project
	 * @return a string representing the name of the project
	 */
	public String getName(){
			return name;
	}
	
	/**
	 * gets the ID of the project
	 * @return
	 */
	public Long getId() {
		return id;
	}

	/**
	 * sets the ID of the project
	 * @param id
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * sets the name of the project
	 * @param name
	 */
	public void setName(String name){
		this.name = name;
	}

	/**
	 * sets the deadline for the project
	 * @param value any java.util.Date object which represents a date in the future.
	 */
	public void setDeadline(Date value) {
		this.setDeadLine(value);
	}
	
	/**
	 * check whether the project is valid or not.
	 * @return
	 * @todo
	 */
	public boolean isValid(){
		// TODO: add validation here
		return true;
	}

	public byte[] getImage() {
		return image;
	}
	
	/**
	 * sets the image for the project as base64 encode
	 * @param image the base64 encoded image
	 */
	public void setImage(byte[] image) {
		this.image = image;
	}

	public Date getDeadLine() {
		return deadLine;
	}

	public void setDeadLine(Date deadLine) {
		this.deadLine = deadLine;
	}

	public int getDaysToDeadLine() {
		long now = new Date().getTime();
		long deadline = this.getDeadLine().getTime();
		long difference = (now - deadline);
		boolean isDue = false;
		
		if (difference < 0){ 
			difference *= -1;
			isDue = true;
		}
		
		int days = (int) difference / (1000 * 60 * 60 * 24);
		
		if (isDue){ days *= -1; }
		return days;
	}
	
	
	public String getDaysToDeadLineInWords(){
		int days = this.getDaysToDeadLine();
		String dayString = "";
		if (days == 0) { dayString = "heute"; }
		else if (days > 0){ dayString = "in " + days + " " + (days == 1 ? "Tag" : "Tagen"); }
		else {
			dayString = "vor " + (-1 * days) + " " + (days == -1 ? "Tag" : "Tagen"); 
		}
		return dayString;
	}
	
	
	public String getDeadlineColorClass(){
		String colorClass = "default";
		int days = this.getDaysToDeadLine();
		
		if (days > 30) colorClass = "green";
		else if (days > 20) colorClass = "yellow";
		else if (days > 10) colorClass = "orange";
		else if (days > 0) colorClass = "red";
		else if (days <= 0) colorClass = "dark-red";
		
		return colorClass;
			
		
	}

	public Set<TodoGroup> getTodoGroups() {
		return this.todoGroups;
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
