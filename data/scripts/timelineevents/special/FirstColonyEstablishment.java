package data.scripts.timelineevents.special;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.ui.Alignment;
import com.fs.starfarer.api.ui.TooltipMakerAPI;
import com.fs.starfarer.api.util.Misc;
import data.scripts.managers.AoTDFactionManager;
import data.scripts.models.BaseFactionTimelineEvent;
import data.scripts.models.TimelineEventType;

import java.awt.*;

public class FirstColonyEstablishment extends BaseFactionTimelineEvent {
    public FirstColonyEstablishment(String entityId) {
        this.entityId = entityId;
    }

    @Override
    public String getTitleOfEvent() {
        return "New Beginning";
    }

    @Override
    public void createDetailedTooltipOnHover(TooltipMakerAPI tooltip) {
        super.createDetailedTooltipOnHover(tooltip);
        tooltip.addPara("%s has been colonized, becoming the first world under the control of %s.", 5f, Color.ORANGE, getName(), Global.getSector().getPlayerFaction().getDisplayNameLong());
     }

    @Override
    public String getImagePath() {
        return Global.getSettings().getSpriteName("industry", "pop_low");
    }

    public String getName(){
       return lastSavedName;
    }

    @Override
    public void updateDataUponEntryOfUI() {
        AoTDFactionManager.getMarketsUnderPlayer().stream().filter(x->x.getPrimaryEntity().getId().equals(entityId)).findFirst().ifPresent(x->lastSavedName = x.getName());
    }

    @Override
    public void createSmallNoteForEvent(TooltipMakerAPI tooltip) {
        tooltip.addPara(getName() +" has been colonized", Misc.getTextColor(),0f).setAlignment(Alignment.MID);
    }

    @Override
    public TimelineEventType getEventType() {
        return TimelineEventType.UNIQUE;
    }
}
