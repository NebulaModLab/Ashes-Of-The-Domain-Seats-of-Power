package data.scripts.policies;

import com.fs.starfarer.api.impl.campaign.intel.BaseIntelPlugin;
import com.fs.starfarer.api.ui.Alignment;
import com.fs.starfarer.api.ui.TooltipMakerAPI;
import com.fs.starfarer.api.util.Misc;
import data.scripts.models.BaseFactionPolicy;

import java.awt.*;

public class PrivatizedMilitaryPolicy extends BaseFactionPolicy {
    @Override
    public void createTooltipDescription(TooltipMakerAPI tooltip) {
        tooltip.addPara("Reduces fleet size on all markets by %s", 0, Misc.getNegativeHighlightColor(), "50%.").setAlignment(Alignment.MID);
        tooltip.addPara("All markets spawn Privateer Defence Fleets.",  Misc.getPositiveHighlightColor(),3f).setAlignment(Alignment.MID);
        tooltip.addPara("Size of fleets scales with market's income ",  Color.ORANGE,1f).setAlignment(Alignment.MID);
        tooltip.addPara("Number fleets scales with market's size ",  Color.ORANGE,1f).setAlignment(Alignment.MID);
        tooltip.addPara("Fleets will follow their own fleet doctrine.",  Color.ORANGE,1f).setAlignment(Alignment.MID);
    }

    @Override
    public void createDetailedTooltipDescription(TooltipMakerAPI tooltip) {
        tooltip.addPara("Reduces fleet size on all markets by %s", 5f, Misc.getNegativeHighlightColor(), "50%.");
        tooltip.addPara("All markets spawn Privateer Defence Fleets.",  Misc.getPositiveHighlightColor(),3f);
        tooltip.addPara("Each market spawns from %s to %s fleets depending on market size",5f,Color.ORANGE,"2","5");
        tooltip.addPara(BaseIntelPlugin.BULLET+"Size 4 : %s",3f,Color.ORANGE,"2");
        tooltip.addPara(BaseIntelPlugin.BULLET+"Size 6 : %s",3f,Color.ORANGE,"3");
        tooltip.addPara(BaseIntelPlugin.BULLET+"Size 8 : %s",3f,Color.ORANGE,"5");
        tooltip.addPara("Fleet get %s Fleet Point for each %s earned by market, maximum of %s fleet points can be reached.",5f,Color.ORANGE,"+1",Misc.getDGSCredits(2000),"300");

        tooltip.addPara("Fleets will follow their own fleet doctrine and will use it's own designs, not faction ones.",  Color.ORANGE,5f);
        super.createDetailedTooltipDescription(tooltip);
    }
}
