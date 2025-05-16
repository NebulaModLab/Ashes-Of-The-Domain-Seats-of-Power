package data.scripts.timelineevents.templates;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.impl.campaign.ids.Factions;
import com.fs.starfarer.api.impl.campaign.ids.Industries;
import com.fs.starfarer.api.ui.Alignment;
import com.fs.starfarer.api.ui.TooltipMakerAPI;
import com.fs.starfarer.api.util.Misc;
import data.scripts.models.BaseFactionTimelineEvent;
import data.scripts.models.TimelineEventType;

import java.awt.*;

public class FirstMarketCondition extends BaseFactionTimelineEvent {
    String entityId;
    String lastSavedName;
   public String marketCondition;
    public void init(String entityId,String marketCondition) {
        this.marketCondition = marketCondition;
        this.entityId = entityId;
    }
    @Override
    public String getTitleOfEvent() {
        return Global.getSettings().getMarketConditionSpec(marketCondition).getName();
    }
    @Override
    public String getID() {
        return "FirstMarketCondition_"+marketCondition;
    }
    @Override
    public void createDetailedTooltipOnHover(TooltipMakerAPI tooltip) {
        super.createDetailedTooltipOnHover(tooltip);
    }

    @Override
    public String getImagePath() {
        return Global.getSettings().getIndustrySpec(Industries.TECHMINING).getImageName();
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
        tooltip.addPara(getName() +" has been colonized, possessing "+Global.getSettings().getMarketConditionSpec(marketCondition).getName(), Misc.getTextColor(),0f).setAlignment(Alignment.MID);
    }

    @Override
    public TimelineEventType getEventType() {
        return TimelineEventType.RESEARCH_AND_EXPLORATION;
    }
}
