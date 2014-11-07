package edge.templates;

import edge.models.Project;
import edge.models.Todo;

import java.util.HashMap;

public class TodoTemplate extends BaseTemplate {
	private Project project = null;
	
	private HashMap<String, Todo> files = new HashMap<String, Todo>(){
		private static final long serialVersionUID = 4396856278328549765L;
		{
			put("Todo#1", new Todo());
			put("Todo#2", new Todo());
		}
	};
	
	private HashMap<String, Todo> getFiles(){
		return this.files;
	}
	
	private void setProject(Project project){
		this.project = project;
	}
	
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
