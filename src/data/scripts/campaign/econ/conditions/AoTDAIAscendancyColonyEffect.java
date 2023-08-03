package data.scripts.campaign.econ.conditions;

import com.fs.starfarer.api.campaign.econ.Industry;
import com.fs.starfarer.api.campaign.econ.MarketAPI;
import com.fs.starfarer.api.campaign.econ.MarketImmigrationModifier;
import com.fs.starfarer.api.campaign.econ.MutableCommodityQuantity;
import com.fs.starfarer.api.combat.MutableStat;
import com.fs.starfarer.api.impl.campaign.econ.BaseHazardCondition;
import com.fs.starfarer.api.impl.campaign.econ.LCAttractorHigh;
import com.fs.starfarer.api.impl.campaign.econ.LCAttractorLow;
import com.fs.starfarer.api.impl.campaign.econ.MildClimate;
import com.fs.starfarer.api.impl.campaign.population.PopulationComposition;
import com.fs.starfarer.api.ui.TooltipMakerAPI;
import com.fs.starfarer.api.util.Misc;

import java.util.Map;

public class AoTDAIAscendancyColonyEffect extends LCAttractorHigh implements MarketImmigrationModifier {

    public void modifyIncoming(MarketAPI market, PopulationComposition incoming) {
        super.modifyIncoming(market, incoming);
        float positive = 0;
        for (Map.Entry<String, MutableStat.StatMod> stringStatModEntry : market.getIncoming().getWeight().getFlatMods().entrySet()) {
            if(stringStatModEntry.getValue().value>=0){
                positive+=stringStatModEntry.getValue().value;
            }
        }
        incoming.getWeight().modifyFlat(getModId(), -(positive*0.9f), Misc.ucFirst(condition.getName().toLowerCase()));
    }

    @Override
    public void apply(String id) {

        super.apply(id);
        if(market.getAdmin().isAICore()){
            for (Industry industry : market.getIndustries()) {
                for (MutableCommodityQuantity mutableCommodityQuantity : industry.getAllSupply()) {
                    mutableCommodityQuantity.getQuantity().modifyFlat("Ai_ascendancy",1,"Ai Ascendancy Government Effect");
                }
            }
            market.getStability().unmodifyFlat("Ai_Governor");
            market.getHazard().modifyFlat("Ai_Governor",-0.25f,"AI Ascendancy Government");


        }
        else{
            for (Industry industry : market.getIndustries()) {
                for (MutableCommodityQuantity mutableCommodityQuantity : industry.getAllSupply()) {
                    mutableCommodityQuantity.getQuantity().unmodifyFlat("Ai_ascendancy");
                }
            }
            market.getStability().modifyFlat("Ai_Governor",-14,"Ai core is not governor");
            market.getHazard().unmodifyFlat("Ai_Governor");

        }


    }
    protected void createTooltipAfterDescription(TooltipMakerAPI tooltip, boolean expanded) {
        super.createTooltipAfterDescription(tooltip, expanded);
        tooltip.addPara(
                "Bonus of Government:\n%s",
                10f,
                Misc.getStoryBrightColor(),
                "Increase Production by 1 Unit when AI is Administrator\nLowers Hazard Rating by 25% when AI is Administrator\nUnlocks All Remnant Ships\nYou get Monthly +20 Relations with Remnant"
        );
        tooltip.addPara(
                "Penalties of Government:\n%s",
                10f,
                Misc.getNegativeHighlightColor(),
                "-14 Stability when AI is not Administrator\nPermanent State of war with Hegemony, Luddic Church and Luddic Path\nReduced Population Growth by 90%"
        );
    }

    @Override
    public void unapply(String id) {
        super.unapply(id);
    }
    @Override
    public boolean showIcon() {
        return true;
    }

    public String getModId() {
        return condition.getId();
    }


}
