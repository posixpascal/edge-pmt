package edge.helper;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import org.apache.commons.io.FilenameUtils;
import edge.models.User;
import it.sauronsoftware.ftp4j.FTPAbortedException;
import it.sauronsoftware.ftp4j.FTPClient;
import it.sauronsoftware.ftp4j.FTPDataTransferException;
import it.sauronsoftware.ftp4j.FTPException;
import it.sauronsoftware.ftp4j.FTPFile;
import it.sauronsoftware.ftp4j.FTPIllegalReplyException;
import it.sauronsoftware.ftp4j.FTPListParseException;

/**
 * Interface implementation of the EdgeUploader class.
 * This class handles file uploads using FTP.
 * 
 * The class will check the database for the user's FTP settings
 * and tries to connect to the FTP server.
 */
public class EdgeFTP implements EdgeUploader {
	/**
	 * the prefix every temporary folder gets.
	 * the temporary folder name is generated like this:
	 * <tt>&lt;prefix&gt;-&lt;unix timestamp&gt;
	 */
	private final String tempPrefix = "edge-ftp-";
	
	private String host;
	private String port;
	private String user;
	private String pass;
	
	private FTPClient client;
	
	/**
	 * @constructor
	 * @param host the ftp server host address (IP addresses are supported too)
	 * @param port String the port where the FTP client runs. (defaults to 21 when null)
	 * @param user the user which EdgePMT will use for login on the server side 
	 * @param pass the password of the user
	 */
	public EdgeFTP(String host, String port, String user, String pass){
		this.host = host;
		this.port = (port == null ? "21" : port);
		this.user = user;
		this.pass = pass;
		this.client = new FTPClient();
	}
	
	/**
	 * Constructs a new EdgeFTP instance with an user object.
	 * This constructor tries to get the required FTP settings from the user's
	 * config.
	 * @param user Any user object which has valid FTP settings stored in the database
	 */
	public EdgeFTP(User user){
		this.host = user.getSettingFor("ftp_host", null).getStringValue();
		this.port = user.getSettingFor("ftp_port", null).getStringValue();
		this.user = user.getSettingFor("ftp_user", null).getStringValue();
		this.pass = user.getSettingFor("ftp_pass", null).getStringValue();
		this.client = new FTPClient();
	}
	
