package api;

import java.util.*;

public class DWGraph_DS implements directed_weighted_graph {

    Map<Integer, node_data> Nodes = new HashMap<>(); //all nodes in the graph
    Map<Integer, HashMap<Integer, edge_data>> Edges = new HashMap<>(); //all node connect to a hashmap that contains<neighbor key, weight>
    Map<Integer, ArrayList<Integer>> reverseEdges = new HashMap<>();


    int MC;
    int edgeSize;

    //basic constructor
    public DWGraph_DS(){
        this.MC = 0;
        this.edgeSize =0;
    }


    public DWGraph_DS(directed_weighted_graph n){

        Iterator<node_data> it1 =  n.getV().iterator();
        while(it1.hasNext()){
            node_data tempNode = it1.next();
            node_data newNode = new NodeData(tempNode);
            this.addNode(newNode);
        }
        Iterator<node_data> outerIT = n.getV().iterator();
        while(outerIT.hasNext()){
            node_data tempNode = outerIT.next();
            Iterator<edge_data> innerIT = n.getE(tempNode.getKey()).iterator();
            while(innerIT.hasNext()) {
                edge_data tempEdge = innerIT.next();
                this.connect(tempEdge.getSrc(), tempEdge.getDest(), tempEdge.getWeight());
            }
        }
    }

    /**
     * returns the node_data that linked with this key
     * @param key - the node_id
     * @return node_data
     */
    @Override
    public node_data getNode(int key) {
        return Nodes.get(key);
    }

    /**
     * returns the data of the edge (src,dest), null if none.-O(1)
     * @param src-node
     * @param dest-node
     * @return edge_data associated with this edge, null if none
     */

    @Override
    public edge_data getEdge(int src, int dest) {
        if(Nodes.containsKey(src) && Nodes.containsKey(dest)){ //src and dest exist in the graph
            if(Edges.get(src).containsKey(dest)){ //there is an edge between src and dest
                return Edges.get(src).get(dest); // returns the edge_data
            }
        }
        return null;
    }

    /**
     * adds a new node to the graph with the given node_data.
     * @param n
     */
    @Override
    public void addNode(node_data n) {
        if(!Nodes.containsKey(n.getKey())){
            Nodes.put(n.getKey(), n);
            Edges.put(n.getKey(), new HashMap<>());
            reverseEdges.put(n.getKey(), new ArrayList<>());
            MC++;
        }
    }

    @Override
    public void connect(int src, int dest, double w) {
        if(!Nodes.containsKey(src) || !Nodes.containsKey(dest)) return;
        if(Edges.get(src).containsKey(dest) && Edges.get(src).get(dest).getWeight()==w) return;
        if(Edges.get(src).containsKey(dest)){ //just override the weight
            int prevTag = Edges.get(src).get(dest).getTag();
            String prevInfo = Edges.get(src).get(dest).getInfo();
            Edges.get(src).put(dest, new edgeData(src, dest, w, prevTag, prevInfo));

        }

        else{
            Edges.get(src).put(dest, new edgeData(src, dest, w)); //new edge
            edgeSize++;


        }
        reverseEdges.get(dest).add(src); // src is pointing on dest, in the reverse - dest is pointing on src
        MC++;
    }

    @Override
    public Collection<node_data> getV() {
        return Nodes.values();
    }

    @Override
    public Collection<edge_data> getE(int node_id) {
        if(Nodes.containsKey(node_id)){
            return Edges.get(node_id).values();
        }
        return null;
    }

    @Override
    public node_data removeNode(int key) {
        node_data tempNode = null;

        //we are going to iterate through all the neigh and check if there is and edge_data that (getDest == key)
        if(Nodes.containsKey(key) && reverseEdges.get(key) != null){ // the node exist in the graph and has edges from other nodes to it.
            tempNode = this.getNode(key);
            Iterator<Integer> it = reverseEdges.get(key).iterator();
            while(it.hasNext()){
                int n = it.next();
                if(Edges.containsKey(n)) {
                    Edges.get(n).remove(key);
                    edgeSize--;
                }
            }
            edgeSize = edgeSize - Edges.get(key).size(); // decreasing the size of the edges (delete all edges that going out from key)
            Nodes.remove(key);
            Edges.remove(key);
            reverseEdges.remove(key);
            MC++;
        }
        else if(Nodes.containsKey(key)){//there isnt any edges from src or to src
            Nodes.remove(key);
        }
        return tempNode;

    }


    @Override
    public edge_data removeEdge(int src, int dest) {
        edge_data removedEdge = null;
        if(!Nodes.containsKey(src) || !Nodes.containsKey(dest)) return null;
        if(Edges.get(src).containsKey(dest)){
            Integer thissrc=src;
            removedEdge = Edges.get(src).remove(dest);
            reverseEdges.get(dest).remove(thissrc);//src no longer has edge to dest
            edgeSize--;
            MC++;
        }
        return removedEdge;
    }

    public Collection<Integer> getReverseEdges(int key){
        return reverseEdges.get(key);
    }

    @Override
    public int nodeSize() {
        return Nodes.size();
    }

    @Override
    public int edgeSize() {
        return edgeSize;
    }

    @Override
    public int getMC() {
        return MC;
    }
}