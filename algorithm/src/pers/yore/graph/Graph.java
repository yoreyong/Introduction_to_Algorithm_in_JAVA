package pers.yore.graph;

/*
 * @className: Graph
 * @description: Graph class, YY照着老师给的文件
 * @author: YORE
 * @date: 2022/4/1
 */

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;


public class Graph implements Iterable<Graph.Vertex> {
    AdjList[] adjList;
    int n;  // number of vertices in the graph
    int m;  // number of edges in the graph
    final boolean directed; //true if graph is directed, false otherwise


    /**
     * @className: Vertex -
     * @description: Nested class: Class to represent a vertex of graph.
     */

    public class Vertex {
        int name;   // name of the vertex

        // ****************** CONSTRUCTORS for Vertex *********************
        /**
         * @description: Constructor for vertex.
         *
         * @param:  n   : int - name of the vertex
         */
        public Vertex(int n) {
            name = n;
        }

        /**
         * @description: Constructor for vertex, to be used in applications that need to extend vertex.
         *
         * @param:  u    : Vertex - the vertex to be cloned
         */
        public Vertex(Vertex u) {
            name = u.name;
        }

        // ****************** METHODS for Vertex*********************

        /**
         * @description: Number of outgoing edges from the vertex.
         *
         * @param:  null
         * @return: # of edge   : int -
         */
        public int outDegree() {
            return (adj(this).outEdges.size());
        }

        /**
         * @description: Number of outgoing edges from the vertex.
         *
         * @param:  null
         * @return: # of edge   : int -
         */
        public int inDegree() {
            return (adj(this).inEdges.size());
        }

        /**
         * @description: Method to get name of vertex.
         *
         * @param:  null
         * @return: name    : int - name of vertex
         */
        public int getName() {
            return name;
        }

        /**
         * @description: Index i stores vertex with name i + 1.
         *
         * @param:  null
         * @return: index i : int -
         */
        public int getIndex() {
            return (name - 1);
        }

        /**
         * @description: HashCode of a vertex can be its name, since name is unique
         *
         * @param:  null
         * @return: name    : int -
         */
        public int hashCode() {
            return name;
        }

        /**
         * @description: equals
         *
         * @param:
         * @return:
         */
        public boolean equals(Object other) {
            Vertex otherVertex = (Vertex) other;   // cast
            if(otherVertex == null) {
                return false;
            }
            return this.name == otherVertex.name;
        }

        /**
         * @description: Method to get vertex name.
         *
         * @param:  null
         * @return: name    : String - vertex name
         */
        public String toString() {
            return (Integer.toString(name));
        }
    }


    /**
     * @className: Edge -
     * @description: Nested class: Class to represent an edge of graph.
     */

    public class Edge implements Comparable<Edge> {
        Vertex from;    // head vertex
        Vertex to;      // tail vertex
        int weight;     // weight of edge
        int name;       // name of edge

        // ****************** CONSTRUCTORS for Edge *********************

        /**
         * @description: Constructor for Edge
         *
         * @param:  u   : Vertex - Vertex from which edge starts
         * @param:  v   : Vertex - Vertex on which edge lands
         * @param:  w   : int - weight of edge
         * @param:  n   : int - name of edge
         */
        public Edge (Vertex u, Vertex v, int w, int n) {
            from = u;
            to = v;
            weight = w;
            name = n;
        }


        // ****************** METHODS for Edge *********************

        /**
         * @description: Method to get vertex incident to edge ar "from" end.
         *
         * @param:  null
         * @return: from    : Vertex - Vertex from which edge starts
         */
        public Vertex fromVertex() {
            return from;
        }

        /**
         * @description: Method to get vertex incident to edge ar "to" end.
         *
         * @param:  null
         * @return: to    : Vertex - Vertex from which edge starts
         */
        public Vertex toVertex() {
            return to;
        }

        /**
         * @description: Method to get weight of edge.
         *
         * @param:  null
         * @return: weight  : int - weight of edge
         */
        public int getWeight() {
            return weight;
        }

        /**
         * @description: Method to set weight of edge.
         *
         * @param:  newWeight   : int - new weight of edge
         * @return: oldWeight   : int - old weight of edge
         */
        public int setWeight(int newWeight) {
            int oldWeight = weight;
            weight = newWeight;
            return oldWeight;
        }

        /**
         * @description: Method to get name of edge.
         *
         * @param:  null
         * @return: name    : int - name of edge
         */
        public int getName() {
            return name;
        }

        /**
         * Set the name of an Edge
         */
        public void setName(int n) {
            name = n;
        }

