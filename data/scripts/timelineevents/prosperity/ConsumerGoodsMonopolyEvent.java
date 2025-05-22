package data.scripts.timelineevents.prosperity;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.impl.campaign.ids.Commodities;
import com.fs.starfarer.api.ui.Alignment;
import com.fs.starfarer.api.ui.TooltipMakerAPI;
import com.fs.starfarer.api.util.Misc;
import data.scripts.models.TimelineEventType;
import data.scripts.timelineevents.templates.MonopolyEvent;

public class ConsumerGoodsMonopolyEvent extends MonopolyEvent {
    public ConsumerGoodsMonopolyEvent() {
        super(TimelineEventType.PROSPERITY, "ConsumerMonopoly", Commodities.DOMESTIC_GOODS,Commodities.LUXURY_GOODS);
    }
    @Override
    public String getTitleOfEvent() {
        return "Provider";
    }

    @Override
    public TimelineEventType getEventType() {
        return TimelineEventType.PROSPERITY;
    }

    @Override
    public int getPointsForGoal() {
        return 120;
    }

    @Override
    public String getImagePath() {
        return Global.getSettings().getSpriteName("illustrations","freighters");
    }
    @Override
    public void createDetailedTooltipOnHover(TooltipMakerAPI tooltip) {
        super.createDetailedTooltipOnHover(tooltip);
        tooltip.addPara(
                "With sprawling manufactories and efficient trade networks, %s has emerged as the Sector’s dominant supplier of everyday life. " +
                        "From household staples to high-end luxuries, your markets shape the daily routines of millions.",
                10f,
                Misc.getHighlightColor(),
                Global.getSector().getPlayerFaction().getDisplayNameLong()
        );
        tooltip.addPara(
                "When colonists want comfort, when traders want demand, and when elites want indulgence — they turn to you.",
                5f
        );
    }

    @Override
    public void createSmallNoteForEvent(TooltipMakerAPI tooltip) {
        tooltip.addPara(
                "%s has become the Sector’s premier source of domestic and luxury goods.",
                0f,
                Misc.getTextColor(),
                Global.getSector().getPlayerFaction().getDisplayNameLong()
        ).setAlignment(Alignment.MID);
    }


}
