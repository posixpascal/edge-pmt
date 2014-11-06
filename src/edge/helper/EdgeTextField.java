package edge.helper;

import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;

/**
 * I'm sub-classing TextField & PasswordField to add custom error/success
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
 * I'll also add neat functions to display different stuff in the future.
 * 
 *
 */
public class EdgeTextField extends TextField {
	private final String STATE_ERROR = "state-error";
	private final String STATE_SUCCESS = "state-success";
	private final String STATE_WARNING = "state-warning";
	private final String STATE_DEFAULT = "";
	
	public EdgeTextField(){
		
	}
	
	public boolean addState(EdgeState state){
		String stateClass = "";
		try {
			stateClass = this.getProperties().get(state.name()).toString();
		} catch (SecurityException e) {
			System.out.println("[EdgeTextField]: Unknown state: " + state.name());
			return false;
		}
		
		return true;
	}
	

}
