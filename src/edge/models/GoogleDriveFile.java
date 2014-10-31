package edge.models;

import java.util.Date;

import javax.persistence.*;

import org.hibernate.*;
import org.hibernate.Query;

@Entity
@Table
public class GoogleDriveFile {
	@Id
	@GeneratedValue
	private Integer id;
	
	private String url;
	private String fileName;
	
	private Date created;
	private Date modified;
	
	@PrePersist
	protected void onCreate(){
		created = new Date();
	}
	
	@PreUpdate
	protected void onUpdate(){
		modified = new Date();
	}
	
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getUrl() {
		return url;
	}
	
	public void setUrl(String url) {
		this.url = url;
	}
	
	public String getFileName() {
		return fileName;
	}
	
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
}
