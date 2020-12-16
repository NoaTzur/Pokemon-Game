package gameClient;

import java.util.HashMap;
import java.util.Map;

/**
 * this class in a "helper" class, we build this to prevent the agents from walking to the same pokemon.
 * this class contains one data structure = a Map build this way:
 * <Integer - is the agent id, CL_Pokemon - is the chosen pokemon at a moment for the specific agent>.
 * in this way we assure that pokemon "connected" to some agent wont be chose again.
 * in the "init" function in the main class, we initiate that structure with all the agents at the specific game,
 * and each agent pokemon is "null"
 * if the agent is "pointing" to null, we can know that no pokemon "connected" to him and we can choose new one!
 * this class has 3 functions:
 * addPok = connect agent to his chosen pokemon
 * getPok = return the pokemon that connected to the agent.
 * containsPok = function that checks if a specific pokemon is connected to some agent.
 * containsPok function is using the "equals" function. we override this function in the CL_Pokemon class.
 */
public class EdgesWithPok {

    private static Map<Integer, CL_Pokemon> chosenPoks = new HashMap<>();

    /**
     * connect agent to his chosen pokemon
     * @param id - id of the agent
     * @param pok - chosen pokemon
     */
    public void addPok(int id, CL_Pokemon pok) {
        chosenPoks.put(id, pok);
    }

    /**
     * returns true if the pokemon is already connected to some agent ,
     * and false if there isnt an agent connected to it
     * @param pok - pokemon
     * @return true/false
     */
    public boolean containsPok(CL_Pokemon pok){
        boolean ans = false;
        if(chosenPoks.isEmpty()) return ans;
        for(int i=0; i<chosenPoks.size(); i++){
            if(chosenPoks.get(i) != null) {
                if (chosenPoks.get(i).equals(pok)) {
                    ans = true;
                }
            }
        }
        return ans;
    }

    /**
     * returns the pokemon that connected to the agent.
     * @param id - id of the agent
     * @return the connected pokemon (CL_Pokemon type)
     */
    public CL_Pokemon getPok(int id){
        return chosenPoks.get(id);
    }
}
