package cs445.a2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StreamTokenizer;
import java.util.HashMap;
import java.util.Map;

/**
 * This class uses two stacks to evaluate an infix arithmetic expression from an
 * InputStream. It should not create a full postfix expression along the way; it
 * should convert and evaluate in a pipelined fashion, in a single pass.
 */
public class InfixExpressionEvaluator {
    // Tokenizer to break up our input into tokens
    StreamTokenizer tokenizer;

    // Stacks for operators (for converting to postfix) and operands (for
    // evaluating)
    StackInterface<Character> operatorStack;
    StackInterface<Double> operandStack;

    /**
     * Initializes the evaluator to read an infix expression from an input
     * stream.
     * @param input the input stream from which to read the expression
     */
    public InfixExpressionEvaluator(InputStream input) {
        // Initialize the tokenizer to read from the given InputStream
        tokenizer = new StreamTokenizer(new BufferedReader(
                        new InputStreamReader(input)));

        // StreamTokenizer likes to consider - and / to have special meaning.
        // Tell it that these are regular characters, so that they can be parsed
        // as operators
        tokenizer.ordinaryChar('-');
        tokenizer.ordinaryChar('/');

        // Allow the tokenizer to recognize end-of-line, which marks the end of
        // the expression
        tokenizer.eolIsSignificant(true);

        // Initialize the stacks
        operatorStack = new ArrayStack<Character>();
        operandStack = new ArrayStack<Double>();
    }

