import java.io.*;
import java.util.Collections;
import java.util.Comparator;

public class DelivB {

	private File inputFile;
	private File outputFile;
	private PrintWriter output;
	private Graph graph;

	// Constructor - DO NOT MODIFY
	public DelivB(File in, Graph gr) {
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

		// Calls the method that will do the work of deliverable B
		runDelivB();

		output.flush();
	}

	// *********************************************************************************
	// This is where your work starts
	private int time = 0;

	private void runDelivB() {
		// Delete these lines when you add functionality
		// System.out.println("DelivB: To be implemented");//Prints to console
		// output.println("DelivB: To be implemented");//Prints to file

		DFS();
		printDiscoveryAndFinishTimes();
		
		EdgesClassification();

	}

	private void printDiscoveryAndFinishTimes() {

		System.out.println(String.format("%-10s%-10s%-10s", "Node", "Disc", "Finish"));
		output.println(String.format("%-10s%-10s%-10s", "Node", "Disc", "Finish"));

		for (Node u : graph.getNodeList()) {
			output.println(String.format("%-10s%-10d%-10d", u.getAbbrev(), u.getDisTime(), u.getFinishtime()));
			System.out.println(String.format("%-10s%-10d%-10d", u.getAbbrev(), u.getDisTime(), u.getFinishtime()));

		}
		output.println();
	}

	// inantialize
	private void DFS() {
		System.out.println("DFS of graph");
		
		Collections.sort(graph.getNodeList(), new Nodesorter());
		for (Node u : graph.getNodeList()) {
			u.setColor("WHITE");
			u.setParent(null);
		}
		time = 0;

		for (Node u : graph.getNodeList()) {
			if (u.getValue().equalsIgnoreCase("s")) {
				// System.out.println(u.getValue()+u.getAbbrev());
				DFSvisit(u);
			}
		}
		for (Node u : graph.getNodeList()) {
			if (u.getColor().equalsIgnoreCase("White")) {
				// time = 0;
				DFSvisit(u);

			}

		}
	}
	private class Nodesorter implements Comparator<Node> {

		public int compare(Node o1, Node o2) {
			return o1.getName().compareToIgnoreCase(o2.getName());

				
			}
				
			

		}

	private void DFSvisit(Node u) {
		time = time + 1;
		u.setDisTime(time);
		u.setColor("Gray");
		Collections.sort(u.getOutgoingEdges(), new Edgesorter());
		for (Edge e : u.getOutgoingEdges()) {
			Node v = e.getHead();
			// System.out.println("head" + v.getHead().getAbbrev() + " " +
			// v.getHead().getColor());
			if (v.getColor().equalsIgnoreCase("White")) {
				e.setEdgeType("Tree");
				v.setParent(u);
				DFSvisit(v);

			}
		}
		u.setColor("Black");
		time = time + 1;
		u.setFinishtime(time);
	}

	private void EdgesClassification() {

		for (Edge e : graph.getEdgeList()) {
			Node u = e.getTail();
			Node v = e.getHead();
			if (u.getDisTime() < v.getDisTime() && u.getFinishtime() > v.getFinishtime()) {
				if (e.getEdgeType() == null) {
					e.setEdgeType("Forward");
				}
			} else if (u.getDisTime() > v.getDisTime() && u.getFinishtime() < v.getFinishtime()) {
				e.setEdgeType("Back ");

			} else if (v.getDisTime() < v.getFinishtime() && u.getDisTime() < u.getFinishtime()) {
				e.setEdgeType("Cross ");
			}

		}
		System.out.println("Edge Classification");
		System.out.println("Edge"+ "     " + "Type" );
		for (Edge e : graph.getEdgeList()) {
			Node u = e.getTail();
			Node v = e.getHead();

			System.out.println(u.getAbbrev() + "-->" + v.getAbbrev() + "   " + e.getEdgeType());

		}

	}

	// for each edge in outgoing edges
	// v= edge.get Head();
	// find adjecent node
	// go to the head of the that was teching u.
	// head of outgoing edge of u
	//

	// choose the node closest
	// break ties alpha by name
	// restart alpatically by name,
	// start with node with value "s"

	//
	private class Edgesorter implements Comparator<Edge> {

		public int compare(Edge o1, Edge o2) {

			if (o1.getDistance() > o2.getDistance()) {
				return 1;
			} else if (o1.getDistance() < o2.getDistance()) {
				return -1;
			} else {
				return o1.getHead().getName().compareTo(o2.getHead().getName());
			}

		}

	}

}
