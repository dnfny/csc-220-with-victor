package prog03;
import prog02.UserInterface;
import prog02.GUI;

/**
 *
 * @author vjm
 */
public class Main {
  /** Use this variable to store the result of each call to fib. */
  public static double fibn;

  /** Determine the time in microseconds it takes to calculate the
      n'th Fibonacci number.
      @param fib an object that implements the Fib interface
      @param n the index of the Fibonacci number to calculate
      @return the time for the call to fib(n)
  */
  public static double time (Fib fib, int n) {
    // Get the current time in nanoseconds.
    long start = System.nanoTime();

    // Calculate the n'th Fibonacci number.  Store the
    // result in fibn.
    fibn = fib.fib(n);

    // Get the current time in nanoseconds.
    long end = System.nanoTime();

    // Return the difference between the end time and the
    // start time divided by 1000.0 to convert to microseconds.
    return (end-start)/1000;
  }

  /** Determine the average time in microseconds it takes to calculate
      the n'th Fibonacci number.
      @param fib an object that implements the Fib interface
      @param n the index of the Fibonacci number to calculate
      @param ncalls the number of calls to average over
      @return the average time per call
  */
  public static double averageTime (Fib fib, int n, int ncalls) {
    double totalTime = 0;

    // Add up the total call time for ncalls calls to time (above).
    	for(int i = 0; i<ncalls; i++)
    	{
    		totalTime += time(fib,n); 
    	}

    // Return the average time.
    	return totalTime/ncalls; 

  }

  /** Determine the time in microseconds it takes to to calculate the
      n'th Fibonacci number.  Average over enough calls for a total
      time of at least one second.
      @param fib an object that implements the Fib interface
      @param n the index of the Fibonacci number to calculate
      @return the time it it takes to compute the n'th Fibonacci number
  */
  public static double accurateTime (Fib fib, int n) {
    // Get the time in microseconds using the time method above.
    double t = time(fib, n);

    // If the time is (equivalent to) more than a second, return it.
    if (t > 1e6)
      return t;

    // Estimate the number of calls that would add up to one second.
    // Use   (int)(YOUR EXPESSION)   so you can save it into an int variable.
    int numcalls = (int)(1e6 / t);

    // Get the average time using averageTime above and that many
    // calls and return it.
    return averageTime(fib, n, numcalls);
  }

  private static UserInterface ui = new GUI();

  public static void doExperiments (Fib fib) {
	  String myString = "";
	  double time = 0.0;
	  double c=0;
	  int n =0;
	  double error=0;
	  double timeEst=0;
	  String [] choice = { "Calculate ", "Cancel" };
	 
		 while(true)
		 {
			  int input = ui.getCommand(choice);
			  if(input==1)
			  {
				  break;
			  }
			 try 
			 {
				 myString = ui.getInfo("Enter the value for n: ");
				 n = Integer.parseInt(myString);
			  while (n<=0)
			  {
				  ui.sendMessage("Try another number. No negative integers, blank integers, or non-integers");
				  myString = ui.getInfo("Enter the value for n: ");
				  n = Integer.parseInt(myString);
			  }
			  
			  time = accurateTime(fib,n);
			  
			 if(c>0)
			 {
				 timeEst= c*fib.o(n);
				 error = ((Math.abs(time-timeEst))/timeEst)*100;
				  ui.sendMessage("The estimated running time with the previous constant: "+c+" is "+timeEst);
			 }
			
			 c = time / fib.o(n);
			 ui.sendMessage("n: "+n+", fib(n): "+fibn+", running time: "+time+", Percentage of error "+ error +"%");
			 } 
			 catch (NumberFormatException e)
			{
				 ui.sendMessage("Try another number. No negative integers, blank integers, or non-integers"); 
			 }
		 }
		 
		  
	  
  }

  public static void doExperiments () {
	  
	  String [] inputs = {"Exponential Fib", "Linear Fib", "Log Fib", "Constant Fib", "Mystery Fib"};
	  
	  while(true)
	  {
		  int input = ui.getCommand(inputs);
		  
		  switch(input) {
		  
		  case 0:
			  	doExperiments(new ExponentialFib());
			  	break;
			  	
		  case 1:
			    doExperiments(new LinearFib());
			  	break;
			  
		  case 2:
			  	doExperiments(new LogFib());
			  	break;
		 
		  case 3:
			  	doExperiments(new ConstantFib());
			  	break;
			  	
		  case 4:
			  	doExperiments(new MysteryFib());
			  	break;
		default:
				return;
			  	
		  }
	  }
  }

  

static void labExperiments () {
    // Create (Exponential time) Fib object and test it.
    Fib efib = new LinearFib();
    System.out.println(efib);
    	for (int i = 0; i < 11; i++)
    	{
    		System.out.println(i + " " + efib.fib(i));
    	}
    
    // Determine running time for n1 = 20 and print it out.
    int n1 = 20;
    double time1ave = averageTime(efib, n1,1000);
    double time1acc = accurateTime(efib, n1);
    System.out.println("n1 " + n1 + " time1 average " + time1ave);
    System.out.println("n1 " + n1 + " time1 accurate " + time1acc);
    
    int ncalls= (int)(1e6/ time1ave);
    time1ave = averageTime(efib,n1,ncalls);
    time1acc= accurateTime(efib,n1);
    System.out.println("n1 " + n1 + " time1 average " + time1ave);
    System.out.println("n1 " + n1 + " time1 accurate " + time1acc);
    
    // Calculate constant:  time = constant times O(n).
    double c = time1acc / efib.o(n1);
    System.out.println("constant: " + c);
    
     //Estimate running time for n2=30.
    int n2 = 30;
    double time2est = c * efib.o(n2);
    System.out.println("n2 " + n2 + " estimated time " + time2est);
    
    // Calculate actual running time for n2=30.
    double time2 = averageTime(efib, n2, 100);
    ncalls= (int)(1e6/ time2);
    time2 = averageTime(efib,n2,ncalls);
    
    System.out.println("n2 " + n2 + " actual time " + time2);
    time2= accurateTime(efib,n2);
    System.out.println("n2 " + n2 + " actual time " + time2);
    
    int n3 = 100;
    double time3est = c*efib.o(n3);
    System.out.println("n3 "+n3+" estimated time "+time3est);
    double years = time3est/ 1e6/3600/24/365.25;
    System.out.println("years: "+years);
  }

  /**
   * @param args the command line arguments
   */
  public static void main (String[] args) {
    doExperiments();
    labExperiments();
  }
}
