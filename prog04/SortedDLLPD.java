package prog04;

/** This is an implementation of the prog02.PhoneDirectory interface that uses
 *   a doubly linked list to store the data.
 *   @author vjm
 */
public class SortedDLLPD extends DLLBasedPD {
  /** Add an entry or change an existing entry.
      @param name The name of the person being added or changed.
      @param number The new number to be assigned.
      @return The old number or, if a new entry, null.
  */
  public String addOrChangeEntry (String name, String number) {
	  
    String oldNumber = null;
    FindOutput fo = find(name);
    if (fo.found) 
    {
      oldNumber = fo.entry.getNumber();
      fo.entry.setNumber(number);
    } 
    else 
    {
    		DLLEntry entry = new DLLEntry(name, number);
    		DLLEntry next  = fo.entry;
    		DLLEntry previous;
	    		if(next == null )
	    		{
	    			previous=tail;
	    			tail=entry;
	    		}
	    		else 
	    		{
	    			previous = next.getPrevious();
	    			next.setPrevious(entry);
	    			
	    		}
    		entry.setNext(next);
    		entry.setPrevious(previous);
    		
	    		if(previous==null)
	    		{
	    			next=head;
	    			head=entry;
	    		}
	    		else
	    		{
	    			previous.setNext(entry);
	    		}

    }
    return oldNumber;
  }
  
  protected FindOutput find (String name) {
	   
		 int insert= 0;
		 for( DLLEntry entry = head; entry != null; entry  = entry.getNext())
		 {
			  insert = entry.getName().compareToIgnoreCase(name);
			  
			  if(insert==0)
			  {
				  return new FindOutput(true,entry);
			  }
			  else if(insert>0)
			  {
				  return new FindOutput(false,entry);
			  }
				
		  }	
	        
	    return new FindOutput(false, null ); // Name not found
	    
	  }
}
