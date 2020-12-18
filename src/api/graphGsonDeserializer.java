package api;

import com.google.gson.*;

import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;

/**
 * takes an JSON file and creates a directed_weighted_graph.
 */
public class graphGsonDeserializer implements JsonDeserializer<directed_weighted_graph> {

    @Override
    public directed_weighted_graph deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        JsonObject jsonObj = jsonElement.getAsJsonObject();
        JsonArray myGraphOBJ = jsonObj.get("Nodes").getAsJsonArray();

        directed_weighted_graph myGraph = new DWGraph_DS();

        //creating the graph hashmap
        for(JsonElement nodes: myGraphOBJ) {

            int id = nodes.getAsJsonObject().get("id").getAsInt();

            String pos = nodes.getAsJsonObject().get("pos").getAsString();
            List<String> elephantList = Arrays.asList(pos.split(","));

            double locationX = Double.parseDouble(elephantList.get(0));
            double locationY = Double.parseDouble(elephantList.get(1));
            double locationZ = Double.parseDouble(elephantList.get(2));

            node_data newNode = new NodeData(id, locationX, locationY, locationZ);

            myGraph.addNode(newNode);
         }

        JsonArray edgesHash = jsonObj.get("Edges").getAsJsonArray();
        for (JsonElement edges : edgesHash){

            int src = edges.getAsJsonObject().get("src").getAsInt();
            double w = edges.getAsJsonObject().get("w").getAsDouble();
            int dest = edges.getAsJsonObject().get("dest").getAsInt();

            myGraph.connect(src, dest, w);
            }

        //don't need to copy the reverseEdges hashmap because in the connect function = the reverseEdges fills automatically
        return myGraph;
    }
}
