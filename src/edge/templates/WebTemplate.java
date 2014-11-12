package edge.templates;

import java.util.HashMap;
import edge.controllers.BaseController;

public class WebTemplate extends BaseController {
	@SuppressWarnings("unused")
	private HashMap<String, String> files = new HashMap<String, String>(){
		private static final long serialVersionUID = 4396856278328549766L;
		{
			put("index.html", "<!DOCTYPE html>\n<html>\n\t<head></head>\n\t<body></body>\n<html>");
			put("robots.txt", "yo");
			
			put("assets/img", "yo");
			
			put("assets/css", "yo");
			put("assets/css/main.css", "yo");
			
			put("assets/js", "yo");
			put("assets/js/main.js", "yo");
		}
	};

	public boolean run() {
		return false;
	}


	public void beforeStart() {
	}


	public void afterFinish() {
	}
	
}
