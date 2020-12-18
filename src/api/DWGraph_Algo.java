package api;


import java.io.*;
import java.io.PrintWriter;
import java.util.*;
import com.google.gson.GsonBuilder;
import com.google.gson.Gson;

import static java.lang.Double.POSITIVE_INFINITY;

/**
 * This interface represents a Directed (positive) Weighted Graph Theory Algorithms including:
 * 0. clone() - deep copy
 * 1. init(graph)- initiate this class with given graph
 * 2. isConnected() - checks if the graph is strongly connected
 * 3. double shortestPathDist(int src, int dest) - based on the weight of the edges
 * 4. List<node_data> shortestPath(int src, int dest) - a list of the nodes
 * 5. Save(file)- the graph is saved as JSON file
 * 6. Load(file) - from JSON file to directed_weighted_graph
 */

public class DWGraph_Algo implements dw_graph_algorithms {

    directed_weighted_graph currGraph = new DWGraph_DS();
    Map<Integer, Integer> parents = new HashMap<>();

    /**
     * init(graph)- initiate this class with given graph
     * @param g - given graph
     */
    @Override
    public void init(directed_weighted_graph g) {
        currGraph = g;
    }

    /**
     * return the graph which this class work on
     * @return directed_weighted_graph
     */
    @Override
    public directed_weighted_graph getGraph() {
        return currGraph;
    }

    /**
     * Compute a deep copy of this weighted graph.
     * @return directed_weighted_graph
     */
    @Override
    public directed_weighted_graph copy() {
        directed_weighted_graph newGraph = new DWGraph_DS(currGraph);
        return newGraph;
    }

    /**
     * Returns true if and only if there is a valid path from each node to each
     * based on regular BFS algorithm and reverse BFS.
     * returns true if and only id BFS1 and BFS2 returns true
     * @return true/false
     */
    @Override
    public boolean isConnected() {
        if (currGraph.nodeSize() < 2) //graph with 0 or 1 node is linked
            return true;
        int n = currGraph.getV().iterator().next().getKey();
        return BFS1(n) && BFS2(n);
    }

    /**
     * regular BFS algorithms,this function take an arbitrary source node, iterates through all the nodes
     * that connected to it. to each node - if this node has not been visited yet - mark it as visited, put it in a queue, and
     * reduce a counter that initialize with the number of nodes in the graph, by one.
     * After the iterating all source "neighbors", this steps occurs again, with the next node from the queue.
     * if the queue becomes empty, and there is nodes that has not been visited (counter !=0), it means the grapgh has more then 1 components (not connected) in the graph.
     * and thats means the graph is not connected.
     * @param n - arbitrary node
     * @return true\false
     */
    public boolean BFS1(int n) {
        Map<Integer, Integer> hasVisited = new HashMap<>();
        int counter = currGraph.nodeSize() - 1;

        Queue<Integer> allNeigh = new LinkedList<>(); // im going to use a queue in order to traverse all nodes by levels(not really necessary here)
        allNeigh.add(n); // put in the queue the src key

        hasVisited.put(n, 1); // mark the src node as visited

        while (!allNeigh.isEmpty()) {
            int tempNode = allNeigh.remove(); //removes the next element in queue
            for (edge_data it : currGraph.getE(tempNode)) { //traverse all his neighbors and marked them as visited if not already
                if (hasVisited.get(it.getDest()) == null) {
                    counter--; //every node that hasn't been checked - is a new node the we can reach
                    hasVisited.put(it.getDest(), 1); // marked
                    allNeigh.add(it.getDest());  // add him to the queue - for checking his neighbors
                }
            }
            if (counter == 0) //if we checked all n-1 nodes left (without src) then we can reach any node in the graph = connected
                return true;
        }
        return false;
    }

    /**
     * this function is the sasme as BFS1, the different is it working on HashMap that represents a reverse edges.
     * it means - if there is an edge between node 4 and 3, in the reverse HashMap, will be an edge between 3 and 4.
     * the order reversed. in this way, we checks if the graph is connected after "reversing" the arrow of the edges !
     * so if the graph isnt connected, this algorithm will return false
     * @param n - arbitrary node - the same node we sent to BFS1
     * @return true/false
     */
    public boolean BFS2(int n) {
        Map<Integer, Integer> hasVisited = new HashMap<>();
        int counter = currGraph.nodeSize() - 1;

        Queue<Integer> allNeigh = new LinkedList<>(); // im going to use a queue in order to traverse all nodes by levels(not really necessary here)
        allNeigh.add(n); // put in the queue the src key

        hasVisited.put(n, 1); // mark the src node as visited

        while (!allNeigh.isEmpty()) {
            int tempNode = allNeigh.remove(); //removes the next element in queue
            Iterator<Integer> it = ((DWGraph_DS)(currGraph)).getReverseEdges(tempNode).iterator();
            while (it.hasNext()) {//traverse all his neighbors and marked them as visited if not already
                int tempInt = it.next();
                if (hasVisited.get(tempInt) == null) {
                    counter--; //every node that hasn't been checked - is a new node the we can reach
                    hasVisited.put(tempInt, 1); // marked
                    allNeigh.add(tempInt);  // add him to the queue - for checking his neighbors
                }
            }
            if (counter == 0) //if we checked all n-1 nodes left (without src) then we can reach any node in the graph = connected
                return true;
        }
        return false;
    }
    /**
     * returns the length of the shortest path between src to dest (based on the edges weights)
     * if there is no path, returns -1
     * @param src - start node
     * @param dest - end (target) node
     * @return double number representing the path length
     */
    @Override
    public double shortestPathDist(int src, int dest) {
        return Dijkstra(src, dest);
    }

