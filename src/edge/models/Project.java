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
	
	public void setName(String name){
		this.name = name;
	}
}
