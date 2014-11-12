package edge.models;

public abstract class BaseModel {
	
	/**
	 * wip: unused at the moment.
	 * TODO: dump the model to the console/log file
	 *
	 */
	public void dump(){
		
	}
	
	/**
	 * returns the models Classname as string
	 */
	public String toString(){
		return this.getClass().getName();
	}
}
