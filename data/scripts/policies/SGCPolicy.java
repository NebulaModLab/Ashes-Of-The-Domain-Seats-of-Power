package data.scripts.policies;

import com.fs.starfarer.api.impl.campaign.intel.BaseIntelPlugin;
import com.fs.starfarer.api.ui.Alignment;
import com.fs.starfarer.api.ui.TooltipMakerAPI;
import com.fs.starfarer.api.util.Misc;
import data.scripts.models.BaseFactionPolicy;

import java.awt.*;

public class SGCPolicy extends BaseFactionPolicy {
    @Override
    public void createTooltipDescription(TooltipMakerAPI tooltip) {
        tooltip.addPara("Provides bonuses towards %s, %s and %s, scaling with presence of Patrol HQ / Military Base / High Command", 0, Misc.getPositiveHighlightColor(), "Ground defence","Stability","Fleet size").setAlignment(Alignment.MID);
        tooltip.addPara("Increases demand for supplies and heavy armaments based on colony size",  Misc.getNegativeHighlightColor(),3f).setAlignment(Alignment.MID);
        tooltip.addPara("Slows down Major Crisis progression.",  Misc.getPositiveHighlightColor(),3f).setAlignment(Alignment.MID);
    }

    @Override
    public void createDetailedTooltipDescription(TooltipMakerAPI tooltip) {
        tooltip.addTitle(getSpec().getName());
        tooltip.addPara("Provides bonuses towards %s, %s and %s, scaling with presence of Patrol HQ / Military Base / High Command (with none present being treated as tier 0)", 5, Misc.getPositiveHighlightColor(), "Ground defence","Stability","Fleet size");
        tooltip.addPara(BaseIntelPlugin.BULLET+"1 + %s ground defence multiplier",3f, Color.ORANGE,"0.25 * tier");
        tooltip.addPara(BaseIntelPlugin.BULLET+"1 + %s stability ( %s )",3f,Color.ORANGE," 0.5 * tier","rounded down, so 1/1/2/2");
        tooltip.addPara(BaseIntelPlugin.BULLET+"%s + %s fleet size",3f,Color.ORANGE,"10%","15% * tier");
        tooltip.addPara("Increases demand for supplies and heavy armaments based on colony size",  Misc.getNegativeHighlightColor(),5f);
        tooltip.addPara(BaseIntelPlugin.BULLET+"1 + %s supplies (rounded down)",3f, Color.ORANGE,"0.5 * market size");
        tooltip.addPara(BaseIntelPlugin.BULLET+"%s - 1 heavy armaments (rounded down)",3f, Color.ORANGE,"0.5 * market size");

        tooltip.addPara("Slows down Major Crisis progression.",  Misc.getPositiveHighlightColor(),5f);
        super.createDetailedTooltipDescription(tooltip);
    }
}
