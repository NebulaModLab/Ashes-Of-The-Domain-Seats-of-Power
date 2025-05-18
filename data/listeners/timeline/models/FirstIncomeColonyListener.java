package data.listeners.timeline.models;

import com.fs.starfarer.api.impl.campaign.ids.Factions;
import com.fs.starfarer.api.util.Misc;
import data.scripts.managers.FactionManager;
import data.scripts.models.BaseFactionTimelineEvent;
import data.scripts.timelineevents.templates.FirstIncomeThresholdEvent;
import data.scripts.timelineevents.templates.FirstSizeReach;

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
        FactionManager.getMarketsUnderPlayer().stream()
                .filter(x->x.getNetIncome()>=checkedSizeFor)
                .findFirst()
                .ifPresent(x -> {
                    BaseFactionTimelineEvent event = new FirstIncomeThresholdEvent(x.getPrimaryEntity().getId(),checkedSizeFor,romanNumeral);
                    FactionManager.getInstance().addEventToTimeline(event);
                    finish(event);
                });
    }
}
