package data.scripts.timelineevents.research_explo;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.impl.campaign.ids.Commodities;
import com.fs.starfarer.api.impl.campaign.ids.Factions;
import com.fs.starfarer.api.ui.Alignment;
import com.fs.starfarer.api.ui.TooltipMakerAPI;
import com.fs.starfarer.api.util.Misc;
import data.scripts.models.TimelineEventType;
import data.scripts.timelineevents.templates.FirstMarketCondition;

import java.awt.*;

public class MildConditionEvent extends FirstMarketCondition {
    @Override
    public String getTitleOfEvent() {
        return "The Paradise";
    }

    @Override
    public void createSmallNoteForEvent(TooltipMakerAPI tooltip) {
        tooltip.addPara(getName() + " colonized — the first with a mild climate", Misc.getTextColor(), 0f).setAlignment(Alignment.MID);
    }


    @Override
    public void createDetailedTooltipOnHover(TooltipMakerAPI tooltip) {
        super.createDetailedTooltipOnHover(tooltip);
        tooltip.addPara(
                "%s becomes the first active colony established on a world with a mild climate — a rare and valuable find in the sector.",
                5f,
                Color.ORANGE,
                getName()
        );
    }
    public String getImagePath() {
        return Global.getSettings().getSpriteName("illustrations","mild");
    }

    @Override
    public TimelineEventType getEventType() {
        return TimelineEventType.RESEARCH_AND_EXPLORATION;
    }
}
