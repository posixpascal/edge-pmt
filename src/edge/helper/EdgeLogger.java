package edge.helper;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;

import edge.logic.MainApplication;


/**
 * The <tt>EdgeLogger</tt> provides a neat interface to collect log data.
 * Logs are useful in case when the application suddenly hangs up or struggles
 * with situations we have not prepared for (a hdd crash for example).
 * 
 * The logger receives a string containing the actual file to log in the constructor.
 * check the <tt>EdgeLogger JavaDoc</tt> for further examples.
 */
public class EdgeLogger {
	private File logFile = null;
	private FileWriter logFileWriter = null;
	private int logCycle = 0;
	
	public EdgeLogger(String logFile) {
		File f = new File(logFile);
		if (!f.exists()){
			try {
				f.createNewFile();
			} catch (IOException e) {
				System.err.println("Konnte LogDatei nicht erstellen. Beende...");
				e.printStackTrace();
				System.exit(-1);
			}
		}
		try {
			this.logFileWriter = new FileWriter(f);
		} catch (IOException e) {
			System.err.println("Konnte LogDatei nicht zum Schreiben öffnen. Bitte überprüfen Sie die Rechte...");
			e.printStackTrace();
			System.exit(-1);
		}
		this.setLogFile(f);
	}
	
	/**
	 * Logs a message to the logFile.
	 * This method is used as an alias when no controller is specified.
	 * It defaults <tt>controller</tt> to MainApplication
	 * SuppressedWarning because we haven't had the chance to use this method yet.
	 * @param message the actual message which should be logged to the file.
	 */
	@SuppressWarnings("unused")
	private void log(String message){
		log(null, message);
	}
	
	/**
	 * Logs a message to the logFile.
	 * The logString is built using the following schema:
	 * <tt>[current time][current controller][current package][current logcycle]: message</tt>
	 * @param controller the controller class which fired the log method (defaults to MainApplication)
	 * @param message the actual message which should be logged to the file.
	 */
	public void log(Class<?> controller, String message){
		if (controller == null) { controller = MainApplication.class; }
		
		StringBuilder logMessage = new StringBuilder();
		logMessage.append("[" + new Date().toString() + "]");
		logMessage.append("[" + controller.getName() + "]");
		logMessage.append("[" + controller.getPackage().getName() + "]");
		logMessage.append("[" + this.logCycle++ + "]");
		logMessage.append(": " + message + "\n");
		
		try {
			this.logFileWriter.write(logMessage.toString());
		} catch (IOException e) {
			System.err.println("Konnte nicht zur LogDatei schreiben. Bitte überprüfen Sie die Schreibrechte...");
			e.printStackTrace();
		}
	}

	public File getLogFile() {
		return logFile;
	}

	public void setLogFile(File logFile) {
		this.logFile = logFile;
	}
	
	

}
