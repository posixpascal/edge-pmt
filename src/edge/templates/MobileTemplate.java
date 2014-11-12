package edge.templates;

import java.util.HashMap;

/**
 * Creates initial file structure for a mobile project.
 */
public class MobileTemplate extends BaseTemplate {
	@SuppressWarnings("unused")
	private HashMap<String, String> files = new HashMap<String, String>(){
		private static final long serialVersionUID = 4396856278328549766L;
		{
			
		
		}
	};
	
	@Override
	public boolean run(){
		return false;
	}


	@Override
	public void beforeStart() {
	}

	@Override
	public void afterFinish() {
	}
	
}
