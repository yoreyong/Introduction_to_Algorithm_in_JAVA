package GraphAlgorithm;

/*
 * @className: DFS
 * @description: TODO 类描述
 * @author: YORE
 * @date: 2022/4/28
 */

import GraphAlgorithm.Graph.*;

import java.io.File;
import java.util.ArrayDeque;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class DFS extends Graph.GraphAlgorithm<DFS.DFSVertex>{
    public static final int INFINITY = Integer.MAX_VALUE;
    public enum status {New, Active, Finished}

    Vertex src;
    Queue<Vertex> startList = new LinkedList<>();
    Queue<Vertex> finishList = new LinkedList<>();
    static ArrayDeque<Vertex> topoStk = new ArrayDeque<>();

    public interface Debug {
        boolean DEBUG_PRINT = true;
        boolean TEST_PRINT = true;
        boolean PRINT = true;
    }

    // Class to store information about vertices during DFS
    public static class DFSVertex implements Factory {
        boolean mark;
        Vertex parent;
        int distance;
        status status;
        long start, finish;

        public DFSVertex(Vertex v) {
            mark = false;
            parent = null;
            distance = INFINITY;
            status = DFS.status.New;
            start = 0;
            finish = 0;
        }

        public DFSVertex make(Vertex v) {
            return new DFSVertex(v);
        }
    }

    // code to initialize storage for vertex properties is in GraphAlgorithm class
    public DFS(Graph g) {
        super(g, new DFSVertex(null));
        startList.clear();
        finishList.clear();
        topoStk.clear();
    }

    public void dfs(Vertex src) {
        get(src).mark = true;
        if(Debug.DEBUG_PRINT) System.out.print(src.getName() + " -> ");
        startList.add(src);

        for(Edge e : g.incident(src)) {
            Vertex w = e.otherEnd(src);
            if(!get(w).mark) {
                get(w).parent = src;
                get(w).distance = get(src).distance + 1;
                dfs(w);
            }
        }
        finishList.add(src);
        topoStk.push(src);
    }

    public static LinkedList<Vertex> topologicalList(Graph g) {
        LinkedList<Vertex> topologicalOrder = new LinkedList<>();
        while(!DFS.topoStk.isEmpty()) {
            topologicalOrder.add(DFS.topoStk.pop());
        }
        return topologicalOrder;
    }

    public static DFS depthFirstSearch(Graph g) {
        DFS d = new DFS(g);

        for(Vertex v : g) {
            d.get(v).mark = false;
            d.get(v).parent = null;
            d.get(v).status = status.New;
            d.get(v).distance = 0;
            d.get(v).start = 0;
            d.get(v).finish = 0;
        }

        for(Vertex v : g) {
            if(!d.get(v).mark) {
                d.dfs(v);
            }
        }
        if(Debug.DEBUG_PRINT) System.out.println();

        return d;
    }

    public static DFS depthFirstSearch(Graph g, Vertex src) {
        DFS d = new DFS(g);
        for(Vertex v : g) {
            d.get(v).mark = false;
            d.get(v).parent = null;
            d.get(v).status = status.New;
            d.get(v).distance = 0;
            d.get(v).start = 0;
            d.get(v).finish = 0;
        }

        d.dfs(src);

        for(Vertex v : g) {
            if(!d.get(v).mark) {
                d.dfs(v);
            }
        }

        if(Debug.DEBUG_PRINT) System.out.println();
        return d;
    }

    public static DFS depthFirstSearch(Graph g, int s) {
        return depthFirstSearch(g, g.getVertex(s));
    }


    /**
     * Is Vertex v is DAG.
     * @param v -
     * @return true - is DAG, false - not DAG
     */
    public boolean isDAG(Vertex v) {
        get(v).status = status.Active;

        for(Edge e : g.incident(v)) {
            Vertex w = e.otherEnd(v);
            if(get(w).status == status.Active) {
                return false;
            } else if(get(w).status == status.New) {
                if(!isDAG(w)) return false;
            }
        }
        get(v).status = status.Finished;
        return true;
    }

    public static boolean isDAGAll(Graph g) {
        DFS d = new DFS(g);

        for(Vertex v : g) {
            d.get(v).status = status.New;
        }

        for(Vertex v : g) {
            if(d.get(v).status == status.New) {
                if(!d.isDAG(v)) return false;
            }
        }

        return true;
    }



    public static void main(String[] args) throws Exception {
        // string format: 7 - #vertex, 8 - #edge, 1,2 - u,v 2 - weight
        String string = "7 8   1 2 2   1 3 3   2 4 5   3 4 4   4 5 1   5 1 -7   6 7 -1   7 6 -1 1";
        Scanner in;
        // If there is a command line argument, use it as file from which
        // input is read, otherwise use input from string.
        in = args.length > 0 ? new Scanner(new File(args[0])) : new Scanner(string);
        // Read graph from input
        Graph g = Graph.readDirectedGraph(in);
        int s = in.nextInt();


        // Test case 1: graph g, start vertex 1
        DFS d = depthFirstSearch(g, s);
        g.printGraph(false);

        System.out.println("Output of DFS:\nNode\tDist\tParent\n----------------------");
        for(Vertex v: g) {
            if(d.get(v).distance == INFINITY) {
                System.out.println(v + "\tInf\t--");
            } else {
                System.out.println(v + "\t" + d.get(v).distance + "\t" + d.get(v).parent);
            }
        }

        System.out.print("Preorder: ");
        while(!d.startList.isEmpty()) {
            Vertex v = d.startList.remove();
            System.out.print(v + " ");
        }
        System.out.println();

        System.out.print("Postorder: ");
        while(!d.finishList.isEmpty()) {
            Vertex v = d.finishList.remove();
            System.out.print(v + " ");
        }
        System.out.println();

        System.out.print("Topoligical sort : ");
        LinkedList<Vertex> topo = DFS.topologicalList(g);
        while(!topo.isEmpty()) {
            Vertex v = topo.remove();
            System.out.print(v + " ");
        }
        System.out.println();

        if(!isDAGAll(g)) {
            System.out.println("Invalid graph: not a DAG");
        } else {
            System.out.println("Invalid graph: is a DAG");
        }
    }

}