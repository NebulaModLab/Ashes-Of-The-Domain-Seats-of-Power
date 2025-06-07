package data.conditions;

import com.fs.starfarer.api.campaign.econ.Industry;
import com.fs.starfarer.api.impl.campaign.econ.BaseMarketConditionPlugin;
import com.fs.starfarer.api.impl.campaign.ids.Conditions;
import com.fs.starfarer.api.impl.campaign.intel.BaseIntelPlugin;
import com.fs.starfarer.api.ui.TooltipMakerAPI;
import com.fs.starfarer.api.util.Misc;

import java.awt.*;

public class FollowersOfLuddCondition extends BaseMarketConditionPlugin {

    @Override
    public void apply(String id) {
        int mult = 1;
        if (market.hasCondition(Conditions.LUDDIC_MAJORITY)) {
            mult = 2;
        }
        market.getStability().modifyFlat(id, mult, this.getName());
        market.getAccessibilityMod().modifyFlat(id, 0.05f * mult, this.getName());
        int finalMult = mult;
        market.getIndustries().stream().filter(x->x.getSpec().hasTag("industry")).forEach(x -> x.getSupplyBonus().modifyFlat(id, finalMult, this.getName()));
        int stabPenaltyIndustries = 0;
        for (Industry industry : market.getIndustries()) {
            if (industry.getSpecialItem() != null) {
                stabPenaltyIndustries += 2;
            }
            if (industry.getAICoreId() != null) {
                stabPenaltyIndustries += 3;
            }

        }
        if (stabPenaltyIndustries > 0) {
            market.getStability().modifyFlat(id + "penalty_1", -stabPenaltyIndustries, this.getName() + " (Usage of Moloch Curses)");
        }
        if (market.getAdmin() != null && market.getAdmin().isAICore()) {
            market.getStability().modifyFlat(id + "penalty_2", -stabPenaltyIndustries, this.getName() + " (Servant of Moloch)");

        }


    }

    @Override
    public void unapply(String id) {

        market.getStability().unmodifyFlat(id);
        market.getAccessibilityMod().unmodifyFlat(id);
        market.getIndustries().forEach(x -> x.getSupplyBonus().unmodifyFlat(id));
        market.getStability().unmodifyFlat(id + "penalty_1");
        market.getStability().unmodifyFlat(id + "penalty_2");


    }

    @Override
    protected void createTooltipAfterDescription(TooltipMakerAPI tooltip, boolean expanded) {
        super.createTooltipAfterDescription(tooltip, expanded);
        tooltip.setBulletedListMode(BaseIntelPlugin.INDENT);
        tooltip.addPara("+1 Stability", Misc.getPositiveHighlightColor(),3f);
        tooltip.addPara("+5% fleet size",Misc.getPositiveHighlightColor(),3f);
        tooltip.addPara("+5% Accessibility",Misc.getPositiveHighlightColor(),3f);
        tooltip.addPara("+1 to production for all industries",Misc.getPositiveHighlightColor(),3f);
        tooltip.addPara("Bonuses double if %s condition is present",3f, Color.ORANGE,"Luddic Majority");
        tooltip.setBulletedListMode(null);
        tooltip.addPara("%s stability for each Colony Item used in market",5f, Misc.getNegativeHighlightColor(),"-2");

        tooltip.addPara("%s stability for each AI Core in Industry",5f, Misc.getNegativeHighlightColor(),"-3");
        tooltip.addPara("%s stability if AI core is administrator",3f, Misc.getNegativeHighlightColor(),"-8");
    }
}
