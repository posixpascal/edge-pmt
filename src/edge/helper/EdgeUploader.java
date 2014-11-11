package edge.helper;

import java.io.File;

public interface EdgeUploader {
	
	public void setFile(File file);
	public File getFile();
	
	public void upload();
	
}
