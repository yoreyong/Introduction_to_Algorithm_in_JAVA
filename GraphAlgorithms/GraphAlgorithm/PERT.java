/* Starter code for PERT algorithm (Project 3)
 * @author
 */

// change dsa to your netid
package GraphAlgorithm;

import GraphAlgorithm.Graph.*;

import java.io.File;
import java.util.ArrayDeque;
import java.util.LinkedList;
import java.util.Scanner;


public class PERT extends GraphAlgorithm<PERT.PERTVertex> { 
    public static final int INFINITY = Integer.MAX_VALUE;
    public enum status {New, Active, Finished}
    LinkedList<Vertex> finishList;
    LinkedList<Vertex> reverseTopoList;
    ArrayDeque<Vertex> topoStk;
   
    public interface Debug {
		boolean DEBUG_PRINT = false;
		boolean TEST_PRINT = false;
		boolean PRINT = false;
	}
	
    public static class PERTVertex implements Factory {
	// Add fields to represent attributes of vertices here
    	int duration;	// duration of each Vertex
    	boolean mark;
    	Vertex parent;
    	status status;
//    	Timer timer;
    	
    	int es;	// Earliest start time
    	int ec;	// Earliest completion time
    	int ls; // Latest start time
    	int lc;	// Latesr completion time
    	int slack;
    	
		public PERTVertex(Vertex u) {
			duration = INFINITY;
			mark = false;
			parent = null;
			status = status.New;
			
			es = 0;
			ec = 0;
			ls = 0;
			lc = 0;
			slack = 0;	
		}
		
		public PERTVertex make(Vertex u) { 
			return new PERTVertex(u); 
		}
    }

    // Constructor for PERT is private. Create PERT instances with static method pert().
    private PERT(Graph g) {
    	super(g, new PERTVertex(null));
    }

    public void setDuration(Vertex u, int d) {
    	get(u).duration = d;
    }

    // Implement the PERT algorithm. Returns false if the graph g is not a DAG.
    public boolean pert() {
    	for(Vertex v : g) {
    		get(v).status = status.New;
    	}
    	
    	for(Vertex v : g) {
    		if(get(v).status == status.New) {
    			if(!isDAG(v)) return false;
    		}
    	}
    	
		dfsAll(g);	// dfs
    	ecAll();	// Earliest finish time
    	lcAll();	// Latest finish time
    	
    	return true;
    }
    
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

    // Find a topological order of g using DFS
    LinkedList<Vertex> topologicalOrder() {	
		if(Debug.TEST_PRINT) System.out.print("Topoligical Order: ");
		finishList = new LinkedList<>();
		ArrayDeque<Vertex> stk = new ArrayDeque<>(topoStk);
		while(!stk.isEmpty()) {
			Vertex tmp = stk.pop();
			if(Debug.TEST_PRINT) System.out.print(tmp + " ");
			finishList.add(tmp);
		}
		System.out.println();
		
    	return finishList;
    }
    
    LinkedList<Vertex> revTopologicalOrder() {
    	if(Debug.TEST_PRINT) System.out.print("Reverse topoligical Order: ");
    	reverseTopoList = new LinkedList<>();
    	ArrayDeque<Vertex> stk = new ArrayDeque<>(topoStk);
    	while(!stk.isEmpty()) {
    		Vertex tmp = stk.removeLast();
    		if(Debug.TEST_PRINT) System.out.print(tmp + " ");
			reverseTopoList.add(tmp);
    	}
    	System.out.println();
    	
    	return reverseTopoList;
    }

    void dfsVisit(Vertex u) {
//    	this.src = u;
    	get(u).mark = true;
    	if(Debug.DEBUG_PRINT) System.out.print(u.getName() + " -> ");
    	
    	for(Edge e : g.incident(u)) {
    		Vertex w = e.otherEnd(u);
    		if(get(w).mark == false) {
    			get(w).parent = u;
    			dfsVisit(w);
    		}
    	}
    	topoStk.push(u);
    }
    
    public void dfsAll (Graph g) {  	
    	for(Vertex v : g) {
			get(v).mark = false;
			get(v).parent = null;
			get(v).status = status.New;
		}
    	topoStk = new ArrayDeque<>();
    	
    	for(Vertex v : g) {
    		if(get(v).mark == false) {
    			dfsVisit(v);
    		}
    	}
    	if(Debug.DEBUG_PRINT) System.out.println();
    	
    	topologicalOrder();	// topological order
    	revTopologicalOrder();
    } 