        /**
         * @description: Method to find the other end of an edge, given a vertex reference.
         *                 This method is used for undirected graph.
         * @param:  u  : Vertex -
         * @return: to / from  : Vertex - other end of edge
         */
        public Vertex otherEnd(Vertex u) {
            assert from.equals(u) || to.equals(u);

            // if the vertex u is the head of the arc, then return the tail else return the head
            if(from.equals(u)) {
                return to;
            } else {
                return from;
            }
        }

        /**
         * @description: To use hashing with Edge as key, you need to ensure that name is unique.
         *
         * @param:  null
         * @return: null
         */
        public int hashCode() {
            return name;
        }

        /**
         * @description: Edges are equal if they have the same name and connect same ends.
         *
         * @param:  other  : Object -
         * @return: true / false
         */
        public boolean equals(Object other) {
            if(other == null) {
                return false;
            }
            Edge otherEdge = (Edge) other;  // cast object to Edge
            return (this.name == otherEdge.name && this.from.equals(otherEdge.from) && this.to.equals(otherEdge.to));
        }

        /**
         * @description: Compare weights of two edges.
         *
         * @param:  other  : Edge -
         * @return: 1  : this.weight > other.weight
         *         -1  : this.weight < other.weight
         *          0  : this.weight = other.weight
         */
        public int compareTo(Edge other) {
            if(other == null || this.weight > other.weight) {
                return 1;
            } else if(this.weight < other.weight) {
                return -1;
            } else {
                return 0;
            }
        }

        /**
         * @description: Return the string "(x,y)", where edge goes from x to y
         *
         * @param:  null
         * @return: "(x,y)"    : String -
         */
        public String toString() {
            return "(" + from + "," + to + ")";
        }

        public String stringWithSpaces() {
            return from + " " + to + " " + weight;
        }
    }


    // ****************** CONSTRUCTORS for Graph *********************
    // default is undirected graph
    public Graph(int n) {
        directed = false;
        initialize(n);
    }

    public Graph(int n, boolean directed) {
        this.directed = directed;
        initialize(n);
    }

    public Graph(Graph g) {
        this.adjList = g.adjList;
        this.directed = g.directed;
        this.n = g.n;
        this.m = g.m;
    }

    // ****************** METHODS for Graph*********************
    /**
     * @description: Create an array of Vertex objects. Index 0 stores vertex 1.
     *
     * @param:  n   : int - number of vertices in the graph
     * @return: null
     */
    void initialize(int n) {
        adjList = new AdjList[n];
        this.n = n;
        m = 0;
        // create an array of Vertex objects. Index 0 stores vertex 1.
        for (int i = 0; i < n; i++) {
            adjList[i] = new AdjList(i + 1);    // index i stores vertex named i + 1
        }
    }

    /** add a new edge to graph */
    public Edge addEdge(Vertex from, Vertex to, int weight, int name) {
        Edge e = new Edge(from, to, weight, name);
        if(directed) {
            adj(from).outEdges.add(e);
            adj(to).inEdges.add(e);
        } else {
            adj(from).outEdges.add(e);
            adj(to).outEdges.add(e);
        }
        m++;  // Increment edge count
        return e;
    }

    /**
     * @description: Add new Edge to graph, by index of vertices
     *
     * @param:
     * @return: e   : Edge -
     */
    public Edge addEdge(int fromIndex, int toIndex, int weight) {
        m++;    // number of edge + 1
        Edge e = new Edge(adj(fromIndex).vertex, adj(toIndex).vertex, weight, m);
        if(directed) {  // directed graph
            adj(fromIndex).outEdges.add(e);
            adj(toIndex).inEdges.add(e);
        } else {    // non-directed graph
            adj(fromIndex).outEdges.add(e);
            adj(toIndex).outEdges.add(e);
        }
        return e;
    }

    /**
     * @description: Get the number of vertices in graph.
     *
     * @param:  null
     * @return: n   : int  - number of vertices in graph
     */
    public int size() {
        return n;
    }

    /**
     * @description: Get the number og edges in graph.
     *
     * @param:  null
     * @return: m   : int - number og edges in graph
     */
    public int edgeSize() {
        return m;
    }

    /**
     * @description: Is the graph directed?
     *
     * @param:  null
     * @return: directed    : boolean - true, directed; false, non-directed
     */
    public boolean isDirected() {
        return directed;
    }

    /**
     * @description: Method to reverse the edge of a graph. Applicable to directed graphs only.
     *
     * @param:  null
     * @return: null
     */
    public void reverseGraph() {
        if(directed) {
            for(AdjList list : adjList) {
                List<Edge> tmp = list.outEdges;
                list.outEdges = list.inEdges;
                list.inEdges = tmp;
            }
        }
    }

    /**
     * @description: Method to create iterator for vertices of graph.
     *
     * @param:
     * @return:
     */
    public Iterator<Vertex> iterator() {
        return new GraphIterator();
    }

