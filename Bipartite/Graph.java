import components.array.Array;
import components.array.Array1L;
import components.set.Set;
import components.set.Set2;

/**
 * Graph class.
 *
 * @author Harsh Patel
 *
 * @convention <pre>
 * $this is a simple graph
 *  - no repeated edges
 *  - no self loops
 *
 * </pre>
 */
public class Graph implements GraphInterface {

    /**
     * Vertices in @code this
     */
    private Array<Set<Integer>> graph;

    /**
     * Edges in @code this
     */

    /**
     * Creates initial representation of @code this with n number of vertices
     */
    private void createNewRep(int n) {
        // Create empty adjacency list
        this.graph = new Array1L<>(n);
        for (int i = 0; i < this.graph.length(); i++) {
            this.graph.setEntry(i, new Set2<>());
        }
    }

    /**
     * Constructor for graph.
     *
     * @param n
     *            number of vertices
     */
    public Graph(int n) {
        this.createNewRep(n);
    }

    @Override
    public void addEdge(int vertice1, int vertice2) {
        // Only add edge if graph does not contain it and it is not a selfloop
        if (!this.graph.entry(vertice1).contains(vertice2)) {
            this.graph.entry(vertice1).add(vertice2);
            this.graph.entry(vertice2).add(vertice1);

        }
    }

    @Override
    public void removeEdge(int vertice1, int vertice2) {
        // Remove vertice2 from vertice1 and vise versa
        this.graph.entry(vertice1).remove(vertice2);
        this.graph.entry(vertice2).remove(vertice1);
    }

    @Override
    public boolean areAdjacent(int vertice1, int vertice2) {
        // Only need to check one of the vertices
        return (this.graph.entry(vertice1).contains(vertice2));
    }

    @Override
    public int degree(int vertice) {
        return this.graph.entry(vertice).size();
    }

    @Override
    public int numberOfVertices() {
        return this.graph.length();
    }

    /**
     * For testing purposes.
     */
    public void displayList() {
        for (int i = 0; i < this.graph.length(); i++) {
            System.out.print(i + ": ");
            for (int entry : this.graph.entry(i)) {
                System.out.print(entry + ", ");
            }
            System.out.println();
        }
    }

    @Override
    public int[] adjacentVertices(int vertice) {
        int[] adjacent = new int[this.degree(vertice)];
        // Keep track of elements in adjacent
        int n = 0;
        for (int i = 0; i < this.numberOfVertices(); i++) {
            if (this.areAdjacent(vertice, i)) {
                adjacent[n] = i;
                n++;
            }
        }
        return adjacent;
    }

}
