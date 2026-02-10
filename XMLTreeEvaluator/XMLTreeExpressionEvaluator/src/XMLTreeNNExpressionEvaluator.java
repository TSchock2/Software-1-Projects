import components.naturalnumber.NaturalNumber;
import components.naturalnumber.NaturalNumber2;
import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;
import components.utilities.Reporter;
import components.xmltree.XMLTree;
import components.xmltree.XMLTree1;

/**
 * Program to evaluate XMLTree expressions of {@code int}.
 *
 * @author Tyler Schock
 *
 */
public final class XMLTreeNNExpressionEvaluator {

    /**
     * Private constructor so this utility class cannot be instantiated.
     */
    private XMLTreeNNExpressionEvaluator() {
    }

    /**
     * Evaluate the given expression.
     *
     * @param exp
     *            the {@code XMLTree} representing the expression
     * @return the value of the expression
     * @requires <pre>
     * [exp is a subtree of a well-formed XML arithmetic expression]  and
     *  [the label of the root of exp is not "expression"]
     * </pre>
     * @ensures evaluate = [the value of the expression]
     */
    private static NaturalNumber evaluate(XMLTree exp) {
        assert exp != null : "Violation of: exp is not null";

        NaturalNumber expValue = new NaturalNumber2();

        if (exp.label().equals("number")) {
            expValue = new NaturalNumber2(exp.attributeValue("value"));
        } else {
            String operator = exp.label();
            XMLTree child0 = exp.child(0);
            XMLTree child1 = exp.child(1);
            expValue.transferFrom(evaluate(child0));

            if (operator.equals("plus")) {
                expValue.add(evaluate(child1));
            } else if (operator.equals("divide")) {
                NaturalNumber childOne = new NaturalNumber2(
                        evaluate(exp.child(1)));
                if (childOne.isZero()) {
                    Reporter.fatalErrorToConsole("Error: Dividing by zero");
                } else {
                    expValue.divide(evaluate(child1));
                }
            } else if (operator.equals("times")) {
                expValue.multiply(evaluate(child1));
            } else if (operator.equals("minus")) {
                NaturalNumber childZero = new NaturalNumber2(
                        evaluate(exp.child(0)));
                NaturalNumber childOne = new NaturalNumber2(
                        evaluate(exp.child(1)));

                if (childOne.compareTo(childZero) > 0) {
                    Reporter.fatalErrorToConsole(
                            "Creating a negative natural number");
                } else {
                    expValue.subtract(evaluate(child1));
                }
            }
        }

        return expValue;
    }

    /**
     * Main method.
     *
     * @param args
     *            the command line arguments
     */
    public static void main(String[] args) {
        SimpleReader in = new SimpleReader1L();
        SimpleWriter out = new SimpleWriter1L();

        out.print("Enter the name of an expression XML file: ");
        String file = in.nextLine();
        while (!file.equals("")) {
            XMLTree exp = new XMLTree1(file);
            out.println(evaluate(exp.child(0)));
            out.print("Enter the name of an expression XML file: ");
            file = in.nextLine();
        }

        in.close();
        out.close();
    }

}