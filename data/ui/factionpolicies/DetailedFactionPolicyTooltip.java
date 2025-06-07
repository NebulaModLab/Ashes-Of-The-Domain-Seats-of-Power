package data.ui.factionpolicies;

import com.fs.starfarer.api.ui.TooltipMakerAPI;
import com.fs.starfarer.api.util.Misc;
import data.scripts.managers.AoTDFactionManager;
import data.scripts.models.BaseFactionPolicy;

import java.awt.*;

public class DetailedFactionPolicyTooltip implements TooltipMakerAPI.TooltipCreator {
    BaseFactionPolicy policy;
    public static float width = 450;

    public DetailedFactionPolicyTooltip(BaseFactionPolicy policy) {
        this.policy = policy;
    }

    @Override
    public boolean isTooltipExpandable(Object tooltipParam) {
        return false;
    }

    @Override
    public float getTooltipWidth(Object tooltipParam) {
        return width;
    }

    @Override
    public void createTooltip(TooltipMakerAPI tooltip, boolean expanded, Object tooltipParam) {
        tooltip.setTitleOrbitronLarge();
        tooltip.addTitle(policy.getSpec().getName());
        policy.createDetailedTooltipDescription(tooltip);
        if(AoTDFactionManager.getInstance().doesHavePolicyEnabled(policy.getSpec().getId())){
            if(AoTDFactionManager.getInstance().doesHavePolicyInCopy(policy.getSpec().getId())){
                tooltip.addPara("This policy is already in effect for about %s ",5f, Color.ORANGE, Misc.getStringForDays((int) policy.getDaysTillPlaced()));
            }
            else{
                tooltip.addPara("This policy was in effect for about %s ",5f, Color.ORANGE,Misc.getStringForDays((int) policy.getDaysTillPlaced()));
                tooltip.addPara("If left Command UI tab, this policy will unapply!",Misc.getNegativeHighlightColor(),5f);

            }

        }
        else{
            tooltip.addPara("This policy will be in effect once chosen and left Command UI tab ",Color.ORANGE,5f);

        }
    }
}
