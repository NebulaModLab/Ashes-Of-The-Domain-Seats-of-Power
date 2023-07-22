package data.scripts.industry;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.impl.campaign.econ.impl.BaseIndustry;
import com.fs.starfarer.api.impl.campaign.ids.Conditions;
import com.fs.starfarer.api.impl.campaign.ids.Stats;
import com.fs.starfarer.api.ui.TooltipMakerAPI;
import com.fs.starfarer.api.util.Misc;
import data.plugins.AoDCapitalUtilis;

public class KaysaarSpaceElevator extends BaseIndustry {
    public static int MIN_SIZE = 20;

    public boolean TooSmollFaction=false;
    public boolean tooSmollPlanet =false;

    public boolean ALREADY_HAVE_CAPITAL=false;
    public static float ACCESIBILITY_BONUS = 0.8f;
    public static float PRODUCTION_BOUUS = 30f;



    @Override
    public void apply() {
        super.apply(true);
        if(isFunctional()) {
            market.getAccessibilityMod().modifyFlat("SpaceElevatorAccess", ACCESIBILITY_BONUS, "Space Elevator");
            Global.getSector().getPlayerStats().getDynamic().getMod(Stats.CUSTOM_PRODUCTION_MOD).modifyMult("SpaceElevator", 1f + PRODUCTION_BOUUS / 100f, "Space Elevator");
            if (!market.hasCondition(Conditions.INDUSTRIAL_POLITY)) {
                market.addCondition(Conditions.INDUSTRIAL_POLITY);
            }
        }
    }

    @Override
    public void unapply() {
        super.unapply();
        Global.getSector().getPlayerStats().getDynamic().getMod(Stats.CUSTOM_PRODUCTION_MOD).unmodifyMult("SpaceElevator");
        market.getAccessibilityMod().unmodifyFlat("SpaceElevatorAccess");
        if (market.hasCondition(Conditions.INDUSTRIAL_POLITY)) {
            market.removeCondition(Conditions.INDUSTRIAL_POLITY);

        }
    }

    @Override
    public boolean showWhenUnavailable() {
        if(Global.getSettings().getModManager().isModEnabled("Us")){
            if(market.hasCondition("US_elevator")){
                return false;
            }
        }
        return true;
    }
    @Override
    protected void buildingFinished() {
      super.buildingFinished();
      AoDCapitalUtilis.setCapital(this.market);
    }
    public boolean isAvailableToBuild() {
        if(Global.getSettings().getModManager().isModEnabled("US")){
            if(market.hasCondition("US_elevator")){
                return false;
            }
        }
        return AoDCapitalUtilis.canBuildCapital(this.market);
    }

    public String getUnavailableReason() {
       return AoDCapitalUtilis.getUnavailableReasonForCapital(this.market);

    }
    public String getImproveMenuText() {
        return "Change Visual";
    }

    @Override
    public boolean canImprove() {
        return false;
    }

    @Override
    public boolean canInstallAICores() {
        return false;
    }
    @Override
    protected void addRightAfterDescriptionSection(TooltipMakerAPI tooltip, IndustryTooltipMode mode) {
        super.addRightAfterDescriptionSection(tooltip, mode);
        if (IndustryTooltipMode.ADD_INDUSTRY.equals(mode)){
            tooltip.addPara("Building that monument will consolidate yourself as one of the powers to be reckoned with in the Persean Sector.", Misc.getHighlightColor(), 10f);
        }
    }
}
