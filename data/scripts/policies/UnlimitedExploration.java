package data.scripts.policies;

import com.fs.starfarer.api.impl.campaign.intel.BaseIntelPlugin;
import com.fs.starfarer.api.ui.Alignment;
import com.fs.starfarer.api.ui.TooltipMakerAPI;
import com.fs.starfarer.api.util.Misc;
import data.industry.NovaExploraria;
import data.scripts.managers.AoTDFactionManager;
import data.scripts.models.BaseFactionPolicy;
import data.scripts.models.TimelineEventType;

import java.awt.*;

public class UnlimitedExploration extends BaseFactionPolicy {
    @Override
    public void createTooltipDescription(TooltipMakerAPI tooltip) {
        tooltip.addPara("Increase amount of Exploraria Fleets by %s", 0, Misc.getPositiveHighlightColor(), "1").setAlignment(Alignment.MID);
        tooltip.addPara("Allows to sent Abyss Expedition Teams", Misc.getPositiveHighlightColor(), 3f).setAlignment(Alignment.MID);
        tooltip.addPara("This policy can only be removed when there are no expeditions being conducted by Nova Exploraria!",Misc.getNegativeHighlightColor(),3f).setAlignment(Alignment.MID);

        super.createTooltipDescription(tooltip);
    }

    @Override
    public void createDetailedTooltipDescription(TooltipMakerAPI tooltip) {
        tooltip.addPara("Increase amount of Nova Exploraria Fleets by %s", 5, Misc.getPositiveHighlightColor(), "1");
        tooltip.addPara("Allows to sent Abyss Expedition Teams", Misc.getPositiveHighlightColor(), 3f);
        tooltip.addPara("This policy can only be removed when there are no expeditions being conducted by Nova Exploraria!",Misc.getNegativeHighlightColor(),3f);
        super.createDetailedTooltipDescription(tooltip);
    }

    @Override
    public void applyPolicy() {
        if(NovaExploraria.getNova()!=null){
            NovaExploraria.getNova().getMaxAmountOfExpeditions().modifyFlat("unlimited_explo",1);
        }
        NovaExploraria.setCanDoAbyssDiving(true);
    }

    @Override
    public void unapplyPolicy() {
        if(NovaExploraria.getNova()!=null){
            NovaExploraria.getNova().getMaxAmountOfExpeditions().unmodifyFlat("unlimited_explo");
        }
        NovaExploraria.setCanDoAbyssDiving(false);
        super.unapplyPolicy();
    }

    @Override
    public boolean canBeRemoved() {
        return super.canBeRemoved()&&(NovaExploraria.getNova()==null||NovaExploraria.getNova().getCurrentAmountOfExpeditions()==0);
    }

    @Override
    public boolean showInUI() {
        return NovaExploraria.getNova()!=null&& AoTDFactionManager.getInstance().getScriptForGoal(TimelineEventType.RESEARCH_AND_EXPLORATION).reachedGoal("goal_4");
    }

    @Override
    public boolean canUsePolicy() {
        return super.canUsePolicy();
    }
}
