package data.conditions;

import com.fs.starfarer.api.campaign.econ.MarketAPI;
import com.fs.starfarer.api.impl.campaign.econ.BaseMarketConditionPlugin;
import com.fs.starfarer.api.ui.TooltipMakerAPI;
import com.fs.starfarer.api.util.Misc;
import data.scripts.managers.AoTDFactionManager;

import java.awt.*;

public class AoTDFactionCapital extends BaseMarketConditionPlugin {
    public static void applyToCapital(){
        MarketAPI market = AoTDFactionManager.getInstance().getCapitalMarket();
        if(market!=null&&!market.hasCondition("aotd_capital")){
            market.addCondition("aotd_capital");
        }
    }
    public static void applyPenalties(){
        if(!AoTDFactionManager.getInstance().doesControlCapital()&&AoTDFactionManager.getInstance().didDeclaredCapital()){
            AoTDFactionManager.getMarketsUnderPlayer().forEach(x->x.getStability().modifyFlat("aotd_capital",-7,"Capital Lost"));
        }
        else{
            AoTDFactionManager.getMarketsUnderPlayer().forEach(x->x.getStability().unmodifyFlat("aotd_capital"));

        }

    }
    @Override
    public void apply(String id) {
        super.apply(id);
        market.getStability().modifyFlat("aotd_capital",3,"Faction Capital");
    }

    @Override
    protected void createTooltipAfterDescription(TooltipMakerAPI tooltip, boolean expanded) {
       tooltip.addPara("%s stability bonus ",3f, Color.ORANGE,"+3");
       tooltip.addPara("Allows construction of unique capital buildings", Misc.getPositiveHighlightColor(),3f);
       tooltip.addPara("Losing this world will result in -7 Stability penalty across all worlds, until it's retaken",Misc.getNegativeHighlightColor(),5f);
    }
}
