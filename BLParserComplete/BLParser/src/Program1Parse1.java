import components.map.Map1L;
import components.program.Program;
import components.program.Program1;
import components.queue.Queue;
import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;
import components.statement.Statement;
import components.statement.Statement1;
import components.utilities.Reporter;
import components.utilities.Tokenizer;

/**
 * Layered implementation of secondary method {@code parse} for {@code Program}.
 *
 * @author Jared Koharik and......
 *
 */
public final class Program1Parse1 extends Program1 {

    /*
     * Private members --------------------------------------------------------
     */

    /**
     * Parses a single BL instruction from {@code tokens} returning the
     * instruction name as the value of the function and the body of the
     * instruction in {@code body}.
     *
     * @param tokens
     *            the input tokens
     * @param body
     *            the instruction body
     * @return the instruction name
     * @replaces body
     * @updates tokens
     * @requires <pre>
     * [<"INSTRUCTION"> is a prefix of tokens]  and
     *  [<Tokenizer.END_OF_INPUT> is a suffix of tokens]
     * </pre>
     * @ensures <pre>
     * if [an instruction string is a proper prefix of #tokens]  and
     *    [the beginning name of this instruction equals its ending name]  and
     *    [the name of this instruction does not equal the name of a primitive
     *     instruction in the BL language] then
     *  parseInstruction = [name of instruction at start of #tokens]  and
     *  body = [Statement corresponding to the block string that is the body of
     *          the instruction string at start of #tokens]  and
     *  #tokens = [instruction string at start of #tokens] * tokens
     * else
     *  [report an appropriate error message to the console and terminate client]
     * </pre>
     */
    private static String parseInstruction(Queue<String> tokens,
            Statement body) {
        assert tokens != null : "Violation of: tokens is not null";
        assert body != null : "Violation of: body is not null";
        assert tokens.length() > 0 && tokens.front().equals("INSTRUCTION") : ""
                + "Violation of: <\"INSTRUCTION\"> is proper prefix of tokens";

        Reporter.assertElseFatalError(tokens.dequeue().equals("INSTRUCTION"),
                "Improper Formatting: Missing \"INSTRUCTION\" token");
        String instructionName = tokens.dequeue();
        Reporter.assertElseFatalError(Tokenizer.isIdentifier(instructionName),
                "Improper Formatting: Is not a proper Identifier");
        Reporter.assertElseFatalError(tokens.dequeue().equals("IS"),
                "Improper Formatting: Missing \"IS\" token");

        body.parseBlock(tokens);

        tokens.dequeue();
        Reporter.assertElseFatalError(instructionName.equals(tokens.dequeue()),
                "Instruction names do not match");

        // This line added just to make the program compilable.
        return instructionName;
    }

    /*
     * Constructors -----------------------------------------------------------
     */

    /**
     * No-argument constructor.
     */
    public Program1Parse1() {
        super();
    }

    /*
     * Public methods ---------------------------------------------------------
     */

    @Override
    public void parse(SimpleReader in) {
        assert in != null : "Violation of: in is not null";
        assert in.isOpen() : "Violation of: in.is_open";
        Queue<String> tokens = Tokenizer.tokens(in);
        this.parse(tokens);
    }

    @Override
    public void parse(Queue<String> tokens) {
        assert tokens != null : "Violation of: tokens is not null";
        assert tokens.length() > 0 : ""
                + "Violation of: Tokenizer.END_OF_INPUT is a suffix of tokens";

        Statement1 body = new Statement1();

        Map1L<String, Statement> context = new Map1L<>();

        Reporter.assertElseFatalError(tokens.dequeue().equals("PROGRAM"),
                "Improper Formatting: Missing \"PROGRAM\" token");
        this.setName(tokens.dequeue());
        Reporter.assertElseFatalError(tokens.dequeue().equals("IS"),
                "Improper Formatting: Missing \"IS\" token");

        while (!tokens.front().equals("BEGIN")
                && !tokens.front().equals(Tokenizer.END_OF_INPUT)) {

            Statement instructionBody = body.newInstance();
            String instructionName = parseInstruction(tokens, instructionBody);
            context.add(instructionName, instructionBody);
        }

        Reporter.assertElseFatalError(tokens.dequeue().equals("BEGIN"),
                "Improper Formatting: Missing token \"BEGIN\"");

        body.parseBlock(tokens);
        Reporter.assertElseFatalError(tokens.dequeue().equals("END"),
                "File does not end properly");
        Reporter.assertElseFatalError(this.name().equals(tokens.dequeue()),
                "Program names to not match at both ends of the file");
        Reporter.assertElseFatalError(
                tokens.dequeue().equals(Tokenizer.END_OF_INPUT),
                "There are extra tokens after the end of the program");

        this.swapContext(context);
        this.swapBody(body);

    }

    /*
     * Main test method -------------------------------------------------------
     */

    /**
     * Main method.
     *
     * @param args
     *            the command line arguments
     */
    public static void main(String[] args) {
        SimpleReader in = new SimpleReader1L();
        SimpleWriter out = new SimpleWriter1L();
        /*
         * Get input file name
         */
        out.print("Enter valid BL program file name: ");
        String fileName = in.nextLine();
        /*
         * Parse input file
         */
        out.println("*** Parsing input file ***");
        Program p = new Program1Parse1();
        SimpleReader file = new SimpleReader1L(fileName);
        Queue<String> tokens = Tokenizer.tokens(file);
        file.close();
        p.parse(tokens);
        /*
         * Pretty print the program
         */
        out.println("*** Pretty print of parsed program ***");
        p.prettyPrint(out);

        in.close();
        out.close();
    }

}
