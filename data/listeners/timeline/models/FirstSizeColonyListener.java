package data.listeners.timeline.models;

import data.scripts.managers.AoTDFactionManager;
import data.scripts.models.BaseFactionTimelineEvent;
import data.scripts.timelineevents.templates.FirstSizeReach;

public class FirstSizeColonyListener extends BaseOneTimeListener {
    int checkedSizeFor;
    int romanNumeral;
    public FirstSizeColonyListener(String memoryFlagToCheck, int size,int numberForTitle) {
        super(memoryFlagToCheck+"_"+size);
        this.checkedSizeFor = size;
        this.romanNumeral = numberForTitle;
    }

    @Override
    public void advanceImpl(float amount) {
        AoTDFactionManager.getMarketsUnderPlayer().stream()
                .filter(x->x.getSize()>=checkedSizeFor)
                .findFirst()
                .ifPresent(x -> {
                    BaseFactionTimelineEvent event = new FirstSizeReach(x.getPrimaryEntity().getId(),checkedSizeFor,romanNumeral);
                    AoTDFactionManager.getInstance().addEventToTimeline(event);
                    finish(event);
                });
    }
}
