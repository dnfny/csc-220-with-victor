package prog06;
 //final
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
 
import prog02.GUI;
import prog02.UserInterface;
 
public class WordStep {
       public static UserInterface ui = new GUI();
 
       public static void main(String[] args) {
    	   		WordStep game = new WordStep();
 
            String words = ui.getInfo("Enter new dictionary file name");
            game.loadDictionary(words);
            String start, target;
            String[] commands = { "Human plays.", "Computer plays." };
            int c = 0;
            start = ui.getInfo("Enter starting word");
            target = ui.getInfo("Enter target word");
            while (true) 
            {
            		c = ui.getCommand(commands);
                switch (c) 
                {
                		case 0:
                				game.play(start, target);
                				return;
                    case 1:
                            game.solve(start, target);
                            return;
                 }
            }
      }
 
       public static void play(String start, String target) {
           	UserInterface ui = new GUI();
           	String newstart = start;
            while (true) 
            {
            		ui.sendMessage("The current word is " + newstart);
                ui.sendMessage("The target word is " + target);
                String tryword= ui.getInfo("Enter the next word");
                 if (!oneLetter(tryword, newstart)) 
                 {
                	 	ui.sendMessage("User Input off by greater than one letter. Try again");
                    ui.sendMessage("current word = " + tryword);
                    continue;
                 }
                 tryword = newstart;
                 if (newstart.equals(target))
                 {
                	 	break;
                 }
 
             }
                  ui.sendMessage("You Win!");
     }
 
    void solve(String start, String target) {
                                // Step 10
    		Queue<Node> nodes = new LinkedList<>();
        Node startNode = find(start);
        nodes.offer(startNode);
        while (!nodes.isEmpty()) 
        {
        		Node theNode = nodes.poll();
 
        		for (int i = 0; i < e.size(); i++) 
        		{
        			Node temp = e.get(i);
        			if (temp != startNode && temp.previous == null && oneLetter(theNode.data, temp.data)) 
        			{
        				temp.previous = theNode;
                     nodes.offer(temp);
                     if (temp.data.equals(target)) 
                     {
                    	 	Stack<Node> fin = new Stack<Node>();
                         String message = "";
                         while(temp != null ) 
                         {
                        	 	fin.push(temp);
                        	 	temp = temp.previous;
                          }
                             while (!fin.empty() ) 
                             {
                            	 	temp =fin.pop();
                                                           message = message + (temp.data + " ");
                                                                                                               
                             }
                             ui.sendMessage(message);
                             ui.sendMessage("Yay you win!");
                      }
        			}
                                                               
        		}
        }
 
    }
 
    public static boolean oneLetter(String one, String two) {
     	int count = 0;
     	if (one.length() != two.length()) 
         {
         	return false;
         } 
     	else 
     	{
         	for (int index = 0; index < one.length(); index++) 
             {
             	if (one.charAt(index) != two.charAt(index))
                 {
             		count++;
                 }
             }
         }
       if((count > 1)) 
        {
    	   		return false;
        }
       else 
       {
    	   		return true;
       }
    }
 
    public Node find(String target) {
    		for (int i = 0; i < e.size(); i++) 
    		{
    			if (e.get(i).data.equals(target)) 
    			{
    				return e.get(i);
    			}
        }
        return null;
                }
 
     List<Node> e = new ArrayList<Node>();
 
     public void loadDictionary(String words) {
     try {
	    	 	Scanner in = new Scanner(new File("words.txt"));
	        String word;
	        while (in.hasNextLine()) 
	        {
	        		word = in.nextLine();
	        		Node r = new Node(word);
	         	e.add(r);
	        	}
	        	in.close();
         } 
     catch (FileNotFoundException ex) 
     	{
    	 		System.out.println(words + ": file not found.");
    	 		System.out.println(ex);
    	 		return;
     	}
     }
 
    private static class Node<List> {
 
    		private String data;
    		private Node previous;
 
        private Node(String data) {
        		this.data = data;
        		previous = null; // Necessary in C++ but not in Java.
        }
 
        private Node(String data, Node<List> next) 
        {
        		this.data = data;
        		this.previous = previous;
        }
    }
 
   List<Node> nodes = new ArrayList<Node>();
}
 
