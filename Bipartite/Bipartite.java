import components.set.Set;
import components.set.Set2;
import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;

/**
 * Reads a graph from a file and outputs whether the graph is complete
 * bipartite, bipartite, or neither.
 *
 * @author Harsh Patel
 *
 * @inputFileRequirements <pre>
 * 1. The first line contains the number of vertices (n). It is
 *    assumed that vertices are numbers 0 to n-1
 * 2. Each successive line contains two integers separated by a comma.
 *    The first integer is the starting vertex for the edge, and the second
 *    integer is the ending vertex to the edge.
 *            </pre>
 *
 */
public class Bipartite {

    /**
     * Returns the first integer or "separator string" (maximal length string of
     * characters in {@code separators}) in the given {@code text} starting at
     * the given {@code position}.
     *
     * @param text
     *            the {@code String} from which to get the integer or separator
     *            string
     * @param position
     *            the starting index
     * @param separators
     *            the {@code Set} of separator characters
     * @return the integer or separator that is in text
     * @requires 0 <= position < |text|
     * @ensures nextIntOrSeparator = an int or a string of @code separators
     */
    public static String nextIntOrSeparator(String text, int position,
            Set<Character> separators) {
        assert text != null : "Violation of: text is not null";
        assert separators != null : "Violation of: separators is not null";
        assert 0 <= position : "Violation of: 0 <= position";
        assert position < text.length() : "Violation of: position < |text|";

        String number = "";
        boolean isSeparator = false;
        int i = position;

        if (separators.contains(text.charAt(position))) {
            number += text.charAt(position);
        }

        StringBuffer buf = new StringBuffer();
        while (i < text.length() && !separators.contains(text.charAt(i))
                && !isSeparator) {
            if (!separators.contains(text.charAt(i))) {
                buf.append(text.charAt(i));
            } else {
                isSeparator = true;
            }
            number = buf.toString();
            i++;
        }

        return number;
    }

    /**
     * Imports a graph from a file input
     *
     * @param line
     *            the line in the file to start at
     *
     * @param graph
     *            the graph object to import to
     * @param fileIn
     *            the file to import from
     * @param line
     *            the line to start importing in the file
     * @requires fileIn to be in the correct format
     *
     */
    public static void getGraph(Graph graph, SimpleReader fileIn, String line) {
        // The set of separator characters
        Set<Character> separators = new Set2<>();
        separators.add(',');
        separators.add('\n');
        separators.add(' ');

        // Get graph from file
        line = fileIn.nextLine();
        while (!fileIn.atEOS()) {
            int position = 0;
            while (position < line.length()) {

                //Iterate over line until a number is found, put into vertice1
                String number = nextIntOrSeparator(line, position, separators);
                position += number.length();
                while (separators.contains(number.charAt(0))) {
                    number = nextIntOrSeparator(line, position, separators);
                    position += number.length();
                }
                int vertice1 = Integer.parseInt(number);

                //Same thing for vertice2
                number = nextIntOrSeparator(line, position, separators);
                position += number.length();
                while (separators.contains(number.charAt(0))) {
                    number = nextIntOrSeparator(line, position, separators);
                    position += number.length();
                }
                int vertice2 = Integer.parseInt(number.trim());

                // Add edge between the two vertices
                graph.addEdge(vertice1, vertice2);
            }
            line = fileIn.nextLine();
        }
    }

    /**
     * Returns if a graph is complete bipartite, bipartite, or neither
     *
     * @param graph
     *            the graph to check
     * @param colored
     *            an array to track what color each vertex holds in the two
     *            coloring.
     * @returns <pre>
     *          1 if graph is biparte
     *          2 if graph is complete biparte
     *          0 if graph is neither
     * </pre>
     */
    public static int isBipartite(Graph graph, int[] colored) {
        int result = 2;
        int white = 0;
        int black = 0;

        // Determine if graph is biparte or neither
        for (int i = 0; i < graph.numberOfVertices(); i++) {
            // Add first color to noncolored vertices, keep count of white
            if (colored[i] == 0) {
                colored[i] = 1;
                white++;
            }
            // Go through adjacent vertices and check what color they are
            for (int j = 0; j < graph.numberOfVertices(); j++) {
                // Keep count of how many black
                if (graph.areAdjacent(i, j)) {
                    if (colored[j] == colored[i]) {
                        result = 0;
                    } else if (colored[j] == 0) {
                        colored[j] = colored[i] * -1;
                        black++;
                    }
                }
            }
        }

        // To differentiate between biparte and complete biparte, check if the degree of
        // each vertex is equal to number of vertices with opposite color
        if (result == 2) {
            for (int i = 0; i < graph.numberOfVertices(); i++) {
                if (i % 2 != 1) {
                    if (graph.degree(i) != white) {
                        result = 1;
                    }
                } else {
                    if (graph.degree(i) != black) {
                        result = 1;
                    }
                }
            }
        }
        return result;
    }

    /**
     * Main method.
     *
     * @param args
     *            the command line arguments
     */

    public static void main(String[] args) {
        String fileName = "";
        SimpleReader in = new SimpleReader1L();

        // Get file name from either args or prompt
        if (args.length == 0) {
            System.out.println(
                    "Please enter an input file (include file path and extension):");
            fileName = in.nextLine();
        } else {
            fileName = args[0];
        }

        //Get first line (number of vertices)
        SimpleReader fileIn = new SimpleReader1L(fileName);
        String line = fileIn.nextLine();

        Graph elGraph = new Graph(Integer.parseInt(line.trim()));

        // Get graph from file
        getGraph(elGraph, fileIn, line);

        int[] colored = new int[elGraph.numberOfVertices()];

        // Output result
        int result = isBipartite(elGraph, colored);
        String output = "";

        if (result == 0) {
            output = "Congratulations, this graph is NEITHER complete bipartite or bipartite.";
        } else if (result == 1) {
            output = "Congratulations, this graph is BIPARTITE.";
        } else if (result == 2) {
            output = "Congratulations, this graph is COMPLETE BIPARTITE.";
        }

        System.out.println(output);
        System.out.println("Have a nice day.");

        fileIn.close();
        in.close();
    }
}
