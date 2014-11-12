package edge.logic;

import edge.templates.BaseTemplate;

/**
 * The engine which will execute java templates
 * 
 * Templates are build as simple Java classes which
 * have to implement the EdgeTemplate interface.
 * 
 * The <tt>EdgeTemplateRunner</tt> runs the template from the database
 * with environment informations and reports states to the application.
 * 
 */
public class EdgeTemplateRunner {
	private String templateClass;
	private BaseTemplate templateInstance;
	
	/**
	 * Used to test templates. Do not use this as the start method.
	 * @param args command line arguments
	 */
	public static void main(String[] args){
		// Test stuff:
		EdgeTemplateRunner edgeTemplateRunner = new EdgeTemplateRunner();
		edgeTemplateRunner.setTemplateClass("edge.templates.MobileTemplate");

	}
	
	/**
	 * Sets the templateClass (<tt>edge.templates.YourTemplateClass</tt>) and executes it right away.
	 * @param templateClass
	 */
	private void setTemplateClass(String templateClass){
		this.templateClass = templateClass;
		try {
			templateInstance = (BaseTemplate) Class.forName(this.templateClass).newInstance();
			templateInstance.run();
		} catch (InstantiationException | IllegalAccessException
				| ClassNotFoundException e) {
			e.printStackTrace();
		}
		
	}
}
