import api.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import gameClient.DFS;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class algoTests {
    directed_weighted_graph theGraph = new DWGraph_DS();


    void CreatingBigG(){ //Creates graph with 100,000 nodes and 10 times edges

        for(int i=0;i<100000;i++){//insert nodes
            node_data nodes = new NodeData(i);
            theGraph.addNode(nodes);
        }
        for (int i=0;i<1000000-4;i++){ //insert edges
            double weightOfEdge = Math.random()*50;
            if(i>100000-10){
                theGraph.connect(i/10,i/10+4,weightOfEdge);
            }
            else{
                theGraph.connect(i,i+2,weightOfEdge);

            }
        }
    }

    @Test
    void saveTest() {
        directed_weighted_graph aa = new DWGraph_DS();
//        node_data n1 = new NodeData();
//        node_data n2 = new NodeData();
//        node_data n3 = new NodeData();
//        node_data n4 = new NodeData();
//
//        aa.addNode(n1);
//        aa.addNode(n2);
//        aa.addNode(n3);
//        aa.addNode(n4);
//
//        aa.connect(n1.getKey(), n2.getKey(), 1.3);
//        aa.connect(n2.getKey(), n3.getKey(), 1.76);

        dw_graph_algorithms a1 = new DWGraph_Algo();
        a1.init(aa);

        a1.save("C:\\Users\\Noa\\Desktop\\New folder\\ex2\\data\\A2");


    }

    @Test
    void loadTest() {
        dw_graph_algorithms newGraph = new DWGraph_Algo();
        String file = "C:\\Users\\Noa\\Desktop\\New folder (2)\\data\\A1";
        newGraph.load(file);
        directed_weighted_graph myGraph = newGraph.getGraph();
        assertEquals(36, myGraph.edgeSize());
        assertEquals(17, myGraph.nodeSize());


//        Gson gson = new GsonBuilder().setPrettyPrinting().create();
//        JsonParser jp = new JsonParser();
//        JsonElement je = jp.parse(g0);
//        String prettyJsonString = gson.toJson(je);
//        System.out.println(prettyJsonString);



    }

    @Test
    void copyGraph() { //checks if deep copy func working well
        node_data newNode1 = new NodeData(4);
        node_data newNode2 = new NodeData(2);
        node_data newNode3 = new NodeData(45);
        node_data newNode4 = new NodeData(7);
        node_data newNode5 = new NodeData(8);
        node_data newNode6 = new NodeData(5);
        node_data newNode7 = new NodeData(3);
        theGraph.addNode(newNode1);
        theGraph.addNode(newNode2);
        theGraph.addNode(newNode3);
        theGraph.addNode(newNode4);
        theGraph.addNode(newNode5);
        theGraph.addNode(newNode6);
        theGraph.addNode(newNode7);
        theGraph.connect(4, 2, 5.5);
        theGraph.connect(2, 8, 6);
        theGraph.connect(8, 4, 8);
        dw_graph_algorithms origin = new DWGraph_Algo();
        origin.init(theGraph);

        directed_weighted_graph newG = origin.copy();
        assertEquals(theGraph.getEdge(4, 2).getWeight(), newG.getEdge(4,2).getWeight());

        theGraph.removeNode(4);
        assertNotNull(newG.getNode(4));
    }


    @Test
    void saveAndLoadBigG(){ //checks the save and loaf function in a row + running time

        String fileName= "C:\\Users\\Noa\\Desktop\\New folder\\ex2\\src\\test.txt";
        CreatingBigG();

        long t1= System.currentTimeMillis();
        dw_graph_algorithms a1 = new DWGraph_Algo();
        a1.init(theGraph);
        a1.save(fileName);
        assertTrue(a1.save(fileName));
        System.out.println("Save graph to file-BIG graph. passed  - TRUE");

        dw_graph_algorithms a2 = new DWGraph_Algo();
        a2.load(fileName);
        assertTrue(a2.load(fileName));
        System.out.println("Load graph from file-BIG graph. passed  - TRUE");


        long t2 = System.currentTimeMillis();
        long ans = (long)((t2-t1));
        assertTrue(ans/1000.0 < 14);
        System.out.println("Save graph to file and load it from file to new graph, took less then 14 seconds - TRUE");
    }


    void notConnectedG1(){ //creates connected graph - (picture is added)

        node_data newNode1 = new NodeData(3);
        node_data newNode2 = new NodeData(6);
        node_data newNode3 = new NodeData(2);
        node_data newNode4 = new NodeData(10);
        node_data newNode5 = new NodeData(21);
        node_data newNode6 = new NodeData(67);
        node_data newNode7 = new NodeData(9);
        node_data newNode8 = new NodeData(4);
        theGraph.addNode(newNode1);
        theGraph.addNode(newNode2);
        theGraph.addNode(newNode3);
        theGraph.addNode(newNode4);
        theGraph.addNode(newNode5);
        theGraph.addNode(newNode6);
        theGraph.addNode(newNode7);
        theGraph.addNode(newNode8);
        theGraph.connect(2,3,8.2);
        theGraph.connect(3,6,0.8);
        theGraph.connect(6,10,10.01);
        theGraph.connect(6,2,5.6);
        theGraph.connect(2,10,18.3);
        theGraph.connect(10,4,7.2);
        theGraph.connect(4,10,8.4);
        theGraph.connect(6,21,3.2);
        theGraph.connect(21,4,1.0);
        theGraph.connect(21,67,2.3);

        theGraph.connect(67,21,6.0);

        theGraph.connect(67,9,3.5);

        theGraph.connect(9,67,4.1);

        theGraph.connect(9,4,2.7);


    }
    void ConnectedG1(){ //creates connected graph - (picture is added)
        node_data newNode1 = new NodeData(0);
        node_data newNode2 = new NodeData(1);
        node_data newNode3 = new NodeData(2);
        node_data newNode4 = new NodeData(3);

        theGraph.addNode(newNode1);
        theGraph.addNode(newNode2);
        theGraph.addNode(newNode3);
        theGraph.addNode(newNode4);

        theGraph.connect(0,1,8.2);
        theGraph.connect(1,2,0.8);
        theGraph.connect(2,3,10.01);
        theGraph.connect(3,0,5.6);

    }
    void ConnectedG2(){ //creates connected graph - (picture is added)
        node_data newNode1 = new NodeData(0);
        node_data newNode2 = new NodeData(1);
        node_data newNode3 = new NodeData(2);
        node_data newNode4 = new NodeData(3);
        node_data newNode5 = new NodeData(4);


        theGraph.addNode(newNode1);
        theGraph.addNode(newNode2);
        theGraph.addNode(newNode3);
        theGraph.addNode(newNode4);
        theGraph.addNode(newNode5);

        theGraph.connect(0,1,8.2);
        theGraph.connect(1,2,0.8);
        theGraph.connect(2,3,10.01);
        theGraph.connect(3,0,5.6);
        theGraph.connect(2,4,5.6);
        theGraph.connect(4,2,5.6);

    }

    @Test
    void connectivity(){//checks the connectivity of the graph
        ConnectedG1();
        dw_graph_algorithms algo = new DWGraph_Algo();
        algo.init(theGraph);
        assertTrue(algo.isConnected());
        System.out.print("Connectivity ex1.tests.test - Small graph - TRUE");

    }

    @Test
    void deConnectivity(){
        notConnectedG1();
        dw_graph_algorithms algo = new DWGraph_Algo();
        algo.init(theGraph);
        assertFalse(algo.isConnected());
        System.out.print("Connectivity test - large graph - TRUE");

    }

    @Test
    void connectivity2(){//checks the connectivity of the graph
        ConnectedG2();
        dw_graph_algorithms algo = new DWGraph_Algo();
        algo.init(theGraph);
        assertTrue(algo.isConnected());
        System.out.print("Connectivity ex1.tests.test - Small graph - TRUE");

    }
    @Test
    void shortestPathDist(){ //checks the shortestPathDist func
        notConnectedG1();
        dw_graph_algorithms algo = new DWGraph_Algo();
        algo.init(theGraph);
        double shortestDist = algo.shortestPathDist(3,9);
        double EPS = 0.01;
        assertTrue(9.8-shortestDist < EPS);
        System.out.println("Shortest path destination ex1.tests.test, number 1- passed - TRUE");
    }

//    @Test
//    void shortestPathDistTime(){ // checks the running time of shortestPathDistTime
//        notConnectedG1();
//        dw_graph_algorithms algo = new DWGraph_Algo();
//        algo.init(theGraph);
//        long t1= System.currentTimeMillis();
//        algo.shortestPathDist(0,99999);
//        long t2= System.currentTimeMillis();
//        assertTrue((t2-t1)/1000.0 < 10);
//        System.out.println("Time it takes for searching the shortest path in a BIG graph: " + (t2-t1)/1000.0 + " TRUE");
//        List<node_info> aa = algo.shortestPath(0,10000);
//        System.out.println("Number of nodes in path: "+aa.size());
//    }
//
    @Test
    void create() {

        node_data node1 = new NodeData(0);
        node_data node2 = new NodeData(1);
        node_data node3 = new NodeData(2);
        node_data node4 = new NodeData(3);
        node_data node5 = new NodeData(4);
        node_data node6 = new NodeData(5);
        node_data node7 = new NodeData(6);
        node_data node8 = new NodeData(7);
        node_data node9 = new NodeData(8);
        node_data node10 = new NodeData(9);
        node_data node11 = new NodeData(10);
        theGraph.addNode(node1);
        theGraph.addNode(node2);
        theGraph.addNode(node3);
        theGraph.addNode(node4);
        theGraph.addNode(node5);
        theGraph.addNode(node6);
        theGraph.addNode(node7);
        theGraph.addNode(node8);
        theGraph.addNode(node9);
        theGraph.addNode(node10);
        theGraph.addNode(node11);

        theGraph.connect(0,1,6);
        theGraph.connect(1,0,6);
        theGraph.connect(1,2,6);

        theGraph.connect(2,1,6);
        theGraph.connect(2,3,6);
        theGraph.connect(3,2,6);
        theGraph.connect(3,4,6);
        theGraph.connect(4,3,6);
        theGraph.connect(4,5,6);
        theGraph.connect(5,4,6);
        theGraph.connect(5,6,6);
        theGraph.connect(6,5,6);
        theGraph.connect(6,7,6);
        theGraph.connect(7,6,6);
        theGraph.connect(7,8,6);
        theGraph.connect(8,7,6);
        theGraph.connect(8,9,6);
        theGraph.connect(9,8,6);
        theGraph.connect(9,10,6);
        theGraph.connect(10,9,6);
        theGraph.connect(10,0,6);
        theGraph.connect(0,10,6);

        dw_graph_algorithms a = new DWGraph_Algo();
        a.init(theGraph);
        List<node_data> tt = a.shortestPath(0,3);
        System.out.println(tt.size());


    }

    @Test
    void shortestPathList(){//checks shortestPath func
        notConnectedG1();
        dw_graph_algorithms aa = new DWGraph_Algo();
        aa.init(theGraph);
        List<node_data> ans = aa.shortestPath(3, 9);
        assertEquals(3, ans.get(0).getKey());
        assertEquals(6, ans.get(1).getKey());
        assertEquals(21, ans.get(2).getKey());
        assertEquals(67, ans.get(3).getKey());
        assertEquals(9, ans.get(4).getKey());

        assertEquals(5, ans.size());
        System.out.println("Number of nodes in path: "+ ans.size());
        System.out.println("The list :3->6->21->67->9" );

    }

//    @Test
//    void loadTest(){
//        String graph = "C:\\Users\\Noa\\Desktop\\New folder\\ex2\\src\\A0";
//    }



    public void unConnG(){
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
        //theGraph.connect(1,0,2);
        theGraph.connect(0,1,2);
        //theGraph.connect(1,2,2);
        theGraph.connect(2,1,2);

        theGraph.connect(4,3,7);
        theGraph.connect(3,4,7);



    }
    @Test
    void dfsTest(){
        unConnG();
        DFS dfs = new DFS();
        List<Integer> aa = dfs.DFSalgo(theGraph);
        System.out.println(aa.size());
        System.out.println(aa.get(0));
        System.out.println(aa.get(1));
        System.out.println(aa.get(2));
        System.out.println(aa.get(3));
        System.out.println(aa.get(4));
    }
}
