package test.java;
import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Stack;

import org.junit.Before;
import org.junit.After;
import org.junit.Test;

import main.java.RPNCalculator;

public class RPNCalculatorTester {
	
	private RPNCalculator cal;
	private final ByteArrayOutputStream errMsg = new ByteArrayOutputStream();
	private final ByteArrayOutputStream outputMsg = new ByteArrayOutputStream();
	
	@Before
	public void initTester() {
		cal = new RPNCalculator();
		System.setErr(new PrintStream(errMsg));
		System.setOut(new PrintStream(outputMsg));
	}
	
	@After
	public void cleanUpStreams() {
		System.setErr(null);
		System.setOut(null);
	}
	
	@Test
	public void testAddition() {
		String[] simpleTest = {"2", "3", "+"};
		cal.rpn(simpleTest);
		assertEquals(5, cal.getTopElement());
	}
	
	@Test
	public void testSubstraction() {
		Stack<Integer> stack = new Stack<Integer>();
		stack.push(-1);
		String[] simpleTest = {"2", "3", "-"};
		cal.rpn(simpleTest);
		assertEquals(-1, cal.getTopElement());
	}
	
	@Test
	public void testMultiplication() {
		String[] simpleTest = {"2", "3", "*"};
		cal.rpn(simpleTest);
		assertEquals(6, cal.getTopElement());
	}
	
	@Test
	public void testDivision() {
		String[] simpleTest = {"2", "3", "/"};
		cal.rpn(simpleTest);
		assertEquals(0, cal.getTopElement());
	}
	
	@Test
	public void testModulo() {
		String[] simpleTest = {"2", "3", "%"};
		cal.rpn(simpleTest);
		assertEquals(2, cal.getTopElement());
	}
	
	@Test
	public void testOperandValidation() {
		String[] invalidOperand = {"2.5", "3", "+"};
		cal.rpn(invalidOperand);		
		assertEquals("\nError: couldn't parse '2.5' as an integer or operator\n"
				+ "Error: bad input, discard this line of input\n",
				errMsg.toString());
		assertEquals(false, cal.isValidOperand(invalidOperand[0]));
	}
	
	@Test
	public void testInsufficientInput() {
		String[] insufficentInput = {"2", "+"};
		cal.rpn(insufficentInput);
		assertEquals("\nError: insufficient input for '+' operator\n"
				+ "Error: bad input, discard this line of input\n", errMsg.toString());
	}
	
	@Test
	public void testOperatorValidation() {
		String[] invalidOperator = {"2", "2", "r"};
		cal.rpn(invalidOperator);
		assertEquals("\nError: couldn't parse 'r' as an integer or operator\n"
				+ "Error: bad input, discard this line of input\n",
				errMsg.toString());
		assertEquals(false, cal.isValidOperator(invalidOperator[2]));
	}
	
	@Test
	public void testSpecialOperations() {
		String[] specialTest = {"2", "3", "4", "+", "d", "p", "*"};
		cal.rpn(specialTest);
		assertEquals("\n7\n", outputMsg.toString());
		assertEquals(14, cal.getTopElement());
	}
	
	@Test
	public void testFaultTolerance() {
		String[] faultInput = {"2", "8", "2", "+"};
		cal.rpn(faultInput);
		assertEquals("2 10", cal.toString());
	}
}