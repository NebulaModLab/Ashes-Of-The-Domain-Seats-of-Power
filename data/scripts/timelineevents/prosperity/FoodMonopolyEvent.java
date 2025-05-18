package data.scripts.timelineevents.prosperity;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.impl.campaign.ids.Commodities;
import com.fs.starfarer.api.ui.TooltipMakerAPI;
import com.fs.starfarer.api.util.Misc;
import data.scripts.models.BaseFactionTimelineEvent;
import data.scripts.models.TimelineEventType;
import data.scripts.timelineevents.templates.MonopolyEvent;

public class FoodMonopolyEvent extends MonopolyEvent {


    public FoodMonopolyEvent(TimelineEventType typeOfMonopoly, String id) {
        super(typeOfMonopoly, id, Commodities.FOOD);
    }
    @Override
    public String getTitleOfEvent() {
        return "Spoils of the Harvest";
    }

    @Override
    public TimelineEventType getEventType() {
        return TimelineEventType.PROSPERITY;
    }

    @Override
    public int getPointsForGoal() {
        return 180;
    }

    @Override
    public String getImagePath() {
        return Global.getSettings().getSpriteName("illustrations","harvest");
    }
    @Override
    public void createDetailedTooltipOnHover(TooltipMakerAPI tooltip) {
        super.createDetailedTooltipOnHover(tooltip);
        tooltip.addPara(
                "Through the coordinated development of fertile agri-worlds %s has come to dominate the Sector’s food supply. With over 30%% of all food passing through your hands, your faction now sets the terms of survival. Entire systems, once self-sufficient, now rely on your convoys. The balance of power tips with every crate delivered—or withheld.",
                10f,
                Misc.getHighlightColor(),
                Global.getSector().getPlayerFaction().getDisplayNameLong()
        );
    }

    @Override
    public void createSmallNoteForEvent(TooltipMakerAPI tooltip) {
        tooltip.addPara(
                "%s has secured a controlling share of the food trade across the Sector.",
                0f,
                Misc.getTextColor(),
                Global.getSector().getPlayerFaction().getDisplayNameLong()
        );
    }
}
