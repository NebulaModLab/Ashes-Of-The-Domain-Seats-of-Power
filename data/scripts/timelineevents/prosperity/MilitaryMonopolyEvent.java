package data.scripts.timelineevents.prosperity;

import data.scripts.models.TimelineEventType;
import data.scripts.timelineevents.templates.MonopolyEvent;

public class MilitaryMonopolyEvent extends MonopolyEvent {
    public MilitaryMonopolyEvent(TimelineEventType typeOfMonopoly, String id, String... commoditiesForMonopoly) {
        super(typeOfMonopoly, id, commoditiesForMonopoly);
    }
}
