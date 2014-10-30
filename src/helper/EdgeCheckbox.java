package helper;

import javafx.event.ActionEvent;
import javafx.scene.control.CheckBox;

// FIXME: Wie wäre es wenn wir die CheckBox subclassen 
// dann könnteten wir mit stateChanged 2 events auslösen:
// wenn die checkbox aktiv ist
// - beispielsweise: ausgrauen & in db schreiben
// wenn die checkbox nicht aktiv ist
// - wieder aktivieren (sofern sie ausgegraut war) you know.

public class EdgeCheckbox extends CheckBox {
	public void stateChanged(ActionEvent checked, ActionEvent unchecked){
		
	}
}
