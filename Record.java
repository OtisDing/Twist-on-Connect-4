//Made by Otis Ding
//Student ID: 251220811
//This class creates a Record object

public class Record {
	
	
	//Initialize instanced variables
	private String key;
	private int score, level;
	
	//Constructor, initializes instanced variables
	public Record(String key, int score, int level) {
		
		this.key = key;
		this.score = score;
		this.level = level;
		
	}
	
	//Get method, returns the key string associated with the Record object
	public String getKey() {
		return this.key;
	}
	
	//Get method, returns the score integer associated with the Record object
	public int getScore() {
		return this.score;
	}
	
	//get method, returns the level integer associated with the Record object
	public int getLevel() {
		return this.level;
	}
	
}
