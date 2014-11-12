package edge.controllers;

import it.sauronsoftware.ftp4j.FTPAbortedException;
import it.sauronsoftware.ftp4j.FTPClient;
import it.sauronsoftware.ftp4j.FTPDataTransferException;
import it.sauronsoftware.ftp4j.FTPException;
import it.sauronsoftware.ftp4j.FTPIllegalReplyException;

import java.io.File;
import java.io.IOException;

import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ProgressBar;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import org.apache.commons.io.FilenameUtils;

import edge.helper.EdgeFTP;
import edge.helper.EdgeSession;
import edge.logic.Database;
import edge.models.FTPFiles;
import edge.models.User;

public class UploadController extends BaseController {
	private File fileToBeUploaded;
	
	
	@FXML
	private void cancelUpload(){};
	
	public File getFile() {
		return fileToBeUploaded;
	}



	public void setFile(File fileToBeUploaded) {
		this.fileToBeUploaded = fileToBeUploaded;
	}

	@FXML
	private Text filenameText;
	
	@FXML
	private Text sizeText;
	
	@FXML
	private ProgressBar progressBar;

	@FXML
	private void initialize(){
		User activeUser = EdgeSession.getActiveUser();
		
		filenameText.setText(this.fileToBeUploaded.getName());
		progressBar.setProgress(ProgressBar.INDETERMINATE_PROGRESS);
		
		Runnable uploadMethod = () -> {
			EdgeFTP ftp = new EdgeFTP(activeUser);
			ftp.setFile(this.fileToBeUploaded);
			ftp.upload();
			FTPFiles ftpFile = new FTPFiles();
			ftpFile.setFileName(fileToBeUploaded.getName());
			ftpFile.setProject(((ProjectViewController) this.getParent()).getProject());
			ftpFile.setUser(EdgeSession.getActiveUser());
			ftpFile.setSize(fileToBeUploaded.length());
			String extension = FilenameUtils.getExtension(fileToBeUploaded.getAbsolutePath());
			ftpFile.setPreview(imageToByteArray(fromFileExtension(extension)));
			ftpFile.setUrl(EdgeSession.getActiveUser().getSettingFor("ftp_path", null).getStringValue());
			Database.saveAndCommit(ftpFile);

			
			EdgeSession.getActiveUser().getFtpFiles().add(ftpFile);
			Database.saveAndCommit(ftpFile);
			Database.saveAndCommit(((ProjectViewController) this.getParent()).getProject());
			Database.saveAndCommit(EdgeSession.getActiveUser());
			((ProjectViewController) this.getParent()).addFileToGrid(ftpFile);
			
			Stage s = (Stage) this.filenameText.getScene().getWindow();
			s.close();
		};
		
		Thread uploadThread = new Thread(uploadMethod);
		uploadThread.start();
		
		
		
		
	}
}
