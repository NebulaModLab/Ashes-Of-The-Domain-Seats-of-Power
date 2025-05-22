package data.scripts.timelineevents.templates;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.econ.MarketAPI;
import com.fs.starfarer.api.ui.Alignment;
import com.fs.starfarer.api.ui.TooltipMakerAPI;
import data.scripts.managers.FactionManager;
import data.scripts.models.BaseFactionTimelineEvent;
import data.scripts.models.TimelineEventType;

import java.awt.*;

public class FactionExpansionEvent extends BaseFactionTimelineEvent {
    public int combinedMarketSize;
    public FactionExpansionEvent(int marketSize, int romanNumeral) {
        combinedMarketSize = marketSize;
        this.romanNumeral = romanNumeral;
    }

    @Override
    public String getTitleOfEvent() {
        return toRoman(romanNumeral)+" Sphere of Expansion";
    }

    @Override
    public TimelineEventType getEventType() {
        return TimelineEventType.UNIQUE;
    }

    @Override
    public String getID() {
        return super.getID()+combinedMarketSize;
    }

    @Override
    public void createSmallNoteForEvent(TooltipMakerAPI tooltip) {
        tooltip.addPara(Global.getSector().getPlayerFaction().getDisplayNameLong()+" has reached combined market size of %s",0f, Color.ORANGE,""+combinedMarketSize).setAlignment(Alignment.MID);
    }

    @Override
    public void createDetailedTooltipOnHover(TooltipMakerAPI tooltip) {
        tooltip.addPara("Due to massive colonization investments, %s has reached combined size of %s",5f,Color.ORANGE,Global.getSector().getPlayerFaction().getDisplayNameLong(),combinedMarketSize+"");
    }

    @Override
    public boolean checkForCondition() {
        float size = 0;
        for (MarketAPI marketAPI : FactionManager.getMarketsUnderPlayer()) {
            size+=marketAPI.getSize();
        }
        return size>=combinedMarketSize;
    }

    @Override
    public int getPointsForGoal() {
        return 15;
    }
}
