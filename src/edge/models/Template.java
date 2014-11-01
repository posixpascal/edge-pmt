package edge.models;

import java.util.Date;

import javax.persistence.*;

import org.hibernate.*;
import org.hibernate.Query;

@Entity
@Table
public class Template extends BaseModel {
	@Id
	@GeneratedValue
	private Integer id;
	
	public String templateName;
	
	// eg: edge.templates.MobileTemplate
	public String templateClass;
	
	
	
}
