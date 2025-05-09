package data.scripts.managers;

import com.fs.starfarer.api.campaign.listeners.EconomyTickListener;

public class FactionMonthlyUpdateListenner implements EconomyTickListener {
    @Override
    public void reportEconomyTick(int iterIndex) {

    }

    @Override
    public void reportEconomyMonthEnd() {
        FactionManager.getInstance().reportMonthEnd();
    }
}
