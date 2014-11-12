package edge.helper;

import java.util.ArrayList;
import java.util.List;
import javafx.scene.control.TextField;

/**
 * We're sub-classing TextField & PasswordField to add custom error/success
 * states to any of those fields. One can use this class to prevent redundant
 * code. for example:
 * 
 * 	EdgeTextField usernameField = new EdgeTextField();
 * 	usernameField.setState(EdgeTextField.STATE_ERROR);
 * 
 * The .setState method removes any previously added states in favor of the new state.
 * In former code we had to write something like this:
 * 
 * 	TextField usernameField = new TextField();
 * 	usernameField.getStyleClasses().remove("success");
 *  usernameField.getStyleClasses().add("error");
 *  
 * This repeating grows exponential by the number of fields.
 *  
 * We'll also add neat functions to display different stuff in the future.
 * 
 *
 */
public class EdgeTextField extends TextField {
	@SuppressWarnings("unused")
	private final String STATE_ERROR = "state-error";
	
	@SuppressWarnings("unused")
	private final String STATE_SUCCESS = "state-success";
	
	@SuppressWarnings("unused")
	private final String STATE_WARNING = "state-warning";
	
	@SuppressWarnings("unused")
	private final String STATE_DEFAULT = "";
	
	public EdgeTextField(){
		super();
	}
	
	/**
	 * Sets the state of the application
	 * @param state
	 * @return
	 */
	public boolean addState(EdgeState state){
		String stateClass = "";
		
		try {
			
			stateClass = this.getProperties().get(state.name()).toString();
			this.getStyleClass().removeAll(getStateClasses());
			this.getStyleClass().add(stateClass);
			
		} catch (SecurityException e) {
			// thrown when a state doesn't exist.
			EdgeError.alertAndExit("Fataler Fehler", "Die Anwendung versuchte auf ein State zuzugreifen, welcher nicht unterstützt wird.");
		}
		
		return true;
	}
	
	/**
	 * Returns a list of all known EdgeStates. 
	 * This method is used to clear every state before setting a different state.
	 * @return A list of every state Edge PMT knows about.
	 */
	private List<String> getStateClasses(){
		List<String> classes = new ArrayList<String>();
		for (int i = 0; i < EdgeState.values().length; i++){
			classes.add(this.getProperties().get(EdgeState.values()[0].name()).toString());
		}
		return classes;
	}

}
