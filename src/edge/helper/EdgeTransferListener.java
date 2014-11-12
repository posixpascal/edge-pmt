package edge.helper;

import java.util.Properties;

import javafx.scene.control.ProgressBar;
import javafx.scene.text.Text;
import it.sauronsoftware.ftp4j.FTPDataTransferListener;

/**
 * Reports the upload process to the caller.
 * just a simple wrapper around the FTPDataTransferListener
 */
public class EdgeTransferListener implements FTPDataTransferListener {
	
	/**
	 * not used.
	 */
	public void started() {
		// Transfer started
	}
	
	private Text speedText;
	private ProgressBar progressBar;
	private Properties valueProperty;
	
	/**
	 * Sets the speed label text for further reference
	 * @param speedText the javafx.scene.Text object
	 */
	public void setSpeed(Text speedText){
		this.speedText = speedText;
	}
	
	/**
	 * Sets the current value of the speed
	 * @param valueProperty a valid Property
	 */
	public void setValueProperty(Properties valueProperty){
		this.valueProperty = valueProperty;
	}
	
	/**
	 * Sets the current progressbar
	 * @param pb any javafx.scene.ProgressBar object
	 */
	public void setProgressBar(ProgressBar pb){
		this.progressBar = pb;
		this.progressBar.setProgress(ProgressBar.INDETERMINATE_PROGRESS);
	}
	
	/**
	 * called after every chunk the FTP client uploads
	 * this method is used to calculate the download speed.
	 * note: it's not 100% precisely
	 * this also sets the speedText.
	 */
	public void transferred(int length) {
		this.speedText.setText(Math.round(length / 1024) + "kBit/s");
		this.valueProperty.setProperty("speed", "" + Math.round(length / 1024));
	}

	/**
	 * called when the upload is completed.
	 */
	public void completed() {
		this.speedText.setText("hochgeladen.");
		this.progressBar.setProgress(0.0);
	}
	
	/**
	 * not used.
	 */
	public void aborted() {
	}

	
	/**
	 * not used.
	 */
	public void failed() {
	}


}