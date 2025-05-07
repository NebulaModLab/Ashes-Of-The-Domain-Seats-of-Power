package data.scripts.managers;

import com.fs.starfarer.api.Global;
import data.scripts.models.BaseFactionPolicy;

import java.util.ArrayList;

public class FactionManager {
    public ArrayList<BaseFactionPolicy>currentFactionPolicies = new ArrayList<>();

    public static final String memKey ="$aotd_faction_manager";
    public static FactionManager getInstance(){
        if(Global.getSector().getPersistentData().get(memKey)==null){
           setInstance();
        }
        return (FactionManager)Global.getSector().getPersistentData().get(memKey);
    }
    public static void setInstance(){
        Global.getSector().getPersistentData().put(memKey, new FactionManager());
    }

    public boolean doesHavePolicyEnabled(String policyID){
        return currentFactionPolicies.stream().anyMatch(x->x.getSpec().getId().equals(policyID));
    }

}
