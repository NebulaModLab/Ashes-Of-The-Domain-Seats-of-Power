package data.scripts.timelineevents.prosperity;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.impl.campaign.ids.Commodities;
import com.fs.starfarer.api.ui.TooltipMakerAPI;
import com.fs.starfarer.api.util.Misc;
import data.scripts.models.TimelineEventType;
import data.scripts.timelineevents.templates.MonopolyEvent;

public class UnderworldMonopolyEvent extends MonopolyEvent {
    public UnderworldMonopolyEvent() {
        super(TimelineEventType.PROSPERITY, "UnderworldMonopoly", Commodities.DRUGS,Commodities.ORGANS);
    }
    @Override
    public String getTitleOfEvent() {
        return "Concierge of Crime"; //Yes i watched blacklist and what? We wil have Reddington references
    }

    @Override
    public TimelineEventType getEventType() {
        return TimelineEventType.PROSPERITY;
    }

    @Override
    public int getPointsForGoal() {
        return 120;
    }

    @Override
    public String getImagePath() {
        return Global.getSettings().getSpriteName("illustrations","tritachyon_bar");
    }
    @Override
    public void createDetailedTooltipOnHover(TooltipMakerAPI tooltip) {
        super.createDetailedTooltipOnHover(tooltip);
        tooltip.addPara(
                "With permissive free port policies and the Sector’s most expansive industrial infrastructure, %s has become the central hub for the production and distribution of illicit goods. " +
                        "From recreational drugs to harvested organs, your worlds are the beating heart of the Sector’s underworld economy.",
                10f,
                Misc.getHighlightColor(),
                Global.getSector().getPlayerFaction().getDisplayNameLong()
        );
        tooltip.addPara(
                "Your ports are where smugglers strike deals, cartels establish networks, and fortunes are made in the shadows.",
                5f
        );
    }

    @Override
    public void createSmallNoteForEvent(TooltipMakerAPI tooltip) {
        tooltip.addPara(
                "%s has risen as the Sector’s primary nexus for illegal trade, empowered by open trade policies and unmatched industrial capacity.",
                0f,
                Misc.getTextColor(),
                Global.getSector().getPlayerFaction().getDisplayNameLong()
        );
    }

}
