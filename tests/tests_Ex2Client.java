import api.*;
import gameClient.CL_Pokemon;
import gameClient.DFS;
import gameClient.util.Point3D;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class tests_Ex2Client {

    public void unConnG(directed_weighted_graph theGraph){
        /*

        creating an unconnected graph with 4 components

        0 -----> 1
                  \
                   \
                    \
                     > 2
        3 <----> 4

        */
        node_data node1 = new NodeData(1);
        node_data node2 = new NodeData(0);
        node_data node3 = new NodeData(2);

        node_data node4 = new NodeData(4);
        node_data node5 = new NodeData(3);

        theGraph.addNode(node1);
        theGraph.addNode(node2);
        theGraph.addNode(node3);
        theGraph.addNode(node4);
        theGraph.addNode(node5);

        theGraph.connect(0,1,2);
        theGraph.connect(1,2,2.5);
        theGraph.connect(4,3,6.7);
        theGraph.connect(3,4,7.1);
        
    }

    @Test
    void DFSonA2graph(){
        //creating graph based on A2 - with 6 components and test our DFS algorithm that supposed to return a list
        // of nodes, each node represents a different component in the graph
        directed_weighted_graph theGraph = new DWGraph_DS();
        dw_graph_algorithms algo = new DWGraph_Algo();
        algo.init(theGraph);
        algo.load("data\\A10");
        DFS dfs = new DFS();
        List<Integer> componentsList = dfs.DFSalgo(algo.getGraph());
        assertEquals(6, componentsList.size());
    }

    @Test
    void DFS(){
        directed_weighted_graph theGraph = new DWGraph_DS();
        unConnG(theGraph);
        DFS dfs = new DFS();
        List<Integer> componentsList = dfs.DFSalgo(theGraph);
        assertEquals(4, componentsList.size());
        System.out.println("This list has 4 nodes, each one representing a different component in the graph");
    }

    @Test
    void pokEquals(){
        edge_data edge1 = new edgeData(6,5, 2.3);
        Point3D point1 = new Point3D(6,7,8);
        CL_Pokemon pok1 = new CL_Pokemon(point1,6,7, 8, edge1);
        CL_Pokemon pok2 = new CL_Pokemon(point1, 6,3,9,edge1);
        assertTrue(pok1.equals(pok2));
        System.out.println("pokemons is on the same edge - return true");
        //my equals function chekcs if the pokemons is on the same edge ! should return true
    }

}
