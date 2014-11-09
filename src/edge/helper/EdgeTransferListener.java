package edge.helper;

import javafx.scene.control.ProgressBar;
import javafx.scene.text.Text;
import it.sauronsoftware.ftp4j.FTPDataTransferListener;

public class EdgeTransferListener implements FTPDataTransferListener {

	public void started() {
		// Transfer started
	}
	
	private Text speedText;
	private ProgressBar progressBar;
	
	public void setSpeed(Text speedText){
		this.speedText = speedText;
	}
	
	public void setProgressBar(ProgressBar pb){
		this.progressBar = pb;
		this.progressBar.setProgress(this.progressBar.INDETERMINATE_PROGRESS);
	}

	public void transferred(int length) {
		this.speedText.setText(Math.round(length / 1024) + "kBit/s");
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