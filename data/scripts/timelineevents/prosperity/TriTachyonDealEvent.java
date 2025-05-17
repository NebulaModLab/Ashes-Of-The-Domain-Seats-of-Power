package data.scripts.timelineevents.prosperity;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.impl.campaign.ids.Factions;
import com.fs.starfarer.api.impl.campaign.intel.TriTachyonDeal;
import com.fs.starfarer.api.impl.campaign.intel.events.TriTachyonHostileActivityFactor;
import com.fs.starfarer.api.ui.Alignment;
import com.fs.starfarer.api.ui.TooltipMakerAPI;
import com.fs.starfarer.api.util.Misc;
import data.scripts.models.BaseFactionTimelineEvent;
import data.scripts.models.TimelineEventType;

public class TriTachyonDealEvent extends BaseFactionTimelineEvent {

    @Override
    public String getID() {
        return super.getID();
    }

    @Override
    public String getTitleOfEvent() {
        return "Mutual Agreement";
    }

    @Override
    public TimelineEventType getEventType() {
        return TimelineEventType.PROSPERITY;
    }

    @Override
    public int getPointsForGoal() {
        return 100;
    }

    @Override
    public void updateDataUponEntryOfUI() {
        lastSavedName = Global.getSector().getFaction(Factions.TRITACHYON).getDisplayNameLong();
    }

    @Override
    public boolean checkForCondition() {
        return TriTachyonDeal.hasDeal();
    }

    @Override
    public String getImagePath() {
        return Global.getSettings().getSpriteName("illustrations","corporate") ;
    }

    @Override
    public void createDetailedTooltipOnHover(TooltipMakerAPI tooltip) {
        super.createDetailedTooltipOnHover(tooltip);
        tooltip.addPara(
                "%s and Tri-Tachyon entered into a mutual agreement. The new accord boosted accessibility and opened trade lanes, benefiting both sides.",
                5f,
                Misc.getHighlightColor(),
                Global.getSector().getPlayerFaction().getDisplayNameLong()
        );
    }

    @Override
    public void createSmallNoteForEvent(TooltipMakerAPI tooltip) {
        tooltip.addPara("A trade agreement was signed between Tri-Tachyon and " + Global.getSector().getPlayerFaction().getDisplayNameLong(), Misc.getTextColor(), 0f).setAlignment(Alignment.MID);
    }
}
