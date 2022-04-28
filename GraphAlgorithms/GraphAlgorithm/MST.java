package GraphAlgorithm;

/**
 * @className: MST - minimum spanning tree
 * @description: find an acyclic path with minimum weight
 * @author: YORE
 * @date: 2022/4/28
 **/

import GraphAlgorithm.Graph.*;

import java.util.LinkedList;
import java.util.PriorityQueue;


public class MST extends GraphAlgorithm<MST.MSTVertex>{
    String algorithm;
    public long wmst;               // weight of MST
    public LinkedList<Edge> mst;    // MST

    /*
     * Construction for MST
     */
    public MST(Graph g) {
        super(g, new MSTVertex(null));
        mst = new LinkedList<>();
        wmst = 0;
    }

    /**
     * @className: MSTVertex
     * @description:
     */
    public static class MSTVertex implements Comparable<MSTVertex>, Factory {

        public MSTVertex(Vertex u) {

        }

        public MSTVertex make(Vertex u) {return new MSTVertex(u);}

        public int compareTo(MSTVertex other) {
            return 0;
        }
    }

    public static MST kruskal(Graph g) {
        MST m = new MST(g);
        m.algorithm = "Kruskal";
        Edge[] edgeArray = g.getEdgeArray();
        return m;
    }

    public  static MST prim(Graph g, Vertex s) {
        MST m = new MST(g);
        m.algorithm = "Prim with PriorityQueue<Edge>";
        PriorityQueue<Edge> q = new PriorityQueue<>();
        return m;
    }

    // No changes need to be made below this

    public static MST mst (Graph g, Vertex s, String choice) {
        if(choice.equals("Kruskal")) {
            return kruskal(g);
        } else {
            return prim(g, s);
        }
    }

    public static void main(String[] args) throws java.io.FileNotFoundException {
        java.util.Scanner in;
        String choice = "Prim";
        if (args.length == 0 || args[0].equals("-")) {
            in = new java.util.Scanner(System.in);
        } else {
            java.io.File inputFile = new java.io.File(args[0]);
            in = new java.util.Scanner(inputFile);
        }

        if (args.length > 1) { choice = args[1]; }

        Graph g = Graph.readGraph(in);
        Vertex s = g.getVertex(1);

        Timer timer = new Timer();
        MST m = mst(g, s, choice);
        System.out.println("Algorithm: " + m.algorithm + "\n" + m.wmst);
        System.out.println(timer.end());
    }

}
