package edge.models;

import java.util.Date;

import javax.persistence.*;

import org.hibernate.*;
import org.hibernate.Query;

@Entity
@Table
public class UserRole extends BaseModel {
	@Id
	@GeneratedValue
	private Integer id;
	
	private Boolean canCreateProjects;
	private Boolean canCreateUsers;
	private Boolean canCreateTodos;
	private Boolean isSuperUser;
	private Boolean canUploadFiles;
	private Boolean canAssignTodosToOthers;
	private Boolean canCreateMilestones;
	private Boolean canCloseTodos;
	private Boolean canComment;
	private Boolean canDeleteProjects;
	
	public Boolean getCanCreateProjects() {
		return canCreateProjects;
	}

	public void setCanCreateProjects(Boolean canCreateProjects) {
		this.canCreateProjects = canCreateProjects;
	}

	public Boolean getCanCreateUsers() {
		return canCreateUsers;
	}

	public void setCanCreateUsers(Boolean canCreateUsers) {
		this.canCreateUsers = canCreateUsers;
	}

	public Boolean getCanCreateTodos() {
		return canCreateTodos;
	}

	public void setCanCreateTodos(Boolean canCreateTodos) {
		this.canCreateTodos = canCreateTodos;
	}

	public Boolean getIsSuperUser() {
		return isSuperUser;
	}

	public void setIsSuperUser(Boolean isSuperUser) {
		this.isSuperUser = isSuperUser;
	}

	public Boolean getCanUploadFiles() {
		return canUploadFiles;
	}

	public void setCanUploadFiles(Boolean canUploadFiles) {
		this.canUploadFiles = canUploadFiles;
	}

	public Boolean getCanAssignTodosToOthers() {
		return canAssignTodosToOthers;
	}

	public void setCanAssignTodosToOthers(Boolean canAssignTodosToOthers) {
		this.canAssignTodosToOthers = canAssignTodosToOthers;
	}

	public Boolean getCanCreateMilestones() {
		return canCreateMilestones;
	}

	public void setCanCreateMilestones(Boolean canCreateMilestones) {
		this.canCreateMilestones = canCreateMilestones;
	}

	public Boolean getCanDeleteProjects() {
		return canDeleteProjects;
	}

	public void setCanDeleteProjects(Boolean canDeleteProjects) {
		this.canDeleteProjects = canDeleteProjects;
	}

	public Boolean getCanCloseTodos() {
		return canCloseTodos;
	}

	public void setCanCloseTodos(Boolean canCloseTodos) {
		this.canCloseTodos = canCloseTodos;
	}

	public Boolean getCanComment() {
		return canComment;
	}

	public void setCanComment(Boolean canComment) {
		this.canComment = canComment;
	}


	
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
}
