package data.scripts.timelineevents.prosperity;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.econ.MarketAPI;
import com.fs.starfarer.api.impl.campaign.ids.Industries;
import com.fs.starfarer.api.ui.Alignment;
import com.fs.starfarer.api.ui.TooltipMakerAPI;
import com.fs.starfarer.api.util.Misc;
import data.scripts.managers.FactionManager;
import data.scripts.models.BaseFactionTimelineEvent;
import data.scripts.models.TimelineEventType;

public class TwelveSturcutresEvent extends BaseFactionTimelineEvent {
    @Override
    public TimelineEventType getEventType() {
        return TimelineEventType.PROSPERITY;
    }

    @Override
    public int getPointsForGoal() {
        return 40;
    }

    @Override
    public String getTitleOfEvent() {
        return "Advanced Infrastructure";
    }
    @Override
    public void createDetailedTooltipOnHover(TooltipMakerAPI tooltip) {
        super.createDetailedTooltipOnHover(tooltip);
        tooltip.addPara(
                "%s has become the first colony under your banner to support four full-fledged industries, a mark of true economic maturity. From manufacturing to resource processing, this diversified powerhouse now underpins your faction’s prosperity and resilience.",
                10f,
                Misc.getHighlightColor(),
                getName()
        );
    }

    @Override
    public String getImagePath() {
        return Global.getSettings().getIndustrySpec(Industries.LIGHTINDUSTRY).getImageName();
    }

    @Override
    public void createSmallNoteForEvent(TooltipMakerAPI tooltip) {
        tooltip.addPara(getName() + " is surging with industrial growth.",
                Misc.getTextColor(), 0f).setAlignment(Alignment.MID);

    }

    @Override
    public boolean checkForCondition() {
        MarketAPI market = FactionManager.getMarketsUnderPlayer().stream().filter(x->x.getIndustries().size()>=12).findFirst().orElse(null);
        if (market != null) {
            entityId = market.getPrimaryEntity().getId();
        }
        return market != null;
    }

    @Override
    public void updateDataUponEntryOfUI() {
        FactionManager.getMarketsUnderPlayer().stream().filter(x -> x.getPrimaryEntity().getId().equals(entityId)).findFirst().ifPresent(x -> lastSavedName = x.getName());
    }
}
