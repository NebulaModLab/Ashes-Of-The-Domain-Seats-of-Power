package data.scripts.timelineevents.prosperity;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.impl.campaign.ids.Factions;
import com.fs.starfarer.api.ui.Alignment;
import com.fs.starfarer.api.ui.TooltipMakerAPI;
import com.fs.starfarer.api.util.Misc;
import data.scripts.models.BaseFactionTimelineEvent;
import data.scripts.models.TimelineEventType;

import java.awt.*;

public class ParadiseColonizationEvent extends BaseFactionTimelineEvent {
    String marketID;
    String lastSavedName;
    public ParadiseColonizationEvent(String marketID) {
        this.marketID = marketID;

    }

    @Override
    public String getTitleOfEvent() {
        return "Class V Jewel";
    }

    @Override
    public void createDetailedTooltipOnHover(TooltipMakerAPI tooltip) {
        super.createDetailedTooltipOnHover(tooltip);
        tooltip.addPara("%s has been colonized, becoming the first world of Class V under %s.", 5f, Color.ORANGE, getName(), Global.getSector().getPlayerFaction().getDisplayNameLong());
    }

    public String getName(){
        return lastSavedName;
    }

    @Override
    public String getImagePath() {
        return Global.getSettings().getSpriteName("illustrations","gilead");
    }

    @Override
    public void updateDataUponEntryOfUI() {
        Misc.getFactionMarkets(Factions.PLAYER).stream().filter(x->x.getPrimaryEntity().getId().equals(marketID)).findFirst().ifPresent(x->lastSavedName = x.getName());
    }

    @Override
    public void createSmallNoteForEvent(TooltipMakerAPI tooltip) {
        tooltip.addPara(getName() +" planet of Class V has been colonized", Misc.getTextColor(),0f).setAlignment(Alignment.MID);
    }

    @Override
    public TimelineEventType getEventType() {
        return TimelineEventType.PROSPERITY;
    }
}