    /**
     * Dijkstra algorithm - at the beginning of the code, with foreach loop, marked all nodes in the graph with Tag = INFINITY.
     * the Tag label will represent the distance from the src node. src node Tag ==0.
     * I used a PriorityQueue as a min-Heap, that will sort the values by the Tag label(compareTo function is added to the node_info class).
     * first, the src node pushed into the queue. while the queue is not empty, iterate through all nodes that connected to src node,
     * and update its Tag to = parent_node_Tag(src in the beginning) + weight of parent--connected_nodes edge.
     * After we going through all the nodes "neighbors" - mark it as visited so it wont checks it again.
     * to each node, we updates its parent (in HashMap that designated for it).
     * When arriving to the dest node - return its Tag . its Tag will be the summary of the shortest distance from src to dest
     * thanks to the priority queue that poll the nodes that holds the smallest Tag(distance).
     * @param src - node to begin
     * @param dest - destination node
     * @return - dest.Tag(shortest path), -1 if there is not such path.
     */
    public double Dijkstra(int src, int dest) {
        if(src == dest)
            return 0;
        Map<Integer, Integer> hasVisited = new HashMap<>();

        Iterator<node_data> nodes = currGraph.getV().iterator();
        while(nodes.hasNext()){
            nodes.next().setWeight(POSITIVE_INFINITY);
        }
        currGraph.getNode(src).setWeight(0);

        PriorityQueue<node_data> allNodes = new PriorityQueue<node_data>();
        allNodes.offer(currGraph.getNode(src));

        while (!allNodes.isEmpty()) {
            node_data tempNode = allNodes.poll();
            if (hasVisited.get(tempNode.getKey()) == null) { //not visited yet
                for (edge_data it : currGraph.getE(tempNode.getKey())) {
                    node_data itDestNode = currGraph.getNode(it.getDest());
                    double dist = tempNode.getWeight() + it.getWeight(); // parent.dist+weight(parent,neighbor)
                    if (dist < itDestNode.getWeight()) { // POSITIVE_INFINITY means we never change his tag
                        itDestNode.setWeight(dist); //update the tag
                        allNodes.add(itDestNode); // puts it in the queue
                        parents.put(it.getDest(), tempNode.getKey()); //update its parent
                    }
                }
                if (tempNode.getKey() == dest) //arrived to the dest node = return its tag = the cumulative amount of weight
                    return tempNode.getWeight();
                hasVisited.put(tempNode.getKey(), 1);
            }
        }
        return -1; //there is no such path
    }

    /**
     * returns the the shortest path between src to dest - as an ordered List of nodes: src-- n1--n2--...dest.
     * This function uses the parent HashMap that the Dijkstra() function fills.
     * Beginning with the destination node, we going "up" in the HashMap and by its key, pull out its parent.
     * this parent, thank to Dijkstra() has the smallest Tag(smallest distance) from all potential other parents of the dest node.
     * In this order, it pulls the parent  till we arrive to the "final" parent - the src node.
     * now its looks like: dest--dest.parent--dest.parent.parent-------src.
     * with the help of reverse() function in the Collection class, the list can be reversed and this is the path.
     * @param src - start node
     * @param dest - end (target) node
     * @return - list of nodes that represent the shortest path.
     */
    @Override
    public List<node_data> shortestPath(int src, int dest) {
        List<node_data> shortestPath = new LinkedList<>();

        if (shortestPathDist(src, dest) == -1)
            return null;
        if (src == dest) {
            shortestPath.add(currGraph.getNode(src));
            return shortestPath;
        }

        shortestPath.add(currGraph.getNode(dest));  //put destination node in the list
        node_data parent = currGraph.getNode(parents.get(dest)); // got to dest parent

        while (parent != null) {
            if (!shortestPath.contains(parent)){
                shortestPath.add(parent);  //adds the parents to the list
               // break;
            }
            if (parents.get(parent.getKey()) != null) {
                parent = currGraph.getNode(parents.get(parent.getKey()));//get the next parent
                if(parent.getKey() == src) {
                    shortestPath.add(parent);
                    parent = null;
                }
            }
            else
                parent = null;
        }
        Collections.reverse(shortestPath); // list contains the path but in the reverse order
        return shortestPath;
    }

    /**
     * Save(file)- the graph is saved as JSON file
     * @param file - the file name (may include a relative path).
     * @return
     */
    @Override
    public boolean save(String file) {
        Gson myGson = new GsonBuilder().setPrettyPrinting().serializeSpecialFloatingPointValues().create();
        String json = myGson.toJson(currGraph);


        try {
            PrintWriter pw = new PrintWriter(new File(file));
            pw.write(json);
            pw.close();


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


        return true;
    }

    /**
     * creating an graph from JSON file (with the help of graphGsonDeserializer class)
     * @param file - file name of JSON file
     * @return true/false
     */
    @Override
    public boolean load(String file) {
        try {

            GsonBuilder builder = new GsonBuilder();
            builder.registerTypeAdapter(DWGraph_DS.class, new graphGsonDeserializer());
            Gson gson = builder.create();

            FileReader reader = new FileReader(file);
            currGraph = gson.fromJson(reader, DWGraph_DS.class);

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
