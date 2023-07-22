package data.scripts.industry;

import com.fs.starfarer.api.campaign.econ.Industry;
import com.fs.starfarer.api.impl.campaign.econ.impl.BaseIndustry;
import com.fs.starfarer.api.impl.campaign.ids.Conditions;
import com.fs.starfarer.api.impl.campaign.ids.Stats;
import com.fs.starfarer.api.ui.TooltipMakerAPI;
import com.fs.starfarer.api.util.Misc;
import data.plugins.AoDCapitalUtilis;

public class KaysaarIndustrialMegaplex extends BaseIndustry {
    public static int MIN_SIZE = 20;

    public boolean toosmolfaction=false;
    public boolean toosmollplanet=false;
    public boolean ALREADY_HAVE_CAPITAL=false;
    public static float DISCOUNT = -0.3f;



    @Override
    public void apply() {
        super.apply(true);
        if(isFunctional()) {
            if (!market.hasCondition(Conditions.INDUSTRIAL_POLITY)) {
                market.addCondition(Conditions.INDUSTRIAL_POLITY);

            }
            market.getStats().getDynamic().getMod(Stats.MAX_INDUSTRIES).modifyFlat("indmega",1,"IndustrialMegaplex");
            for (Industry industry : market.getIndustries()) {
                if(industry.isIndustry()){
                    industry.getUpkeep().modifyMult("indmegadiscount",DISCOUNT,"Industrial Megaplex");
                }
            }
        }
    }

    @Override
    public void unapply() {
        super.unapply();
        if (market.hasCondition(Conditions.INDUSTRIAL_POLITY)) {
            market.removeCondition(Conditions.INDUSTRIAL_POLITY);

        }
        for (Industry industry : market.getIndustries()) {
            if(industry.isIndustry()){
                industry.getUpkeep().unmodifyMult("indmegadiscount");
            }
        }
        market.getStats().getDynamic().getMod(Stats.MAX_INDUSTRIES).unmodifyFlat("indmega");
    }

    @Override
    protected void buildingFinished() {
        super.buildingFinished();
        AoDCapitalUtilis.setCapital(this.market);
    }
    public boolean isAvailableToBuild() {
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
