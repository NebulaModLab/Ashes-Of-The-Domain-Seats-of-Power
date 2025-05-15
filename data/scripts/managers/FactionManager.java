package data.scripts.managers;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.CampaignClockAPI;
import com.fs.starfarer.api.combat.MutableStat;
import data.scripts.models.BaseFactionPolicy;
import data.scripts.models.BaseFactionTimelineEvent;
import data.scripts.models.CycleTimelineEvents;
import data.scripts.models.FactionPolicySpec;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

public class FactionManager {
    public ArrayList<BaseFactionPolicy>currentFactionPolicies = new ArrayList<>();
    public HashSet<String> copyOfPolicies = new HashSet<>();
    public MutableStat availablePolicies = new MutableStat(1f);
    public ArrayList<CycleTimelineEvents>cycles = new ArrayList<>();
    public static final String memKey ="$aotd_faction_manager";
    int currentXP;

    public ArrayList<CycleTimelineEvents> getCycles() {
        return cycles;
    }

    public MutableStat xpPointsPerMonth = new MutableStat(0f);
    public void addCycle(int cycle){
        if(cycles.stream().noneMatch(x->x.getRecordedCycle()==cycle)){
            cycles.add(new CycleTimelineEvents(cycle));
        }
    }
    public void addEventToTimeline(BaseFactionTimelineEvent event){
        CampaignClockAPI clock= Global.getSector().getClock();
        event.setDate(clock.getCycle(),clock.getDay(),clock.getMonth());
        event.updateDataUponEntryOfUI();
        getCycle(Global.getSector().getClock().getCycle()).addNewEvent(event);
    }
    public void removeCycle(int cycle){
        cycles.removeIf(x->x.getRecordedCycle()==cycle);
    }
    public CycleTimelineEvents getCycle(int cycle){
        addCycle(cycle);
        return cycles.stream().filter(x->x.getRecordedCycle()==cycle).findFirst().get();
    }
    public ArrayList<BaseFactionPolicy> getCurrentFactionPolicies() {
        return currentFactionPolicies;
    }

    public HashSet<String> getCopyOfPolicies() {
        return copyOfPolicies;
    }

    public MutableStat getAvailablePolicies() {
        return availablePolicies;
    }
    public void reportMonthEnd() {

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
    public BaseFactionPolicy getPolicyFromList(String id){
        return currentFactionPolicies.stream().filter(x->x.getSpec().getId().equals(id)).findFirst().orElse(FactionPolicySpecManager.getSpec(id).getPlugin());
    }

    public boolean doesHavePolicyEnabled(String policyID){
        return currentFactionPolicies.stream().anyMatch(x->x.getSpec().getId().equals(policyID));
    }
    public boolean doesHavePolicyInCopy(String policyID){
        return copyOfPolicies.contains(policyID);
    }
    public void advance(float amount ){
        currentFactionPolicies.forEach(x->x.advance(amount));
    }
    public void addNewPolicy(String id){
        boolean policyExists = doesHavePolicyEnabled(id);
        if(!policyExists){
            BaseFactionPolicy policy = FactionPolicySpecManager.getSpec(id).getPlugin();
            currentFactionPolicies.add(policy);
            policy.applyPolicyEffectAfterChangeInUI(false);
            policy.applyPolicy();
        }
    }
    public void removePolicy(String id){
        boolean policyExists = doesHavePolicyEnabled(id);
        if(policyExists){
            currentFactionPolicies.stream()
                    .filter(x -> x.getSpec().getId().equals(id))
                    .findFirst()
                    .ifPresent(x -> {
                        x.applyPolicyEffectAfterChangeInUI(true);
                        x.unapplyPolicy();
                    });

            currentFactionPolicies.removeIf(x->x.getSpec().getId().equals(id));
        }
    }
    public void addPolicyToCopy(String id){
        copyOfPolicies.add(id);
    }
    public void removePolicyFromCopy(String id){
        copyOfPolicies.remove(id);
    }
    public void applyChangesFromUI(){
         List<BaseFactionPolicy> toRemove = new ArrayList<>(getCurrentFactionPolicies().stream()
                .filter(x -> !getCopyOfPolicies().contains(x.getSpec().getId()))
                .toList());

        toRemove.forEach(x -> removePolicy(x.getSpec().getId()));
        toRemove.clear();
        getCopyOfPolicies().stream().filter(x->!doesHavePolicyEnabled(x)).forEach(this::addNewPolicy);
        getCopyOfPolicies().clear();
        copyOfPolicies.clear();
    }
    public void updateListBeforeUI(){
        copyOfPolicies.clear();
        currentFactionPolicies.forEach(x->copyOfPolicies.add(x.getSpec().getId()));
    }
    public ArrayList<FactionPolicySpec> getSpecsForAvailableUI(){
        ArrayList<FactionPolicySpec> specs = new ArrayList<>(FactionPolicySpecManager.getFactionPolicySpecs().values());
        specs.removeIf(x->!canUsePolicyInUI(x.getPlugin()));
        specs.removeIf(x->copyOfPolicies.contains(x.getId()));

        return specs;
    }
    public void gainXP(float amount ){
        currentXP += (int) amount;
    }
    public boolean canUsePolicyInUI(BaseFactionPolicy policy){
        boolean canUse = true;
        for (String incompatiblePolicyId : policy.getSpec().getIncompatiblePolicyIds()) {
            if(FactionManager.getInstance().doesHavePolicyInCopy(incompatiblePolicyId)){
                canUse = false;
                break;
            }
        }


        return canUse&policy.canUsePolicy()&&copyOfPolicies.stream().noneMatch(x->FactionPolicySpecManager.getSpec(x).getIncompatiblePolicyIds().contains(policy.getSpec().getId()));
    }

}
