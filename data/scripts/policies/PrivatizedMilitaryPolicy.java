package data.scripts.policies;

import com.fs.starfarer.api.ui.Alignment;
import com.fs.starfarer.api.ui.TooltipMakerAPI;
import com.fs.starfarer.api.util.Misc;
import data.scripts.models.BaseFactionPolicy;

import java.awt.*;

public class PrivatizedMilitaryPolicy extends BaseFactionPolicy {
    @Override
    public void createTooltipDescription(TooltipMakerAPI tooltip) {
        tooltip.addPara("Reduces fleet size on all markets by %s", 0, Misc.getNegativeHighlightColor(), "90%.").setAlignment(Alignment.MID);
        tooltip.addPara("All markets spawn Privateer Defence Fleets.",  Misc.getPositiveHighlightColor(),3f).setAlignment(Alignment.MID);
        tooltip.addPara("Size of fleets scales with market's income.",  Color.ORANGE,1f).setAlignment(Alignment.MID);
        tooltip.addPara("Fleets will have their fleet doctrine.",  Color.ORANGE,1f).setAlignment(Alignment.MID);
    }
}
