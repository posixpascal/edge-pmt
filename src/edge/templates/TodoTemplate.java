package edge.templates;

import edge.models.Project;
import edge.models.Todo;

import java.util.HashMap;

/**
 * allows saving of commonly used todos and their todo groups.
 */
public class TodoTemplate extends BaseTemplate {
	private Project project = null;
	
	@SuppressWarnings("unused")
	private HashMap<String, Todo> files = new HashMap<String, Todo>(){
		private static final long serialVersionUID = 4396856278328549765L;
		{
			put("Todo#1", new Todo());
			put("Todo#2", new Todo());
		}
	};
	

	@SuppressWarnings("unused")
	private void setProject(Project project){
		this.project = project;
	}
	
	@SuppressWarnings("unused")
	private Project getProject(){
		return this.project;
	}
	
	public boolean run(){
		if (project == null){ 
			throw new Error("Bitte Projekt setzen. TodoTemplate#setProject");
		}
		return false;
		
		
	}
}
