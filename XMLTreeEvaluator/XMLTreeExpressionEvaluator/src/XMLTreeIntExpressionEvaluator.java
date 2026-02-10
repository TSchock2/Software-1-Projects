import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;
import components.xmltree.XMLTree;
import components.xmltree.XMLTree1;

/**
 * Program to evaluate XMLTree expressions of {@code int}.
 *
 * @author Tyler Schock
 *
 */
public final class XMLTreeIntExpressionEvaluator {

    /**
     * Private constructor so this utility class cannot be instantiated.
     */
    private XMLTreeIntExpressionEvaluator() {
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
    private static int evaluate(XMLTree exp) {
        assert exp != null : "Violation of: exp is not null";

        int childZero = 0;
        int childOne = 0;
        int expValue = 0;
        if (exp.label().equals("times")) {
            if (exp.child(0).label().equals("number")) {
                String child0 = exp.child(0).attributeValue("value");
                childZero = Integer.parseInt(child0);
            } else {
                childZero = evaluate(exp.child(0));
            }

            if (exp.child(1).label().equals("number")) {
                String child1 = exp.child(1).attributeValue("value");
                childOne = Integer.parseInt(child1);
            } else {
                childOne = evaluate(exp.child(1));
            }
            expValue = childZero * childOne;

        } else if (exp.label().equals("divide")) {

            if (exp.child(0).label().equals("number")) {
                String child0 = exp.child(0).attributeValue("value");
                childZero = Integer.parseInt(child0);

            } else {
                childZero = evaluate(exp.child(0));
            }

            if (exp.child(1).label().equals("number")) {
                String child1 = exp.child(1).attributeValue("value");
                childOne = Integer.parseInt(child1);
            } else {
                childOne = evaluate(exp.child(1));
            }
            expValue = childZero / childOne;

        } else if (exp.label().equals("plus")) {
            if (exp.child(0).label().equals("number")) {
                String child0 = exp.child(0).attributeValue("value");
                childZero = Integer.parseInt(child0);
            } else {
                childZero = evaluate(exp.child(0));
            }

            if (exp.child(1).label().equals("number")) {
                String child1 = exp.child(1).attributeValue("value");
                childOne = Integer.parseInt(child1);
            } else {
                childOne = evaluate(exp.child(1));
            }
            expValue = childZero + childOne;

        } else if (exp.label().equals("minus")) {

            if (exp.child(0).label().equals("number")) {
                String child0 = exp.child(0).attributeValue("value");
                childZero = Integer.parseInt(child0);
            } else {
                childZero = evaluate(exp.child(0));
            }

            if (exp.child(1).label().equals("number")) {
                String child1 = exp.child(1).attributeValue("value");
                childOne = Integer.parseInt(child1);
            } else {
                childOne = evaluate(exp.child(1));
            }
            expValue = childZero - childOne;

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