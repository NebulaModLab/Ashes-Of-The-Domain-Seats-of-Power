package data.scripts.timelineevents.military;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.impl.campaign.ids.Factions;
import com.fs.starfarer.api.impl.campaign.intel.events.TriTachyonHostileActivityFactor;
import com.fs.starfarer.api.ui.Alignment;
import com.fs.starfarer.api.ui.TooltipMakerAPI;
import com.fs.starfarer.api.util.Misc;
import data.scripts.timelineevents.templates.DefeatMajorFactionCrisisEvent;

public class TriTachyonFendingOffAttacks extends DefeatMajorFactionCrisisEvent {
    public TriTachyonFendingOffAttacks() {
        super(Factions.TRITACHYON);
    }

    @Override
    public String getTitleOfEvent() {
        return "Commerce War Repelled";
    }

    @Override
    public void createDetailedTooltipOnHover(TooltipMakerAPI tooltip) {
        super.createDetailedTooltipOnHover(tooltip);
        tooltip.addPara(
                "Despite waves of commerce raiders, mercenary fleets, and economic pressure, %s held firm. Through decisive strikes and persistent resistance, the player’s faction made continued aggression too costly for Tri-Tachyon. Though no deal was signed, their attempts to destabilize player colonies were effectively shut down.",
                5f,
                Misc.getHighlightColor(),
                Global.getSector().getPlayerFaction().getDisplayNameLong()
        );
    }

    @Override
    public void createSmallNoteForEvent(TooltipMakerAPI tooltip) {
        tooltip.addPara("Tri-Tachyon's raiding efforts collapsed after facing heavy resistance from " + Global.getSector().getPlayerFaction().getDisplayNameLong(), Misc.getTextColor(), 0f).setAlignment(Alignment.MID);
    }

    @Override
    public int getPointsForGoal() {
        return 100;
    }

    @Override
    public boolean checkForCondition() {
        return TriTachyonHostileActivityFactor.isPlayerCounterRaidedTriTach();
    }
}
