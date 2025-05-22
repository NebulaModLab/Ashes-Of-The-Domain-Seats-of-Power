package data.scripts.timelineevents.special;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.impl.campaign.ids.Industries;
import com.fs.starfarer.api.ui.Alignment;
import com.fs.starfarer.api.ui.TooltipMakerAPI;
import com.fs.starfarer.api.util.Misc;
import data.scripts.models.TimelineEventType;
import data.scripts.timelineevents.templates.FirstIndustryEvent;

public class FirstPlanetaryShieldEvent extends FirstIndustryEvent {
    public FirstPlanetaryShieldEvent( String entityId) {
        super(Industries.PLANETARYSHIELD, entityId);
    }

    @Override
    public String getTitleOfEvent() {
        return "The Shield Rises";
    }
    @Override
    public void createDetailedTooltipOnHover(TooltipMakerAPI tooltip) {
        tooltip.addPara(
                "A planetary shield has been activated on %s — the first of its kind under the banner of %s. The colony now stands guarded against orbital threats.",
                5f,
                Misc.getHighlightColor(),
                getName(),
                Global.getSector().getPlayerFaction().getDisplayNameLong()
        );
    }
    @Override
    public void createSmallNoteForEvent(TooltipMakerAPI tooltip) {
        tooltip.addPara("Planetary shield raised over " + getName(),
                Misc.getTextColor(), 0f).setAlignment(Alignment.MID);
    }

    @Override
    public int getPointsForGoal() {
        return 60;
    }

    @Override
    public TimelineEventType getEventType() {
        return TimelineEventType.UNIQUE;
    }
}
