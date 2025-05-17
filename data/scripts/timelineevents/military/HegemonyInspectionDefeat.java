package data.scripts.timelineevents.military;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.impl.campaign.ids.Factions;
import com.fs.starfarer.api.impl.campaign.intel.events.HegemonyAICoresActivityCause;
import com.fs.starfarer.api.impl.campaign.intel.events.HegemonyHostileActivityFactor;
import com.fs.starfarer.api.ui.Alignment;
import com.fs.starfarer.api.ui.TooltipMakerAPI;
import com.fs.starfarer.api.util.Misc;
import data.scripts.timelineevents.templates.DefeatMajorFactionCrisisEvent;

public class HegemonyInspectionDefeat extends DefeatMajorFactionCrisisEvent {


    public HegemonyInspectionDefeat() {
        super(Factions.HEGEMONY);
    }

    @Override
    public String getTitleOfEvent() {
        return "The Last Inspection";
    }
    @Override
    public void createSmallNoteForEvent(TooltipMakerAPI tooltip) {
        tooltip.addPara("The Hegemony called off its inspections after pressure from " + Global.getSector().getPlayerFaction().getDisplayNameLong(), Misc.getTextColor(), 0f).setAlignment(Alignment.MID);
    }
    @Override
    public void createDetailedTooltipOnHover(TooltipMakerAPI tooltip) {
        super.createDetailedTooltipOnHover(tooltip);
        tooltip.addPara(
                "Following a decisive confrontation, %s has successfully ended the Hegemony’s interference in local technological affairs. Their inspection fleets have been turned away for good.",
                5f,
                Misc.getHighlightColor(),
                Global.getSector().getPlayerFaction().getDisplayNameLong()
        );
    }

    @Override
    public boolean checkForCondition() {
        entityId = Factions.HEGEMONY;
        return HegemonyHostileActivityFactor.isPlayerDefeatedHegemony();
    }
}
