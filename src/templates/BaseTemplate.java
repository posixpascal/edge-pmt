package templates;

import java.util.HashMap;

public class BaseTemplate {
	public BaseTemplate(){}
	
	private HashMap<String, String> files = new HashMap<String, String>(){
		private static final long serialVersionUID = 4396856278328549766L;

		
		{
		
		
		}
	};
	
	private boolean setRootPath(){
		return false;
	}
	
	private boolean makeDirectory(String path){
		return false;
	}
	
	private boolean run(){
		System.out.print("Can't run BaseTemplate. Try using a different template");
		return false;
	}
}