	/**
	 * this method tries to connect and login to the FTP server using the given credentials.
	 * @return true if connection went successfully or false if not.
	 */
	public boolean checkConnection(){
		try {
			client.connect(this.getHost(), this.getPort());
			client.login(this.getUser(), this.getPass());
			client.disconnect(true);
			return true;
		} catch (IllegalStateException | IOException | FTPIllegalReplyException
				| FTPException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * Returns the username of the ftp server
	 * @return string representing the username which EdgeFTP will use to login.
	 */
	public String getUser() {
		return user;
	}

	/**
	 * Sets the username of the ftp server
	 * @param user any valid username as String
	 */
	public void setUser(String user) {
		this.user = user;
	}


	/**
	 * Returns the password credentials of the ftp server
	 * @return string of the password
	 */
	public String getPass() {
		return pass;
	}

	/**
	 * sets the password used to authenticate at the server side
	 * @param pass
	 */
	public void setPass(String pass) {
		this.pass = pass;
	}

	/**
	 * returns the host which EdgeFTP will use to connect to
	 * @return a string representing the hostname
	 */
	private String getHost(){
		return this.host;
	}

	/**
	 * returns the port of the FTP server as an integer
	 * @return an integer representing the port of the ftp server
	 */
	private int getPort() {
		return Integer.parseInt(this.port);
	}

	/**
	 * connects and login to the ftp server
	 */
	public void connect(){
		try {
			this.client.connect(this.getHost(), this.getPort());
			this.client.login(this.getUser(), this.getPass());
		} catch (IllegalStateException | IOException | FTPIllegalReplyException
				| FTPException e) {
			e.printStackTrace();
		}
		
	}

	/**
	 * receives the current FTPClient instance
	 * @return a FTP client instance
	 */
	public Object getClient() {
		return this.client;
		
	}
	
	/**
	 * the file which EdgeFTP will upload
	 */
	private File file;
	
	/**
	 * Sets the file which will be uploaded
	 */
	@Override
	public void setFile(File file) {
		this.file = file;
		
	}

	/**
	 * returns the file which will be uploaded
	 */
	@Override
	public File getFile() {
		return this.file;
		
	}

	/**
	 * starts the upload process
	 */
	@Override
	public void upload() {
		/**
		 * used to count how many times the upload cycle tried
		 * to create the file but it already existed.
		 * this means the upload method will add a version-number of the file 
		 * to the actual file name.
		 * 
		 * if <tt>test.xml</tt> exists, the method will try to upload:
		 * <tt>test2.xml</tt> instead.
		 * 
		 * we start with 2 because if a file
		 */
		int incrementCounter = 2;
		
		FTPClient ftp = ((FTPClient) this.getClient());
		try {
			ftp.connect(this.getHost(), this.getPort());
			ftp.login(this.getUser(), this.getPass());
			
			String fileName = this.getFile().getName();
						
			while (fileExists(fileName)){
				fileName = _addVersionToFileName(this.getFile(), incrementCounter++);
			}

			/**
			 * create a temporary directory to avoid file overwriting in case
			 * a filename is already present in the current directory.
			 * our ftp library (ftp4j) does not provide file renaming (at least their docs doesn't).
			 */
			String timeStampString = "" + new Date().getTime();
			ftp.createDirectory(this.tempPrefix + timeStampString);
			ftp.changeDirectory(this.tempPrefix + timeStampString);
			
			/**
			 * uploads the file and renames it to the new filename while also storing it in the ftps
			 * <tt>home directory</tt>.
			 */
			ftp.upload(this.getFile());
			ftp.rename(this.tempPrefix  + timeStampString + "/" + this.getFile().getName(), "./" + fileName);
			
			/**
			 * remove the temporary folder again
			 */
			ftp.deleteDirectory(this.tempPrefix  + timeStampString);
			
		} catch (IllegalStateException | IOException | FTPIllegalReplyException
				| FTPException | FTPDataTransferException | FTPAbortedException e) {
			e.printStackTrace();
		}

	}
	
	/**
	 * checks if the fileExists in the current directory
	 * @param fileName a fileName to look for
	 * @return true if the fileExists or false if not.
	 */
	private boolean fileExists(String fileName){
		for (FTPFile file :  this.listFiles()){
			if (file.getName().equals(fileName)){
				return true;
			}
		}
		
		return false;
	}
	
	/**
	 * gets a list of files in the current directory.
	 * reports error messages when EdgeFTP couldn't obtain
	 * the filelist is obtained every cycle. be aware of
	 * possible application hang ups because of high cpu & network usage.
	 * @return a FTPFile[] array of files in the current working directory
	 */
	private FTPFile[] listFiles(){
		FTPFile[] result = new FTPFile[0];
		FTPClient ftp = ((FTPClient) this.getClient());
		try {
			result = ftp.list();
		} catch (FTPListParseException | IllegalStateException | IOException | FTPIllegalReplyException | FTPException | FTPDataTransferException | FTPAbortedException e) {
			EdgeError.alert("FTP Fehler aufgetreten.", "EDGE FTP konnte keine Dateiliste beim FTP Server anfragen.");
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * adds a version number to the fileName.
	 * if the user wants to upload <tt>test.csv</tt> but
	 * a test.csv file already exists in the current directory
	 * EdgeFTP will rename the file to: <tt>test-2.csv</tt>
	 * EdgeFTP will increment the counter until a suitable name was found
	 * and returns the new fileName afterwards
	 * @param file the filename which has a valid extension
	 * @param version the versioning of the file
	 * @return the new fileName with the right version attached.
	 */
	private String _addVersionToFileName(File file, int version) {
		String fileName = file.getName();
		String ext = FilenameUtils.getExtension(file.getAbsolutePath());
		String tempFileName = fileName.replaceAll(ext, "");
		tempFileName = tempFileName + "-" + version + ext;
		
		return tempFileName;
	}
}
