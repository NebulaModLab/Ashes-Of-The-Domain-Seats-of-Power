package data.scripts.timelineevents.special;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.impl.campaign.ids.Factions;
import com.fs.starfarer.api.impl.campaign.intel.events.LuddicChurchHostileActivityFactor;
import com.fs.starfarer.api.ui.Alignment;
import com.fs.starfarer.api.ui.TooltipMakerAPI;
import com.fs.starfarer.api.util.Misc;
import data.scripts.models.TimelineEventType;
import data.scripts.timelineevents.templates.DefeatMajorFactionCrisisEvent;

public class MostWanted extends DefeatMajorFactionCrisisEvent {
    public MostWanted() {
        super(Factions.INDEPENDENT);
    }

    @Override
    public String getTitleOfEvent() {
        return "Unbound and Unwatched";
    }

    @Override
    public TimelineEventType getEventType() {
        return TimelineEventType.UNIQUE;
    }

    @Override
    public int getPointsForGoal() {
        return 50;
    }

    @Override
    public boolean checkForCondition() {

        return Global.getSector().getPlayerMemoryWithoutUpdate().getBoolean("$nex_HA_defeatedPoliceExpedition");
    }

    @Override
    public void createDetailedTooltipOnHover(TooltipMakerAPI tooltip) {
        super.createDetailedTooltipOnHover(tooltip);
        tooltip.addPara(
                "Sectorpol issued a formal denunciation of the colony’s free port policy, citing its role in enabling trafficking, piracy, and data smuggling across the Core worlds. But %s refused to implement monitoring protocols or restrict traffic. When a regulatory task force attempted a punitive raid to force the port’s closure, local defenders repelled them with decisive force. Spaceports are remaining open.",
                5f,
                Misc.getHighlightColor(),
                Global.getSector().getPlayerFaction().getDisplayNameLong()
        );
    }



    @Override
    public void createSmallNoteForEvent(TooltipMakerAPI tooltip) {
        tooltip.addPara(
                "Sectorpol forces withdrew after a failed attempt to impose controls on " + Global.getSector().getPlayerFaction().getDisplayNameLong() + "’s free port.",
                Misc.getTextColor(),
                0f
        ).setAlignment(Alignment.MID);
    }

}
