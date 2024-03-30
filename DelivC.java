import java.io.*;
import java.util.Comparator;
import java.util.PriorityQueue;

public class DelivC{

	private File inputFile;
	private File outputFile;
	private PrintWriter output;
	private Graph graph;

	//Constructor - DO NOT MODIFY
	public DelivC(File in, Graph gr) {
		inputFile = in;
		graph = gr;

		// Set up for writing to a file
		try {
			// Use input file name to create output file in the same location
			String inputFileName = inputFile.toString();
			String outputFileName = inputFileName.substring(0, inputFileName.length() - 4).concat("_out.txt");
			outputFile = new File(outputFileName);

			// A Printwriter is an object that can write to a file
			output = new PrintWriter(outputFile);
		} catch (Exception x) {
			System.err.format("Exception: %s%n", x);
			System.exit(0);
		}
		
		// Calls the method that will do the work of deliverable C
		runDelivC();

		output.flush();
	}

	//*********************************************************************************
	//               This is where your work starts
	
	private void runDelivC() {
		
		//Delete these lines when you add functionality
		//System.out.println("DelivC:  To be implemented");//Prints to console
		//output.println("DelivC:  To be implemented");//Prints to file
		MstPrim(graph.starter());
		int totalcost = 0;
		for(Node u:graph.getNodeList()) {
			//u.getKey();
			totalcost += u.getKey();
			
		}
		System.out.println("The minimum spannig tree has a total cost of "+ totalcost + " and includes the following edges");
		for(Node u:graph.getNodeList()) {
			for(Edge e: u.getOutgoingEdges()) {
				if(e.getHead().getParent()==u) {
				System.out.println(e.getHead().getAbbrev() + "-"+ u.getAbbrev());
			
				
			}
			}
		}
		
	
		
	}
	
	
	private void MstPrim(Node r) {
		for(Node u: graph.getNodeList()) {
			//System.out.println(u.getName());
			u.setKey(Integer.MAX_VALUE);
			u.setParent(null);
		}
		r.setKey(0);
		PriorityQueue<Node> Q = new PriorityQueue<>(new Nodecomparator());
		for(Node u : graph.getNodeList()) {
			Q.add(u);
		}
		
		while(!Q.isEmpty()) {
			Node u = Q.poll();
			for(Edge e : u.getOutgoingEdges()) {
				Node v = e.getHead();
				if(Q.contains(v) && e.getDistance() < v.getKey()) {
					v.setParent(u);
					v.setKey(e.getDistance());
					Q.remove(v);
					Q.add(v);
					
					
					//remove Node V and add it again 
			
					//System.out.println(v.getName()+"-"+ u.getName());
					//System.out.println(u.getAbbrev() + "-"+ v.getAbbrev() + "edge weight:" + e.getDistance()) ;
				}
				
				
				
			}
			
		      
		}
		
		
		
  }
	private class Nodecomparator implements Comparator<Node> {

		public int compare(Node o1, Node o2) {
			if(o1.getKey() < o2.getKey()) {
				return -1;
			}else if(o1.getKey() > o2.getKey()) {
				return 1;
			}else {

			return o1.getName().compareToIgnoreCase(o2.getName());

				
			}
				
			

		}
	

}
}

