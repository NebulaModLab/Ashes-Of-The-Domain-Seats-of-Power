package data.intel;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.*;
import com.fs.starfarer.api.campaign.econ.MarketAPI;
import com.fs.starfarer.api.campaign.listeners.FleetEventListener;
import com.fs.starfarer.api.impl.campaign.command.WarSimScript;
import com.fs.starfarer.api.impl.campaign.fleets.RouteManager;
import com.fs.starfarer.api.impl.campaign.ids.Factions;
import com.fs.starfarer.api.impl.campaign.ids.FleetTypes;
import com.fs.starfarer.api.impl.campaign.intel.group.FGTravelAction;
import com.fs.starfarer.api.impl.campaign.intel.group.FGWaitAction;
import com.fs.starfarer.api.impl.campaign.intel.group.GenericRaidFGI;
import com.fs.starfarer.api.impl.campaign.missions.FleetCreatorMission;
import com.fs.starfarer.api.impl.campaign.missions.hub.HubMissionWithTriggers;
import com.fs.starfarer.api.ui.LabelAPI;
import com.fs.starfarer.api.ui.TooltipMakerAPI;
import com.fs.starfarer.api.util.Misc;
import com.fs.starfarer.api.util.WeightedRandomPicker;
import org.lwjgl.util.vector.Vector2f;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class HelldiversRaidIntel extends GenericRaidFGI implements FleetEventListener {
    boolean luddicPath = false;
    public HelldiversRaidIntel(GenericRaidParams params,boolean luddicPath) {
        super(params);
        this.luddicPath = luddicPath;
    }

    protected void addBasicDescription(TooltipMakerAPI info, float width, float height, float opad) {
        info.addImage(getFaction().getLogo(), width, 128, opad);

        StarSystemAPI system = raidAction.getWhere();

        String noun = getNoun();
        if(luddicPath){
            info.addPara(Misc.ucFirst(faction.getPersonNamePrefixAOrAn()) + " %s " + noun + " against "
                            + "the authoritarian threat of luddites"  + ".", opad,
                    faction.getBaseUIColor(), faction.getPersonNamePrefix());
        }
        else{
            info.addPara(Misc.ucFirst(faction.getPersonNamePrefixAOrAn()) + " %s " + noun + " against "
                            + "the authoritarian threat of pirates"  + ".", opad,
                    faction.getBaseUIColor(), faction.getPersonNamePrefix());
        }
        //String aOrAn = Misc.getAOrAnFor(noun);
        //info.addPara(Misc.ucFirst(aOrAn) + " %s " + noun + " against "



    }

    @Override
    public void finish(boolean isAbort) {
        params.raidParams.allowedTargets.forEach(x->x.getMemoryWithoutUpdate().unset("$aotd_chosen_for_raid"));
        super.finish(isAbort);
    }

    @Override
    protected String getAssessmentRiskStringOverride() {
        return "may experience clenched fist of democracy";
    }

    @Override
    public boolean hasCustomRaidAction() {
        return true;
    }

    @Override
    public void doCustomRaidAction(CampaignFleetAPI fleet, MarketAPI market, float raidStr) {

        Misc.getStationBaseFleet(market).despawn(CampaignEventListener.FleetDespawnReason.DESTROYED_BY_BATTLE,null);
    }

    @Override
    protected void initActions() {
        setFaction(params.factionId);
        waitAction = new FGWaitAction(params.source.getPrimaryEntity(), params.prepDays,
                "preparing defensive actions");
        addAction(waitAction, PREPARE_ACTION);

        raidAction = createPayloadAction();

        travelAction = new FGTravelAction(params.source.getPrimaryEntity(),
                raidAction.getWhere().getCenter());

        addAction(travelAction, TRAVEL_ACTION);
        addAction(raidAction, PAYLOAD_ACTION);

        SectorEntityToken returnWhere = params.source.getPrimaryEntity();
        if (returnWhere.getStarSystem() != null) {
            returnWhere = returnWhere.getStarSystem().getCenter();
        }
        returnAction = new FGTravelAction(raidAction.getWhere().getCenter(),
                params.source.getPrimaryEntity());
        returnAction.setTravelText("returning to " + params.source.getPrimaryEntity().getName());
        addAction(returnAction, RETURN_ACTION);

        origin = params.source.getPrimaryEntity();

        int total = 0;
        for (Integer i : params.fleetSizes) total += i;
        createRoute(params.factionId, total, params.fleetSizes.size(), null, params);
    }

    @Override
    public CampaignFleetAPI spawnFleet(RouteManager.RouteData route) {
       CampaignFleetAPI fleet =  super.spawnFleet(route);
       if(fleet!=null){
           fleet.setName("Helldivers");

       }
        return fleet;
    }

    @Override
    protected CampaignFleetAPI createFleet(int size, float damage) {
        Vector2f loc = origin.getLocationInHyperspace();
        boolean pirate = faction.getCustomBoolean(Factions.CUSTOM_PIRATE_BEHAVIOR);

        FleetCreatorMission m = new FleetCreatorMission(getRandom());
        m.setFleetTypeMedium(FleetTypes.TASK_FORCE); // default would be "Patrol", don't want that

        preConfigureFleet(size, m);

        m.beginFleet();

        String factionId = getFleetCreationFactionOverride(size);
        if (factionId == null) factionId = params.factionId;

        m.createFleet(params.style, size, factionId, loc);
        m.triggerSetFleetFaction(params.factionId);

        m.setFleetSource(params.source);
        setFleetCreatorQualityFromRoute(m);
        m.setFleetDamageTaken(damage);
        if (pirate) {
            m.triggerSetPirateFleet();
        } else {
            m.triggerSetWarFleet();
        }

        if (params.remnant) {
            m.triggerSetRemnantConfigActive();
        }

        if (params.makeFleetsHostile) {
            for (MarketAPI market : params.raidParams.allowedTargets) {
                m.triggerMakeHostileToFaction(market.getFactionId());
            }
            m.triggerMakeHostile();
            if (Factions.LUDDIC_PATH.equals(faction.getId())) {
                m.triggerFleetPatherNoDefaultTithe();
            }
        }

        if (params.repImpact == HubMissionWithTriggers.ComplicationRepImpact.LOW || params.repImpact == null) {
            m.triggerMakeLowRepImpact();
        } else if (params.repImpact == HubMissionWithTriggers.ComplicationRepImpact.NONE) {
            m.triggerMakeNoRepImpact();
        }

        if (params.repImpact != HubMissionWithTriggers.ComplicationRepImpact.FULL) {
            m.triggerMakeAlwaysSpreadTOffHostility();
        }

        configureFleet(size, m);

        CampaignFleetAPI fleet = m.createFleet();
        if (fleet != null) {
            configureFleet(size, fleet);
        }
        return fleet;
    }


    @Override
    protected void spawnFleets() {

        Float damage = null;
        if (route != null && route.getExtra() != null) {
            damage = route.getExtra().damage;
        }
        if (damage == null) damage = 0f;

        WeightedRandomPicker<Integer> picker = new WeightedRandomPicker<Integer>(getRandom());
        picker.addAll(params.fleetSizes);

        int total = 0;
        for (Integer i : params.fleetSizes) total += i;

        float spawnsToSkip = total * damage * 0.5f;
        float skipped = 0f;

        //FactionAPI faction = Global.getSector().getFaction(params.factionId);

//		picker.add(1);
//		picker.add(1);

        while (!picker.isEmpty()) {
            Integer size = picker.pickAndRemove();
            if (skipped < spawnsToSkip && getRandom().nextFloat() < damage) {
                skipped += size;
                continue;
            }

            CampaignFleetAPI fleet = createFleet(size, damage);

            if (fleet != null && route != null) {
                fleet.setName("Helldivers");
                setLocationAndCoordinates(fleet, route.getCurrent());
       ;
                fleets.add(fleet);
            }
        }
    }

    protected void showMarketsInDanger(TooltipMakerAPI info, float opad, float width, StarSystemAPI system,
                                       List<MarketAPI> targets, String safeStr, String riskStr, String riskStrHighlight) {

        Color h = Misc.getHighlightColor();
        float raidStr  = getRoute().getExtra().getStrengthModifiedByDamage();
        float defenderStr = WarSimScript.getEnemyStrength(getFaction(), system, isPlayerTargeted());

        List<MarketAPI> safe = new ArrayList<MarketAPI>();
        List<MarketAPI> unsafe = new ArrayList<MarketAPI>();
        for (MarketAPI market : targets) {
            float defensiveStr = defenderStr + WarSimScript.getStationStrength(market.getFaction(), system, market.getPrimaryEntity());
            if (defensiveStr > raidStr * 1.25f) {
                safe.add(market);
            } else {
                unsafe.add(market);
            }
        }

        if (safe.size() == targets.size()) {
            info.addPara("However, all colonies " + safeStr + ", " +
                    "owing to their orbital defenses.", opad);
        } else {
            info.addPara("The following colonies " + riskStr, opad,
                    Misc.getNegativeHighlightColor(), riskStrHighlight);

            FactionAPI f = Global.getSector().getPlayerFaction();
            addMarketTable(info, f.getBaseUIColor(), f.getDarkUIColor(), f.getBrightUIColor(), unsafe, width, opad);
        }
    }

    protected boolean addStrengthDesc(TooltipMakerAPI info, float opad, StarSystemAPI system,
                                      String forces, String outcomeFailure, String outcomeUncertain, String outcomeSuccess) {
        Color h = Misc.getHighlightColor();

        float raidStr  = getRoute().getExtra().getStrengthModifiedByDamage();
        float defenderStr = 0f;
        if (system != null) defenderStr = WarSimScript.getEnemyStrength(getFaction(), system, isPlayerTargeted());

        String strDesc = Misc.getStrengthDesc(raidStr);
        int numFleets = (int) getApproximateNumberOfFleets();
        String fleets = "fleets";
        if (numFleets == 1) fleets = "fleet";

        String defenderDesc = "";
        String defenderHighlight = "";
        Color defenderHighlightColor = h;

        boolean potentialDanger = false;
        String outcome = null;
        if (raidStr < defenderStr * 0.75f) {
            defenderDesc = "The aggressor fleets are superior";
            defenderHighlightColor = Misc.getPositiveHighlightColor();
            defenderHighlight = "superior";
            outcome = outcomeFailure;
        } else if (raidStr < defenderStr * 1.25f) {
            defenderDesc = "The aggressor fleets are evenly matched";
            defenderHighlightColor = h;
            defenderHighlight = "evenly matched";
            outcome = outcomeUncertain;
            potentialDanger = true;
        } else {
            defenderDesc = "The aggressor fleets are outmatched";
            defenderHighlightColor = Misc.getNegativeHighlightColor();
            defenderHighlight = "outmatched";
            outcome = outcomeSuccess;
            potentialDanger = true;
        }

        if (outcome != null) {
            defenderDesc += ", and " + outcome + ".";
        } else {
            defenderDesc += ".";
        }

        defenderDesc = " " + defenderDesc;

        if (system == null) defenderDesc = "";


        LabelAPI label = info.addPara("The " + forces + " are " +
                        "projected to be %s and likely comprised of %s " + fleets + "." + defenderDesc,
                opad, h, strDesc, "" + numFleets);
        label.setHighlight(strDesc, "" + numFleets, defenderHighlight);
        label.setHighlightColors(h, h, defenderHighlightColor);

        return potentialDanger;
    }

    @Override
    public String getName() {
        if(luddicPath){
            return "Major Order: Deploying Freedom to Technophobe Radicals";
        }
        return "Major Order: Liberty Enforcement in Pirate Territory";
    }

    @Override
    public void reportFleetDespawnedToListener(CampaignFleetAPI fleet, CampaignEventListener.FleetDespawnReason reason, Object param) {

    }

    @Override
    public void reportBattleOccurred(CampaignFleetAPI fleet, CampaignFleetAPI primaryWinner, BattleAPI battle) {

    }
}
