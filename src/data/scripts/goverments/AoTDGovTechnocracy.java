package data.scripts.goverments;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.FactionAPI;
import com.fs.starfarer.api.combat.ShipHullSpecAPI;
import com.fs.starfarer.api.impl.campaign.ids.Factions;

public class AoTDGovTechnocracy extends BaseGovernment{
    public AoTDGovTechnocracy(FactionAPI factionAPI, String id) {
        super(factionAPI, id);
    }
    @Override
    public void advance(float amount) {
        Global.getSector().getMemory().set("$aotd_test_gov_advamce",true);
        GovernmentEffectOnColonyApplier("aotd_gov_technocracy");
        GovermentEffectsOnSector();
    }
    @Override
    public void GovermentEffectsOnSector() {
        Global.getSector().getFaction(Factions.LUDDIC_CHURCH).setRelationship(faction.getId(),-100);
        Global.getSector().getFaction(Factions.LUDDIC_PATH).setRelationship(faction.getId(),-100);

    }
}
