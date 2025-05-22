package data.scripts.policies;

import com.fs.starfarer.api.Global;
import data.memory.AoTDSopMemFlags;
import data.scripts.models.BaseFactionPolicy;

public class ResearchGrants extends BaseFactionPolicy {
    @Override
    public boolean canUsePolicy() {
        return Global.getSector().getPlayerFaction().getMemoryWithoutUpdate().is(AoTDSopMemFlags.UNLOCK_POLICY+this.getSpec().getId(),true);
    }
}
