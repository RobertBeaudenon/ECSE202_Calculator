package ass4;
//Robert Beaudenon
//260725065

import acm.program.ConsoleProgram;
import java.util.*;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import acm.gui.TableLayout;
import acm.program.Program;
import java.math.*;



public class JCalcGUI extends Program implements ChangeListener, ActionListener {



	//method precedence of operators
	private int precedence(String operator) {  
		switch (operator) {
		case "+":
			return 0;
		case "-":
			return 0;
		case "x":
			return 1;
		case "/":
			return 1;
		case "^":
			return 2;
		case "u+":
			return 3;
		case "u-":
			return 3;
		default:
			return -1;
		}
	}



	// This method returns the type of the token
	private String typeOfToken(String token) {
		// try will try to do the code in the braces
		// in case the code goes wrong, it will throw an exception
		// then the catch expression will take the exception and do something with it

		try {
			Integer.valueOf(token);
			return "integer";

		} catch (Exception e1) {
			try {
				Double.valueOf(token);
				return "double";

			} catch (Exception e2) {
				if (token.equals(")") || token.equals("(")) {
					return token;
				}
				else {
					return "operator";
				}


			}
		}
	}

	//method to evaluate the elements of the postfix expression

	public double evaluate_v2(String operator, Stack my_stack)
	{


		double n2 = Double.valueOf(my_stack.pop()); //result of  popping

		if(operator.equals("u-")){ 
			return -n2;
		}else if(operator.equals("u+"))
		{
			return n2;
		}else {

			double n1 = Double.valueOf(my_stack.pop()); //result of  popping

			if(operator.equals("+"))
				return n1+n2;
			else if(operator.equals("-"))
				return n1-n2;
			else if(operator.equals("x"))
				return n1*n2;
			else if(operator.equals("/"))
				return n1/n2;
			else 
				return Math.pow(n1, n2);
		}
	}

	String prefix = ""; //creation of empty strings
	String resultCalc="";


	// input text field is empty

	JTextField in = new JTextField("");

	StringTokenizer st;
	JTextField out = new JTextField(""); // output text field is empty

	JTextField prec_tf = new JTextField(""); // precision text field is empty for now
	JSlider prec_slider;
	boolean outWasAppeared = false;


	//init method used to run the whole program

	public void init()
	{


		setSize(500, 500);
		setLayout(new TableLayout(8, 4));

		in.setEditable(false);
		out.setBackground(Color.cyan);

		// To add we need the object and we need to give some constraint -> String
		add(in,"gridwidth = 4 height = 25");
		add(out,"gridwidth = 4 height = 25");


		String BUTTON_SIZE = "65";
		String button_label[]= {"C","(",")","/","7","8","9","x","4","5","6","-","1","2","3","+","0",".","^","="};

		String constraint = "width=" + BUTTON_SIZE + " height=" + BUTTON_SIZE;

		for (int i = 0; i < button_label.length; i++) {


			JButton cur_button = new JButton(button_label[i]);
			cur_button.setFont(new Font("Arial", Font.LAYOUT_NO_START_CONTEXT, 20));
			cur_button.addActionListener(this);



			add(cur_button, constraint);

		}

		//Label
		add(new JLabel("Precision"));

		//Slider
		int default_val = 6;
		String default_val_str = default_val + "";
		prec_slider = new JSlider(0,10,default_val);
		//prec_slider
		prec_slider.addChangeListener(this);

		add(prec_slider,"gridwidth = 2");

		//Precision text field
		prec_tf.setText(default_val_str);
		prec_tf.setEditable(false);
		prec_tf.setBackground(Color.cyan);
		add(prec_tf);




	}

	//state change mehtod used to respond to the events entered on the applet



