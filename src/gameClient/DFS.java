package gameClient;

import api.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * This class representing an improved DFS, and returning a list of Integer, each Integer will
 * represent one (and different) component in an directional un-connected graph.
 * we will use this funcin the initialization of the agents in the game, to be sure every agent covers
 * another component in the graph, and that all the graph is reachable by the agents.
 */
public class DFS {

    HashMap<Integer, Integer> hasVisited = new HashMap<>(); // 0-White, 1-Gray, 2-Black
    List<Integer> componentsNodes = new ArrayList<>();
    static int counter = 0;
    dw_graph_algorithms ga = new DWGraph_Algo();

    /**
     * this algorithm is going through all nodes, for each node- mark it with 0 that represent the white color.
     * white color means that this node has not been visited yet.
     * for each un-visited node - send it(lets call it -src node) to a DFSvisit func that mark it with 1 - grey, that means we has visited this node but not all his
     * neighbors(nodes it connected to).
     * if each neighbor of the src node isnt marked, and there is a returning path to the src from the neighbor
     * it means that they are at the same component. and we send it to the same process.
     * if there isnt a returning path it means that this specific neighbor isnt at the same component, and we will have to check
     * it later (we will get to it in the later).
     * after we have checked all src neighbor-mark it with 2 - black. we hace finished with it.
     * in this way, when we returns to the calling func, we have marked in black all the nodes in the same component and
     * we can take one of them and put it on the List.
     * @param g - un-connected directional graph
     * @return list of nodes
     */

    public List<Integer> DFSalgo(directed_weighted_graph g){

        ga.init(g);
        for (node_data mark: g.getV()){
             hasVisited.put(mark.getKey(), 0);
        }
        for (node_data nodes: g.getV()){
             if(hasVisited.get(nodes.getKey()) == 0){//node hasnt been visited
                 DFSvisit(nodes.getKey(), g);
                 componentsNodes.add(nodes.getKey());
                 counter ++;
             }
        }
        return componentsNodes;
    }

    private void DFSvisit(int src, directed_weighted_graph g ){
        hasVisited.put(src, 1);
        g.getNode(src).setTag(counter);//mark the node with its component number
        for (edge_data edges: g.getE(src) ){
            int dest = edges.getDest();
            double path = ga.shortestPathDist(dest,src);

            if(hasVisited.get(dest) == 0 && path>0){
                 g.getNode(dest).setTag(counter);
                 DFSvisit(dest, g);
             }
             hasVisited.put(src, 2);

        }
    }
}
