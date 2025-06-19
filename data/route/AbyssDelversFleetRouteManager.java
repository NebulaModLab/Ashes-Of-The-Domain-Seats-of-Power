package data.route;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.Script;
import com.fs.starfarer.api.campaign.*;
import com.fs.starfarer.api.campaign.impl.items.BlueprintProviderItem;
import com.fs.starfarer.api.campaign.impl.items.ModSpecItemPlugin;
import com.fs.starfarer.api.impl.campaign.fleets.RouteLocationCalculator;
import com.fs.starfarer.api.impl.campaign.fleets.RouteManager;
import com.fs.starfarer.api.impl.campaign.ids.MemFlags;
import com.fs.starfarer.api.impl.campaign.ids.Submarkets;
import com.fs.starfarer.api.impl.campaign.procgen.SalvageEntityGenDataSpec;
import com.fs.starfarer.api.impl.campaign.rulecmd.salvage.SalvageEntity;
import com.fs.starfarer.api.impl.combat.threat.DisposableThreatFleetManager;
import com.fs.starfarer.api.util.Misc;
import data.industry.NovaExploraria;
import data.intel.AbyssDelversFleetIntel;
import data.intel.TechHunterFleetIntel;
import org.lazywizard.lazylib.MathUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class AbyssDelversFleetRouteManager extends TechHuntersFleetRouteManager {
    public AbyssDelversFleetRouteManager(CampaignFleetAPI fleet, RouteManager.RouteData route, int expectedMonths) {
        super(fleet, route, expectedMonths);
    }
    public boolean retreat= false;
    AbyssDelversFleetIntel intelAbyss;

    @Override
    protected void giveInitialAssignments() {
        RouteManager.RouteSegment current = route.getCurrent();
        intelAbyss = new AbyssDelversFleetIntel(fleet);
        Global.getSector().getIntelManager().addIntel(intelAbyss);
        SectorEntityToken source = route.getMarket().getPrimaryEntity();
        trueSource = source;
        fleet.clearAssignments();
        fleet.setNoAutoDespawn(true);
        fleet.addEventListener(this);
        fleet.addAssignment(FleetAssignment.ORBIT_PASSIVE, source, MathUtils.getRandomNumberInRange(2, 4), "Preparing for \"Last Dive\"");
        pickWorldForTechMining(true);
    }

    @Override
    public void performCalcus(float amount) {
        daysSince+=Global.getSector().getClock().convertToDays(amount);
        if(Misc.getAbyssalDepth(fleet,true)>0.3f){
            if(daysSince>=30){
                daysSince = 0;
                performDiceRollForItems();
                if(!intelAbyss.isEnteredAbyss()){
                    intelAbyss.setEnteredAbyss(true);
                    intelAbyss.sendUpdateIfPlayerHasIntel(null,false);
                }


            }
        }
    }

    @Override
    public void reportFleetDespawnedToListener(CampaignFleetAPI fleet, CampaignEventListener.FleetDespawnReason reason, Object param) {
        if(reason.equals(CampaignEventListener.FleetDespawnReason.DESTROYED_BY_BATTLE)){
            intelAbyss.setFinished(true);
            intelAbyss.setSuccessful(false);
            cargo.clear();
            intelAbyss.sendUpdateIfPlayerHasIntel(null,false);
            intelAbyss.endAfterDelay(5f);
        }

        if(reason.equals(CampaignEventListener.FleetDespawnReason.OTHER)&&param instanceof String && param.equals("rolled_wrong")){
            intelAbyss.setFinished(true);
            intelAbyss.setSuccessful(false);
            cargo.clear();
            intelAbyss.sendUpdateIfPlayerHasIntel(null,false);

            intelAbyss.endAfterDelay(5f);
        }

        if(reason.equals(CampaignEventListener.FleetDespawnReason.REACHED_DESTINATION)){
            intelAbyss.setFinished(true);
            intelAbyss.setSuccessful(true);
            intelAbyss.sendUpdateIfPlayerHasIntel(null,false);
            trueSource.getMarket().getSubmarket(Submarkets.SUBMARKET_STORAGE).getCargo().addAll(cargo);
            intelAbyss.getCargo().addAll(cargo);
            intelAbyss.endAfterDelay(5f);
            cargo.clear();
        }

//        NovaExploraria.finishExpedition();
    }
    @Override
    public void pickWorldForTechMining(boolean ignore) {
        float abyssChance = getPercentageForAbyssExploFailure(monthsBeingOnExpedition);
        boolean doomedByAbyss = Misc.random.nextFloat() < abyssChance;
        fleet.getMemoryWithoutUpdate().set(MemFlags.MAY_GO_INTO_ABYSS,true);
        if (doomedByAbyss&&!ignore) {
           CampaignFleetAPI fleet =  DisposableThreatFleetManager.createThreatFleet(MathUtils.getRandomNumberInRange(1,2), 0, 0, DisposableThreatFleetManager.FabricatorEscortStrength.HIGH, null);

           Global.getSector().getHyperspace().spawnFleet(this.fleet,50,10,fleet);
           fleet.addAssignment(FleetAssignment.ATTACK_LOCATION,fleet,1000);
           return;
        } else {
             SectorEntityToken target = Global.getSector().getHyperspace().createToken(MathUtils.getRandomNumberInRange(-110000,-68000),MathUtils.getRandomNumberInRange(-60000,-38000));
            float travelDays = RouteLocationCalculator.getTravelDays(fleet, target) * 2.5f;
            if(!ignore){
                fleet.clearAssignments();
            }
            fleet.addAssignment(FleetAssignment.GO_TO_LOCATION, target, travelDays,
                    "Venturing into abyss");

            fleet.addAssignment(FleetAssignment.ORBIT_PASSIVE, target, 4,
                    "Calculating coordiantes for new location", new Script() {
                        @Override
                        public void run() {
                            fleet.clearAssignments();
                            pickWorldForTechMining(true);
                        }
                    });

        }

    }

    @Override
    public void performDiceRollForItems() {
// Max cap for chances per group to avoid extreme results
        if(monthsBeingOnExpedition>=expectedMonths){
            return;
        }
        monthsBeingOnExpedition++;

        List<SalvageEntityGenDataSpec.DropData> dropRandom = new ArrayList<>();

        // Helper to scale chances and clamp


        SalvageEntityGenDataSpec.DropData d;

        d = new SalvageEntityGenDataSpec.DropData();
        d.chances = scale(2,15); //
        d.group = "blueprints_low";
        dropRandom.add(d);

        d = new SalvageEntityGenDataSpec.DropData();
        d.chances = scale(4,30);
        d.group = "rare_tech_low";
        dropRandom.add(d);

        if(monthsBeingOnExpedition>2){
            d = new SalvageEntityGenDataSpec.DropData();
            d.chances = scale(1,1);
            d.group = "omega_weapons_small";
            dropRandom.add(d);
            d = new SalvageEntityGenDataSpec.DropData();
            d.chances = scale(1,1);
            d.group = "omega_weapons_medium";
            dropRandom.add(d);
            d = new SalvageEntityGenDataSpec.DropData();
            d.chances = scale(1,1);
            d.group = "omega_weapons_large";
            dropRandom.add(d);
        }



        // You can keep dropValue empty unless you're controlling exact value output
        List<SalvageEntityGenDataSpec.DropData> dropValue = new ArrayList<>();

        // Run the drop
        Random random = new Random();
        CargoAPI found = SalvageEntity.generateSalvage(random, 1f, 1f, 1f, 1f, dropValue, dropRandom);

        // Remove duplicates known to the player
        FactionAPI pf = Global.getSector().getPlayerFaction();
        OUTER: for (CargoStackAPI stack : found.getStacksCopy()) {
            if (stack.getPlugin() instanceof BlueprintProviderItem) {
                BlueprintProviderItem bp = (BlueprintProviderItem) stack.getPlugin();

                List<String> list = bp.getProvidedShips();
                if (list != null) for (String id : list) if (!pf.knowsShip(id)) continue OUTER;

                list = bp.getProvidedWeapons();
                if (list != null) for (String id : list) if (!pf.knowsWeapon(id)) continue OUTER;

                list = bp.getProvidedFighters();
                if (list != null) for (String id : list) if (!pf.knowsFighter(id)) continue OUTER;

                list = bp.getProvidedIndustries();
                if (list != null) for (String id : list) if (!pf.knowsIndustry(id)) continue OUTER;

                found.removeStack(stack);
            } else if (stack.getPlugin() instanceof ModSpecItemPlugin) {
                ModSpecItemPlugin mod = (ModSpecItemPlugin) stack.getPlugin();
                if (!pf.knowsHullMod(mod.getModId())) continue OUTER;
                found.removeStack(stack);
            }
        }

        // Add to fleet’s cargo storage
        cargo.addAll(found);
        if(monthsBeingOnExpedition>=expectedMonths){
            fleet.clearAssignments();
            fleet.addAssignment(FleetAssignment.GO_TO_LOCATION_AND_DESPAWN, trueSource,100000, "Returning to "+trueSource.getStarSystem().getName());
        }
        else{
            pickWorldForTechMining(false);
        }
    }

    @Override
    public void reportBattleOccurred(CampaignFleetAPI fleet, CampaignFleetAPI primaryWinner, BattleAPI battle) {
        if(Misc.getAbyssalDepth(fleet)>0){
            retreat = true;
            monthsBeingOnExpedition = expectedMonths+20;
            fleet.clearAssignments();
            intelAbyss.retreat = true;
            fleet.addAssignment(FleetAssignment.GO_TO_LOCATION_AND_DESPAWN, trueSource, 100000,
                    "Retreating in fear toward " + trueSource.getStarSystem().getName());
            cargo.clear();

        }
    }
    public static float getPercentageForAbyssExploFailure(int months){
        return Math.min(0.9f, months * 0.08f);
    }
}