    /**
     * @description: Method to go through edges incident at vertex u.
     *                  Can be used with implicit/explicit iterators.
     *
     * @param:
     * @return:
     */
    public Iterable<Edge> incident(Vertex u) {
        return adj(u).outEdges;
    }

    /**
     * @description: Method to return the outgoing edges of a directed graph as an iterable object.
     *
     * @param:
     * @return:
     */
    public Iterable<Edge> outEdges(Vertex u) {
        return adj(u).outEdges;
    }

    /**
     * @description: Method to return the incoming edges of a directed graph as an iterable object.
     *
     * @param:
     * @return:
     */
    public  Iterable<Edge> inEdges(Vertex u) {
        return adj(u).inEdges;
    }

    /**
     * @description: Return an array containing the vertices of the graph.
     *
     * @param:
     * @return: arr : Vertex[] -
     */
    public Vertex[] getVertexArray() {
        Vertex[] arr = new Vertex[size()];
        for(Vertex u : this) {
            arr[u.getIndex()] = u;
        }
        return arr;
    }

    //
    public Edge[] getEdgeArray() {
        Edge[] edgeArray = new Edge[edgeSize()];
        int index = 0;
        for(Vertex u : this) {
            for(Edge e : this.incident(u)) {
                Vertex v = e.otherEnd(u);
                if(isDirected() || u.getName() < v.getName()) {
                    edgeArray[index++] = e;
                }
            }
        }
        assert (index == edgeSize());
        return edgeArray;
    }

    /**
     * @className: GraphIterator -
     * @description: Nested class: Iterator class for the vertices of a graph.
     */
    private class GraphIterator implements Iterator<Vertex> {
        ArrayIterator<AdjList> it;
        AdjList cur;

        GraphIterator() {
            it = new ArrayIterator<>(adjList);
        }

        public boolean hasNext() {
            return it.hasNext();
        }

        public Vertex next() {
            cur = it.next();
            return cur.vertex;
        }

        public void remove() {
            throw new java.lang.UnsupportedOperationException();
        }

    }


    /**
     * @description: Help method: print a graph.
     *
     * @param:  hasEdgeWeights  : boolean - true, edges have weight; false, no weight
     * @return:
     */
    public void printGraph(boolean hasEdgeWeights) {
        System.out.println("______________________________________________");
        System.out.println("Graph: n: " + size() + ", m: " + edgeSize() + ", directed: " + directed + ", Edge weights: " + hasEdgeWeights);
        for(Vertex u : this) {
            System.out.print(u + " : ");
            for(Edge e : incident(u)) {
                if(hasEdgeWeights) {
                    System.out.print(" " + e + "[" + e.weight + "]");
                } else {
                    System.out.print(" " + e);
                }
            }
            System.out.println();
        }
        System.out.println("______________________________________________");
    }


    /**
     * @description: Read a directed graph using the Scanner interface
     *
     * @param:
     * @return:
     */
    public static Graph readDirectedGraph(Scanner in) {
        return readGraph(in, true);
    }

    /**
     * @description: Read an undirected graph using the Scanner interface
     *
     * @param:
     * @return:
     */
    public static Graph readGraph(Scanner in) {
        return readGraph(in, false);
    }

    /**
     * @description: readGraph
     *
     * @param:
     * @return:
     */
    public static Graph readGraph(Scanner in, boolean directed) {
        // read the graph related parameters
        int n = in.nextInt();   // number of vertices in the graph
        int m = in.nextInt();   // number of edges in the graph

        // create a graph instance
        Graph g = new Graph(n, directed);
        for (int i = 1; i <= m; i++) {
            int u = in.nextInt();
            int v = in.nextInt();
            int w = in.nextInt();
            g.addEdge(g.getVertex(u), g.getVertex(v), w, i);
        }
        return g;
    }

    /**
     * Interface used by classes that store properties of vertices during graph algorithms
     */
    public interface Factory {
        public Factory make(Vertex u);
    }


    // Utilities of Graph class:
    /**
     * Find vertex no. n
     * @param n
     *           : int
     */
    public Vertex getVertex(int n) {
        return adjList[n - 1].vertex;
    }

    // This method is used when a graph has been cloned to get g's vertex from its clone
    public Vertex getVertex(Vertex u) {
        return getVertex(u.getName());
    }

    // Helper function for parallel arrays used to store vertex attributes
    public static<T> T getVertex(T[] node, Vertex u) {
        return node[u.getIndex()];
    }


    /**
     * @className: AdjList - adjacency list, outEdges and InEdges
     * @description: Nested class: Class to store an adjacency list of a vertex.
     */
    public class AdjList {
        Vertex vertex;  // vertex
        List<Edge> outEdges, inEdges;

