package data.scripts.goverments;

import com.fs.starfarer.api.campaign.FactionAPI;

public interface GovermentAPI {
    public void DiplomacyAssignRole();
    public void DeclareWarOnFaction();
    public void GovernmentEffectOnColonyApplier(String marketConditionId);
    public void GovernmentEffectOnPlayerFleet();
    public void GovermentEffectsOnSector();
    public String GetGovermentId();
    public void SetGovernmentId();
    public void DeclearePeace();
    public boolean CanDecleareWarOnFaction(FactionAPI factionAPI);
    public void ImmidiateEffectOfGoverment();
}
