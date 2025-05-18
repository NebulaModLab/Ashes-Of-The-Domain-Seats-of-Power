package data.scripts.timelineevents.templates;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.impl.campaign.ids.Factions;
import com.fs.starfarer.api.ui.Alignment;
import com.fs.starfarer.api.ui.TooltipMakerAPI;
import com.fs.starfarer.api.util.Misc;
import data.scripts.managers.FactionManager;
import data.scripts.models.BaseFactionTimelineEvent;
import data.scripts.models.TimelineEventType;

import java.awt.*;

public class FirstIndustryEvent extends BaseFactionTimelineEvent {
    String industryId;

    public String getIndustryId() {
        return industryId;
    }

    public FirstIndustryEvent(String industryId, String entityId) {
        this.industryId = industryId;
        this.entityId = entityId;
    }
    @Override
    public String getID() {
        return "FirstIndustry_"+industryId;
    }

    @Override
    public String getImagePath() {
        return Global.getSettings().getIndustrySpec(industryId).getImageName();
    }

    @Override
    public String getTitleOfEvent() {
        return Global.getSettings().getIndustrySpec(industryId).getName();
    }

    @Override
    public void createDetailedTooltipOnHover(TooltipMakerAPI tooltip) {
        super.createDetailedTooltipOnHover(tooltip);
        tooltip.addPara(
                "The first %s has been constructed on %s, marking a new milestone for %s.",
                5f,
                Color.ORANGE,
                Global.getSettings().getIndustrySpec(industryId).getName(),
                getName(),
                Global.getSector().getPlayerFaction().getDisplayNameLong()
        );
    }


    public String getName(){
        return lastSavedName;
    }

    @Override
    public void updateDataUponEntryOfUI() {
        FactionManager.getMarketsUnderPlayer()  .stream().filter(x->x.getPrimaryEntity().getId().equals(entityId)).findFirst().ifPresent(x->lastSavedName = x.getName());
    }

    @Override
    public void createSmallNoteForEvent(TooltipMakerAPI tooltip) {
        tooltip.addPara("First ever " + Global.getSettings().getIndustrySpec(industryId).getName() + " built on " + getName() ,
                Misc.getTextColor(), 0f).setAlignment(Alignment.MID);
    }


    @Override
    public TimelineEventType getEventType() {
        return TimelineEventType.PROSPERITY;
    }
}
