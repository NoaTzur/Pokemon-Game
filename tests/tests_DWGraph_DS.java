import api.DWGraph_DS;
import api.NodeData;
import api.directed_weighted_graph;
import api.node_data;
import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.Iterator;
import java.util.Random;


import static org.junit.jupiter.api.Assertions.*;

public class tests_DWGraph_DS {

    directed_weighted_graph theGraph = new DWGraph_DS();

    @Test
    public void getNodeTest(){
        directed_weighted_graph myGraph = new DWGraph_DS();
        assertNull(myGraph.getNode(1));
        node_data newNode= new NodeData();
        myGraph.addNode(newNode);
        assertTrue(myGraph.getV().contains(newNode));
        myGraph.removeNode(newNode.getKey());
        assertNull(myGraph.getNode(newNode.getKey()));
    }

    @Test
    public void getEdgeTest(){
        directed_weighted_graph myGraph = new DWGraph_DS();
        node_data newNode1= new NodeData();
        node_data newNode2= new NodeData();
        node_data newNode3= new NodeData();
        myGraph.addNode(newNode1);
        myGraph.addNode(newNode2);
        myGraph.addNode(newNode3);
        myGraph.connect(newNode1.getKey(),newNode2.getKey(),1.2);
        assertEquals(myGraph.getEdge(newNode1.getKey(),newNode2.getKey()).getWeight(),1.2);
        assertEquals(myGraph.getEdge(newNode1.getKey(),newNode2.getKey()).getSrc(),newNode1.getKey());
        assertEquals(myGraph.getEdge(newNode1.getKey(),newNode2.getKey()).getDest(),newNode2.getKey());
        assertNull(myGraph.getEdge(newNode3.getKey(),newNode2.getKey()));
    }
    @Test
    public void connectTest() {
        directed_weighted_graph myGraph = new DWGraph_DS();
        node_data newNode1 = new NodeData();
        node_data newNode2 = new NodeData();
        myGraph.addNode(newNode1);
        myGraph.addNode(newNode2);
        myGraph.connect(newNode1.getKey(), newNode2.getKey(), 1.2);
        myGraph.connect(newNode1.getKey(), newNode2.getKey(), 3.4);
        myGraph.connect(newNode1.getKey(), newNode1.getKey(), 3.4);
        assertEquals(myGraph.getEdge(newNode1.getKey(), newNode2.getKey()).getWeight(), 3.4);
        myGraph.connect(6, newNode2.getKey(), 3.4);


    }
    @Test
    public void removeNodeTest() {
        directed_weighted_graph myGraph = new DWGraph_DS();
        node_data newNode1 = new NodeData();
        node_data newNode2 = new NodeData();
        node_data newNode3 = new NodeData();
        node_data newNode4 = new NodeData();
        myGraph.addNode(newNode1);
        myGraph.addNode(newNode2);
        myGraph.addNode(newNode3);
        myGraph.addNode(newNode4);
        myGraph.connect(newNode1.getKey(), newNode2.getKey(), 1.2);
        myGraph.connect(newNode2.getKey(), newNode3.getKey(), 2.2);
        myGraph.connect(newNode2.getKey(), newNode4.getKey(), 2.4);
        myGraph.removeNode(newNode2.getKey());
        assertFalse(myGraph.getV().contains(newNode2));
        assertEquals(0,myGraph.edgeSize());
        assertNull(myGraph.getEdge(newNode1.getKey(), newNode2.getKey()));
        assertNull(myGraph.getNode(newNode2.getKey()));
    }

    @Test
    public void removeEdgeTest() {
        directed_weighted_graph myGraph = new DWGraph_DS();
        node_data newNode1 = new NodeData();
        node_data newNode2 = new NodeData();
        node_data newNode3 = new NodeData();
        node_data newNode4 = new NodeData();
        myGraph.addNode(newNode1);
        myGraph.addNode(newNode2);
        myGraph.addNode(newNode3);
        myGraph.addNode(newNode4);
        myGraph.connect(newNode1.getKey(), newNode2.getKey(), 1.2);
        myGraph.connect(newNode2.getKey(), newNode3.getKey(), 2.2);
        myGraph.connect(newNode2.getKey(), newNode4.getKey(), 2.4);
        myGraph.removeEdge(newNode1.getKey(), newNode2.getKey());
        assertNull(myGraph.getEdge(newNode1.getKey(), newNode2.getKey()));
        assertEquals(2,myGraph.edgeSize());

    }

    @Test
    void getVTest() {
        directed_weighted_graph myGraph = new DWGraph_DS();
        node_data newNode1 = new NodeData();
        node_data newNode2 = new NodeData();
        node_data newNode3 = new NodeData();
        node_data newNode4 = new NodeData();
        myGraph.addNode(newNode1);
        myGraph.addNode(newNode2);
        myGraph.addNode(newNode3);
        myGraph.addNode(newNode4);
        myGraph.connect(newNode2.getKey(), newNode1.getKey(), 1.2);
        myGraph.connect(newNode2.getKey(), newNode3.getKey(), 2.2);
        myGraph.connect(newNode2.getKey(), newNode4.getKey(), 2.4);
        Collection<node_data> v = myGraph.getV();
        Iterator<node_data> it = v.iterator();
        while (it.hasNext()) {
            node_data n = it.next();
            assertNotNull(n);
        }
    }
    @Test
    void getEtest() {
        directed_weighted_graph myGraph = new DWGraph_DS();
        node_data newNode1 = new NodeData();
        node_data newNode2 = new NodeData();
        node_data newNode3 = new NodeData();
        node_data newNode4 = new NodeData();
        myGraph.addNode(newNode1);
        myGraph.addNode(newNode2);
        myGraph.addNode(newNode3);
        myGraph.addNode(newNode4);
        myGraph.connect(newNode2.getKey(), newNode1.getKey(), 1.2);
        myGraph.connect(newNode2.getKey(), newNode3.getKey(), 2.2);
        myGraph.connect(newNode2.getKey(), newNode4.getKey(), 2.4);
        Collection<node_data> v = myGraph.getV();
        Iterator<node_data> it = v.iterator();
        while (it.hasNext()) {
            node_data n = it.next();
            assertNotNull(n);
        }
    }
    @Test
    void nodeSizeTest() {
        directed_weighted_graph myGraph = new DWGraph_DS();
        assertEquals(myGraph.nodeSize(),0);
        node_data newNode1 = new NodeData();
        node_data newNode2 = new NodeData();
        node_data newNode3 = new NodeData();
        node_data newNode4 = new NodeData();
        myGraph.addNode(newNode1);
        myGraph.addNode(newNode2);
        myGraph.addNode(newNode3);
        myGraph.addNode(newNode4);
        assertEquals(myGraph.nodeSize(),4);
        myGraph.removeNode(newNode1.getKey());
        assertEquals(myGraph.nodeSize(),3);
    }

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
    void buildTime(){ // checks the time it takes to build a 100,000 nodes and 1,000,000 edges Graph
        long t1= System.currentTimeMillis();
        CreatingBigG();
        long t2 = System.currentTimeMillis();
        long ans = (long) ((t2-t1)/1000.0);
        System.out.println("milliseconds:" + ans/1000.0);
        assertTrue(ans/1000.0 < 10);
    }



}
