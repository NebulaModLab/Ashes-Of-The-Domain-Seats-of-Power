package data.scripts.industry;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.CargoAPI;
import com.fs.starfarer.api.campaign.CargoStackAPI;
import com.fs.starfarer.api.campaign.FactionAPI;
import com.fs.starfarer.api.campaign.econ.MarketAPI;
import com.fs.starfarer.api.campaign.econ.MarketImmigrationModifier;
import com.fs.starfarer.api.campaign.impl.items.BlueprintProviderItem;
import com.fs.starfarer.api.campaign.impl.items.IndustryBlueprintItemPlugin;
import com.fs.starfarer.api.campaign.impl.items.ModSpecItemPlugin;
import com.fs.starfarer.api.impl.campaign.econ.impl.BaseIndustry;
import com.fs.starfarer.api.impl.campaign.population.PopulationComposition;
import com.fs.starfarer.api.impl.campaign.procgen.SalvageEntityGenDataSpec;
import com.fs.starfarer.api.impl.campaign.rulecmd.salvage.SalvageEntity;
import com.fs.starfarer.api.ui.TooltipMakerAPI;
import com.fs.starfarer.api.util.Misc;
import data.plugins.AoDCapitalUtilis;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class KaysaarExploratoria extends BaseIndustry implements MarketImmigrationModifier {
    public static int MIN_SIZE = 20;
    public boolean toosmolfaction=false;
    public boolean toosmollplanet=false;
    public boolean ALREADY_HAVE_CAPITAL=false;

    public static int IndustryBlueprintWeight = 10000;


    public void apply() {
        super.apply(false);

        if (!isFunctional()) {
            supply.clear();
        }

        market.addTransientImmigrationModifier(this);
    }

    @Override
    public void unapply() {
        market.removeTransientImmigrationModifier(this);
    }


    public boolean isAvailableToBuild() {
        return AoDCapitalUtilis.canBuildCapital(this.market);
    }



    public String getUnavailableReason() {
        return AoDCapitalUtilis.getUnavailableReasonForCapital(this.market);

    }
    @Override
    protected void buildingFinished() {
        super.buildingFinished();
        AoDCapitalUtilis.setCapital(this.market);
    }


    @Override
    public boolean canImprove() {
        return false;
    }

    @Override
    public boolean canInstallAICores() {
        return false;
    }



    public float getPatherInterest() {

        return  8 +super.getPatherInterest();
    }

    public static float getExploratoryCorpsModifier(MarketAPI market) {
        float mod = 0f;
        mod = 2.5f;
        mod *= market.getSize();
        return mod;
    }

    public CargoAPI generateCargoForGatheringPoint(Random random) {
        if (!isFunctional()) return null;
        float base = getExploratoryCorpsModifier(this.market);
        List<SalvageEntityGenDataSpec.DropData> dropRandom = new ArrayList<SalvageEntityGenDataSpec.DropData>();
        List<SalvageEntityGenDataSpec.DropData> dropValue = new ArrayList<SalvageEntityGenDataSpec.DropData>();

        SalvageEntityGenDataSpec.DropData d = new SalvageEntityGenDataSpec.DropData();
        d.chances = 1;
        d.group = "blueprints_low";
        //d.addCustom("item_:{tags:[single_bp], p:{tags:[rare_bp]}}", 1f);
        dropRandom.add(d);

        d = new SalvageEntityGenDataSpec.DropData();
        d.chances = 1;
        d.group = "rare_tech_low";
        d.valueMult = 0.1f;
        dropRandom.add(d);

        d =new SalvageEntityGenDataSpec.DropData();
        d.chances = 1;
        d.group = "ai_cores3";
        //d.valueMult = 0.1f; // already a high chance to get nothing due to group setup, so don't reduce further
        dropRandom.add(d);

        d = new SalvageEntityGenDataSpec.DropData();
        d.chances = 1;
        d.group = "any_hullmod_low";
        dropRandom.add(d);

        d = new SalvageEntityGenDataSpec.DropData();
        d.chances = 5;
        d.group = "weapons2";
        dropRandom.add(d);

        d = new SalvageEntityGenDataSpec.DropData();
        d.group = "ashes_of_domain";
        d.value = IndustryBlueprintWeight;
        dropValue.add(d);
        CargoAPI result = SalvageEntity.generateSalvage(random, 1f, 0.01f, base , 1f, dropValue, dropRandom);

        FactionAPI pf = Global.getSector().getPlayerFaction();
        OUTER: for (CargoStackAPI stack : result.getStacksCopy()) {
            if (stack.getPlugin() instanceof BlueprintProviderItem) {
                BlueprintProviderItem bp = (BlueprintProviderItem) stack.getPlugin();
                List<String> list = bp.getProvidedShips();
                if (list != null) {
                    for (String id : list) {
                        if (!pf.knowsShip(id)) continue OUTER;
                    }
                }

                list = bp.getProvidedWeapons();
                if (list != null) {
                    for (String id : list) {
                        if (!pf.knowsWeapon(id)) continue OUTER;
                    }
                }

                list = bp.getProvidedFighters();
                if (list != null) {
                    for (String id : list) {
                        if (!pf.knowsFighter(id)) continue OUTER;
                    }
                }

                list = bp.getProvidedIndustries();
                if (list != null) {
                    for (String id : list) {
                        if (!pf.knowsIndustry(id)) continue OUTER;
                    }
                }
                result.removeStack(stack);
            } else if (stack.getPlugin() instanceof ModSpecItemPlugin) {
                ModSpecItemPlugin mod = (ModSpecItemPlugin) stack.getPlugin();
                if (!pf.knowsHullMod(mod.getModId())) continue OUTER;
                result.removeStack(stack);
            }
        }
        IndustryBlueprintItemPlugin blueprintItemPlugin = new IndustryBlueprintItemPlugin();


        //result.addMothballedShip(FleetMemberType.SHIP, "hermes_d_Hull", null);

        return result;
    }


    @Override
    protected void addRightAfterDescriptionSection(TooltipMakerAPI tooltip, IndustryTooltipMode mode) {
        super.addRightAfterDescriptionSection(tooltip, mode);
        if (IndustryTooltipMode.ADD_INDUSTRY.equals(mode)){
            tooltip.addPara("Building that monument will consolidate yourself as one of the powers to be reckoned with in the Persean Sector.", Misc.getHighlightColor(), 10f);
        }
    }


    @Override
    public void modifyIncoming(MarketAPI market, PopulationComposition incoming) {

    }
}




