package data.scripts.policies;

import com.fs.starfarer.api.ui.Alignment;
import com.fs.starfarer.api.ui.TooltipMakerAPI;
import com.fs.starfarer.api.util.Misc;
import data.scripts.managers.AoTDFactionManager;
import data.scripts.models.BaseFactionPolicy;
import data.scripts.models.TimelineEventType;

public class RapidIndustrialization extends BaseFactionPolicy {
    @Override
    public void createTooltipDescription(TooltipMakerAPI tooltip) {
        tooltip.addPara("Increase market upkeep multiplier by %s",0f, Misc.getNegativeHighlightColor(),"1.5").setAlignment(Alignment.MID);
        tooltip.addPara("Increase build speed for all industries by %s",0f,Misc.getPositiveHighlightColor(),"50%").setAlignment(Alignment.MID);

        super.createTooltipDescription(tooltip);
    }

    @Override
    public void createDetailedTooltipDescription(TooltipMakerAPI tooltip) {
        tooltip.addPara("Increase market upkeep multiplier by %s",0f, Misc.getNegativeHighlightColor(),"1.5");
        tooltip.addPara("Increase build speed for all industries by %s",0f,Misc.getPositiveHighlightColor(),"50%");

        super.createDetailedTooltipDescription(tooltip);
    }

    @Override
    public void advance(float amount) {
        super.advance(amount);
        AoTDFactionManager.getMarketsUnderPlayer().forEach(x->x.getIndustries().forEach(y->y.advance(amount)));

    }

    @Override
    public void applyPolicy() {
       AoTDFactionManager.getMarketsUnderPlayer().forEach(x->x.getUpkeepMult().modifyMult(getID(),1.5f,"Rapid industrialization"));
    }

    @Override
    public void unapplyPolicy() {
        AoTDFactionManager.getMarketsUnderPlayer().forEach(x->x.getUpkeepMult().unmodifyMult(getID()));

    }

    @Override
    public boolean showInUI() {
        return AoTDFactionManager.getInstance().getScriptForGoal(TimelineEventType.PROSPERITY).reachedGoal("goal_1");
    }
}
