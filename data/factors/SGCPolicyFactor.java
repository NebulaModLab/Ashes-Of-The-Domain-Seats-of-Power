package data.factors;

import com.fs.starfarer.api.impl.campaign.ids.Strings;
import com.fs.starfarer.api.impl.campaign.intel.events.BaseEventFactor;
import com.fs.starfarer.api.impl.campaign.intel.events.BaseEventIntel;
import com.fs.starfarer.api.impl.campaign.intel.events.BaseFactorTooltip;
import com.fs.starfarer.api.impl.campaign.intel.events.EventFactor;
import com.fs.starfarer.api.ui.TooltipMakerAPI;
import com.fs.starfarer.api.util.Misc;
import data.scripts.managers.AoTDFactionManager;

import java.awt.*;

public class SGCPolicyFactor extends BaseEventFactor implements EventFactor{

    public static final float MULT = 0.5f; // Constant multiplier when policy is active

    @Override
    public TooltipMakerAPI.TooltipCreator getMainRowTooltip(BaseEventIntel intel) {
        return new BaseFactorTooltip() {
            @Override
            public void createTooltip(TooltipMakerAPI tooltip, boolean expanded, Object tooltipParam) {
                float opad = 10f;
                Color h = Misc.getHighlightColor();

                tooltip.addPara("The \"Service Guarantees Citizenship\" policy slows down event progress while it is active. " +
                        "This effect does not stop or reverse progress, but it applies globally as long as the policy is in effect.", 0f);

                tooltip.addSpacer(10f);
                tooltip.addPara("Progress multiplier: %s", 0f, h, Strings.X + Misc.getRoundedValueMaxOneAfterDecimal(MULT));
            }
        };
    }

    @Override
    public boolean shouldShow(BaseEventIntel intel) {
        return AoTDFactionManager.getInstance().doesHavePolicyEnabled("aotd_civ_fleet");
    }

    @Override
    public float getAllProgressMult(BaseEventIntel intel) {
        return shouldShow(intel) ? MULT : 0f;
    }

    @Override
    public Color getProgressColor(BaseEventIntel intel) {
        return MULT < 1f ? Misc.getPositiveHighlightColor() : Misc.getHighlightColor();
    }

    @Override
    public String getProgressStr(BaseEventIntel intel) {
        return Strings.X + Misc.getRoundedValueMaxOneAfterDecimal(MULT);
    }

    @Override
    public String getDesc(BaseEventIntel intel) {
        return "Service Guarantees Citizenship";
    }

    @Override
    public Color getDescColor(BaseEventIntel intel) {
        return new Color(150, 41, 253);
    }
}
