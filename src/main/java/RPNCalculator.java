package main.java;
import java.util.Scanner;
import java.util.Stack;

public class RPNCalculator {
	private Stack<Integer> operations;
	
	public RPNCalculator() {
		operations = new Stack<Integer>();
	}
	
	public void rpn(String[] tokens){
	
	    int denominator = 0, duplicate = 0, modulo = 0, stackSizeTracker = 0;
	
	    if(tokens.length == 0) { System.err.println("\nError: empty input"); }
	    
	    //start processing a line of input
	    for (String token: tokens) {
	    	 if (this.isValidOperator(token)) {
	    		//check if there's enough operands in the stack for this operation
	    		if(stackSizeTracker > 1) {
		    		if (token.equals("+")) {
			            operations.push(operations.pop() + operations.pop());
			        } else if (token.equals("-")) {
			            operations.push(-operations.pop() + operations.pop());
			        } else if (token.equals("*")) {
			            operations.push(operations.pop() * operations.pop());
			        } else if (token.equals("/")) {
			            denominator = operations.pop();
			            if(denominator == 0) { 
			            	System.err.println("\nError: denominator can't be 0");
			            	this.clearBadInput(stackSizeTracker);
			            	break;
			            }
			            else { 
			            	operations.push(operations.pop() / denominator);
			            }
			        } else if (token.equals("%")) {
			        	modulo = operations.pop();
			        	operations.push(operations.pop() % modulo);
			        }
		    		stackSizeTracker--;
	    		} else {
	    			//current stackSize is less than or equal to 1, no sufficient operands for current operator
	    			// e.g. "2 +"
	    			System.err.println("\nError: insufficient input for '" + token + "' operator");
	    			this.clearBadInput(stackSizeTracker);
	    			break;
	    		}
	    	} else if (this.isSpecialOperation(token)) {
	    		if (token.equals("d")) {
		        	duplicate = operations.pop();
		        	operations.push(duplicate);
		        	operations.push(duplicate);
		        	stackSizeTracker++;
		        } else {
		        	//token.equals("p") ==> print top element
		        	System.out.println("\n" + operations.pop());
		        	stackSizeTracker--;
		        }
	    	} else if (this.isValidOperand(token)) {
	    		operations.push(Integer.parseInt(token));
	    		stackSizeTracker++;
	    	} else {
	    		this.clearBadInput(stackSizeTracker);
	    		break;
	    	}
	    }
	}
	
	public boolean isValidOperator(String checkOperator) {
		if(checkOperator.equals("+") || checkOperator.equals("-") || checkOperator.equals("*")
			|| checkOperator.equals("/") || checkOperator.equals("%") ){
			return true;
		}
		return false;
	}
	
	public boolean isSpecialOperation(String checkSpecial) {
		if(checkSpecial.equals("d") || checkSpecial.equals("p")) return true;
		return false;
	}
	
	public boolean isValidOperand(String checkOperand) {
		try {
			Integer.parseInt(checkOperand);
		} catch (NumberFormatException e) {
			System.err.println("\nError: couldn't parse '" + checkOperand + "' as an integer or operator");
			return false;
		}
		return true;
	}
	
	
	//helper method to clear out bad input in stack
	public void clearBadInput(int tracker) {
		System.err.println("Error: bad input, discard this line of input");
		while(tracker > 0) {
			operations.pop();
			tracker--;
		}
	}
	
	//helper method to get top element from stack for tester
	public int getTopElement() {
		return this.operations.pop();
	}
	
	public String toString() {
		return this.operations.toString().replaceAll("\\[|\\]|,", "");
	}
	
	public static void main(String[] args) {
		RPNCalculator cal = new RPNCalculator();
		Scanner scanner = new Scanner(System.in);
		while(scanner.hasNextLine()){
			cal.rpn(scanner.nextLine().split(" "));
		}
		
		scanner.close();
		System.out.println(cal.toString());		
	}
}