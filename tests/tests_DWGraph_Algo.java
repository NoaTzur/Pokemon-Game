import api.*;
import org.junit.jupiter.api.Test;

import java.util.*;


import static org.junit.jupiter.api.Assertions.*;

public class tests_DWGraph_Algo {


    @Test
    void isConnectedtest1() {
        directed_weighted_graph g = new DWGraph_DS();
        dw_graph_algorithms g0 = new DWGraph_Algo();
        boolean b= g0.isConnected();
        assertTrue(b); //Empty graph
        node_data newNode0= new NodeData();
        node_data newNode1= new NodeData();
        node_data newNode2= new NodeData();
        node_data newNode3= new NodeData();
        node_data newNode4= new NodeData();
        node_data newNode5= new NodeData();
        node_data newNode6= new NodeData();
        node_data newNode7= new NodeData();
        node_data newNode8= new NodeData();
        node_data newNode9= new NodeData();
        node_data newNode10= new NodeData();
        g.addNode(newNode0);
        g.addNode(newNode1);
        g.addNode(newNode2);
        g.addNode(newNode3);
        g.addNode(newNode4);
        g.addNode(newNode5);
        g.connect(0,1,1.2);
        g.connect(1,2,1.4);
        g.connect(2,3,2.3);
        g.connect(3,4,2.9);
        g.connect(4,5,5.6);
        g.connect(5,2,1.9);
        g.connect(2,0,1.9);
        g0.init(g);
        boolean f=  ((DWGraph_Algo) g0).BFS2(0);
        b= g0.isConnected();
        assertTrue(b);
        assertTrue(b);

    }
    @Test
    void isConnectedtest2() {
        directed_weighted_graph g = new DWGraph_DS();
        dw_graph_algorithms g0 = new DWGraph_Algo();
        node_data newNode0= new NodeData();
        g.addNode(newNode0);
        boolean b = g0.isConnected();
        assertTrue(b);  // one vertex
        node_data newNode1= new NodeData();
        g.addNode(newNode1);
        g0.init(g);
        b = g0.isConnected();
        assertFalse(b); // two unconnected vertex
        g.connect(newNode0.getKey(),newNode1.getKey(),3);
        b = g0.isConnected();
        assertFalse(b);// unconnected
        g.connect(newNode1.getKey(),newNode0.getKey(),2);
        b = g0.isConnected();
        assertTrue(b);
        g.removeNode(1);
        b = g0.isConnected();
        assertTrue(b);
    }
  @Test
  void isConnectedtest3() {
      directed_weighted_graph g = new DWGraph_DS();
      dw_graph_algorithms ga = new DWGraph_Algo();
      boolean b = ga.isConnected();
      assertTrue(b); //Empty graph
      node_data newnode0=new NodeData(0,0,0,0);
      node_data newnode1=new NodeData(1,0,0,0);
      node_data newnode2=new NodeData(2,0,0,0);
      node_data newnode3=new NodeData(3,0,0,0);
      node_data newnode4=new NodeData(4,0,0,0);
      g.addNode(newnode0);
      g.addNode(newnode1);
      g.addNode(newnode2);
      g.addNode(newnode3);
      g.addNode(newnode4);
      g.connect(newnode0.getKey(),newnode2.getKey(),1.2);
      g.connect(newnode0.getKey(),newnode1.getKey(),2.3);
      g.connect(newnode1.getKey(),newnode4.getKey(),5.6);
      g.connect(newnode4.getKey(),newnode3.getKey(),1.9);
      g.connect(newnode3.getKey(),newnode0.getKey(),2.1);
      g.connect(newnode2.getKey(),newnode0.getKey(),3.1);
      ga.init(g);
      b = ga.isConnected();
      assertTrue(b);
      g.removeNode(4);
      b = ga.isConnected();
      assertFalse(b);
      g.removeNode(0);
      g.removeNode(1);// 2 nodes
      b = ga.isConnected();
      assertFalse(b);
      g.removeNode(3);// 1 nodes
      b = ga.isConnected();
      assertTrue(b);
  }
    @Test
    void isConnectedtest4() {
        directed_weighted_graph g = new DWGraph_DS();
        dw_graph_algorithms ga = new DWGraph_Algo();
        node_data newnode1=new NodeData(1,0,0,0);
        node_data newnode2=new NodeData(2,0,0,0);
        node_data newnode3=new NodeData(3,0,0,0);
        node_data newnode4=new NodeData(4,0,0,0);

        g.addNode(newnode1);
        g.addNode(newnode2);
        g.addNode(newnode3);
        g.addNode(newnode4);
        g.connect(newnode1.getKey(),newnode3.getKey(),1.2);
        g.connect(newnode3.getKey(),newnode4.getKey(),2.3);
        g.connect(newnode3.getKey(),newnode2.getKey(),5.6);
        g.connect(newnode4.getKey(),newnode3.getKey(),1.9);
        g.connect(newnode2.getKey(),newnode1.getKey(),2.1);;
        ga.init(g);
        boolean b = ga.isConnected();
        assertTrue(b);
        g.removeEdge(newnode4.getKey(),newnode3.getKey());
        b = ga.isConnected();
        assertFalse(b);
    }
    @Test
    void shortestPathDistTast() {
        directed_weighted_graph g = new DWGraph_DS();
        dw_graph_algorithms ga = new DWGraph_Algo();
        node_data newnode0 = new NodeData(0, 0, 0, 0);
        node_data newnode1 = new NodeData(1, 0, 0, 0);
        node_data newnode2 = new NodeData(2, 0, 0, 0);
        node_data newnode3 = new NodeData(3, 0, 0, 0);
        node_data newnode4 = new NodeData(4, 0, 0, 0);
        g.addNode(newnode0);
        g.addNode(newnode1);
        g.addNode(newnode2);
        g.addNode(newnode3);
        g.addNode(newnode4);
        g.connect(newnode0.getKey(), newnode2.getKey(), 1.2);
        g.connect(newnode0.getKey(), newnode1.getKey(), 2.3);
        g.connect(newnode1.getKey(), newnode4.getKey(), 5.6);
        g.connect(newnode4.getKey(), newnode3.getKey(), 1.9);
        g.connect(newnode3.getKey(), newnode0.getKey(), 2.1);
        g.connect(newnode2.getKey(), newnode0.getKey(), 3.1);
        ga.init(g);

        double shortpath = ga.shortestPathDist(3, 0);
        assertEquals(shortpath, 2.1);
        shortpath = ga.shortestPathDist(2, 1);
        assertEquals(shortpath, 5.4);
        g.removeNode(3);
        shortpath = ga.shortestPathDist(4, 1);
        assertEquals(shortpath, -1);
        shortpath = ga.shortestPathDist(2, 2); // src=dest
        assertEquals(shortpath, 0);
    }
    @Test
    void  shortestPathTast() {
        directed_weighted_graph g = new DWGraph_DS();
        dw_graph_algorithms ga = new DWGraph_Algo();
        node_data newnode0 = new NodeData(0, 0, 0, 0);
        node_data newnode1 = new NodeData(1, 0, 0, 0);
        node_data newnode2 = new NodeData(2, 0, 0, 0);
        node_data newnode3 = new NodeData(3, 0, 0, 0);
        node_data newnode4 = new NodeData(4, 0, 0, 0);
        g.addNode(newnode0);
        g.addNode(newnode1);
        g.addNode(newnode2);
        g.addNode(newnode3);
        g.addNode(newnode4);
        g.connect(newnode0.getKey(), newnode2.getKey(), 1.2);
        g.connect(newnode0.getKey(), newnode1.getKey(), 2.3);
        g.connect(newnode1.getKey(), newnode4.getKey(), 5.6);
        g.connect(newnode4.getKey(), newnode3.getKey(), 1.9);
        g.connect(newnode3.getKey(), newnode0.getKey(), 2.1);
        g.connect(newnode2.getKey(), newnode0.getKey(), 3.1);
        ga.init(g);
        List<node_data> listPath = new LinkedList<>();
        listPath = ga.shortestPath(3, 0);
        node_data newnode=new NodeData();
        assertEquals(listPath.get(0), 3);
        assertEquals(listPath.get(1), 0);

        listPath.removeAll(listPath);
        listPath = ga.shortestPath(4, 2);
        assertEquals(listPath.get(0), 4);
        assertEquals(listPath.get(1), 3);
        assertEquals(listPath.get(2), 0);
        assertEquals(listPath.get(3), 2);

        listPath.removeAll(listPath);
        listPath = ga.shortestPath(4, 4);
        assertEquals(listPath.get(0), 4);

        g.removeNode(0);
        listPath.removeAll(listPath);
        listPath = ga.shortestPath(4, 2);
        assertNull(listPath);
    }

}
