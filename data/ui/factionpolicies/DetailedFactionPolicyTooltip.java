package data.ui.factionpolicies;

import com.fs.starfarer.api.ui.TooltipMakerAPI;
import data.scripts.managers.FactionPolicySpecManager;
import data.scripts.models.BaseFactionPolicy;

public class DetailedFactionPolicyTooltip implements TooltipMakerAPI.TooltipCreator {
    BaseFactionPolicy policy;

    public DetailedFactionPolicyTooltip(BaseFactionPolicy policy) {
        this.policy = policy;
    }

    @Override
    public boolean isTooltipExpandable(Object tooltipParam) {
        return false;
    }

    @Override
    public float getTooltipWidth(Object tooltipParam) {
        return 450;
    }

    @Override
    public void createTooltip(TooltipMakerAPI tooltip, boolean expanded, Object tooltipParam) {
        policy.createDetailedTooltipDescription(tooltip);
    }
}
