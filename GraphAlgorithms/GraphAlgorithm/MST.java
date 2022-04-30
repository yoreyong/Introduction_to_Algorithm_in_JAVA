package GraphAlgorithm;

/**
 * @className: MST - minimum spanning tree
 * @description: find an acyclic path with minimum weight
 * @author: YORE
 * @date: 2022/4/28
 **/

import GraphAlgorithm.Graph.*;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.PriorityQueue;


public class MST extends GraphAlgorithm<MST.MSTVertex>{
    String algorithm;
    public long wmst;               // weight of MST
    public LinkedList<Edge> mst;    // MST
    Vertex src;

    // Debug define class
    public interface Debug {
        boolean DEBUG_PRINT = false;
        boolean TEST_PRINT = true;
        boolean PRINT = true;
    }

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
        boolean seen;
        Vertex parent;

        public MSTVertex(Vertex u) {
            seen = false;
            parent = null;
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
        m.wmst = 0;
        m.mst = new LinkedList<>();
        LinkedList<Vertex> mstV = new LinkedList<>();

        for(Vertex u : g) {
            m.get(u).seen = false;
            m.get(u).parent = null;
        }
        m.get(s).seen = true;
        mstV.add(s);

        PriorityQueue<Edge> q = new PriorityQueue<>();
        for(Edge e : g.incident(s)) {
            q.add(e);
        }
        if(Debug.DEBUG_PRINT) m.printPQ(q);

        while(!q.isEmpty()) {
            Edge e = q.remove();
            Vertex u = e.fromVertex();
            Vertex v;
            if(mstV.contains(u)) {
                v = e.otherEnd(u);
            } else {
                v = u;
                u = e.otherEnd(v);
            }
            if (!m.get(v).seen) {
                m.get(v).seen = true;
                m.get(v).parent = u;
                m.wmst = m.wmst + e.weight;
                mstV.add(v);    // add Vertex v to component S
                m.mst.add(e);   // add e to mst list

                for (Edge e2 : g.incident(v)) {
                    if (!m.get(e2.otherEnd(v)).seen) {
                        q.add(e2);
                    }
                }
                if(Debug.DEBUG_PRINT) m.printPQ(q);
            }
        }
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

    /*
     * Method: Help Method - print priority queue.
     */
    public void printPQ(PriorityQueue<Edge> pq) {
        PriorityQueue<Edge> p  = new PriorityQueue<>(pq);
        System.out.println("______________________________________________");
        System.out.print("Priority Queue = [ ");
        while(!p.isEmpty()) {
            System.out.print(p.remove() + " ");
        }
        System.out.println();
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
        g.printGraph(true);

        Vertex s = g.getVertex(6);

        Timer timer = new Timer();
        MST m = mst(g, s, choice);
        System.out.println("Algorithm: " + m.algorithm + "\n" + m.wmst);
        System.out.println(timer.end());
    }

}