	@Override
	public void stateChanged(ChangeEvent arg0) {
		// TODO Auto-generated method stub
		//System.out.println("Slider changes");
		int prec_slider_value = prec_slider.getValue();
		prec_tf.setText(prec_slider_value+"");


		//respond to the slider event , will set the precision

		String precision= "";
		precision = resultCalc;
		if (prec_slider_value ==0) {
			out.setText(precision.substring(0,precision.indexOf('.')));

		}
		if(precision.length() >= prec_slider_value && prec_slider_value !=0) {
			out.setText(precision.substring(0,precision.indexOf('.')+prec_slider_value+1));

		}

	}

	//method that return the result of the calculator under the form of a string
	public String result_calculator (String str ) {
		// input queue
		Queue input = new Queue();

		// output queue
		Queue output = new Queue();

		// operator stack
		Stack stack = new Stack();
		boolean unary =true;//is the current operator unary
		//str = in.getText();
		//System.out.println("str = "+str);
		StringTokenizer st = new StringTokenizer(str, "+-x/()^", true);

		while (st.hasMoreTokens()) {
			String t = st.nextToken();
			input.Enqueue(t);
		}

		// Shunting yard  algorithm goes here:
		while (!input.isEmpty()) {//while imput not empty

			String newToken = input.Dequeue().trim();  //dequeue token from input 

			if (typeOfToken(newToken).equals("operator")) {//if newToken is an operator
				if (unary) {
					newToken = "u" + newToken;
					// a unary op always goes on 
					// the stack without popping any other op
					stack.push(newToken);
				}else {
					if (!stack.isEmpty()) {  
						while (precedence(stack.top()) > precedence(newToken)//check the precedence of the operator
								|| precedence(stack.top()) == precedence(newToken)) { //with the one present in the stack
							String topOfStack = stack.pop(); //  as long as top operator on stack has higher precedence
							output.Enqueue(topOfStack); // or same and is left associative add to output queue
							if(stack.isEmpty()) {
								break;
							}
						}
					}
					stack.push(newToken);// else push new operator on stack
					unary = true;// if an operator is after this one, it has to be unary
				}
			}

			if (newToken.equals("(")) { //dealing with parentheses
				stack.push(newToken);
			}

			if (newToken.equals(")")) {
				while (!stack.top.keyVal.equals("(")) {
					output.Enqueue(stack.pop());
				}
				stack.pop();
			}
			if (typeOfToken(newToken).equals("integer") || typeOfToken(newToken).equals("double")) {
				output.Enqueue(newToken);
				unary = false;// any operator after an operand is binary
			}

		}

		while (!stack.isEmpty()) {//pop remaining operator from stack and and to output queue
			output.Enqueue(stack.pop());
		}



		while (!output.isEmpty()) {  

			String qElement = output.Dequeue();

			//evaluating postfix using  a stack
			if (typeOfToken(qElement).equals("integer") || typeOfToken(qElement).equals("double")) {//if number
				stack.push(qElement);//push in the stack
			}
			else if (typeOfToken(qElement).equals("operator")){//if operator pop two values, 
				//evaluate the operator with those two operands, and push the result

				double result = evaluate_v2(qElement, stack);
				stack.push((result+""));
			}
		}


		String out_put =stack.top();
		while(out_put.length()<=10) { // if the precision is smaller than 10 add 0 all the way to 10

			out_put = out_put + "0";//if we don't do this we will have a falure using precision
		}
		return out_put;
	}

	//method action performed
	public void actionPerformed(ActionEvent e) {


		//condition  if the user click on the button '=' then the expression will be evaluated

		if(e.getActionCommand()=="=")
		{

			resultCalc = result_calculator(prefix);
			out.setText(resultCalc.substring(0,resultCalc.indexOf('.')+7));

			outWasAppeared = true;
		}//condition if user click the clear button then everything is cleared 
		else if (e.getActionCommand()=="C"){
			in.setText("");
			prefix = "";
			out.setText("");

		}
		else 
		{
			if(outWasAppeared)
			{
				outWasAppeared = false;
				in.setText("");	
				prefix = "";
				out.setText("");
			}


			prefix += e.getActionCommand();
			in.setText(prefix);
		}

	}

}

