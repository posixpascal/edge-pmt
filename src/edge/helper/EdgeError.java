package edge.helper;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

/**
 * This class is used to report error messages to the user
 * while also logging errors to a log file named "edge-pmt.log".
 * 
 * The main intend behind this class is to omit empty try/catch control structures.
 * Sometimes java throws weird generic exceptions which are hard to handle or 
 * where no need to handle any error exists, in these cases EdgeError comes into play
 * and gives a nice (rather unobstrusive) feedback what happens and what possibilities
 * the user has to work around the error.
 * 
 * the interface of this class is completely static, so no instance is needed.
 */

public class EdgeError {
	public static final EdgeLogger edgeLogger = new EdgeLogger("edge-pmt.log");
	
	/**
	 * alias to System.exit.
	 * This method exits the java application and returns an error-code.
	 */
	private static void _exit(){
		System.exit(-1);
	}
	
	/**
	 * Alias to alertAndExit where the subtitle defaults to an empty string.
	 * After the dialog was accepted by the user, the application will exit itself.
	 * @param title the title used for the alert.
	 * @param content the message the user will read.
	 */
	public static void alertAndExit(String title, String content){
		alertAndExit(title, null, content);
	}
	
	/**
	 * An alias to alert where the subtitle defaults to an empty string.
	 * After the dialog was accepted by the user, the application will continue to run.
	 * @param title the title used for the alert
	 * @param content the message the user will read.
	 */
	public static void alert(String title, String content){
		alert(title, null, content);
	}
	
	/**
	 * Displays an alert box to the user which halts the whole application while the Alert is visible.
	 * The user has to click on the "OK" button to resume to his normal operations.
	 * This method is used to represent error messages to the user..
	 * also logs the message to a logfile
	 * @param title the title used for the alert
	 * @param subtitle the subtitle the user willsee (defaults to empty string)
	 * @param content the actual message
	 */
	public static void alert(String title, String subtitle, String content){
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle(title);
		alert.setHeaderText((subtitle == null ? "" : subtitle));
		alert.setContentText(content);
		alert.showAndWait();
		
		EdgeError.edgeLogger.log(EdgeError.class, content);
	}
	
	/**
	 * This method displays an alert box to the user which halts the whole application while the
	 * alert is visible. The user has to click on the "OK" button to let the application continue.
	 * This method also exits the application after the user clicked on the OK button and is
	 * mainly used to display fatal error message which the app itself can't handle.
	 * @see alert
	 * @param title the title used for the alert
	 * @param subtitle an optional subtitle which defaults to an empty string
	 * @param content the message the user will see.
	 */
	public static void alertAndExit(String title, String subtitle, String content){
		alert(title, subtitle, content);
		_exit();
	}
}
