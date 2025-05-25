package data.industry;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.CampaignFleetAPI;
import com.fs.starfarer.api.campaign.StarSystemAPI;
import com.fs.starfarer.api.campaign.econ.MarketAPI;
import com.fs.starfarer.api.combat.MutableStat;
import com.fs.starfarer.api.impl.campaign.fleets.FleetFactory;
import com.fs.starfarer.api.impl.campaign.fleets.RouteManager;
import com.fs.starfarer.api.impl.campaign.ids.Factions;
import com.fs.starfarer.api.impl.campaign.ids.FleetTypes;
import com.fs.starfarer.api.impl.campaign.ids.MemFlags;
import com.fs.starfarer.api.impl.campaign.procgen.themes.RuinsFleetRouteManager;
import com.fs.starfarer.api.util.Misc;
import data.industry.ui.NovaExplorariaButton;
import data.intel.NovaExplorariaExpeditionIntel;
import data.route.NovaExplorariaExpeditionFleetRouteManager;
import data.scripts.managers.AoTDFactionManager;
import data.ui.overview.capitalbuilding.BaseCapitalButton;

import java.util.Random;

import static com.fs.starfarer.api.impl.campaign.procgen.themes.RuinsFleetRouteManager.createScavenger;

public class NovaExploraria extends BaseCapitalIndustry {
    public int amountOfExpeditions =0;
    public MutableStat maxAmountOfExpeditions = new MutableStat(1f);
    public MutableStat multiplierOfExpeditionCost = new MutableStat(1f);

    public MutableStat getMultiplierOfExpeditionCost() {
        return multiplierOfExpeditionCost;
    }

    public boolean canSentExpedition(){
        return amountOfExpeditions<maxAmountOfExpeditions.getModifiedInt();
    }
    public static boolean canDoInifniteTechmining(){
        return Global.getSector().getMemoryWithoutUpdate().is("$aotd_nova_exploraria_update_1",true);
    }
    public static void setCanDoInifniteTechmining(){
         Global.getSector().getMemoryWithoutUpdate().set("$aotd_nova_exploraria_update_1",true);
    }
    public static NovaExploraria getNova(){
        MarketAPI market = AoTDFactionManager.getInstance().getCapitalMarket();

        if(market!=null){
            return (NovaExploraria) market.getIndustry("aotd_nova_exploraria");
        }
        return null;
    }
    public static void   finishExpedition(){
        if(getNova()!=null){
            getNova().setAmountOfExpeditions(getNova().getCurrentAmountOfExpeditions()-1);
        }
    }
    public  int getCurrentAmountOfExpeditions(){
        return amountOfExpeditions;
    }
    @Override
    public void apply() {

    }

    public void setAmountOfExpeditions(int amountOfExpeditions) {
        this.amountOfExpeditions = amountOfExpeditions;
    }
    public MutableStat getMaxAmountOfExpeditions(){
        return maxAmountOfExpeditions;
    }

    public void sentExpeditionFleet(StarSystemAPI systemToSurvey){
        if(!canSentExpedition())return;
        setAmountOfExpeditions(getCurrentAmountOfExpeditions()+1);

        CampaignFleetAPI fleet = createScavenger(FleetTypes.SCAVENGER_LARGE,market.getStarSystem().getLocation(), null, market, false, Misc.random);
        fleet.setFaction(Factions.PLAYER);
        fleet.setNoFactionInName(true);
        fleet.setName("Nova Exploraria Expedition Fleet");
        Long seed = new Random().nextLong();

        fleet.addTag("aotd_expedition");
        market.getContainingLocation().addEntity(fleet);
        fleet.setFacing((float) Math.random() * 360f);
        fleet.setLocation(market.getPrimaryEntity().getLocation().x, market.getPrimaryEntity().getLocation().y);
        RouteManager.RouteData data = new RouteManager.RouteData("nova_exploraria_" + market.getFaction().getId(), market, seed, new RouteManager.OptionalFleetData(market));
        data.addSegment(new RouteManager.RouteSegment(30000f, market.getPrimaryEntity()));
        fleet.getMemoryWithoutUpdate().set(MemFlags.MEMORY_KEY_ALLOW_LONG_PURSUIT, false);
        fleet.getMemoryWithoutUpdate().set(MemFlags.MEMORY_KEY_MAKE_HOLD_VS_STRONGER, false);
        fleet.getMemoryWithoutUpdate().set(MemFlags.FLEET_IGNORES_OTHER_FLEETS, true);

        NovaExplorariaExpeditionIntel intel = NovaExplorariaExpeditionIntel.get(systemToSurvey);
        fleet.addScript(new NovaExplorariaExpeditionFleetRouteManager(fleet, data, systemToSurvey,intel));
        Global.getSector().getIntelManager().addIntel(intel);


    }
    @Override
    public BaseCapitalButton createButton(float width, float height) {
        return new NovaExplorariaButton(width,height,this);
    }
}
