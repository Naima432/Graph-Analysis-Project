import java.io.*;
import java.util.ArrayList;
import java.util.Collections;

public class DelivE {

	private File inputFile;
	private File outputFile;
	private PrintWriter output;
	private Graph graph;

	// Constructor - DO NOT MODIFY
	public DelivE(File in, Graph gr) {
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

		// Calls the method that will do the work of deliverable E
		runDelivE();
		
		//FourthTour();
		output.flush();

	}

	// *********************************************************************************
	// This is where your work starts

	private void runDelivE() {
		// Delete these lines when you add functionality
		// in order Tour
		ArrayList<Node> inorderTour = fisrtTour();
		int FirstTourDistance = tourDist(inorderTour);
        //random
		ArrayList<Node> shafledTour = shaffledTour();
		int ShaffledTourDistance = tourDist(shafledTour);
        //greedy
		ArrayList<Node> GreedyTour = secondTour();
		int SecondTourDistance = tourDist(GreedyTour);
         //insertion
		ArrayList<Node> insertionTour = ThirdTour();
		int ThirdTourDistance = tourDist(insertionTour);

		// ArrayList<Node> FourthTour = FourthTour();

		ArrayList<Node> currentTour = ThirdTour();
		int Swapnum = 1;
		int swapnum = 2;

		currentTour = improveTourBySwapping(currentTour, Swapnum, swapnum);
		//int improvedTourDistance = tourDist(currentTour);

		ArrayList<ArrayList<Node>> improvedTours = new ArrayList<>();
		improvedTours.add(inorderTour);
		improvedTours.add(GreedyTour);
		improvedTours.add(currentTour);
		// improvedTours.add(FourthTour);
		// Find the best tour among the improved ones
		ArrayList<Node> bestTour = fbestTour(improvedTours);
		int bestDistance = tourDist(bestTour);

		// Print the improved tours to the console
		printimprovedTours(improvedTours);
		// FourthTour();
		// Print the best tour to both console and output file
		

		// FourthTour();

		for (Node city : shafledTour) {
			if (city != null) {
				output.print(city.getAbbrev().charAt(0));

			}
		}

		output.print("   " +  ShaffledTourDistance +  "    "  + "(Random)");

		output.println();

		for (Node city : inorderTour) {
			if (city != null) {

				// output.println();
				output.print(city.getAbbrev().charAt(0));
			}
		}

		output.print("   " +  FirstTourDistance +   "    " + "(Inorder)");

		output.println();

		for (Node city : GreedyTour) {
			if (city != null) {

				output.print(city.getAbbrev().charAt(0));
			}
		}
		output.print("   " +  SecondTourDistance +    "    " +  "(Greedy)");
		

		output.println();

		for (Node city : insertionTour) {
			if (city != null) {

				output.print(city.getAbbrev().charAt(0));

			}
		}

		output.print("   " +  ThirdTourDistance +  "    " + "(Insertion Heuristic)");

		output.println();
		output.println();
		printTourANdDistance(bestTour, bestDistance, "Shortest path found was", 53);


		//FourthTour();

		// Close the output file
		// FourthTour() ;
		output.flush();
		output.close();

	}

	private void printTourANdDistance(ArrayList<Node> tour, int dist, String name, int steps) {
		String abbre = getTourAbre(tour);
		if (steps > 0) {
			System.out.println(
					name + " " + abbre + " with distance " + dist + " after " + steps + " steps.");
			output.println(name + " " + abbre + " with distance " + dist + " after " + steps + " steps.");
		} else {
			System.out.println(abbre + "  " + dist);
			
		}
	}

	private String getTourAbre(ArrayList<Node> tour) {
		StringBuilder abbreviation = new StringBuilder();
		for (Node city : tour) {
			if (city != null) {
				abbreviation.append(city.getAbbrev().charAt(0));
			}
		}
		return abbreviation.toString();
	}

	private ArrayList<Node> fbestTour(ArrayList<ArrayList<Node>> tours) {
		int bestDistance = Integer.MAX_VALUE;
		ArrayList<Node> bestTour = null;

		for (ArrayList<Node> tour : tours) {
			int distance = tourDist(tour);
			if (distance < bestDistance) {
				bestDistance = distance;
				bestTour = tour;
			}
		}

		return bestTour;
	}

