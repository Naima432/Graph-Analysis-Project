import java.io.*;
import java.util.Collections;
import java.util.Comparator;
import java.util.ArrayList;




public class DelivD {

	private File inputFile;
	private File outputFile;
	private PrintWriter output;
	private Graph graph;

	//Constructor - DO NOT MODIFY
	public DelivD(File in, Graph gr) {
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
		
		// Calls the method that will do the work of deliverable D
		runDelivD();

		output.flush();
	}

	//*********************************************************************************
	//               This is where your work starts
	
	private void runDelivD() {
		//Delete these lines when you add functionality
		//System.out.println("DelivD:  To be implemented");//Prints to console
		//output.println("DelivD:  To be implemented");//Prints to file
		int n = graph.getNodeList().size()-1;
		Collections.sort(graph.getNodeList(), new SortnodeByVal());
		double distance = BitonicTour(n-1, n)+ nodedistance(graph.getNodeList().get(n-1),graph.getNodeList().get(n));
		System.out.println("Distance of shortest Optimal TOur: " + distance);
		
           
	    
		
		
		ArrayList<Node> optimalTourpath = new ArrayList<>();
	    PrintTourPath(n-1, n, optimalTourpath);
        Node starterCity = graph.getNodeList().get(n);
        optimalTourpath.add(starterCity);
        //System.out.println(starterCity.getAbbrev());
        
	    // Print the optimal tour path
	    //System.out.println("Tour is:");
	    for (Node node : optimalTourpath) {
	        System.out.print(node.getAbbrev()+"->");
	    }
	}
	    
	
   private double BitonicTour(int  i, int j){
	   //Collections.sort(graph.getNodeList(), new SortnodeByVal());
	   
	   //int n = graph.getNodeList().size();
	   
	   if(i==0 && j==1) {
		   
		   return nodedistance(graph.getNodeList().get(i),graph.getNodeList().get(j));
	   }
		   else if(i <j-1) {
			   return nodedistance(graph.getNodeList().get(j-1),graph.getNodeList().get(j))+ BitonicTour(i, j-1);
		   }
	        
		   else if(i==j) {
			   return nodedistance(graph.getNodeList().get(j-1),graph.getNodeList().get(j))+ BitonicTour(j-1, j);
			   
		   } else {
			   //i=j-1
			   double MinCost = Double.MAX_VALUE;
			   int SpecialK = 0;
			   for(int k=0;k<j-1; k++) {
		       double BTour=BitonicTour(k,j-1) + nodedistance(graph.getNodeList().get(k),graph.getNodeList().get(j));
		        if(BTour < MinCost) {
		        	MinCost=BTour;
		        	 SpecialK = k;
		        }
		   }   
			   
		      return MinCost;
		   }
	   
   }
   private void PrintTourPath(int i, int j, ArrayList<Node> tourPath) {
	   
	    if (i == 0 && j == 1) {
	        // If there are only two nodes left in the tour, add them to the tour path.
	        tourPath.add(graph.getNodeList().get(i));
	        tourPath.add(graph.getNodeList().get(j));
	    } else if (i < j - 1) {
	        
	        PrintTourPath(i, j - 1, tourPath);
	        //tourPath.add(graph.getNodeList().get(j));
	    
	    } else {
	        int k = findSpecialK(i, j); 
	        PrintTourPath(k, j - 1, tourPath);
	        tourPath.add(graph.getNodeList().get(k));
	        tourPath.add(graph.getNodeList().get(j));
	       // System.out.println(tourPath);
	    }
	}
   private int findSpecialK(int i, int j) {
       double MinCost = Double.MAX_VALUE;
       int SpecialK = 0;
       for (int k = 0; k < j - 1; k++) {
           double BTour = BitonicTour(k, j - 1) + nodedistance(graph.getNodeList().get(k), graph.getNodeList().get(j));
           if (BTour < MinCost) {
               MinCost = BTour;
               SpecialK = k;
           }
       }
       return SpecialK;
   }

   
   
   
   
   private class SortnodeByVal implements Comparator<Node>{
	   
	   public int compare (Node p1, Node p2) {
		   if(Double.parseDouble(p1.getValue()) > Double.parseDouble(p2.getValue())){
			   return 1;
			   
		   }else if(Double.parseDouble(p1.getValue()) < Double.parseDouble(p2.getValue())) {
               return -1;
		   } else {
			   return p1.getAbbrev().compareTo(p2.getAbbrev());
		   
	   }
   }
   }
   
   private double nodedistance(Node p1 , Node p2) {
	   for(Edge edge:graph.getEdgeList()) {
		   if(edge.getHead().equals(p1) && edge.getTail().equals(p2)|| (edge.getHead().equals(p2)&& edge.getTail().equals(p1))) {
		   return edge.getDistance();
	   }
   }
		return 0;
	
		
   }
}

   



