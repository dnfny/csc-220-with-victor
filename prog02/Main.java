package prog02;

/**
*
* @author vjm
*/
public class Main {

/** Processes user's commands on a phone directory.
     @param fn The file containing the phone directory.
     @param ui The UserInterface object to use
            to talk to the user.
     @param pd The PhoneDirectory object to use
            to process the phone directory.
*/
	public static void processCommands(String fn, UserInterface ui, PhoneDirectory pd) {
	pd.loadData(fn);
	
	String[] commands = {
	"Add/Change Entry",
	"Look Up Entry",
	"Remove Entry",
	"Save Directory",
	"Exit"};
	
	String name, number, oldNumber;
	
	while (true) {
		int c = ui.getCommand(commands);
		switch (c) {
		
		case -1:
		ui.sendMessage("You clicked the red x, restarting.");
		break;
		
		case 0:
		
		name = ui.getInfo("Enter the name"); 
		
		if (name == null)
		{
			
			break; 
		}
		if(name.equals(""))
		{
			ui.sendMessage("Blank name not allowed");
			break; 
		}
		
		number = ui.getInfo("Enter the number");
		
		if (number == null)
		{
			break; 
		}
		else
		{	
			pd.addOrChangeEntry(name, number); 
			
			ui.sendMessage("You have changed an entry"); 
			break;
		}
		
		case 1:
			
		name = ui.getInfo("Enter the name"); 
		if (name == null)
		{
			break; 
		}
		if(name.length()==0)
		{
			ui.sendMessage("Blank name not allowed");
			break; 
		}
		else 
		{
			ui.sendMessage("You want to look up " + name);
			number = pd.lookupEntry(name);
		}
		 
		if (number!= null)
		{
			ui.sendMessage(name + " has number " + number);
			
		}
		else 
		{
			ui.sendMessage(name + " is not listed.");
			break;
		}
		break;
		
		case 2:
		
		name = ui.getInfo("Enter the name");
		
		if (name == null)
		{
			break; 
		}
		if(name.equals(""))
		{
			ui.sendMessage("Blank name not allowed");
			break; 
		}
		
		else
		{
			pd.removeEntry(name); 
			ui.sendMessage("You have removed " + name);
			break;
		}
		case 3:
		
		pd.save();
		ui.sendMessage("The directory has been saved");
		break;
		
		
		case 4:
		ui.sendMessage("The program will exit now.");
		return; 
		}
		}
	}
	
	/**
	* @param args the command line arguments
	*/
	public static void main(String[] args) {
	String fn = "csc220.txt";
	PhoneDirectory pd = new SortedPD();
	UserInterface ui = new GUI();
	processCommands(fn, ui, pd);
	}
}