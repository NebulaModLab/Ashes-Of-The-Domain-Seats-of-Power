package data.plugins;


import com.fs.starfarer.api.BaseModPlugin;
import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.*;
import com.fs.starfarer.api.campaign.econ.MarketAPI;
import com.fs.starfarer.api.characters.AbilityPlugin;
import com.fs.starfarer.api.characters.PersonAPI;
import com.fs.starfarer.api.combat.EngagementResultAPI;

public class AoDCapitalsModPlugin extends BaseModPlugin {
     public void onGameLoad(boolean newGame) {

         CampaignEventListener customlistener = new CampaignEventListener() {
             @Override
             public void reportPlayerOpenedMarket(MarketAPI market) {

             }

             @Override
             public void reportPlayerClosedMarket(MarketAPI market) {

             }

             @Override
             public void reportPlayerOpenedMarketAndCargoUpdated(MarketAPI market) {

             }

             @Override
             public void reportEncounterLootGenerated(FleetEncounterContextPlugin plugin, CargoAPI loot) {

             }

             @Override
             public void reportPlayerMarketTransaction(PlayerMarketTransaction transaction) {

             }

             @Override
             public void reportBattleOccurred(CampaignFleetAPI primaryWinner, BattleAPI battle) {

             }

             @Override
             public void reportBattleFinished(CampaignFleetAPI primaryWinner, BattleAPI battle) {

             }

             @Override
             public void reportPlayerEngagement(EngagementResultAPI result) {

             }

             @Override
             public void reportFleetDespawned(CampaignFleetAPI fleet, FleetDespawnReason reason, Object param) {

             }

             @Override
             public void reportFleetSpawned(CampaignFleetAPI fleet) {

             }

             @Override
             public void reportFleetReachedEntity(CampaignFleetAPI fleet, SectorEntityToken entity) {

             }

             @Override
             public void reportFleetJumped(CampaignFleetAPI fleet, SectorEntityToken from, JumpPointAPI.JumpDestination to) {

             }

             @Override
             public void reportShownInteractionDialog(InteractionDialogAPI dialog) {

             }

             @Override
             public void reportPlayerReputationChange(String faction, float delta) {

             }

             @Override
             public void reportPlayerReputationChange(PersonAPI person, float delta) {

             }

             @Override
             public void reportPlayerActivatedAbility(AbilityPlugin ability, Object param) {

             }

             @Override
             public void reportPlayerDeactivatedAbility(AbilityPlugin ability, Object param) {

             }

             @Override
             public void reportPlayerDumpedCargo(CargoAPI cargo) {

             }


             @Override
             public void reportPlayerDidNotTakeCargo(CargoAPI cargo) {

             }

             @Override
             public void reportEconomyTick(int iterIndex) {

             }

             @Override
             public void reportEconomyMonthEnd() {
                 //for testing purpouse

             }
         };

         Global.getSector().addListener(customlistener);
         if (!Global.getSector().hasScript(AshesCapitalPlugin.class)) {
             Global.getSector().addScript(new AshesCapitalPlugin()); //Check for dittos

         }
     }
 }
