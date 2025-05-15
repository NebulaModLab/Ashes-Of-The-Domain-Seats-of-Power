package data.scripts.listeners;

import com.fs.starfarer.api.campaign.listeners.EconomyTickListener;
import data.scripts.managers.FactionManager;

public class FactionMonthlyUpdateListenner implements EconomyTickListener {
    @Override
    public void reportEconomyTick(int iterIndex) {

    }

    @Override
    public void reportEconomyMonthEnd() {
        //Report fires after month ends
        FactionManager.getInstance().reportMonthEnd();
    }
}
