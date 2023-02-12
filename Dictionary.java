//Written by Otis Ding
//Student ID: 251220811
//This program creates a Dictionary object by implementing the given Dictionary ADT, it also includes a hash function

public class Dictionary implements DictionaryADT{
	
	//Initialize instanced variable
	
	//A linear node object that would store Record objects
	private LinearNode<Record> firstRecord;
	private int size;
	//An array storing linear node objects
	private LinearNode table[];
	//Count that keeps track of how many objects are stored in the hash table
	private int count;
	
	//Constructor - initializes our instanced variables
	public Dictionary (int size) {
		//Initialize size, table, and count
		this.size = size;
		table = new LinearNode[size];
		this.count = 0;
		
		//Initializes all the values in table to null
		for (int i = 0; i < this.size; i++) {
			table[i] = null;
		}
		
	}

	//Put method - inserts a Linear Node object containing a Record object into the hash table
	public int put(Record rec) throws DuplicatedKeyException {
		
		//Creates a Linear Node object storing the parameter Record object, rec
		LinearNode<Record> recordNode = new LinearNode(rec);
		
		//Uses the hash function to find the position where we'll put the Linear Node in the hash table
		int place = hash(rec.getKey());
		
		//Creates a Linear Node object and initializes it to the location of where we're going to put recordNode
		//We're going to use this node for navigation below
		LinearNode<Record> curr = table[place];
		
		
		//Since the table[place] either contains null or another linear node object, and since we need to do stuff even if there's a collision, we use a try statement
		try {
			
			//No NullPointerException is throw if there is a collision
			//We have to insert the linear node into the linked list at table[place] if it exists
			
			//We have to make sure that no duplicate keys exist, so if there is a collision, we check the key of the Record object we collided with to make sure they're not the same
			//If the keys are the same, a DuplicatedKeyException is thrown
			Record element = (Record) table[place].getElement();
			if (element.getKey().equals(rec.getKey())) {
				throw new DuplicatedKeyException("DuplicatedKeyException");
			}
			
			//Interates over the linked list until curr.getNext() == null, so its at the end of the linked list
			while (curr.getNext() != null) {
				curr = curr.getNext();
			}
			
			//Since curr.getNext() == null, we know that we won't mess up the linked list if we insert the new node object here
			curr.setNext(recordNode);
			
			//Increase count since a new object was officially added
			this.count += 1;
			
			//Returns 1 to track the number of collisions
			return 1;
		} catch(NullPointerException e){
			
			//This block of code triggers if a NullPointerException is thrown, which means that table[place] == null, and so table[place] is empty, and there is no collision
			
			//Assigns puts recordNode into the hash table at the position of table[place]
			table[place] = recordNode;
			
			//Since a new linear node was successfully inserted, count is increased
			this.count += 1;
			
			//return 0 since no collision occured
			return 0;
		}


	}

	// remove method - removes the linear node containing the Record object with the parameter 'key' string from the hash table
	public void remove(String key) throws InexistentKeyException {
		
		//Finds where in table the linear node we're looking for is
		int place = hash(key);
		//Boolean used to keep track of if the linear node has been removed or not
		boolean removed = false;
		
		//Initializes some linear node objects
		LinearNode<Record> curr;
		LinearNode<Record> next;
		
		//sets curr to the linear node at the position we're looking for
		curr = table[place];
		
		//If curr == null, we throw the InexistentKeyException right away, since key could be nowhere else but table[place]
		if (curr == null) {
			throw new InexistentKeyException("InexistentKeyException");
		}
		
		//Sets next to the node after curr
		next = curr.getNext();
		
		//if next == null then curr is the only linear node at table[place]
		if (next == null) {
			//We set table[place] to null, thereby removing the linear node, and we decrease count accordingly
			table[place] = null;
			this.count = this.count - 1;
		}else {
			//If next is not null, then there is a linked list present
			//We iterate down the linked list until we find the Record we want to delete, or until we've checked all the elements inside
			while (next != null) {
				//Checks if next Record == the key we're looking to remove
				if (next.getElement().getKey().equals(key)) {
					//If it is, we have curr point to next.getNext(), thereby skipping next, and thus making it inaccessible, and thus deleted
					curr.setNext(next.getNext());
					//Update count and removed accordingly
					this.count = this.count - 1;
					removed = true;
				}
				//Moves curr and next down the linked list, and continues the loop
				curr = curr.getNext();
				next = next.getNext();
			}
			//Checks if removed is still false after having gone through the entire linked list
			//Throws an InexistentKeyException since key could be nowhere else but here
			if (removed = false) {
				throw new InexistentKeyException("InexistentKeyException");
			}
		}
	}

	//get method - returns the Record object with the parameter key string from the hash table
	public Record get(String key) {
		//Finds the location of the object and sets a new linear node object to it
		int place = hash(key);
		LinearNode<Record> curr = table[place];
		
		//Checks all the linear nodes at the position, and if one of them has the key we're looking for, returns the object
		while (curr != null) {
			if (curr.getElement().getKey().equals(key)) {
				return curr.getElement();
			}
			curr = curr.getNext();
		}
		
		//Returns null if no such record object exists
		return null;
		
	}

	//numRecord method - returns the total number of elements in the hash table
	public int numRecords() {
		return this.count;
	}
	
	//hash method - the hash function being used by the hash table
	private int hash(String key) {
		
		//initializes value to 0, and the prime number we're going to use, a, to 47
		//47 was chosen because it is a prime number, and through my tests, produced very good results
		int value = 0;
		int a = 47	;
		
		//Loops through each character of the key string, making it into a large number to minimize the chances of collisions
		for (int i = 0; i < key.length(); i++) {
			char c = key.charAt(i);
			int ascii = c;
			value = (a * value + ascii) % this.size;
		}
		//Makes sure value won't be negative
		if (value < 0) {
			value = value * -1;
		}
		return value;
	}
}
