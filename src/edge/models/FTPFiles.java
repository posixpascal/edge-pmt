package edge.models;

import java.util.Date;
import javax.persistence.*;


@Entity
@Table
public class FTPFiles extends BaseModel {
	@Id
	@GeneratedValue
	private Integer id;
	
	private String url;
	private String fileName;
	
	private String title;
	
	/**
	 * gets the title/name of the uploaded file
	 * @return the name of the uploaded file
	 */
	public String getTitle() {
		return title;
	}
	
	private long size;
	
	
	/**
	 * gets the fileSize as long.
	 * @return the fileSize of the uploaded file
	 */
	public long getSize() {
		return this.size;
	}

	/**
	 * sets the fileSize of the uploaded file
	 * @param size a long number representing the sizue
	 */
	public void setSize(long size) {
		this.size = size;
	}

	/**
	 * sets the title/name of the uploaded file
	 * @param title the filename as string
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	
	/**
	 * gets the uploader of the file
	 * @return a user object whoever uploaded the file
	 */
	public User getUser() {
		return user;
	}

	/**
	 * sets the uploader of the file
	 * @param user any edge.model.User object
	 */
	public void setUser(User user) {
		this.user = user;
	}

	/**
	 * gets the project where the uploaded file belongs to
	 * @return a Project instance of the project which belongs to the uploaded file
	 */
	public Project getProject() {
		return project;
	}
	
	/**
	 * sets the project where the uploaded file belongs to
	 * @param project any valid project object
	 */
	public void setProject(Project project) {
		this.project = project;
	}

	/**
	 * returns a preview icon of the uploaded file as a byteArray
	 * @return a byte array representing the preview icon
	 */
	public byte[] getPreview() {
		return preview;
	}
	
	/**
	 * sets the preview icon of the uploaded file
	 * @param preview a byteArray containing the preview icon 
	 */
	public void setPreview(byte[] preview) {
		this.preview = preview;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="user_id", nullable=false)
	private User user;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="project_id", nullable=false)
	private Project project;
	
	@Column(name="preview", columnDefinition="mediumblob")
	private byte[] preview;
	
	private Date created;
	private Date modified;
	
	@PrePersist
	protected void onCreate(){
		setCreated(new Date());
	}
	
	@PreUpdate
	protected void onUpdate(){
		setModified(new Date());
	}
	
	/**
	 * gets the id of the file
	 * @return the id of the uploaded file as an integer
	 * TODO: maybe we should use long here.
	 */
	public Integer getId() {
		return id;
	}
	
	/**
	 * sets the ID of the file
	 * @param id the database ID as an integer
	 */
	public void setId(Integer id) {
		this.id = id;
	}
	
	/**
	 * sets the filePath of the uploaded file.
	 * the filePath should be relative to the FTP servers root.
	 * 
	 * for example:
	 * the FTP server offers <tt>testuser</tt> an FTP account which points
	 * to the directory: /home/users
	 * after the user logs in, he sees <tt>/home/users</tt> as his <tt>project</tt> root (which is /
	 * relative to the homedir. the user uploads a file called: myFile.csv
	 * the filePath of the file would be: <tt>/myFile.csv</tt>
	 * note: the real filepath would be <tt>/home/users/myFile.csv</tt>
	 * but the EdgeFTP uses relative parts.
	 * @return the relative path of the file
	 */
	public String getUrl() {
		return url;
	}
	
	/**
	 * sets the filePath of the file (relative to the uploaded users FTP home directory)
	 * @param url
	 */
	public void setUrl(String url) {
		this.url = url;
	}
	
	/**
	 * gets the fileName of the uploaded file
	 * @return a string representing the uploaded file's name
	 */
	public String getFileName() {
		return fileName;
	}
	
	/**
	 * sets the uploaded file's name
	 * @param fileName a string representing the file's name
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public Date getModified() {
		return modified;
	}

	public void setModified(Date modified) {
		this.modified = modified;
	}

}
