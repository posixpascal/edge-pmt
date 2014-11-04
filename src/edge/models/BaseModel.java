package edge.models;
import java.lang.reflect.*;
import java.util.Arrays;

public class BaseModel {
	
	/**
	 * 
	 */
	public void dump(){
		
	}
	
	/**
	 * returns the models classname as string
	 */
	public String toString(){
		return this.getClass().getName();
	}
}
