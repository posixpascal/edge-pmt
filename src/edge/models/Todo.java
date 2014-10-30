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

import org.hibernate.validator.messageinterpolation.ResourceBundleMessageInterpolator;
import org.hibernate.validator.resourceloading.PlatformResourceBundleLocator;



@Entity
@Table
public class Todo {
	
	@Id
	@GeneratedValue
	private Long id;
	private Long projectId;
	
	//@NotNull(message = "Das Todo benötigt einen Titel")
	//@Min(2)
	private String title;
	
	//@NotNull(message = "Das kann aber leer sein")
	private String content;
	
	//@NotNull(message = "FÃ¼ge eine Deadline zum Projekt hinzu")
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
	
	public Todo(){}
	
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
	
	public String getTitle() {
		return title;
	}

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
	
	public Long getProjectId() {
		return id;
	}

	public void setProjectId(Long projectId) {
		this.projectId = projectId;
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