	private void printimprovedTours(ArrayList<ArrayList<Node>> tours) {
		for (ArrayList<Node> tour : tours) {
			int distance = tourDist(tour);
			printTourANdDistance(tour, distance, "", 0);
		}
	}

	// first tour (greedy)
	private ArrayList<Node> fisrtTour() {
		// list of all of the cities in the tour
		ArrayList<Node> firstTour = new ArrayList<>();

		Node startCity = graph.getNodeList().get(0);
		// firstTour.add(startCity);

		// Create a list of unvisited cities
		ArrayList<Node> allunvisitedCities = new ArrayList<>(graph.getNodeList());
		// unvisitedCities.remove(startCity);
		firstTour.addAll(allunvisitedCities);
		// Add the starting city again to complete the tour
		firstTour.add(startCity);

		return firstTour;
	}

	private ArrayList<Node> shaffledTour() {
		// list of all of the cities in the tour
		ArrayList<Node> firstTour = new ArrayList<>();

		Node startCity = graph.getNodeList().get(0);
		firstTour.add(startCity);

		// Create a list of unvisited cities
		ArrayList<Node> allunvisitedCities = new ArrayList<>(graph.getNodeList());
		// unvisitedCities.remove(startCity);
		Collections.shuffle(allunvisitedCities);
		firstTour.addAll(allunvisitedCities);
		// Add the starting city again to complete the tour
		firstTour.add(startCity);

		return firstTour;
	}

	private ArrayList<Node> improveTourBySwapping(ArrayList<Node> currentTour, int index1, int index2) {
		ArrayList<Node> newTour = new ArrayList<>(currentTour);

		// Swap the cities at index1 and index2
		Collections.swap(newTour, index1, index2);

		// Calculate the distance of the new tour and compare with the current tour
		int currenDist = tourDist(currentTour);
		int newDis = tourDist(newTour);

		if (newDis < currenDist) {
			// The new tour is better, update the current tour
			currentTour = newTour;
		}

		return currentTour;
	}

	// I will be using greedy Algorism to start
	// start the tour with the first city
	// look for the nearest city to the first city
	// look for the nearest city to that city
	// repeat until u visit the last city and than come back to the first city
	private ArrayList<Node> secondTour() {
		ArrayList<Node> secondTour = new ArrayList<>();

		Node StartCity = graph.getNodeList().get(0);
		secondTour.add(StartCity);

		// list of unvisited cities
		ArrayList<Node> unvisitedCities = new ArrayList<>(graph.getNodeList());
		// unvisitedCities.remove(StartCity);
		// visit the remaining cities

		while (!unvisitedCities.isEmpty()) {
			unvisitedCities.remove(StartCity);
			Node nearestCity = FindNearestCity(StartCity, unvisitedCities);
			secondTour.add(nearestCity);
			// unvisitedCities.remove(nearestCity);
			StartCity = nearestCity;
		}
		secondTour.addAll(unvisitedCities);

		// Add the starting city again

		secondTour.add(graph.getNodeList().get(0));

		return secondTour;
	}

	// to improve my Tour I will be using insertion heuristic algorism
	// start with the first city and than with all the unvisited cities
	// with all the unvisited cities
	// calculate the distance of inserting it in the correct possition which adds
	// less distance
	// insert that city into the chosen position
	// mark that as visited
	// return to the starter city
	private ArrayList<Node> ThirdTour() {
		// build an empty array
		ArrayList<Node> ThirdTour = new ArrayList<>();
		// an array with all the unvited cities
		ArrayList<Node> unvisitedCities = new ArrayList<>(graph.getNodeList());

		// choose the first city as starter move it to the empty tour
		Node FirstCity = unvisitedCities.get(0);
		unvisitedCities.remove(FirstCity);

		// add the starter to the new tour
		ThirdTour.add(FirstCity);
		// go through all the unvisited cities until u find all of them a possition in
		// the new tour
		while (!unvisitedCities.isEmpty()) {
			// look for the city that will add less distance to the tour
			int min = Integer.MAX_VALUE;
			Node newcity = null;
			int newcityIndex = 1;

			// Find the best city to insert and its location in the tour
			for (Node cityToInsert : unvisitedCities) {
				for (int i = 0; i < ThirdTour.size() - 1; i++) {
					ArrayList<Node> newTour = new ArrayList<>(ThirdTour);
					newTour.add(i + 1, cityToInsert);

					// calculate the incresed distance of the new tour and compare it to the tour
					int increasedDist = tourDist(newTour) - tourDist(ThirdTour);
					// if increaseddistance is less than the minDistance than make that one the
					// minincresed distance
					if (increasedDist < min) {
						min = increasedDist;

						newcity = cityToInsert;
						newcityIndex = i + 1;
					}
				}
			}

			// Step 4b: Insert the best city into the tour
			ThirdTour.add(newcityIndex, newcity);
			unvisitedCities.remove(newcity);
			// ThirdTour.add(graph.getNodeList().get(0));
		}
		ThirdTour.add(graph.getNodeList().get(0));

		return ThirdTour;
	}

