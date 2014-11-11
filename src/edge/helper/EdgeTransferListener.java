package edge.helper;

import java.util.Properties;

import javafx.scene.control.ProgressBar;
import javafx.scene.text.Text;
import it.sauronsoftware.ftp4j.FTPDataTransferListener;

public class EdgeTransferListener implements FTPDataTransferListener {

	public void started() {
		// Transfer started
	}
	
	private Text speedText;
	private ProgressBar progressBar;
	private Properties valueProperty;
	
	public void setSpeed(Text speedText){
		this.speedText = speedText;
	}
	
	public void setValueProperty(Properties valueProperty){
		this.valueProperty = valueProperty;
	}
	
	public void setProgressBar(ProgressBar pb){
		this.progressBar = pb;
		this.progressBar.setProgress(this.progressBar.INDETERMINATE_PROGRESS);
	}

	public void transferred(int length) {
		this.speedText.setText(Math.round(length / 1024) + "kBit/s");
		this.valueProperty.setProperty("speed", "" + Math.round(length / 1024));
	}

	public void completed() {
		this.speedText.setText("hochgeladen.");
		this.progressBar.setProgress(0.0);
	}

	public void aborted() {
		// Transfer aborted
	}

	public void failed() {
		// Transfer failed
	}


}