    /**
     * Parses and evaluates the expression read from the provided input stream,
     * then returns the resulting value
     * @return the value of the infix expression that was parsed
     */
    public Double evaluate() throws ExpressionError {
        // Get the first token. If an IO exception occurs, replace it with a
        // runtime exception, causing an immediate crash.
        char trace = 'a';        
        
        try {
            tokenizer.nextToken();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // Continue processing tokens until we find end-of-line
        while (tokenizer.ttype != StreamTokenizer.TT_EOL) {
            // Consider possible token types
            switch (tokenizer.ttype) {
                case StreamTokenizer.TT_NUMBER:
                    // If the token is a number, process it as a double-valued
                    // operand
                    trace = (char)tokenizer.ttype;
                    handleOperand((double)tokenizer.nval);
                    break;
                case '+':
                case '-':
                case '*':
                case '/':
                case '^':
                case '!':
                    // If the token is any of the above characters, process it
                    // is an operator
                    trace = (char)tokenizer.ttype;
                    handleOperator((char)tokenizer.ttype);
                    break;
                case '(':
                case '{':
                    // If the token is open bracket, process it as such. Forms
                    // of bracket are interchangeable but must nest properly.
                    trace = (char)tokenizer.ttype;
                    handleOpenBracket((char)tokenizer.ttype);
                    break;
                case ')':
                case '}':
                    // If the token is close bracket, process it as such. Forms
                    // of bracket are interchangeable but must nest properly.
                    trace = (char)tokenizer.ttype;
                    handleCloseBracket((char)tokenizer.ttype);
                    break;
                case StreamTokenizer.TT_WORD:
                    // If the token is a "word", throw an expression error
                    trace = (char)tokenizer.ttype;
                    throw new ExpressionError("Unrecognized token: " +
                                    tokenizer.sval);
                default:
                    // If the token is any other type or value, throw an
                    // expression error
                    throw new ExpressionError("Unrecognized token: " +
                                    String.valueOf((char)tokenizer.ttype));
            }

            // Read the next token, again converting any potential IO exception
            try {
                tokenizer.nextToken();
            } catch(IOException e) {
                throw new RuntimeException(e);
            }
            
            switch (tokenizer.ttype){
                case '+':
                    if(trace == '(' || trace == '{'){
                        throw new ExpressionError("You may check the brackets.");
                    }
                case '-':
                    if(trace == '(' || trace == '{'){
                        throw new ExpressionError("You may check the brackets.");
                    }
                case '*':
                    if(trace == '(' || trace == '{'){
                        throw new ExpressionError("You may check the brackets.");
                    }
                case '/':
                    if(trace == '(' || trace == '{'){
                        throw new ExpressionError("You may check the brackets.");
                    }
                case '^':
                    if(trace == '(' || trace == '{'){
                        throw new ExpressionError("You may check the brackets.");
                    }
                case '!':
                    if(trace == '(' || trace == '{'){
                        throw new ExpressionError("You may check the brackets.");
                    }
            }
        }

        // Almost done now, but we may have to process remaining operators in
        // the operators stack
        handleRemainingOperators();

        // Return the result of the evaluation
        // TODO: Fix this return statement
        return operandStack.peek();
    }

    /**
     * This method is called when the evaluator encounters an operand. It
     * manipulates operatorStack and/or operandStack to process the operand
     * according to the Infix-to-Postfix and Postfix-evaluation algorithms.
     * @param operand the operand token that was encountered
     */
    void handleOperand(double operand) {
        // TODO: Complete this method
        operandStack.push(operand);
    }

    /**
     * This method is called when the evaluator encounters an operator. It
     * manipulates operatorStack and/or operandStack to process the operator
     * according to the Infix-to-Postfix and Postfix-evaluation algorithms.
     * @param operator the operator token that was encountered
     */
    void handleOperator(char operator) {
        // TODO: Complete this method
        if (operator == '!') {
            if (operandStack.isEmpty()) {
                throw  new ExpressionError("The expression is illegal");
            }
            double operand = operandStack.peek();
            if (Math.abs(operand-Math.round(operand)) <= 0.000000001)
            {
                int result = 1;
                for (int i = 2; i <= (int)operand; i++) {
                    result = result * i;
                }
                operandStack.pop();
                operandStack.push((double)result);
            }
            else  {
                throw new ExpressionError("Only an integer has factorial.");
            }
            return;
        }
        while (checkIsAdvanced(operator)){
            handle();
        }

        operatorStack.push(operator);
    }

    /**
     * This method is called when the evaluator encounters an open bracket. It
     * manipulates operatorStack and/or operandStack to process the open bracket
     * according to the Infix-to-Postfix and Postfix-evaluation algorithms.
     * @param openBracket the open bracket token that was encountered
     */
    
    void handleOpenBracket(char openBracket) {
        // TODO: Complete this method
        operatorStack.push(openBracket);
    }

    /**
     * This method is called when the evaluator encounters a close bracket. It
     * manipulates operatorStack and/or operandStack to process the close
     * bracket according to the Infix-to-Postfix and Postfix-evaluation
     * algorithms.
     * @param closeBracket the close bracket token that was encountered
     */
    void handleCloseBracket(char closeBracket) {
        // TODO: Complete this method
        char anotherOperator;
        if (closeBracket == ')') {
            anotherOperator = '(';
        } 
        else {
            anotherOperator = '{';
        }
        while (!operatorStack.isEmpty() && operatorStack.peek() != anotherOperator) {
            handle();
        }
        if (operatorStack.isEmpty()) {
            throw  new ExpressionError("The expression is illegal");
        }
        if (operatorStack.peek() == anotherOperator) {
            operatorStack.pop();
        }
        
    }

    /**
     * This method is called when the evaluator encounters the end of an
     * expression. It manipulates operatorStack and/or operandStack to process
     * the operators that remain on the stack, according to the Infix-to-Postfix
     * and Postfix-evaluation algorithms.
     */
    void handleRemainingOperators() {
        // TODO: Complete this method
        while(!operatorStack.isEmpty()){
            handle();
        }
    }


    /**
     * Creates an InfixExpressionEvaluator object to read from System.in, then
     * evaluates its input and prints the result.
     * @param args not used
     */
    public static void main(String[] args) {
        System.out.println("Infix expression:");
        InfixExpressionEvaluator evaluator =
                        new InfixExpressionEvaluator(System.in);
        Double value = null;
        try {
            value = evaluator.evaluate();
        } catch (ExpressionError e) {
            System.out.println("ExpressionError: " + e.getMessage());
        }
        if (value != null) {
            System.out.println(value);
        } else {
            System.out.println("Evaluator returned null");
        }
    }


    private boolean checkIsAdvanced(char operator) {
        
        if (operatorStack.isEmpty()) {
            return false;
        }
        Map<Character, Integer> rank = new HashMap<Character, Integer>();
        
        rank.put('(', 1);
        rank.put(')', 1);
        rank.put('{', 1);
        rank.put('}', 1);
        rank.put('+', 2);
        rank.put('-', 2);
        rank.put('*', 3);
        rank.put('/', 3);
        rank.put('^', 4);
        
        if (rank.get(operator) <= rank.get(operatorStack.peek())) {
            return true;
        }
        return false;
    }

    private void handle(){
        double operandB;
        double operandA;
        char operator;

        if(operandStack.isEmpty() || operatorStack.isEmpty()){
            throw new ExpressionError("The expression is illegal");     
        }else{
            operandB = operandStack.pop();
            operator = operatorStack.pop();
        }


        if(!operandStack.isEmpty()){
            operandA = operandStack.pop();
        }else{
            throw new ExpressionError("The expression is illegal");
        }

        double result = 0.0;
        switch(operator){
            case '+':
                result = operandA + operandB;
                break; 
            case '-':
                result = operandA - operandB;
                break; 
            case '*':
                result = operandA * operandB;
                break; 
            case '/':
                if(operandB == 0)
                    throw new ExpressionError("Divided-by-zero is illegal");
                result = operandA / operandB;
                break; 
            case '^':
                result = Math.pow(operandA,operandB);
                break; 
            default:
                throw new ExpressionError("Unrecognized operator:" + operator);
        }
        operandStack.push(result);
    }

}

//corner cases that the expressions are illegal:
	//operand followed by operand
	//operator followed byoperator
	//open bracket follwed by close bracket
	//close bracket follwed by open bracket
	//open bracket followed by a operator
	//operand followed by open bracket
	//close bracket followed by operand
//How? -keep track of the last one you saw and tracked back the former one, then find out the corner cases with an error message(handle the exception)

