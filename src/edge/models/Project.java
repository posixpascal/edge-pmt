package edge.models;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;



@Entity
@Table
public class Project {
	@Id
	@GeneratedValue
	private Long id;
	
	private String name;
	
	public Project(){}
	
	public String getName(){
			return name;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setName(String name){
		this.name = name;
	}
}
