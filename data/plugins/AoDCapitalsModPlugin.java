package data.plugins;


import com.fs.starfarer.api.BaseModPlugin;
import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.*;
import com.fs.starfarer.api.campaign.econ.MarketAPI;
import com.fs.starfarer.api.campaign.listeners.ListenerManagerAPI;
import com.fs.starfarer.api.characters.AbilityPlugin;
import com.fs.starfarer.api.characters.PersonAPI;
import com.fs.starfarer.api.combat.EngagementResultAPI;
import data.scripts.CoreUITrackerScript;
import data.scripts.managers.FactionPolicySpecManager;
import org.json.JSONException;

import java.io.IOException;

public class AoDCapitalsModPlugin extends BaseModPlugin {
    private void setListenersIfNeeded() {
        ListenerManagerAPI l = Global.getSector().getListenerManager();

    }

    public void onGameLoad(boolean newGame) {
        if (!Global.getSettings().getModManager().isModEnabled("aotd_vok")) {
            Global.getSector().addTransientScript(new CoreUITrackerScript());
        }
        FactionPolicySpecManager.loadSpecs();
    }
}