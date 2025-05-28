package data.scripts.policies;

import com.fs.starfarer.api.impl.campaign.intel.BaseIntelPlugin;
import com.fs.starfarer.api.ui.Alignment;
import com.fs.starfarer.api.ui.TooltipMakerAPI;
import com.fs.starfarer.api.util.Misc;
import data.industry.NovaExploraria;
import data.scripts.models.BaseFactionPolicy;

import java.awt.*;

public class UnlimitedExploration extends BaseFactionPolicy {
    @Override
    public void createTooltipDescription(TooltipMakerAPI tooltip) {
        tooltip.addPara("Increase amount of Exploraria Fleets by %s", 0, Misc.getPositiveHighlightColor(), "1").setAlignment(Alignment.MID);
        tooltip.addPara("Allows to sent Abyss Expedition Teams", Misc.getPositiveHighlightColor(), 3f).setAlignment(Alignment.MID);
        super.createTooltipDescription(tooltip);
    }

    @Override
    public void createDetailedTooltipDescription(TooltipMakerAPI tooltip) {
        tooltip.addPara("Increase amount of Nova Exploraria Fleets by %s", 0, Misc.getPositiveHighlightColor(), "1");
        tooltip.addPara("Allows to sent Abyss Expedition Teams", Misc.getPositiveHighlightColor(), 3f);
        super.createDetailedTooltipDescription(tooltip);
    }

    @Override
    public void applyPolicy() {
        NovaExploraria.setCanDoAbyssDiving();
    }
}