    // The following methods are called after calling pert().

    // Earliest time at which task u can be completed
    public int ec(Vertex u) {	
    	return get(u).ec;
    }
    
    public void ecAll() {
    	
    	for(Vertex u : g) {
    		get(u).es = 0;
    	}
    	
    	for(Vertex u : finishList) {
    		get(u).ec = get(u).es + get(u).duration;
        	
        	for(Edge e : g.incident(u)) {
        		Vertex v = e.otherEnd(u);
        		if(get(v).es < get(u).ec)
        			get(v).es = get(u).ec;
        	}
        	get(u).slack = get(u).lc - get(u).ec;
    	}
    }
    
    public int CPL() {
    	int cpl = 0;
    	for(Vertex v : g) {
    		if(get(v).ec >= cpl) {
    			cpl = get(v).ec;
    		}
    	}	
    	if(Debug.TEST_PRINT) System.out.println("cpl = " + cpl);
    	return cpl;
    }

    // Latest completion time of u
    public int lc(Vertex u) {
    	return get(u).lc;
    }
    
    
    public void lcAll() {
    	int cpl = CPL();
    	for(Vertex u : g) {
    		get(u).lc = cpl;
    	}
    	
    	for(Vertex u : reverseTopoList) {  		
    		for(Edge e : g.incident(u)) {
        		Vertex v = e.otherEnd(u);
        		get(v).ls = get(v).lc - get(v).duration;
        		if(get(u).lc > get(v).ls)
        			get(u).lc = get(v).ls;
        	}
    		get(u).slack = get(u).lc - get(u).ec;
    	}
    }

    // Slack of u
    public int slack(Vertex u) {
    	get(u).slack = get(u).lc - get(u).ec;
    	return get(u).slack;
    }

    // Length of a critical path (time taken to complete project)
    public int criticalPath() {
    	return CPL();
    }

    // Is u a critical vertex?
    public boolean critical(Vertex u) {
    	if(get(u).slack == 0) {
    		return true;
    	}
    	return false;
    }

    /* Create a PERT instance on g, runs the algorithm.
	 * Returns PERT instance if successful. Returns null if G is not a DAG.
	 */
	public static PERT pert(Graph g, int[] duration) {
		PERT p = new PERT(g);
		for(Vertex u: g) {
		    p.setDuration(u, duration[u.getIndex()]);
		}
		
		// Run PERT algorithm.  Returns false if g is not a DAG
		if(p.pert()) {
			if(Debug.TEST_PRINT) System.out.println("Valid graph: is a DAG");
			return p;
		} else {
		    return null;
		}
	}

	// Number of critical vertices of g
    public int numCritical() {
    	int numCriticalV = 0;
    	for(Vertex u : g) {
    		if(get(u).slack == 0) {
    			numCriticalV++;
    		}
    	}
    	return numCriticalV;
    }

    
	public static void main(String[] args) throws Exception {
		String graph = "10 13   1 2 1   2 4 1   2 5 1   3 5 1   3 6 1   4 7 1   5 7 1   5 8 1   6 8 1   6 9 1   7 10 1   8 10 1   9 10 1      0 3 2 3 2 1 3 2 4 1";
		Scanner in;
		// If there is a command line argument, use it as file from which
		// input is read, otherwise use input from string.
		in = args.length > 0 ? new Scanner(new File(args[0])) : new Scanner(graph);
		Graph g = Graph.readDirectedGraph(in);	// raed the graph
		g.printGraph(false);
	
		int[] duration = new int[g.size()];
		for(int i=0; i<g.size(); i++) {
		    duration[i] = in.nextInt();
		}
		PERT p = pert(g, duration);
		
		if(p == null) {
		    System.out.println("Invalid graph: not a DAG");
		} else {
		    System.out.println("Number of critical vertices: " + p.numCritical());
		    System.out.println("u\tDur\tEC\tLC\tSlack\tCritical");
		    for(Vertex u: g) {
		    	System.out.println(u + "\t" + duration[u.getIndex()] + "\t" + p.ec(u) + "\t" + p.lc(u) + "\t" + p.slack(u) + "\t" + p.critical(u));
		    }
		    
		    System.out.println("______________________________________________");
		    System.out.println("# Vertices: " + g.size() + ", Edges: " + g.edgeSize());
		    System.out.println("Output:");
		    System.out.println(p.criticalPath() + " " + p.numCritical());
		}
    }
}