        // ****************** CONSTRUCTORS for AdjList *********************
        /**
         * @description: Create an empty AdjList entry for Vertex named n.
         *
         * @param:  n:  int - Vertex name
         */
        public AdjList(int n) {
            vertex = new Vertex(n);
            outEdges = new LinkedList<>();  // incident edges of vertex in undirected graph,
            // or its outgoing edges in a directed graph
            inEdges = new LinkedList<>();   // empty list if undirected graph;
            // list of incoming edges at vertex if directed graph
        }

    }

    /**
     * @description: Return the adjacency list of vertex u.
     *
     * @param:  u   : int - Vertex u
     * @return: adjList[i]  : AdjList - adjacency list of vertex index
     */
    public AdjList adj(Vertex u) {
        return adjList[u.getIndex()];   // u.getIndex() = u.name - 1
    }

    /**
     * @description: Return the adjacency list of vertex stored at index.
     *
     * @param:  index   : int - index of vertex stored in AdjList[]
     * @return: adjList[index]  : AdjList - adjacency list of vertex index
     */
    public AdjList adj(int index) {
        return adjList[index];
    }


    /** Class to store properties of vertices in a class V that implements Factory.
     *  A parallel array is created to store properties of all vertices.
     *  Class GraphAlgorithm creates a Store from its constructor.
     */
    public class Store<V extends Factory> {
        // use a parallel array for storing information about vertices
        Factory[] node;
        Factory vertexFactory;

        public Store(Factory vf) {
            vertexFactory = vf;
            node = new Factory[size()];
            for(Vertex u: Graph.this) {
                node[u.getIndex()] = vf.make(u);
            }
        }

        public V get(Vertex u) {
            return (V) node[u.getIndex()];
        }

        public V put(Vertex u, V value) {
            V oldValue = (V) node[u.getIndex()];
            node[u.getIndex()] = value;
            return oldValue;
        }
    }

    /** Class to facilitate writing graph algorithms, where a generic class V
     *  is used to store attributes of vertices during the algorithm.
     */
    public static class GraphAlgorithm<V extends Factory> {
        public Graph g;
        Factory vf;
        Store<V> store;

        /**
         * @param: a graph g and a sample node vf used to create additional nodes using the Factory interface
         */
        public GraphAlgorithm(Graph g, Factory vf) {
            this.g = g;
            this.vf = vf;
            store = g.new Store<>(vf);
        }

        /** Return the object that stores the attributes of vertex u
         */
        public V get(Vertex u) {
            return store.get(u);
        }

        /** Put the object storing attributes of u in store
         */
        public V put(Vertex u, V value) {
            return store.put(u, value);
        }
    }

    /**
     * @className:
     * @description: Nested class: Iterator class to iterate over an array or subarray. Remove is not implemented.
     */
    public static class ArrayIterator<T> implements Iterator<T> {
        T[] arr;
        int startIndex, endIndex, cursor;

        /**
         * Iterate over the entire array
         */
        public ArrayIterator(T[] a) {
            arr = a;
            startIndex = 0;
            endIndex = a.length - 1;
            cursor = -1;
        }

        /**
         * Iterate over a[start..end], both inclusive.
         */
        public ArrayIterator(T[] a, int start, int end) {
            arr = a;
            startIndex = start;
            endIndex = end;
            cursor = start - 1;
        }

        public boolean hasNext() {
            return (cursor < endIndex);
        }

        public T next() {
            return arr[++cursor];
        }

        public void remove() {
            throw new java.lang.UnsupportedOperationException();
        }

    }


    /** Timer class for roughly calculating running time of programs
     *  @author rbk
     *  Usage:  Timer timer = new Timer();
     *          timer.start();
     *          timer.end();
     *          System.out.println(timer);  // output statistics
     */
    public static class Timer {
        long startTime, endTime, elapsedTime, memAvailable, memUsed;
        boolean ready;

        public Timer() {
            startTime = System.currentTimeMillis();
            ready = false;
        }

        public void start() {
            startTime = System.currentTimeMillis();
            ready = false;
        }

        public Timer end() {
            endTime = System.currentTimeMillis();
            elapsedTime = endTime-startTime;
            memAvailable = Runtime.getRuntime().totalMemory();
            memUsed = memAvailable - Runtime.getRuntime().freeMemory();
            ready = true;
            return this;
        }

        public long duration() {
            if(!ready) {
                end();
            }
            return elapsedTime;
        }

        public long memory() {
            if(!ready) {
                end();
            }
            return memUsed;
        }

        public String toString() {
            if(!ready) {
                end();
            }
            return "Time: " + elapsedTime + " msec.\n" + "Memory: " + (memUsed/1048576) + " MB / " + (memAvailable/1048576) + " MB.";
        }

    }

}

