package data.scripts.listeners;

import com.fs.starfarer.api.EveryFrameScript;
import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.util.IntervalUtil;
import data.scripts.managers.AoTDFactionManager;

public class FactionAdvance implements EveryFrameScript {
    IntervalUtil util = new IntervalUtil(Global.getSector().getClock().getSecondsPerDay()/4f,Global.getSector().getClock().getSecondsPerDay()/4f);
    @Override
    public boolean isDone() {
        return false;
    }

    @Override
    public boolean runWhilePaused() {
        return false;
    }

    @Override
    public void advance(float amount) {
        util.advance(amount);
        if(util.intervalElapsed()){
            AoTDFactionManager.getInstance().advance(util.getElapsed());
        }
    }
}
