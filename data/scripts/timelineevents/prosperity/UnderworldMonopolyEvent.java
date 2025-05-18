package data.scripts.timelineevents.prosperity;

import com.fs.starfarer.api.impl.campaign.ids.Commodities;
import data.scripts.models.TimelineEventType;
import data.scripts.timelineevents.templates.MonopolyEvent;

public class UnderworldMonopolyEvent extends MonopolyEvent {
    public UnderworldMonopolyEvent(TimelineEventType typeOfMonopoly, String id, String... commoditiesForMonopoly) {
        super(typeOfMonopoly, id, Commodities.DRUGS,Commodities.ORGANS);
    }
}
