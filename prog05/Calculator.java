package  prog05;

import java.util.Stack;
import prog02.UserInterface;
import prog02.GUI;

public class Calculator {
  Object[] tokenize (String s) 
  {
	    input = s;
	    index = 0;
	    Object[] out = new Object[s.length()];
	    int n = 0;
	    
	    while (!atEndOfInput ()) {
	    		if (isNumber())
		      {
		    	  	out[n++] = getNumber();
		      }
	    		else
		      {
		    	  	out[n++] = s.charAt(index++);
		      }
	    }
	
	    Object[] out2 = new Object[n];
	    System.arraycopy(out, 0, out2, 0, n);
	    return out2;
  }

  String input = null;
  int index = 0;

  boolean atEndOfInput () {
	  	while (index < input.length() &&Character.isWhitespace(input.charAt(index)))
	  		index++;
	  	return index == input.length();
  }

  	boolean isNumber () {
    return Character.isDigit(input.charAt(index));
  }

  double getNumber () {
    int index2 = index;
    while (index2 < input.length() && (Character.isDigit(input.charAt(index2)) ||input.charAt(index2) == '.'))
    		index2++;
    double d = 0;
    try {
    		d = Double.parseDouble(input.substring(index, index2));
    } catch (Exception e) {
    		System.out.println(e);
    }
    index = index2;
    return d;
  }

  char getOperator () {
    char op = input.charAt(index++);
    if (OPERATORS.indexOf(op) == -1)
      System.out.println("Operator " + op + " not recognized.");
    return op;
  }

  Stack<Double> numberStack = new Stack<Double>();
  Stack<Character> operatorStack = new Stack<Character>();

  String numberStackToString () {
    String s = "numberStack: ";
    Stack<Double> helperStack = new Stack<Double>();
    
    double helper = 0;
    while(!numberStack.isEmpty())
    {
	    helper = numberStack.pop();
	    helperStack.push(helper);
    }	
    
    while(!helperStack.isEmpty())
    {
	    helper = helperStack.pop();
	    numberStack.push(helper);
	    s = s + " " + helper;
    }
    return s;
  }

  String operatorStackToString () {
    String s = "operatorStack: ";
    // EXERCISE

    Stack<Character> helperStack = new Stack<Character>();
    Character helper = ' ';
    while(!operatorStack.isEmpty())
    {
	    helper = operatorStack.pop();
	    helperStack.push(helper);
    }
    while(!helperStack.isEmpty())
    {
	    helper = helperStack.pop();
	    operatorStack.push(helper);
	    s = s + " " + helper;
    }
    
    return s;
  }

  static UserInterface ui = new GUI();

  void displayStacks () {
    ui.sendMessage(numberStackToString() + "\n" +
                   operatorStackToString());
  }

  double evaluate (String expr) {
	 
    while (!operatorStack.empty()) operatorStack.pop();
    while (!numberStack.empty()) numberStack.pop();

  
    Object[] inputs = tokenize(expr);

    for (int i = 0; i < inputs.length; i++) 
    {
      if (inputs[i] instanceof Double) 
      {
        double x = (Double) inputs[i];
        numberStack.push(x);
        displayStacks();
      }
      else 
      {
        char o = (Character) inputs[i];
        if(o == '-'){
        		if(i == 0 || !((inputs[i-1] instanceof Double) || (Character)inputs[i-1] == ')'))
        		{
        			o = 'u';
        		}
        }
       
        processOperator(o);
        displayStacks();
      }
    }
    while(!operatorStack.empty()) evaluateOperator();

    return numberStack.pop();
  }

  static final String OPERATORS = "()+-*/^u";
  static final int[] PRECEDENCE = { -1, -1, 1, 1, 2, 2, 3, 4};
  int precedence (char op) {
    return PRECEDENCE[OPERATORS.indexOf(op)];
  }
  
  double evaluateOperator (double a, char op, double b){
	  double result = 0;
	  if (op == '*')
	  {
		  result = a*b;
	  }
	  else if (op == '/')
	  {
		  result = b/a;
	  }
	  else if (op == '+')
	  {
		  result = a+b;
	  }
	  else if (op == '-')
	  {
		  result = b-a;
	  }
	  else if (op == '^')
	  {
		  result = Math.pow(b, a);
	  }
	 
	  return result;
  }
  /*else if (op == 'u'){
  //if
  result = -b;
  }*/
  /*else if (op == ')' && op == '('){
  operatorStack.pop();
  op = operatorStack.peek();
  evaluateOperator(a, op, b);
  }*/
  
  void evaluateOperator (){
	  char op = operatorStack.pop();
	  if(op == 'u')
	  {
		  numberStack.push(-numberStack.pop());
	  }
	  else
	  {
		  double a = numberStack.pop();
		  double b = numberStack.pop();
		  numberStack.push(evaluateOperator(a, op, b));  
	  }
	  displayStacks();
  }
  
  void processOperator (char op){
	  if(op == ')')
	  {
		  while(!operatorStack.empty() && operatorStack.peek() != '(')
		  {
			  evaluateOperator();
		  }
		  operatorStack.pop();
	  }
	  if(op != '(' && op != ')')
	  {
		  while(!operatorStack.empty() && precedence(operatorStack.peek()) >= precedence(op))
		  {
			  evaluateOperator();
		  }
	  }
	  if(op != ')')
	  {
		  operatorStack.push(op);
	  }
  }
  public static void main (String[] args) {
    Calculator parser = new Calculator();

    while (true) {
      String line = ui.getInfo("Enter arithmetic expression or cancel.");
      if (line == null)
        return;

      try {
        double result = parser.evaluate(line);
        ui.sendMessage(line + " = " + result);
      } catch (Exception e) {
        System.out.println(e);
      }
    }
  }
}
