import java.util.*;

public class Graph {

	private ArrayList<Node> nodeList;
	private ArrayList<Edge> edgeList;
	
	public Graph() {
		nodeList = new ArrayList<Node>();
		edgeList = new ArrayList<Edge>();
	}
	
	public ArrayList<Node> getNodeList() {
		return nodeList;
	}
	
	public ArrayList<Edge> getEdgeList() {
		return edgeList;
	}
	
	public void addNode(Node n) {
		nodeList.add(n);
	}
	
	public void addEdge(Edge e) {
		edgeList.add(e);
	}
	
	public Node starter() {
		for (Node u : getNodeList()) {
			if (u.getValue().equalsIgnoreCase("s")) {
				return u ;
			}
		
	}
		return null;
	
}
}
