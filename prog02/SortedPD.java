package prog02; 
/**
 *
 * @author vjm
 */
public class SortedPD extends ArrayBasedPD {

	 public String removeEntry (String name) {
		 FindOutput fo = find(name);
		 if (!fo.found)
		 {
			 return null;
		 }
		 else
		 { 
			 //DirectoryEntry entry = theDirectory[fo.index+1];
			 for (int i = fo.index; i < size; i++)
			 {
			 	theDirectory[i] = theDirectory[i+1];
			 }
			 size --; 
			 modified = true; 
			 return null;
		 }
	 }

	 
	public String addOrChangeEntry (String name, String number) { 
	String oldNumber = null; 
	FindOutput fo = find(name); 
	int insertionIndex = 0; 
	if(!fo.found)
	{ 
		insertionIndex = fo.index; 

		for (int i = size; i >= insertionIndex; i--)
		{
			theDirectory[i+1] = theDirectory[i];
		}
		theDirectory[insertionIndex] = new DirectoryEntry(name, number);
		size++; 
	}
	else 
	{
	      if (size >= theDirectory.length)
	      {
	    	  	reallocate();
	      }
	      insertionIndex = fo.index;         
	      theDirectory[insertionIndex].setNumber(number);
	               
	      return null;
	}
	modified = true; 
	return oldNumber; 
}

protected FindOutput find (String name) {
	
	 int first = 0; 
	 int last = size-1; 
	 int middle = 0; 
	 int cmp = 0; 
	while (first <= last)
	{
		middle = (first + last)/2; 
		cmp = name.compareToIgnoreCase(theDirectory[ middle].getName());   	
		
		if (cmp == 0)
			{
		
				return new FindOutput(true, middle); 
			}
		else if(first==last)
		{
			return new FindOutput(false, middle);
		}
			else 
			{
				if(cmp > 0)
				{	
					first = (middle +1); 
					
				}
				else if (cmp < 0)
				{
					{
						last =  (middle - 1);
					}
					 
				}
			}
	}
		 
	return new FindOutput(false, size);
	
} 


} 