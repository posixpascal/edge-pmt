package edge.models;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import edge.logic.Database;
import javax.persistence.*;
import org.hibernate.*;
import org.hibernate.Query;
import org.hibernate.annotations.Type;
import java.util.Set;


@Entity
@Table
public class TodoGroup extends BaseModel implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="id")
	@GeneratedValue
	private Long id;
	
	@Column
	@Type(type="timestamp")
	private Date created;
	
	@Column
	@Type(type="timestamp")
	private Date modified;
	
	private String title;
	
	public String getTitle(){
		return this.title;
	}
	
	public void setTitle(String title){
		this.title = title;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "project_id")
	private Project project;

	
	/**
	 * @constructor
	 */
	public TodoGroup(){}

	/**
	 * gets the attached project of the current todogroup
	 * @return a Project instance of the project attached to this todoGroup
	 */
    public Project getProject()  
    {  
        return project;  
    }  
    
    /**
     * sets the project of the current todoGroup
     * @param project
     */
    public void setProject(Project project) {
		this.project = project;
		
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
	 * @return a TodoGroup object containing the first result found by title & projectId
	 * @throws ProjectNotFoundException when a project wasn't found in the database
	 * @author pr
	 */
	public static Object findByTitle(String title, Project project){
		Session session = Database.getSession();
		session.beginTransaction();
		
		Query projectQuery = session.createQuery("from Project as project where project.id = :project_id");
		projectQuery.setParameter("project_id", project.getId());
		List<?> result = projectQuery.list();
		
		if (result.size() == 0){ throw new Error("ProjectNotFoundException"); }
		
		Query query = session.createQuery("from TodoGroup as todoGroup where title = :title and project_id = :project_id");
		query.setParameter("project_id", project.getId());
		query.setParameter("title", title);
		
		result = query.list();
		session.getTransaction().commit();
		Database.closeSession();
		
		if (result.size() == 0){ return null; }
		return result.get(0);
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
	
	/**
	 * Gets all todoGroups from the database.
	 * @return
	 */
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