	private Node FindNearestCity(Node currentCity, ArrayList<Node> unvisitedCities) {
		Node nearestCity = null;
		int minDistance = Integer.MAX_VALUE;

		for (Node city : unvisitedCities) {
			int distance = getDistanceBetweenCities(currentCity, city);
			if (distance < minDistance) {
				minDistance = distance;
				nearestCity = city;
			}
		}
		return nearestCity;
	}

	private int tourDist(ArrayList<Node> tour) {
		int totalDistance = 0;

		for (int i = 0; i < tour.size() - 1; i++) {
			Node currentCity = tour.get(i);
			Node nextCity = tour.get(i + 1);

			// Calculate the distance between currentCity and nextCity
			int distance = getDistanceBetweenCities(currentCity, nextCity);

			// Add the distance to the total
			totalDistance += distance;
		}

		return totalDistance;
	}

	private int getDistanceBetweenCities(Node city1, Node city2) {

		ArrayList<Edge> edges = graph.getEdgeList();
		for (Edge edge : edges) {
			if ((edge.getTail() == city1 && edge.getHead() == city2)
					|| (edge.getTail() == city2 && edge.getHead() == city1)) {
				return edge.getDistance();
			}
		}

		// If no edge found between the cities, return -1
		return -1;
	}

	private ArrayList<Node> FourthTour() {
		ArrayList<Node> currentTour = ThirdTour();
		int currentDis = tourDist(currentTour);
		boolean improvement = true;
		//int steps = 0;
		while (improvement) {
			improvement = false;

			for (int i = 1; i < currentTour.size() - 1; i++) {
				for (int j = i + 1; j < currentTour.size(); j++) {
					ArrayList<Node> newTour = twoSwap(currentTour, i, j);
					int newdist = tourDist(newTour);
					//steps++;
					if (newdist < currentDis) {
						currentTour = newTour;
						currentDis = newdist;
						improvement = true;
					} else {
						// System.out.println("Tour Tour:");
						for (Node city : newTour) {
							if (city != null) {
								System.out.print(city.getAbbrev().charAt(0));
								output.print(city.getAbbrev().charAt(0));
							}
						}
						System.out.print("   " +  newdist +    "    " +  "(Swipe)");
						output.print("   " +  newdist +    "    " +  "(Swipe)");
						
                        System.out.println();
						output.println();
					}
				}
			}
		}

		// Print the tour and its distance
		/*
		 * System.out.println("Tour Tour:"); for (Node city : currentTour) { if (city !=
		 * null) { System.out.print(city.getAbbrev()); output.println(city.getAbbrev());
		 * 
		 * } } System.out.println(); System.out.println("Tour Distance: " +
		 * currentDistance);
		 */
		//System.out.print(steps);
		return currentTour;
	}

	private ArrayList<Node> twoSwap(ArrayList<Node> tour, int i, int j) {
		ArrayList<Node> newTour = new ArrayList<>(tour.subList(0, i));
		newTour.addAll(reverse(tour, i, j));
		newTour.addAll(tour.subList(j, tour.size()));
		return newTour;
	}

	private ArrayList<Node> reverse(ArrayList<Node> tour, int i, int j) {
		ArrayList<Node> reverse = new ArrayList<>();
		for (int k = j; k >= i; k--) {
			reverse.add(tour.get(k));
		}
		return reverse;
	}

}