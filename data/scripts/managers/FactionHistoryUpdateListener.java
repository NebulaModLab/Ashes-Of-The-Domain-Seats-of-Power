package data.scripts.managers;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.listeners.EconomyTickListener;

public class FactionHistoryUpdateListener implements EconomyTickListener {
    public  int currentCycle;

    @Override
    public void reportEconomyTick(int iterIndex) {

    }

    @Override
    public void reportEconomyMonthEnd() {
        if(Global.getSector().getClock().getCycle()!=currentCycle){
            // Add new Cycle entry
        }
    }

    public FactionHistoryUpdateListener() {
        currentCycle = Global.getSector().getClock().getCycle();
    }
}
