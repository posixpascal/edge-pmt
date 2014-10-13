package edge.logic;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.util.HashMap;
import java.io.IOException;

public class ConfigParser {
	public final String DEFAULT_CONFIG = "src/config.xml";
	public final String MODULE_CONFIG = "src/edge/modules/config.xml";
	public HashMap<String, Object> config = new HashMap<String, Object>();
	
	
	/**
	 * Parses the given XML file into a HashMap object.
	 * @param configFileName
	 * @return boolean whether the import went successful or not.
	 */
	public boolean loadConfig(String configFileName){
		Document document = null;
		DocumentBuilder documentBuilder = null;
		DocumentBuilderFactory documentBuilderFactory = null;
		boolean isConfigLoaded = false;
		try {
			
			File configFile = new File(configFileName);
			documentBuilderFactory = DocumentBuilderFactory.newInstance();
			documentBuilder = documentBuilderFactory.newDocumentBuilder();
			document = documentBuilder.parse(configFile);
			
			HashMap<String, HashMap<String, Object>> databaseConfigs = this.parseDatabasesFromConfig(document);
			
			this.config.put("database", databaseConfigs);
			
			databaseConfigs = null;
			isConfigLoaded = true;
			
		} catch (IOException e){
			System.out.println("Config File not found");
			e.printStackTrace();
			
		} catch (Exception e){
			e.printStackTrace();
		}
		
		return isConfigLoaded;		
	}
	
	/**
	 * TODO: implement this
	 * @param targetFile
	 * @return
	 */
	public boolean saveConfig(String targetFile){
		return true;
	}
	
	
	/**
	 * parses XML <database></database> node to HashMap for later retrival
	 * example xml node:
	 * <database name="EDGE" environment="development">
	 *		<host>localhost</host>
	 *		<port>3306</port>
	 *		<user>root</user>
	 *		<pass>troopermedia</pass>		
	 * </database>
	 * it uses the environment  as the HashMap key. 
	 * @param document
	 * @return
	 */
	private HashMap<String, HashMap<String, Object>> parseDatabasesFromConfig(Document document){
		HashMap<String, HashMap<String, Object>> databaseConfigs = new HashMap<String, HashMap<String, Object>>();
		NodeList databaseNode = document.getElementsByTagName("database");
		
		for (int i = 0; i < databaseNode.getLength(); i++){
			Node childNode = databaseNode.item(i);
			Element childElement = (Element) childNode;
			HashMap<String, Object> databaseConfig = new HashMap<String, Object>();
			
			
			String databaseName = childElement.getAttribute("name");
			String databaseEnvironment = childElement.getAttribute("environment");
			
			String host = childElement.getElementsByTagName("host").item(0).getTextContent();
			int port = Integer.parseInt(childElement.getElementsByTagName("port").item(0).getTextContent());
			
			String username = childElement.getElementsByTagName("user").item(0).getTextContent();
			String password = childElement.getElementsByTagName("pass").item(0).getTextContent();
			
			databaseConfig.put("dbname", databaseName);
			databaseConfig.put("host", host);
			databaseConfig.put("port", port);
			databaseConfig.put("username", username);
			databaseConfig.put("password", password);
			
			databaseConfigs.put(databaseEnvironment, databaseConfig);
		}
		
		return databaseConfigs;
	}
	
	// TODO: add check if HashMap is returned here.
	public HashMap<String, Object> getDatabaseByEnvironment(String name){
		HashMap<String, Object> databaseEnvironment = (HashMap<String, Object>) this.config.get("database");
		return (HashMap<String, Object>) databaseEnvironment.get(name);
	}

}
