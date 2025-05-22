package data.listeners.timeline;

import data.listeners.timeline.models.BaseOneTimeListener;
import data.memory.AoTDSopMemFlags;
import data.scripts.managers.AoTDFactionManager;
import data.scripts.models.BaseFactionTimelineEvent;

public class MiscEventListener extends BaseOneTimeListener {
    BaseFactionTimelineEvent event;
    // USE THOSE ONLY FOR FACTION EVENTS OR STH THAT HAVE ALWAYS THE SAME ID
    public MiscEventListener(String memoryFlagToCheck, BaseFactionTimelineEvent event) {
        super(memoryFlagToCheck+"_"+event.getID());
        this.event = event;
    }
    public MiscEventListener(BaseFactionTimelineEvent event) {

        super(AoTDSopMemFlags.MISC_EVENT +"_"+event.getID());
        this.event = event;
    }
    @Override
    public void advanceImpl(float amount) {
        if(event.checkForCondition()){

            AoTDFactionManager.getInstance().addEventToTimeline(event);
            finish(event);
        }
    }
}
