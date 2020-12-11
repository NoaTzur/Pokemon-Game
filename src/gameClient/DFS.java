package gameClient;

import api.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DFS {

    HashMap<Integer, Integer> hasVisited = new HashMap<>(); // 0-White, 1-Gray, 2-Black
    List<Integer> componentsNodes = new ArrayList<>();
    static int counter = 0;
    dw_graph_algorithms ga = new DWGraph_Algo();

    public List<Integer> DFSalgo(directed_weighted_graph g){

        ga.init(g);
        for (node_data mark: g.getV()){
             hasVisited.put(mark.getKey(), 0);
        }

        for (node_data nodes: g.getV()){
             if(hasVisited.get(nodes.getKey()) == 0){//&& hasVisited.get(nodes.getKey())!= 1 && hasVisited.get(nodes.getKey())!= 0){
                 DFSvisit(nodes.getKey(), g);
                 componentsNodes.add(nodes.getKey());
                 counter ++;
             }
        }
        return componentsNodes;
    }

    public void DFSvisit(int src, directed_weighted_graph g ){
        hasVisited.put(src, 1);
        g.getNode(src).setTag(counter);
        for (edge_data edges: g.getE(src) ){
            int dest = edges.getDest();
            double path = ga.shortestPathDist(dest,src);

            if(hasVisited.get(dest) == 0 && path>0){// && hasVisited.get(dest)!= 1 && hasVisited.get(dest)!= 0){
                 g.getNode(dest).setTag(counter);
                 DFSvisit(dest, g);
             }
             hasVisited.put(src, 2);

        }
    }
}
