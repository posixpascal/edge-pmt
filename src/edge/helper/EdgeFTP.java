package edge.helper;

import java.io.File;
import java.io.IOException;

import edge.models.User;
import it.sauronsoftware.ftp4j.FTPAbortedException;
import it.sauronsoftware.ftp4j.FTPClient;
import it.sauronsoftware.ftp4j.FTPDataTransferException;
import it.sauronsoftware.ftp4j.FTPException;
import it.sauronsoftware.ftp4j.FTPIllegalReplyException;

public class EdgeFTP implements EdgeUploader {
	private String host;
	private String port;
	private String user;
	private String pass;
	
	private FTPClient client;
	
	public EdgeFTP(String host, String port, String user, String pass){
		this.host = host;
		this.port = port;
		this.user = user;
		this.pass = pass;
		this.client = new FTPClient();
	}
	
	public EdgeFTP(User user){
		this.host = user.getSettingFor("ftp_host", null).getStringValue();
		this.port = user.getSettingFor("ftp_port", null).getStringValue();
		this.user = user.getSettingFor("ftp_user", null).getStringValue();
		this.pass = user.getSettingFor("ftp_pass", null).getStringValue();
		this.client = new FTPClient();
	}
	
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
	
	public String getUser() {
		return user;
	}


	public void setUser(String user) {
		this.user = user;
	}


	public String getPass() {
		return pass;
	}


	public void setPass(String pass) {
		this.pass = pass;
	}


	private String getHost(){
		return this.host;
	}

	private int getPort() {
		return Integer.parseInt(this.port);
	}

	public void connect(){
		try {
			this.client.connect(this.getHost(), this.getPort());
			this.client.login(this.getUser(), this.getPass());
		} catch (IllegalStateException | IOException | FTPIllegalReplyException
				| FTPException e) {
			e.printStackTrace();
		}
		
	}

	public Object getClient() {
		return this.client;
		
	}

	private File file;
	
	@Override
	public void setFile(File file) {
		this.file = file;
		
	}

	@Override
	public File getFile() {
		return this.file;
		
	}

	@Override
	public void upload() {
		try {
			((FTPClient) this.getClient()).connect(this.getHost(), this.getPort());
			((FTPClient) this.getClient()).login(this.getUser(), this.getPass());
			((FTPClient) this.getClient()).upload(this.getFile());
		} catch (IllegalStateException | IOException | FTPIllegalReplyException
				| FTPException | FTPDataTransferException | FTPAbortedException e) {
			e.printStackTrace();
		}

	}
}
