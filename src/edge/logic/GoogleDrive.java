package edge.logic;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.java6.auth.oauth2.FileCredentialStore;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.media.MediaHttpDownloader;
import com.google.api.client.googleapis.media.MediaHttpUploader;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.InputStreamContent;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import com.google.api.services.drive.model.File;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;


public class GoogleDrive {
	private static final String UPLOAD_FILE_PATH = "edge-pmt/";
	private static final String DIR_FOR_DOWNLOADS = "downloads/";
	private static final java.io.File UPLOAD_FILE = new java.io.File(UPLOAD_FILE_PATH);
	
	private static final HttpTransport HTTP_TRANSPORT = new NetHttpTransport();
	private static final JsonFactory JSON_FACTORY = new JacksonFactory();
	
	private static Drive drive;
	
	public Credential authorize() throws Exception {
		GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(
			JSON_FACTORY, new InputStreamReader(GoogleDrive.class.getResourceAsStream("./google_drive_auths.json")));
		
		FileCredentialStore credentialStore = new FileCredentialStore(
			new java.io.File("src/edge/logic/google_drive_auth.json"), JSON_FACTORY);	
		
		GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
				HTTP_TRANSPORT, JSON_FACTORY, clientSecrets,
				Collections.singleton(DriveScopes.DRIVE_FILE)).setCredentialStore(credentialStore).build();
		
		return new AuthorizationCodeInstalledApp(flow, new LocalServerReceiver()).authorize("user");	
	}
	
	public File uploadFile(java.io.File uploadFile, boolean useDirectUpload) throws IOException{
		File fileMetadata = new File();
		
		fileMetadata.setTitle(uploadFile.getName());
		
		InputStreamContent mediaContent = new InputStreamContent(
				Files.probeContentType(Paths.get(uploadFile.getPath())),
				new BufferedInputStream(
						new FileInputStream(uploadFile)
				));
				
		Drive.Files.Insert insert = drive.files().insert(fileMetadata, mediaContent);
		MediaHttpUploader uploader = insert.getMediaHttpUploader();
		uploader.setDirectUploadEnabled(useDirectUpload);
		
		return insert.execute();
	}
	
	public void downloadFile(File uploadedFile, boolean useDirectDownload) throws IOException {
		java.io.File parentDir = new java.io.File(DIR_FOR_DOWNLOADS);
		if (!parentDir.exists() && !parentDir.mkdirs()){
			throw new IOException("Keine Ordner für Downloads angegeben.");
		}
		
		OutputStream out = new FileOutputStream(new java.io.File(parentDir, uploadedFile.getTitle()));
		
		Drive.Files.Get get = drive.files().get(uploadedFile.getId());
		MediaHttpDownloader downloader = get.getMediaHttpDownloader();
		downloader.setDirectDownloadEnabled(useDirectDownload);
		downloader.download(new GenericUrl(uploadedFile.getDownloadUrl()), out);
	}
}
