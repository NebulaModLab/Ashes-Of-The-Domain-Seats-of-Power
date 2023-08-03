package data.scripts.campaign.econ.conditions;

import com.fs.starfarer.api.campaign.econ.Industry;
import com.fs.starfarer.api.campaign.econ.MutableCommodityQuantity;
import com.fs.starfarer.api.impl.campaign.econ.BaseHazardCondition;
import com.fs.starfarer.api.impl.campaign.ids.Industries;
import com.fs.starfarer.api.ui.TooltipMakerAPI;
import com.fs.starfarer.api.util.Misc;

public class AoTdTechnocracyColonyEffect extends BaseHazardCondition {
    String RESEARCH_FACILITY = "researchfacility";
    @Override
    public void apply(String id) {
        super.apply(id);
        market.getIncomeMult().modifyPercent("technocracy_gov",-20f,"Research Spending's");
        if(market.hasIndustry(RESEARCH_FACILITY)){
            market.getIndustry(RESEARCH_FACILITY).getUpkeep().modifyMult("technocracy_gov",0,"Technocracy Government");
        }



    }
    protected void createTooltipAfterDescription(TooltipMakerAPI tooltip, boolean expanded) {
        super.createTooltipAfterDescription(tooltip, expanded);
        tooltip.addPara(
                "Bonus of Government:\n%s",
                10f,
                Misc.getStoryBrightColor(),
                "Unlocks Experimental Tech Tree\nUnlock new upgrade to Research Facility and ability to enhance some industries\nDeletes Research Facility Upkeep"
        );
        tooltip.addPara(
                "Penalties of Government:\n%s",
                10f,
                Misc.getNegativeHighlightColor(),
                "Reduced Income by 20% on all worlds\nPermament state of war with Luddic Church and Luddic Path"
        );
    }

    @Override
    public void unapply(String id) {
        super.unapply(id);
        market.getIncomeMult().unmodifyPercent("technocracy_gov");
        if(market.hasIndustry(RESEARCH_FACILITY)){
            market.getIndustry(RESEARCH_FACILITY).getUpkeep().unmodifyMult("technocracy_gov");
        }
    }
    @Override
    public boolean showIcon() {
        return true;
    }

    public String getModId() {
        return condition.getId();
    }
}
