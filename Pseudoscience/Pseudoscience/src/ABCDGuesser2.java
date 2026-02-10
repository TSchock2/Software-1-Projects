import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;
import components.utilities.FormatChecker;

/**
 * Put a short phrase describing the program here.
 *
 * @author Put your name here
 *
 */
public final class ABCDGuesser2 {

    /**
     * Private constructor so this utility class cannot be instantiated.
     */
    private ABCDGuesser2() {
    }

    /**
     * Repeatedly asks the user for a positive integer until the user enters
     * one. Returns the positive integer.
     *
     * @param in
     *            the input stream
     * @param out
     *            the output stream
     * @return a positive integer entered by the user
     */
    private static double getPositiveDouble(SimpleReader in, SimpleWriter out) {

        boolean number = false;
        double posNum = -1.0;

        while (posNum < 0.0) {

            out.println("Enter a positive number for mu: ");
            String num = in.nextLine();
            if (FormatChecker.canParseDouble(num) != number) {
                posNum = Double.parseDouble(num);
            }
            if (FormatChecker.canParseInt(num) == number && posNum < 0) {
                out.println(
                        "Your input was not a number or the number you entered "
                                + "was not postitive, try again. ");
            }
        }
        return posNum;
    }

    /**
     * Repeatedly asks the user for a positive real number not equal to 1.0
     * until the user enters one. Returns the positive real number.
     *
     * @param in
     *            the input stream
     * @param out
     *            the output stream
     * @return a positive real number not equal to 1.0 entered by the user
     */
    private static double getPositiveDoubleNotOne(SimpleReader in,
            SimpleWriter out) {

        boolean grtrThanOne = false;
        double grtrOne = 0.0;
        double one = 2.0;
        int check = 0;

        while (grtrOne < one) {
            out.println("Enter a positive number greater than one: ");
            String num = in.nextLine();
            if (FormatChecker.canParseDouble(num) != grtrThanOne) {
                check = Integer.parseInt(num);
                grtrOne = Double.parseDouble(num);
            }
            if (FormatChecker.canParseDouble(num) == grtrThanOne
                    && check == 1) {
                out.println(
                        "The number you entered was not a number or less than one. "
                                + "Enter a positive number greater than one");
            }
        }
        return grtrOne;
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

        //Initializing variables
        double[] jager = { -5, -4, -3, -2, -1, -1 / 2, -1 / 3, -1 / 4, 0, 1 / 4,
                1 / 3, 1 / 2, 1, 2, 3, 4, 5 };
        boolean loop = false;
        double current = 0;
        double muCheck = 0;
        double check = 0;
        double a = 0;
        double b = 0;
        double c = 0;
        double d = 0;
        double percent = 100;

        //Call to methods for positive numbers and numbers > 1
        double mu = getPositiveDouble(in, out);

        double w = getPositiveDoubleNotOne(in, out);
        double x = getPositiveDoubleNotOne(in, out);
        double y = getPositiveDoubleNotOne(in, out);
        double z = getPositiveDoubleNotOne(in, out);

        //iterate through loops to find best approximation for mu
        for (int h = 0; loop != true; h++) {

            //find best value for w * a
            for (int i = 0; i < jager.length; i++) {
                a = jager[i];
                double wA = (Math.pow(w, a));

                //find best value for x * b
                for (int j = 0; j < jager.length; j++) {
                    b = jager[j];
                    double xB = (Math.pow(x, b));

                    //find best value for y * c
                    for (int k = 0; k < jager.length; k++) {
                        c = jager[k];
                        double yC = (Math.pow(y, c));

                        //find best value for z * d
                        for (int n = 0; n < jager.length; n++) {
                            d = jager[n];
                            double zD = (Math.pow(z, d));

                            //put values together from de jager
                            current = wA * xB * yC * zD;

                            /**
                             * check to see if check is within 1 percent and
                             * muCheck is close to mu
                             */
                            muCheck = Math.abs(mu - current);
                            check = (muCheck / mu) * percent;
                            if (check < 1 && check > 0) {
                                loop = true;
                            }
                        }
                    }
                }
            }
        }

        //output results
        out.println("The values for a, b, c, d with the least error are: " + a
                + ", " + b + ", " + c + ", " + d);
        out.println("Best approximation was: " + muCheck + " The error was: "
                + check);
        in.close();
        out.close();
    }
}
