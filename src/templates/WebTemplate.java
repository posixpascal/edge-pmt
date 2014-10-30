package templates;

import java.util.HashMap;

public class WebTemplate {
	private HashMap<String, String> files = new HashMap<String, String>(){
		private static final long serialVersionUID = 4396856278328549766L;
		{
			put("index.html", "<!DOCTYPE html>\n<html>\n\t<head></head>\n\t<body></body>\n<html>");
			put("robots.txt", "yo");
			put("assets/img", "yo");
			put("assets/css", "yo");
			put("assets/js", "yo");
		}
	};
	
}
