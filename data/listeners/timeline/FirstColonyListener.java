package data.listeners.timeline;

import com.fs.starfarer.api.EveryFrameScript;
import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.CampaignClockAPI;
import com.fs.starfarer.api.campaign.PlanetAPI;
import com.fs.starfarer.api.campaign.econ.MarketAPI;
import com.fs.starfarer.api.campaign.listeners.PlayerColonizationListener;
import com.fs.starfarer.api.impl.campaign.ids.Factions;
import com.fs.starfarer.api.util.IntervalUtil;
import com.fs.starfarer.api.util.Misc;
import data.scripts.managers.FactionManager;
import data.scripts.timelineevents.FirstColonyEstablishment;

public class FirstColonyListener implements EveryFrameScript {
    public static String memoryFlagToCheck = "$aotd_sop_first_colonization";
    public IntervalUtil util = new IntervalUtil(2f, 2f);

    @Override
    public boolean isDone() {
        return Global.getSector().getMemory().is(memoryFlagToCheck,true);
    }

    @Override
    public boolean runWhilePaused() {
        return false;
    }

    @Override
    public void advance(float amount) {
        util.advance(amount);
        if (util.intervalElapsed()) {
            if(!Misc.getFactionMarkets(Factions.PLAYER).isEmpty()&&!isDone()){
                CampaignClockAPI clock= Global.getSector().getClock();
                FactionManager.getInstance().getCycle(Global.getSector().getClock().getCycle()).addNewEvent(new FirstColonyEstablishment(clock.getCycle(),clock.getDay(),clock.getMonth(),Misc.getFactionMarkets(Factions.PLAYER).stream().findFirst().get().getPrimaryEntity().getId()));
                Global.getSector().getMemory().set(memoryFlagToCheck,true);
            }
        }
    }
}
