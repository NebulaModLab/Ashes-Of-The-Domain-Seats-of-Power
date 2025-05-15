package data.plugins;


import com.fs.starfarer.api.BaseModPlugin;
import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.impl.campaign.ids.Conditions;
import data.listeners.timeline.ParadiseColonyListenerEnforcer;
import data.listeners.timeline.models.FirstMarketConditionListener;
import data.listeners.timeline.models.FirstSizeColonyListener;
import data.listeners.timeline.ParadiseColonyListener;
import data.scripts.managers.TimelineListenerManager;
import data.listeners.timeline.FirstColonyListener;
import data.listeners.timeline.GateHaulerWitness;
import data.memory.AoTDSopMemFlags;
import data.scripts.CoreUITrackerScript;
import data.scripts.listeners.FactionAdvance;
import data.scripts.listeners.FactionHistoryUpdateListener;
import data.scripts.listeners.FactionMonthlyUpdateListenner;
import data.scripts.managers.*;
import data.scripts.timelineevents.research_explo.FirstVastRuins;

public class AoDCapitalsModPlugin extends BaseModPlugin {

    public void onGameLoad(boolean newGame) {
        if (!Global.getSettings().getModManager().isModEnabled("aotd_vok")) {
            Global.getSector().addTransientScript(new CoreUITrackerScript());
        }
        FactionPolicySpecManager.loadSpecs();
        if (!Global.getSector().hasScript(FactionAdvance.class)) {
            Global.getSector().addScript(new FactionAdvance());
        }
        FactionManager.getInstance().getAvailablePolicies().modifyFlat("aotd_bonus", 2f);
        FactionManager.getInstance().addNewPolicy("aotd_civ_fleet");
        Global.getSector().getListenerManager().addListener(new FactionMonthlyUpdateListenner(), true);
        if (!Global.getSector().getListenerManager().hasListenerOfClass(FactionHistoryUpdateListener.class)) {
            Global.getSector().getListenerManager().addListener(new FactionHistoryUpdateListener());
        }
        addTransientScripts();
        if(newGame){
            Global.getSector().getEconomy().getMarketsCopy().forEach(x->x.getPrimaryEntity().getMemoryWithoutUpdate().set("$aotd_was_colonized",true));
        }


    }
    public void addTransientScripts(){
        TimelineListenerManager.getInstance().addNewListener(new FirstColonyListener(AoTDSopMemFlags.FIRST_COLONY_TIMELINE_FLAG));
        TimelineListenerManager.getInstance().addNewListener(new GateHaulerWitness(AoTDSopMemFlags.GATE_ARRIVAL_WITNESS));
        TimelineListenerManager.getInstance().addNewListener(new ParadiseColonyListener(AoTDSopMemFlags.CLASS_V_COLONY));
        for (int i = 6; i <=10 ; i++) {
            TimelineListenerManager.getInstance().addNewListener(new FirstSizeColonyListener(AoTDSopMemFlags.SIZE_FLAG_COLONY,i,i-5));
        }
        TimelineListenerManager.getInstance().addNewListener(new FirstMarketConditionListener(AoTDSopMemFlags.MARKET_CONDITION_COLONIZED, Conditions.RUINS_VAST,new FirstVastRuins(),false));
        TimelineListenerManager.getInstance().setNeedsResetAfterInterval(true);
        Global.getSector().getListenerManager().addListener(new ParadiseColonyListenerEnforcer(),true);
    }
}