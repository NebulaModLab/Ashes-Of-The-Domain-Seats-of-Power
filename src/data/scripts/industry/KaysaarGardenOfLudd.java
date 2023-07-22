package data.scripts.industry;

import com.fs.starfarer.api.campaign.econ.Industry;
import com.fs.starfarer.api.impl.campaign.econ.impl.BaseIndustry;
import com.fs.starfarer.api.impl.campaign.ids.Commodities;
import com.fs.starfarer.api.impl.campaign.ids.Conditions;
import com.fs.starfarer.api.impl.campaign.ids.Industries;
import com.fs.starfarer.api.ui.TooltipMakerAPI;
import com.fs.starfarer.api.util.Misc;
import data.plugins.AoDCapitalUtilis;


public class KaysaarGardenOfLudd extends BaseIndustry {
    public static float HAZARD_RAITING_BONUS = -0.25f;


    @Override
    public void apply() {
        super.apply(true);
        if(isFunctional()){
            Industry pops = market.getIndustry(Industries.POPULATION);
            pops.getDemand(Commodities.FOOD).getQuantity().modifyMult("GardenofLuddFood",0);
            market.getHazard().modifyFlat("GardenofLuddAccess",HAZARD_RAITING_BONUS,"Garden of Ludd");
        }
    }
    @Override
    public void unapply() {
        super.unapply();
        Industry pops = market.getIndustry(Industries.POPULATION);
        pops.getDemand(Commodities.FOOD).getQuantity().unmodifyMult("GardenofLuddFood");
        market.getHazard().unmodifyFlat("GardenofLuddAccess");
    }

    @Override
    protected void buildingFinished() {
        super.buildingFinished();
        AoDCapitalUtilis.setCapital(this.market);
    }
    public boolean isAvailableToBuild() {
        if(!market.hasCondition(Conditions.HABITABLE)||!market.hasCondition(Conditions.MILD_CLIMATE)||!market.hasCondition(Conditions.FARMLAND_BOUNTIFUL)){
            return false;
        }
        return AoDCapitalUtilis.canBuildCapital(this.market);

    }

    public String getUnavailableReason() {
        String toReturn = AoDCapitalUtilis.getUnavailableReasonForCapital(this.market);
        if(!market.hasCondition(Conditions.HABITABLE)||!market.hasCondition(Conditions.MILD_CLIMATE)||!market.hasCondition(Conditions.FARMLAND_BOUNTIFUL)){
           toReturn +="This market does not have Habitable, Mild , or Bountifull Farmland Condition";
        }
        return toReturn;


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
