package data.scripts.listeners;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.listeners.EconomyTickListener;
import data.scripts.managers.AoTDFactionManager;

public class FactionHistoryUpdateListener implements EconomyTickListener {
    public  int currentCycle = 0;

    @Override
    public void reportEconomyTick(int iterIndex) {

    }

    @Override
    public void reportEconomyMonthEnd() {
        if(Global.getSector().getClock().getCycle()!=currentCycle){
            currentCycle = Global.getSector().getClock().getCycle();
            if(!AoTDFactionManager.getMarketsUnderPlayer().isEmpty()){
                AoTDFactionManager.getInstance().addCycle(currentCycle);
            }
        }
    }

    public FactionHistoryUpdateListener() {
        currentCycle = Global.getSector().getClock().getCycle();
    }
}
