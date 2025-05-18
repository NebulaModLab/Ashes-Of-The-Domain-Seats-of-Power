package data.scripts.managers;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.CampaignClockAPI;
import com.fs.starfarer.api.campaign.econ.MarketAPI;
import com.fs.starfarer.api.combat.MutableStat;
import com.fs.starfarer.api.impl.campaign.ids.Factions;
import com.fs.starfarer.api.util.Misc;
import com.fs.starfarer.campaign.econ.Market;
import data.scripts.models.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

public class FactionManager {
    public ArrayList<BaseFactionPolicy> currentFactionPolicies = new ArrayList<>();
    public HashSet<String> copyOfPolicies = new HashSet<>();
    public MutableStat availablePolicies = new MutableStat(1f);
    public ArrayList<CycleTimelineEvents> cycles = new ArrayList<>();
    public HashMap<TimelineEventType, MutableStat> goalStats = new HashMap<>();
    public static int maxPerCategory = 1000;
    public static final String memKey = "$aotd_faction_manager";
    int currentXP;

    public HashMap<TimelineEventType, MutableStat> getGoalStats() {
        return goalStats;
    }

    public MutableStat getGoalStat(TimelineEventType type) {
        return goalStats.get(type);
    }
    public ArrayList<CycleTimelineEvents> getCycles() {
        return cycles;
    }

    public MutableStat xpPointsPerMonth = new MutableStat(0f);

    public void addCycle(int cycle) {
        if (cycles.stream().noneMatch(x -> x.getRecordedCycle() == cycle)) {
            cycles.add(new CycleTimelineEvents(cycle));
        }
    }

    public void addEventToTimeline(BaseFactionTimelineEvent event) {
        CampaignClockAPI clock = Global.getSector().getClock();
        event.setDate(clock.getCycle(), clock.getDay(), clock.getMonth());
        event.updateDataUponEntryOfUI();
        getCycle(Global.getSector().getClock().getCycle()).addNewEvent(event);
    }

    public void removeCycle(int cycle) {
        cycles.removeIf(x -> x.getRecordedCycle() == cycle);
    }

    public CycleTimelineEvents getCycle(int cycle) {
        addCycle(cycle);
        return cycles.stream().filter(x -> x.getRecordedCycle() == cycle).findFirst().get();
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

    public static FactionManager getInstance() {
        if (Global.getSector().getPersistentData().get(memKey) == null) {
            setInstance();
        }
        return (FactionManager) Global.getSector().getPersistentData().get(memKey);
    }

    public static void setInstance() {
        FactionManager manager = new FactionManager();
        manager.goalStats.put(TimelineEventType.MILITARY, new MutableStat(0f));
        manager.goalStats.put(TimelineEventType.PROSPERITY, new MutableStat(0f));
        manager.goalStats.put(TimelineEventType.RESEARCH_AND_EXPLORATION, new MutableStat(0f));
        Global.getSector().getPersistentData().put(memKey, manager);

    }

    public BaseFactionPolicy getPolicyFromList(String id) {
        return currentFactionPolicies.stream().filter(x -> x.getSpec().getId().equals(id)).findFirst().orElse(FactionPolicySpecManager.getSpec(id).getPlugin());
    }

    public boolean doesHavePolicyEnabled(String policyID) {
        return currentFactionPolicies.stream().anyMatch(x -> x.getSpec().getId().equals(policyID));
    }

    public boolean doesHavePolicyInCopy(String policyID) {
        return copyOfPolicies.contains(policyID);
    }

    public void advance(float amount) {
        currentFactionPolicies.forEach(x -> x.advance(amount));
        cycles.forEach(x->x.getEventsDuringCycle().forEach(y->y.applyEffects(y.getEventsAffected())));
    }

    public void addNewPolicy(String id) {
        boolean policyExists = doesHavePolicyEnabled(id);
        if (!policyExists) {
            BaseFactionPolicy policy = FactionPolicySpecManager.getSpec(id).getPlugin();
            currentFactionPolicies.add(policy);
            policy.applyPolicyEffectAfterChangeInUI(false);
            policy.applyPolicy();
        }
    }

    public void removePolicy(String id) {
        boolean policyExists = doesHavePolicyEnabled(id);
        if (policyExists) {
            currentFactionPolicies.stream()
                    .filter(x -> x.getSpec().getId().equals(id))
                    .findFirst()
                    .ifPresent(x -> {
                        x.applyPolicyEffectAfterChangeInUI(true);
                        x.unapplyPolicy();
                    });

            currentFactionPolicies.removeIf(x -> x.getSpec().getId().equals(id));
        }
    }

    public void addPolicyToCopy(String id) {
        copyOfPolicies.add(id);
    }

    public void removePolicyFromCopy(String id) {
        copyOfPolicies.remove(id);
    }

    public void applyChangesFromUI() {
        List<BaseFactionPolicy> toRemove = new ArrayList<>(getCurrentFactionPolicies().stream()
                .filter(x -> !getCopyOfPolicies().contains(x.getSpec().getId()))
                .toList());

        toRemove.forEach(x -> removePolicy(x.getSpec().getId()));
        toRemove.clear();
        getCopyOfPolicies().stream().filter(x -> !doesHavePolicyEnabled(x)).forEach(this::addNewPolicy);
        getCopyOfPolicies().clear();
        copyOfPolicies.clear();
    }

    public void updateListBeforeUI() {
        copyOfPolicies.clear();
        currentFactionPolicies.forEach(x -> copyOfPolicies.add(x.getSpec().getId()));
    }

    public ArrayList<FactionPolicySpec> getSpecsForAvailableUI() {
        ArrayList<FactionPolicySpec> specs = new ArrayList<>(FactionPolicySpecManager.getFactionPolicySpecs().values());
        specs.removeIf(x -> !canUsePolicyInUI(x.getPlugin()));
        specs.removeIf(x -> copyOfPolicies.contains(x.getId()));

        return specs;
    }

    public void gainXP(float amount) {
        currentXP += (int) amount;
    }

    public boolean canUsePolicyInUI(BaseFactionPolicy policy) {
        boolean canUse = true;
        for (String incompatiblePolicyId : policy.getSpec().getIncompatiblePolicyIds()) {
            if (FactionManager.getInstance().doesHavePolicyInCopy(incompatiblePolicyId)) {
                canUse = false;
                break;
            }
        }


        return canUse & policy.canUsePolicy() && copyOfPolicies.stream().noneMatch(x -> FactionPolicySpecManager.getSpec(x).getIncompatiblePolicyIds().contains(policy.getSpec().getId()));
    }

    public static String getStringType(TimelineEventType type) {
        String str = type.toString().toLowerCase().replace('_', ' ');
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }

    public static Color getColorForEvent(TimelineEventType type) {
        if (type == TimelineEventType.UNIQUE) {
            return new Color(107, 60, 143);
        }
        if (type == TimelineEventType.MILITARY) {
            return new Color(137, 68, 59);

        }
        if (type == TimelineEventType.RESEARCH_AND_EXPLORATION) {
            return new Color(62, 131, 191);

        }
        if (type == TimelineEventType.PROSPERITY) {
            return new Color(194, 152, 57);

        }
        return null;
    }
    public static List<MarketAPI>getMarketsUnderPlayer(){
        return Misc.getFactionMarkets(Factions.PLAYER).stream().filter(x->!x.hasTag("nex_playerOutpost")).toList();
    }
}
