package edge.models;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.annotations.Type;
import edge.logic.Database;



@Entity
@Table
public class Project extends BaseModel implements java.io.Serializable {
	private static final long serialVersionUID = 463646589751663896L;

	@Id
	@GeneratedValue
	@Column(name="id")
	private Long id;
	
	private String name;
	
	@Column(name="image", columnDefinition="mediumblob")
	private byte[] image;

	private String customerName;
	private Date deadLine;
	
	@Column
	@Type(type="timestamp")
	private Date created;
	
	@Column
	@Type(type="timestamp")
	private Date modified;
	
	@OneToMany(fetch = FetchType.EAGER)
	private Set<FTPFiles> ftpFiles = new HashSet<FTPFiles>(0);
	
	@OneToMany(fetch = FetchType.EAGER)
	private Set<Comment> comments = new HashSet<Comment>(0);
	
	/**
	 * returns a hashset containing all attached comments to this project
	 * @return hashset with all comments for this project
	 */
	public Set<Comment> getComments(){
		return this.comments;
	}
	
	/**
	 * sets a hashset containing all attached comments to this project
	 * @param comments hashset with all comments for this project
	 */
	public void setComments(Set<Comment> comments){
		this.comments = comments;
	}
	
	
	
	/**
	 * gets the associated ftp files which were uploaded to this project
	 * @return a hashset of the FTPFiles which were uploaded to the project
	 */
	public Set<FTPFiles> getFtpFiles() {
		return ftpFiles;
	}

	/**
	 * sets the ftp files 
	 * @param ftpFiles a hashset of FTPFiles
	 */
	public void setFtpFiles(Set<FTPFiles> ftpFiles) {
		this.ftpFiles = ftpFiles;
	}

	
	@PrePersist
	protected void onCreate(){
		created = new Date();
	}
	
	@ManyToMany(fetch = FetchType.EAGER)
	private Set<User> users = new HashSet<User>(0);
	
	
	@OneToMany(fetch = FetchType.EAGER)
	private Set<TodoGroup> todoGroups = new HashSet<TodoGroup>(0);
	
	@OneToMany(fetch = FetchType.EAGER)
	private Set<Todo> todos = new HashSet<Todo>(0);
	
	
	/**
	 * returns a hashset containing all attached users to this project
	 * @return
	 */
	public Set<User> getUsers(){
		return this.users;
	}
	
	/**
	 * sets all users which are working on this project
	 * @param users a hashset of users
	 */
	public void setUsers(Set<User> users){
		this.users = users;
	}

	/**
	 * returns a hashset containing all attached todos to this project
	 * @return hashset with all todos for this project
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

	/**
	 * gets the deadline of the project
	 * @return a date object (null if not set)
	 */
	public Date getDeadLine() {
		return deadLine;
	}

	/**
	 * sets the deadline for this project
	 * @param deadLine
	 */
	public void setDeadLine(Date deadLine) {
		this.deadLine = deadLine;
	}

	/**
	 * returns the number of days until the project has ended.
	 * this uses the deadline to produce a number.
	 * 
	 * if the number is negative the project is over its deadline
	 * @return an integer representing the days until the project is due 
	 */
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
	
	/**
	 * returns the number of days as a string
	 * @return string when the project is due
	 */
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
	
	/**
	 * gets a css color class for the days until deadline.
	 * if the deadline is 30 or more days ahead of the project, the color will be green.
	 * if its 20 or more days ahead it'll be yellow
	 * if its 10 or more it'll be orange.
	 * if its below 10 but more than 0 it'll be red
	 * and if the deadline was reached the color will be dark-red-
	 * @return a string representing a CSS class.
	 */
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

	/**
	 * sets the todoGroups which are attached to this project.
	 * @return a HashSet containing every todoGroup attached to this project.
	 */
	public Set<TodoGroup> getTodoGroups() {
		return this.todoGroups;
	}

}
