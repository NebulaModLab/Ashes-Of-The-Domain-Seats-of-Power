package data.scripts.timelineevents.military;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.impl.campaign.ids.Factions;
import com.fs.starfarer.api.impl.campaign.intel.events.LuddicPathHostileActivityFactor;
import com.fs.starfarer.api.impl.campaign.intel.events.PirateHostileActivityFactor;
import com.fs.starfarer.api.ui.Alignment;
import com.fs.starfarer.api.ui.TooltipMakerAPI;
import com.fs.starfarer.api.util.Misc;
import data.scripts.timelineevents.templates.DefeatMajorFactionCrisisEvent;

public class LuddicPathDefeat extends DefeatMajorFactionCrisisEvent {
    public LuddicPathDefeat() {
        super(Factions.LUDDIC_PATH);
    }

    @Override
    public String getTitleOfEvent() {
        return "Path Broken";
    }

    @Override
    public void createDetailedTooltipOnHover(TooltipMakerAPI tooltip) {
        super.createDetailedTooltipOnHover(tooltip);
        tooltip.addPara(
                "After countless skirmishes, sabotage attempts, and sudden strikes, %s managed to outmaneuver and suppress the Luddic Path's operations. While their ideology persists, their reach has been blunted—for now.",
                5f,
                Misc.getHighlightColor(),
                Global.getSector().getPlayerFaction().getDisplayNameLong()
        );
    }

    @Override
    public void createSmallNoteForEvent(TooltipMakerAPI tooltip) {
        tooltip.addPara("Luddic Path operations in the sector were suppressed by " + Global.getSector().getPlayerFaction().getDisplayNameLong(), Misc.getTextColor(), 0f)
                .setAlignment(Alignment.MID);
    }

    @Override
    public boolean checkForCondition() {
        return LuddicPathHostileActivityFactor.isPlayerDefeatedPatherExpedition();
    }
}
