package data.scripts.policies;

import com.fs.starfarer.api.ui.Alignment;
import com.fs.starfarer.api.ui.TooltipMakerAPI;
import com.fs.starfarer.api.util.Misc;
import data.scripts.models.BaseFactionPolicy;

public class SGCPolicy extends BaseFactionPolicy {
    @Override
    public void createTooltipDescription(TooltipMakerAPI tooltip) {
        tooltip.addPara("Provides bonuses towards %s, %s and %s, scaling with presence of Patrol HQ / Military Base / High Command", 0, Misc.getPositiveHighlightColor(), "Ground defence","Stability","Fleet size").setAlignment(Alignment.MID);
        tooltip.addPara("Increases demand for supplies and heavy armaments based on colony size",  Misc.getNegativeHighlightColor(),3f).setAlignment(Alignment.MID);
        tooltip.addPara("Slows down Major Crisis progression.",  Misc.getPositiveHighlightColor(),3f).setAlignment(Alignment.MID);

    }
}
