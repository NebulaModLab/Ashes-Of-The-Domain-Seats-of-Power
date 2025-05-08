package data.scripts.managers;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.combat.MutableStat;
import data.scripts.models.BaseFactionPolicy;
import data.scripts.models.FactionPolicySpec;

import java.util.ArrayList;

public class FactionManager {
    public ArrayList<BaseFactionPolicy>currentFactionPolicies = new ArrayList<>();
    public MutableStat availablePolicies = new MutableStat(1f);
    public static final String memKey ="$aotd_faction_manager";
    int currentXP;
    public MutableStat xpPointsPerMonth = new MutableStat(0f);

    public ArrayList<BaseFactionPolicy> getCurrentFactionPolicies() {
        return currentFactionPolicies;
    }

    public MutableStat getAvailablePolicies() {
        return availablePolicies;
    }

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
    public void advance(float amount ){
        currentFactionPolicies.forEach(x->x.advance(amount));
    }
    public void addNewPolicy(String id){
        boolean policyExists = doesHavePolicyEnabled(id);
        if(!policyExists){
            BaseFactionPolicy policy = FactionPolicySpecManager.getSpec(id).getPlugin();
            currentFactionPolicies.add(policy);
            policy.applyPolicy();
        }
    }
    public void removePolicy(String id){
        boolean policyExists = doesHavePolicyEnabled(id);
        if(policyExists){
            currentFactionPolicies.stream().filter(x->x.getSpec().getId().equals(id)).findFirst().ifPresent(BaseFactionPolicy::unapplyPolicy);
            currentFactionPolicies.removeIf(x->x.getSpec().getId().equals(id));
        }
    }
    public ArrayList<FactionPolicySpec>getSpecsForAvailable(){
        ArrayList<FactionPolicySpec> specs = new ArrayList<>(FactionPolicySpecManager.getFactionPolicySpecs().values());
        specs.removeIf(x->currentFactionPolicies.stream().anyMatch(y->y.getSpec().getId().equals(x.getId())));
        return specs;
    }
    public void gainXP(float amount ){
        currentXP += (int) amount;
    }

}
