package gameClient;

import java.util.HashMap;
import java.util.Map;

public class EdgesWithPok {

    private static Map<Integer, CL_Pokemon> chosenPoks = new HashMap<>();

    public void addPok(int id, CL_Pokemon pok) {
        chosenPoks.put(id, pok);
    }
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
    public CL_Pokemon getPok(int id){
        return chosenPoks.get(id);
    }
}
