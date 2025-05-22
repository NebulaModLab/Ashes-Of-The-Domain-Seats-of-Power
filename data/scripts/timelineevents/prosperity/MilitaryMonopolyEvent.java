package data.scripts.timelineevents.prosperity;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.impl.campaign.ids.Commodities;
import com.fs.starfarer.api.ui.Alignment;
import com.fs.starfarer.api.ui.TooltipMakerAPI;
import com.fs.starfarer.api.util.Misc;
import data.scripts.models.TimelineEventType;
import data.scripts.timelineevents.templates.MonopolyEvent;

public class MilitaryMonopolyEvent extends MonopolyEvent {
    public MilitaryMonopolyEvent() {
        super(TimelineEventType.MILITARY, "MilitaryMonopoly", Commodities.HAND_WEAPONS,Commodities.SHIPS,Commodities.MARINES);
    }
    @Override
    public String getTitleOfEvent() {
        return "Arsenal of the Sector";
    }

    @Override
    public TimelineEventType getEventType() {
        return TimelineEventType.MILITARY;
    }

    @Override
    public int getPointsForGoal() {
        return 200;
    }

    @Override
    public String getImagePath() {
        return Global.getSettings().getSpriteName("illustrations","raid_prepare");
    }
    @Override
    public void createDetailedTooltipOnHover(TooltipMakerAPI tooltip) {
        super.createDetailedTooltipOnHover(tooltip);
        tooltip.addPara(
                "Fueled by cutting-edge shipyards, extensive military academies, and arms megafactories, %s has become the Sector's foremost provider of military power. " +
                        "From patrol cutters to capital ships, from platoons of marines to crates of precision weaponry, your faction arms the fleets that decide the fate of star systems.",
                10f,
                Misc.getHighlightColor(),
                Global.getSector().getPlayerFaction().getDisplayNameLong()
        );
        tooltip.addPara(
                "Faction leaders, pirates, and mercenaries alike come to your ports not just for supplies — but to shape their wars.",
                5f
        );
    }


    @Override
    public void createSmallNoteForEvent(TooltipMakerAPI tooltip) {
        tooltip.addPara(
                "%s leads the Sector in military production and arms distribution.",
                0f,
                Misc.getTextColor(),
                Global.getSector().getPlayerFaction().getDisplayNameLong()
        ).setAlignment(Alignment.MID);
    }

}
