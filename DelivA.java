import java.io.*;
import java.util.Collections;
import java.util.Comparator;

public class DelivA {

	private File inputFile;
	private File outputFile;
	private PrintWriter output;
	private Graph graph;

	//Constructor - DO NOT MODIFY
	public DelivA(File in, Graph gr) {
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
		
		// Calls the method that will do the work of deliverable A
		runDelivA();

		output.flush();
	}

	//*********************************************************************************
	//               This is where your work starts
	
	private void runDelivA() {
		
		Collections.sort(graph.getNodeList(), new IndegreeSorter());
		
		System.out.println("Indegree:");
		output.println("Indegree:");
		for(Node n: graph.getNodeList()) {
			System.out.println("Node " + n.getAbbrev()+ " has indegree " + n.getIncomingEdges().size());
		    output.println("Node " + n.getAbbrev()+  " has indegree " + n.getIncomingEdges().size());
		}
		
		Collections.sort(graph.getNodeList(), new OutdegreeSorter());
		System.out.println("Outdegree:");
		output.println("Outdegree:");
		for(Node n2: graph.getNodeList()) {
			System.out.println("Node " + n2.getAbbrev()+ " has outdegree " + n2.getOutgoingEdges().size());
		    output.println("Node " + n2.getAbbrev()+ " has outdegree " + n2.getOutgoingEdges().size());
		}
		
	} 
	    private class  IndegreeSorter implements Comparator<Node>{

			@Override
			public int compare(Node o1, Node o2) {
				// sort nodes decending order of indegree
				//1. Node with greater indegree
	    		//2. Node with greater outDegree
	    		//3.alphabetically
				int CompareIndegree = Integer.compare(o2.getIncomingEdges().size(), o1.getIncomingEdges().size());
	            if (CompareIndegree != 0) {
	            	return CompareIndegree;
	            } else {
	            	int compareoutdegree = Integer.compare(o2.getOutgoingEdges().size(), o1.getOutgoingEdges().size());
	                if(compareoutdegree != 0) {
	                	return compareoutdegree;
	                } else {
	                	return o1.getAbbrev().compareToIgnoreCase(o2.getAbbrev());
	                }
	            }
				
			}
	    	
	    }
	    private class  OutdegreeSorter implements Comparator<Node>{

			@Override
			public int compare(Node o1, Node o2) {
				// sort nodes decending order of outdegree
				//1. Node with greater outdegree
	    		//2. Node with greater inDegree
	    		//3.alphabetically

			int CompareOutdegree = Integer.compare(o2.getOutgoingEdges().size(), o1.getOutgoingEdges().size());
            if (CompareOutdegree != 0) {
            	return CompareOutdegree;
            } else {
            	int compareIndegree = Integer.compare(o2.getIncomingEdges().size(), o1.getIncomingEdges().size());
                if(compareIndegree != 0) {
                	return compareIndegree;
                } else {
                	return o1.getAbbrev().compareToIgnoreCase(o2.getAbbrev());
                }
            }
				
				
			}
	    	
	    }
}
	    
	    
		
			
   


