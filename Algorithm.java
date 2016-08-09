package Dijkstra;

import java.util.ArrayList;
import java.util.Arrays;

public class Algorithm {
	
	// It will store the path as a String
	String via;
	
	// It will store the shortest path
	ArrayList<Integer> path;
	
	// It will store the shortest distance
	int distance;
	
	// Constructor
	public Algorithm(){
		path = new ArrayList<Integer>();
		distance = 0;	
		via = "";
	}
	
	
	public void Dijkstra(int graph[][], int source, int destination){
		
		// size is the number of vertex
		int size = graph[0].length;
		
		// Add the source to the path
		path.add(source);
		
		// It will store previous vertexes of the path
		int parent[] = new int[size];
		
		// It will mark if a vertex is selected in path or not
		Boolean markedVertex[] = new Boolean[size];
	
		// It will store all the distance
		int distances[] = new int[size];
			
		// Initialize all markedVertex as false and all distances to be MAXIMUM
		// and parent of all vertices as -1
		Arrays.fill(markedVertex, false);
		Arrays.fill(distances, Integer.MAX_VALUE);
		Arrays.fill(parent, -1);
		
		// Distance from source to source is 0
		distances[source] = 0;
		
		for (int i=0; i<size-1; i++){
			
			int mini = getMinimumDistance(distances, markedVertex, size);
			
			// marked the vertex which has mimimum distance as true
			markedVertex[mini] = true;
			
			// change distance of adjacent vertices of the selected vertex if ...
			for (int j=0; j<size; j++){
				if( !markedVertex[j] && // ... it is not marked yet
						graph[mini][j]!=0 && // ... there is an edge connecting those two vertices
							distances[mini]!=Integer.MAX_VALUE && // ... distnce b/w them is not infinite i.e. MAXIMUM
								distances[mini] + graph[mini][j] < distances[j] ){ // ... and one more condition
					parent[j] = mini;
					distances[j] = distances[mini] + graph[mini][j];
				}
			}
			
		}
		
		getActualDistance(distances, parent, source, destination, size);
	}
	
	
	// Method to find the vertex with minimum distance from those
	// vertices only which are not marked yet.
	private int getMinimumDistance(int distances[], Boolean markedVertex[], int size){
		
		// select a maximum to compare distances
		int maximum = Integer.MAX_VALUE;
		
		// select the mimimum index
		int min_index = -1;
		
		for (int i=0; i<size; i++)
			if (markedVertex[i] == false && distances[i] <= maximum){
				maximum = distances[i];
				min_index = i;
        }
		return min_index;
	}
	
	
	// To get the actual distance from source to destination only
	private void getActualDistance(int distances[], int parent[], int source, int destination, int size){
		
		for (int k=0; k<size; k++){
			if (k == destination){
				//System.out.println(source + "->" + k + " is " + distances[k]);
				distance = distances[k];
				printPath(parent, k, destination);
			}
		}
		printIt(path, destination);
	}
	
	
	// To add path to ArrayList excuding the destination
	private void printPath(int parent[], int x, int destination){
		
		// if vertex has no parent then return
		if(parent[x] == -1)
			return;
		
		printPath(parent, parent[x], destination);
		
		// doesnt want to include destination in path
		if (x == destination)
			return;
		
		// adding vertices in path
		path.add(x);
	}
	
	
	// To verify the path and to add destination in it
	private void printIt(ArrayList<Integer> path, int destination){
		
		// adding the destination in path
		path.add(destination);
		
		// printing path to verify it
		for (int l=0; l<path.size(); l++)
			via += " " + path.get(l);
			//System.out.print(path.get(l) + ", ");
		//System.out.println(via);
	}
	
	public void reset(){
		path.clear();
		distance = 0;	
		via = "";
	}
}
