package data.scripts.timelineevents.research_explo;

import com.fs.starfarer.api.Global;
import data.scripts.models.TimelineEventType;
import data.scripts.timelineevents.templates.FirstMarketCondition;

public class MildConditionEvent extends FirstMarketCondition {
    @Override
    public String getTitleOfEvent() {
        return "The Paradise";
    }

    @Override
    public TimelineEventType getEventType() {
        return TimelineEventType.RESEARCH_AND_EXPLORATION;

    }
    public String getImagePath() {
        return Global.getSettings().getSpriteName("illustrations","mild");
    }


}
