package data.scripts.timelineevents.templates;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.impl.campaign.ids.Factions;
import com.fs.starfarer.api.ui.Alignment;
import com.fs.starfarer.api.ui.TooltipMakerAPI;
import com.fs.starfarer.api.util.Misc;
import data.scripts.models.BaseFactionTimelineEvent;
import data.scripts.models.TimelineEventType;

import java.awt.*;

public  class FirstSizeReach extends BaseFactionTimelineEvent {
    String entityId;
    String lastSavedName;
    int reachedSize;
    int romani;
    public FirstSizeReach(String entityId,int reachedSize,int romani) {
        this.entityId = entityId;
        this.reachedSize = reachedSize;
        this.romani = romani;

    }

    public  String  getRomanNumeral(){
        return toRoman(romani);
    }

    @Override
    public String getID() {
        return "FirstSizeReach_"+reachedSize;
    }

    @Override
    public String getImagePath() {
        return Global.getSettings().getSpriteName("industry", "pop_high");
    }

    @Override
    public String getTitleOfEvent() {
        return "Blooming population "+getRomanNumeral();
    }

    @Override
    public void createDetailedTooltipOnHover(TooltipMakerAPI tooltip) {
        super.createDetailedTooltipOnHover(tooltip);
        tooltip.addPara(
                "%s has reached size %s, becoming the first colony of this size under the control of %s.",
                5f,
                Color.ORANGE,
                getName(),
                ""+reachedSize,
                Global.getSector().getPlayerFaction().getDisplayNameLong()
        );
    }

    public String getName(){
        return lastSavedName;
    }

    @Override
    public void updateDataUponEntryOfUI() {
        Misc.getFactionMarkets(Factions.PLAYER).stream().filter(x->x.getPrimaryEntity().getId().equals(entityId)).findFirst().ifPresent(x->lastSavedName = x.getName());
    }

    @Override
    public void createSmallNoteForEvent(TooltipMakerAPI tooltip) {
        tooltip.addPara(getName() +" reached size "+reachedSize, Misc.getTextColor(),0f).setAlignment(Alignment.MID);
    }

    @Override
    public TimelineEventType getEventType() {
        return TimelineEventType.PROSPERITY;
    }
    private String toRoman(int number) {
        String[] romans = {"M","CM","D","CD","C","XC","L","XL","X","IX","V","IV","I"};
        int[] values = {1000,900,500,400,100,90,50,40,10,9,5,4,1};
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < values.length; i++) {
            while (number >= values[i]) {
                number -= values[i];
                result.append(romans[i]);
            }
        }
        return result.toString();
    }

}
