package edge.logic;

import edge.templates.BaseTemplate;

public class EdgeTemplateRunner {
	private String templateClass;
	private BaseTemplate templateInstance;
	
	
	public static void main(String[] args){
		// Test stuff:
		EdgeTemplateRunner edgeTemplateRunner = new EdgeTemplateRunner();
		edgeTemplateRunner.setTemplateClass("edge.templates.MobileTemplate");

	}
	
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
