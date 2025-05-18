package data.scripts.listeners;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.listeners.EconomyTickListener;
import com.fs.starfarer.api.impl.campaign.ids.Factions;
import com.fs.starfarer.api.util.Misc;
import data.scripts.managers.FactionManager;

public class FactionHistoryUpdateListener implements EconomyTickListener {
    public  int currentCycle = 0;

    @Override
    public void reportEconomyTick(int iterIndex) {

    }

    @Override
    public void reportEconomyMonthEnd() {
        if(Global.getSector().getClock().getCycle()!=currentCycle){
            currentCycle = Global.getSector().getClock().getCycle();
            if(!FactionManager.getMarketsUnderPlayer().isEmpty()){
                FactionManager.getInstance().addCycle(currentCycle);
            }
        }
    }

    public FactionHistoryUpdateListener() {
        currentCycle = Global.getSector().getClock().getCycle();
    }
}
