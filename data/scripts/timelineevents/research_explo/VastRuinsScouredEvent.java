package data.scripts.timelineevents.research_explo;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.ui.Alignment;
import com.fs.starfarer.api.ui.TooltipMakerAPI;
import com.fs.starfarer.api.util.Misc;
import data.scripts.managers.AoTDFactionManager;
import data.scripts.models.BaseFactionTimelineEvent;
import data.scripts.models.TimelineEventType;

import java.awt.*;

public class VastRuinsScouredEvent extends BaseFactionTimelineEvent {
    public VastRuinsScouredEvent(String entityId) {
        this.entityId = entityId;
    }

    @Override
    public String getTitleOfEvent() {
        return "Spoils of the Forgotten";
    }

    @Override
    public void createDetailedTooltipOnHover(TooltipMakerAPI tooltip) {
        super.createDetailedTooltipOnHover(tooltip);
        tooltip.addPara("%s located on %s has been fully exhausted!.", 5f, Color.ORANGE, "Vast Ruins",getName());
    }

    @Override
    public String getImagePath() {
        return Global.getSettings().getSpriteName("illustrations", "salvor_ruins");
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
        tooltip.addPara("Nothing remains in the vast ruins on " + getName() + "—they’ve given up all their secrets.",
                Misc.getTextColor(), 0f).setAlignment(Alignment.MID);
    }

    @Override
    public int getPointsForGoal() {
        return 150;
    }

    @Override
    public TimelineEventType getEventType() {
        return TimelineEventType.RESEARCH_AND_EXPLORATION;
    }
}
