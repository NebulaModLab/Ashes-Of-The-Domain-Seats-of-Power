package data.listeners.timeline;

import com.fs.starfarer.api.Global;
import data.listeners.timeline.models.BaseOneTimeListener;
import data.scripts.managers.AoTDFactionManager;
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
        if (!AoTDFactionManager.getMarketsUnderPlayer().isEmpty()) {
            BaseFactionTimelineEvent event = new FirstColonyEstablishment(AoTDFactionManager.getMarketsUnderPlayer().stream().findFirst().get().getPrimaryEntity().getId());
            AoTDFactionManager.getInstance().addEventToTimeline(event);
            finish(event);
        }
    }
}
