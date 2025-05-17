package data.scripts.timelineevents.special;

import com.fs.starfarer.api.ui.Alignment;
import com.fs.starfarer.api.ui.TooltipMakerAPI;
import com.fs.starfarer.api.util.Misc;
import data.scripts.models.TimelineEventType;
import data.scripts.timelineevents.templates.FirstMarketCondition;

public class OrbitalShadeEvent extends FirstMarketCondition {
    @Override
    public String getTitleOfEvent() {
        return "Solar Light Manipulation";
    }
    @Override
    public void createSmallNoteForEvent(TooltipMakerAPI tooltip) {
        tooltip.addPara(getName() + " became the first colony to alter its sunlight from orbit", Misc.getTextColor(), 0f).setAlignment(Alignment.MID);
    }
    @Override
    public void createDetailedTooltipOnHover(TooltipMakerAPI tooltip) {
        super.createDetailedTooltipOnHover(tooltip);
        tooltip.addPara(
                "%s is the first active colony with implemented orbital infrastructure capable of manipulating solar radiation — redirecting, concentrating, or dispersing sunlight as needed to shape the environment.",
                5f,
                Misc.getHighlightColor(),
                getName()
        );
    }

    @Override
    public TimelineEventType getEventType() {
        return TimelineEventType.UNIQUE;
    }
}
