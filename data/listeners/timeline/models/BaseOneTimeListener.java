package data.listeners.timeline.models;

import com.fs.starfarer.api.Global;
import data.scripts.managers.AoTDFactionManager;
import data.scripts.models.BaseFactionTimelineEvent;

public abstract class BaseOneTimeListener extends BaseTimelineListener{
    public String memoryFlagToCheck;
    public BaseOneTimeListener(String memoryFlagToCheck){
        this.memoryFlagToCheck = memoryFlagToCheck;
    }

    @Override
    public void advance(float amount) {
        if(isDone())return;
        super.advance(amount);
    }

    @Override
    public boolean isDone() {
        return Global.getSector().getMemoryWithoutUpdate().is(memoryFlagToCheck,true);
    }
    public void finish(BaseFactionTimelineEvent event){
        if(event!=null){
            event.createIntelEntryForUnlocking();
        }
        Global.getSector().getMemoryWithoutUpdate().set(memoryFlagToCheck,true);
    }
    public void addEvent(BaseFactionTimelineEvent event){
        AoTDFactionManager.getInstance().addEventToTimeline(event);
        finish(event);
    }
}
