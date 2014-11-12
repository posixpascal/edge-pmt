package edge.helper;

/**
 * This enum represents every known state a field can have.
 */
public enum EdgeState {
	/**
	 * Green background-color, dark green border. 
	 */
	STATE_SUCCESS,
	
	/**
	 * Red background-color, dark red border.
	 */
	STATE_ERROR,
	
	/**
	 * Yellow background-color, orange border
	 */
	STATE_WARNING,
	
	/**
	 * javafx default style (white background, gray border)
	 */
	STATE_DEFAULT
}
