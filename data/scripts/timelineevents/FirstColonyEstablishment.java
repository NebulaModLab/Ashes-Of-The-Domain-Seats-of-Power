package data.scripts.timelineevents;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.impl.campaign.ids.Factions;
import com.fs.starfarer.api.ui.Alignment;
import com.fs.starfarer.api.ui.TooltipMakerAPI;
import com.fs.starfarer.api.util.Misc;
import data.scripts.models.BaseFactionTimelineEvent;

import java.awt.*;

public class FirstColonyEstablishment extends BaseFactionTimelineEvent {
    String marketID;
    String lastSavedName;
    public FirstColonyEstablishment(int cycle, int day, int month,String marketID) {
        super(cycle, day, month);
        this.marketID = marketID;
        updateDataUponEntryOfUI();
    }

    @Override
    public String getTitleOfEvent() {
        return "Established First Colony";
    }

    @Override
    public void createDetailedTooltipOnHover(TooltipMakerAPI tooltip) {
        super.createDetailedTooltipOnHover(tooltip);
        tooltip.addPara("%s has been colonized, becoming the first world under the control of %s.", 5f, Color.ORANGE, getName(), Global.getSector().getPlayerFaction().getDisplayNameLong());
    }

    public String getName(){
       return lastSavedName;
    }

    @Override
    public void updateDataUponEntryOfUI() {
        Misc.getFactionMarkets(Factions.PLAYER).stream().filter(x->x.getPrimaryEntity().getId().equals(marketID)).findFirst().ifPresent(x->lastSavedName = x.getName());
    }

    @Override
    public void createSmallNoteForEvent(TooltipMakerAPI tooltip) {
        tooltip.addPara(getName() +" has been colonized", Misc.getTextColor(),0f).setAlignment(Alignment.MID);
    }
}
