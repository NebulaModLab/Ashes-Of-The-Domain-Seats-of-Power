package data.listeners.timeline;

import data.listeners.timeline.models.BaseOneTimeListener;
import data.scripts.managers.FactionManager;
import data.scripts.models.BaseFactionTimelineEvent;

public class MiscEventListener extends BaseOneTimeListener {
    BaseFactionTimelineEvent event;
    // USE THOSE ONLY FOR FACTION EVENTS OR STH THAT HAVE ALWAYS THE SAME ID
    public MiscEventListener(String memoryFlagToCheck, BaseFactionTimelineEvent event) {
        super(memoryFlagToCheck+"_"+event.getID());
        this.event = event;
    }

    @Override
    public void advanceImpl(float amount) {
        if(event.checkForCondition()){

            FactionManager.getInstance().addEventToTimeline(event);
            finish(event);
        }
    }
}
