package edge.helper;

import java.io.File;

/**
 * A simple interface which will be used in the future
 * to build flexible upload engines (FTP, SCP, Dropbox for example).
 * 
 * As long as the engine follows this little spec, EdgeFTP is able
 * to use the engine without any configurations. 
 */
public interface EdgeUploader {
	
	public void setFile(File file);
	public File getFile();
	
	public void upload();
	
}
