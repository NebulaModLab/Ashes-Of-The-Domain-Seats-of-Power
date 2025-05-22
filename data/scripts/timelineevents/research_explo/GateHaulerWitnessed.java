package data.scripts.timelineevents.research_explo;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.ui.Alignment;
import com.fs.starfarer.api.ui.TooltipMakerAPI;
import com.fs.starfarer.api.util.Misc;
import data.scripts.models.BaseFactionTimelineEvent;
import data.scripts.models.TimelineEventType;

import java.awt.*;

public class GateHaulerWitnessed extends BaseFactionTimelineEvent {
    public String name;

    public GateHaulerWitnessed(String starSystemName) {
        this.name = starSystemName;

    }
    @Override
    public String getID() {
        return "GateHaulerWitnessed";
    }
    @Override
    public String getTitleOfEvent() {
        return "Gate Hauler Arrival";
    }

    @Override
    public String getImagePath() {
        return Global.getSettings().getSpriteName("illustrations","gate_hauler2");
    }

    @Override
    public void createDetailedTooltipOnHover(TooltipMakerAPI tooltip) {
        super.createDetailedTooltipOnHover(tooltip);
        tooltip.addPara("The inhabitants of %s star system witnessed the arrival of an ancient Gate Hauler.", 5f, Color.ORANGE, name);
    }

    @Override
    public void createSmallNoteForEvent(TooltipMakerAPI tooltip) {
        tooltip.addPara("A Gate Hauler has arrived in the " + name + " system.", Misc.getTextColor(), 0f).setAlignment(Alignment.MID);
    }

    @Override
    public int getPointsForGoal() {
        return 70;
    }

    @Override
    public TimelineEventType getEventType() {
        return TimelineEventType.RESEARCH_AND_EXPLORATION;
    }
}
