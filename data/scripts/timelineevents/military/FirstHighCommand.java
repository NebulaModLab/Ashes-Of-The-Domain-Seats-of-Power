package data.scripts.timelineevents.military;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.impl.campaign.ids.Industries;
import com.fs.starfarer.api.ui.Alignment;
import com.fs.starfarer.api.ui.TooltipMakerAPI;
import com.fs.starfarer.api.util.Misc;
import data.scripts.models.TimelineEventType;
import data.scripts.timelineevents.templates.FirstIndustryEvent;

import java.awt.*;

public class FirstHighCommand extends FirstIndustryEvent {
    public FirstHighCommand( String entityId) {
        super(Industries.HIGHCOMMAND, entityId);
    }

    @Override
    public String getTitleOfEvent() {
        return "Chain of Command";
    }
    @Override
    public void createDetailedTooltipOnHover(TooltipMakerAPI tooltip) {
        super.createDetailedTooltipOnHover(tooltip);
        tooltip.addPara(
                "The High Command on %s marks the first centralized military authority established by %s. Strategic operations can now be directed from a secure command center.",
                5f,
                Color.ORANGE,
                getName(),
                Global.getSector().getPlayerFaction().getDisplayNameLong()
        );
    }
    @Override
    public void createSmallNoteForEvent(TooltipMakerAPI tooltip) {
        tooltip.addPara("High Command established on " + getName(), Misc.getTextColor(), 0f).setAlignment(Alignment.MID);
    }

    @Override
    public TimelineEventType getEventType() {
        return TimelineEventType.MILITARY;
    }
}
