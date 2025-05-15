package data.scripts.timelineevents.research_explo;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.ui.Alignment;
import com.fs.starfarer.api.ui.TooltipMakerAPI;
import com.fs.starfarer.api.util.Misc;
import data.scripts.timelineevents.templates.FirstMarketCondition;

import java.awt.*;

public class FirstVastRuins extends FirstMarketCondition {
    @Override
    public String getTitleOfEvent() {
        return "Echo of Past";
    }

    @Override
    public void createSmallNoteForEvent(TooltipMakerAPI tooltip) {
        tooltip.addPara("The inhabitants of " + getName() + " stand in awe, gazing upon the vast ruins that stretch across the entire colony.", Misc.getTextColor(), 0f).setAlignment(Alignment.MID);
    }

    @Override
    public void createDetailedTooltipOnHover(TooltipMakerAPI tooltip) {
        super.createDetailedTooltipOnHover(tooltip);
        tooltip.addPara("%s marks as first active colony where %s were located.", 5f, Color.ORANGE,getName(),Global.getSettings().getMarketConditionSpec(marketCondition).getName());
    }
}
