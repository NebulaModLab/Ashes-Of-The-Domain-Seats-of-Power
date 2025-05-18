package data.listeners.timeline;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.impl.campaign.ids.Factions;
import com.fs.starfarer.api.util.Misc;
import data.listeners.timeline.models.BaseOneTimeListener;
import data.scripts.managers.FactionManager;
import data.scripts.models.BaseFactionTimelineEvent;
import data.scripts.timelineevents.special.FirstColonyEstablishment;

public class FirstColonyListener extends BaseOneTimeListener {
    public FirstColonyListener(String memoryFlagToCheck) {
        super(memoryFlagToCheck);
    }

    @Override
    public boolean isDone() {
        return Global.getSector().getMemory().is(memoryFlagToCheck, true);
    }

    @Override
    public void advanceImpl(float amount) {
        if (!FactionManager.getMarketsUnderPlayer().isEmpty()) {
            BaseFactionTimelineEvent event = new FirstColonyEstablishment(FactionManager.getMarketsUnderPlayer().stream().findFirst().get().getPrimaryEntity().getId());
            FactionManager.getInstance().addEventToTimeline(event);
            finish(event);
        }
    }
}
