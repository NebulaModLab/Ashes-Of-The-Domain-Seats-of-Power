package data.listeners.timeline.models;

import com.fs.starfarer.api.impl.campaign.ids.Factions;
import com.fs.starfarer.api.util.Misc;
import data.scripts.managers.FactionManager;
import data.scripts.models.BaseFactionTimelineEvent;
import data.scripts.timelineevents.prosperity.FirstSizeReach;

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
        Misc.getFactionMarkets(Factions.PLAYER).stream()
                .filter(x->x.getSize()>=checkedSizeFor)
                .findFirst()
                .ifPresent(x -> {
                    BaseFactionTimelineEvent event = new FirstSizeReach(x.getPrimaryEntity().getId(),checkedSizeFor,romanNumeral);
                    FactionManager.getInstance().addEventToTimeline(event);
                    finish(event);
                });
    }
}
