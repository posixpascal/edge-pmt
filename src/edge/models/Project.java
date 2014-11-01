package edge.models;

import java.time.LocalDate;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;

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
public class Project extends BaseModel {
	
	@Id
	@GeneratedValue
	private Long id;
	
	//@NotNull(message = "Das Projekt benötigt einen Namen")
	//@Min(2)
	private String name;
	private String image;
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
	
	@PreUpdate
	protected void onUpdate(){
		modified = new Date();
	}
	
	
	public Project(){}
	
	public Date getCreated() {
		return created;
	}
	public void setCreated(Date created) {
		this.created = created;
	}
	public Date getModified() {
		return modified;
	}
	public void setModified(Date modified) {
		this.modified = modified;
	}
	
	public static List<Project> getAll() {
		Session session = Database.getSession();
		session.beginTransaction();
		
		Query query = session.createQuery("from Project");
		
		@SuppressWarnings("unchecked")
		List<Project> result = (List<Project>) query.list();
		
		session.close();
		
		return result;
	}
	
	

	
	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getName(){
			return name;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setName(String name){
		this.name = name;
	}

	public void setDeadline(Date value) {
		this.deadLine = value;
	}
	
	public boolean isValid(){
		// TODO: add validation here
		return true;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
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
