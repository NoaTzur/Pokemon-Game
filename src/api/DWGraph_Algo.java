package api;


import java.io.*;
import java.io.PrintWriter;
import java.util.*;
import com.google.gson.GsonBuilder;
import com.google.gson.Gson;

import static java.lang.Double.POSITIVE_INFINITY;

public class DWGraph_Algo implements dw_graph_algorithms {

    directed_weighted_graph currGraph = new DWGraph_DS();
    Map<Integer, Integer> parents = new HashMap<>();
    //tagClass tags = new tagClass();


    @Override
    public void init(directed_weighted_graph g) {
        currGraph = g;
    }

    @Override
    public directed_weighted_graph getGraph() {
        return currGraph;
    }

    @Override
    public directed_weighted_graph copy() {
        directed_weighted_graph newGraph = new DWGraph_DS(currGraph);
        return newGraph;
    }

    @Override
    public boolean isConnected() {
        if (currGraph.nodeSize() < 2) //graph with 0 or 1 node is linked
            return true;
        int n = currGraph.getV().iterator().next().getKey();
        return BFS1(n) && BFS2(n);
    }

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

    @Override
    public double shortestPathDist(int src, int dest) {
        return Dijkstra(src, dest);
    }

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
            if (!shortestPath.contains(parent))
                shortestPath.add(parent);  //adds the parents to the list
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


    @Override
    public boolean save(String file) {
        Gson myGson = new GsonBuilder().setPrettyPrinting().create();
        String json = myGson.toJson(currGraph);
        System.out.println(json);

        try {
            PrintWriter pw = new PrintWriter(new File(file));
            pw.write(json);
            pw.close();


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


        return true;
    }

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
