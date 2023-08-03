package data.scripts.goverments;

import com.fs.starfarer.api.EveryFrameScript;
import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.FactionAPI;
import com.fs.starfarer.api.campaign.impl.items.ShipBlueprintItemPlugin;
import com.fs.starfarer.api.combat.ShipHullSpecAPI;
import com.fs.starfarer.api.impl.campaign.ids.Factions;
import com.fs.starfarer.api.impl.campaign.rulecmd.salvage.special.BlueprintSpecial;
import com.fs.starfarer.api.impl.campaign.skills.AutomatedShips;
import com.fs.starfarer.campaign.command.CustomProductionPanel;

public class AoTDGovAiAscendancy extends BaseGovernment {
    public boolean hadImidiateEffect = false;
    public AoTDGovAiAscendancy(FactionAPI factionAPI , String govType){
        super(factionAPI,govType);
    }
    @Override
    public void advance(float amount) {
        Global.getSector().getMemory().set("$aotd_test_gov_advamce",true);
        GovernmentEffectOnColonyApplier("aotd_gov_ai_ascendancy");
        GovermentEffectsOnSector();
        if(faction!=null){
            ImmidiateEffectOfGoverment();
        }

    }

    @Override
    public void ImmidiateEffectOfGoverment() {
            for (ShipHullSpecAPI allShipHullSpec : Global.getSettings().getAllShipHullSpecs()) {
                if(allShipHullSpec.getManufacturer().equals("Remnant")){
                    this.faction.addKnownShip(allShipHullSpec.getBaseHullId(),true);
                }
                Global.getSector().getMemory().set("$aotd_test_gov_rem",true);

            }
    }

    @Override
    public void GovernmentEffectOnColonyApplier(String marketConditionId) {
        super.GovernmentEffectOnColonyApplier(marketConditionId);
        Global.getSector().getMemory().set("$aotd_test_Gov",true);
    }

    @Override
    public void GovermentEffectsOnSector() {
        Global.getSector().getFaction(Factions.HEGEMONY).setRelationship(faction.getId(), -100);
        Global.getSector().getFaction(Factions.LUDDIC_CHURCH).setRelationship(faction.getId(),-100);
        Global.getSector().getFaction(Factions.LUDDIC_PATH).setRelationship(faction.getId(),-100);
    }
}
