package data.listeners.timeline;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.LocationAPI;
import com.fs.starfarer.api.campaign.comm.IntelInfoPlugin;
import com.fs.starfarer.api.impl.campaign.intel.misc.GateHaulerIntel;
import data.listeners.timeline.models.BaseOneTimeListener;
import data.scripts.managers.FactionManager;
import data.scripts.models.BaseFactionTimelineEvent;
import data.scripts.timelineevents.research_explo.GateHaulerWitnessed;

import java.util.List;

public class GateHaulerWitness extends BaseOneTimeListener {


    public GateHaulerWitness(String memoryFlagToCheck) {
        super(memoryFlagToCheck);
    }

    @Override
    public void advanceImpl(float amount) {
        List<IntelInfoPlugin> gates = Global.getSector().getIntelManager().getIntel(GateHaulerIntel.class);
        if (gates.isEmpty()) return;
        for (IntelInfoPlugin gate : gates) {
            if (gate instanceof GateHaulerIntel) {
                if (((GateHaulerIntel) gate).getAction() != null && ((GateHaulerIntel) gate).getAction() == GateHaulerIntel.GateHaulerAction.INBOUND) {
                    LocationAPI location = ((GateHaulerIntel) gate).getGateHauler().getContainingLocation();
                    Global.getSector().getEconomy().getMarkets(location).stream().filter(x -> x.getFaction() != null && x.getFaction().isPlayerFaction()).findFirst().ifPresent(x -> {
                        BaseFactionTimelineEvent event = new GateHaulerWitnessed(((GateHaulerIntel) gate).getGateHauler().getStarSystem().getBaseName());
                        FactionManager.getInstance().addEventToTimeline(event);
                        finish(event);
                    });
                }
            }
        }

    }
}
