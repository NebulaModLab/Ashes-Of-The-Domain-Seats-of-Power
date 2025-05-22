package data.listeners.timeline.models;

import data.scripts.managers.AoTDFactionManager;
import data.scripts.models.BaseFactionTimelineEvent;
import data.scripts.timelineevents.templates.FirstIncomeThresholdEvent;

public class FirstIncomeColonyListener extends BaseOneTimeListener {
    int checkedSizeFor;
    int romanNumeral;
    public FirstIncomeColonyListener(String memoryFlagToCheck, int size,int numberForTitle) {
        super(memoryFlagToCheck+"_"+size);
        this.checkedSizeFor = size;
        this.romanNumeral = numberForTitle;
    }

    @Override
    public void advanceImpl(float amount) {
        AoTDFactionManager.getMarketsUnderPlayer().stream()
                .filter(x->x.getNetIncome()>=checkedSizeFor)
                .findFirst()
                .ifPresent(x -> {
                    BaseFactionTimelineEvent event = new FirstIncomeThresholdEvent(x.getPrimaryEntity().getId(),checkedSizeFor,romanNumeral);
                    AoTDFactionManager.getInstance().addEventToTimeline(event);
                    finish(event);
                });
    }
}
