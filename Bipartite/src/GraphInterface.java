/**
 * Graph Interface.
 *
 * @author Harsh Patel
 *
 * @model <pre>
 * $this is a simple graph
 * Edges (v1, v2) are between two different vertices v1 and v2
 *
 *</pre>
 */

public interface GraphInterface {

    /**
     * Adds an edge to a graph
     *
     * @param vertice1
     *            the starting vertice
     * @param vertice2
     *            the destination vertice
     * @updates this.edges
     * @ensures $this stays simple
     */
    void addEdge(int vertice1, int vertice2);

    /**
     * Removes an edge from a graph
     *
     * @param vertice1
     *            the first vertice
     * @param vertice2
     *            the second vertice
     * @requires (vertice1, vertice2) is in edges
     * @ensures $this stays simple
     */
    void removeEdge(int vertice1, int vertice2);

    /**
     * Returns if two vertices are adjacent
     *
     * @param vertice1
     *            the first vertice
     * @param vertice2
     *            the seconds vertice
     * @returns if there is an edge connecting vertice1 and vertice2
     */
    boolean areAdjacent(int vertice1, int vertice2);

    /**
     * Returns the degree of a vertice.
     *
     * @param vertice
     *            the vertice
     * @returns the number of edges connected to a vertice
     */
    int degree(int vertice);

    /**
     * Returns the number of vertices
     *
     * @returns number of elements in this.vertices
     */
    int numberOfVertices();

    /**
     * Returns an array of vertices that @code vertice is adjacent to. This
     * allows for easy iteration through adjacent vertices.
     *
     * @param vertice
     *            the vertice to inspect
     * @return array of vertice indexes that @code vertice is adjacent to
     */
    int[] adjacentVertices(int vertice);
}
