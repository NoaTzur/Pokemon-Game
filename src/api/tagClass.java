package api;
import javax.swing.text.html.HTMLEditorKit;
import java.util.*;

import static java.lang.Double.POSITIVE_INFINITY;

public class tagClass implements Comparator<Integer> {

    Map<Integer, Double> tags = new HashMap<>();

    public void setNewTag(int key, double newTag){
        tags.put(key,newTag);
    }

    public void setTags(Collection<node_data> keys){
        Iterator<node_data> allKeys = keys.iterator();
        while(allKeys.hasNext()){
            tags.put(allKeys.next().getKey(), POSITIVE_INFINITY);
        }
    }
    public Double getTag(int key){
        return tags.get(key);
    }

    @Override
    public int compare(Integer o1, Integer o2) {
        if (tags.get(o1) - tags.get(o2) > 0) return 1;
        else if (tags.get(o1) -tags.get(o2) < 0) return -1;
        return 0;
    }

    //dijkstra using tags
    /*
    public double Dijkstra(int src, int dest) {
        Map<Integer, Integer> hasVisited = new HashMap<>();

        for (node_data it1 : currGraph.getV()) {
            tags.setNewTag(it1.getKey(), POSITIVE_INFINITY);
        }
        tags.setNewTag(src, 0.0);

        PriorityQueue<node_data> allNodes = new PriorityQueue<node_data>();
        allNodes.add(currGraph.getNode(src));

        while (!allNodes.isEmpty()) {
            node_data tempNode = allNodes.poll();
            if (hasVisited.get(tempNode.getKey()) == null) { //not visited yet
                for (edge_data it : currGraph.getE(tempNode.getKey())) {
                    double dist = tags.getNewTag(tempNode.getKey()) + it.getWeight(); // parent.dist+weight(parent,neighbor)
                    if (dist < tags.getNewTag(it.getDest())) { // -1 = infinity, it means we never didnt change his tag
                        tags.setNewTag(it.getDest(), dist); //update the tag
                        allNodes.add(currGraph.getNode(it.getDest())); // puts it in the queue
                        parents.put(it.getDest(), tempNode.getKey()); //update its parent
                    }
                }
                if (tempNode.getKey() == dest) //arrived to the dest node = return its tag = the cumulative amount of weight
                    return tags.getNewTag(tempNode.getKey());
                hasVisited.put(tempNode.getKey(), 1);
            }
        }
        return -1; //there is no such path
    }
     */

    /*
    public double Dijkstra(int src, int dest) {
        Map<Integer, Integer> hasVisited = new HashMap<>();

        currGraph.getNode(src).setWeight(0);

        PriorityQueue<node_data> allNodes = new PriorityQueue<node_data>();
        allNodes.add(currGraph.getNode(src));

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
     */
}
