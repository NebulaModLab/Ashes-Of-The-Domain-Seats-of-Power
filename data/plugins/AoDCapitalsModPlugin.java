package data.plugins;


import com.fs.starfarer.api.BaseModPlugin;
import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.*;
import com.fs.starfarer.api.campaign.econ.MarketAPI;
import com.fs.starfarer.api.campaign.listeners.ListenerManagerAPI;
import com.fs.starfarer.api.characters.AbilityPlugin;
import com.fs.starfarer.api.characters.PersonAPI;
import com.fs.starfarer.api.combat.EngagementResultAPI;
import data.listeners.timeline.FirstColonyListener;
import data.scripts.CoreUITrackerScript;
import data.scripts.managers.*;
import org.json.JSONException;

import java.io.IOException;

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


    }
    public void addTransientScripts(){
        SectorAPI sector = Global.getSector();
        sector.addTransientScript(new FirstColonyListener());

    }
}