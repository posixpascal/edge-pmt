package edge.models;

import javax.persistence.*;


@Entity
@Table
public class Template extends BaseModel {
	@Id
	@GeneratedValue
	private Integer id;
	
	public String templateName;
	
	/**
	 * gets the template name
	 * @return the templateName as string
	 */
	public String getTemplateName() {
		return templateName;
	}

	/**
	 * sets the template name as string
	 * @param templateName
	 */
	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}

	/**
	 * gets the template java class as string
	 * @return any java class (package) as string
	 */
	public String getTemplateClass() {
		return templateClass;
	}

	/**
	 * sets the templateClass for this template
	 * A valid templateClass would be:
	 * 
	 * <tt>edge.templates.WebTemplate</tt>
	 * 
	 * A template has to implement the EdgeTemplate interface!
	 * 
	 * @param templateClass any valid java class (package) as string
	 */
	public void setTemplateClass(String templateClass) {
		this.templateClass = templateClass;
	}

	public String templateClass;
	
	
	
	
	
}
