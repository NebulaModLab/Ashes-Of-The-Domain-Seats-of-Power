package data.scripts.timelineevents.military;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.impl.campaign.ids.Factions;
import com.fs.starfarer.api.impl.campaign.intel.PerseanLeagueMembership;
import com.fs.starfarer.api.ui.Alignment;
import com.fs.starfarer.api.ui.TooltipMakerAPI;
import com.fs.starfarer.api.util.Misc;
import data.scripts.timelineevents.templates.DefeatMajorFactionCrisisEvent;

public class DefeatingPerseanLeague extends DefeatMajorFactionCrisisEvent {
    public DefeatingPerseanLeague() {
        super(Factions.PERSEAN);
    }

    @Override
    public String getTitleOfEvent() {
        return "League of Their Own";
    }

    @Override
    public void createDetailedTooltipOnHover(TooltipMakerAPI tooltip) {
        super.createDetailedTooltipOnHover(tooltip);
        tooltip.addPara(
                "Faced with economic pressure, political coercion, and an overwhelming blockade, %s refused to bend to Persean League demands.",
                5f,
                Misc.getHighlightColor(),
                Global.getSector().getPlayerFaction().getDisplayNameLong()
        );
    }

    @Override
    public void createSmallNoteForEvent(TooltipMakerAPI tooltip) {
        tooltip.addPara("The Persean League's pressure campaign failed after its blockade was defeated by " +
                        Global.getSector().getPlayerFaction().getDisplayNameLong(), Misc.getTextColor(), 0f)
                .setAlignment(Alignment.MID);
    }

    @Override
    public boolean checkForCondition() {
        return PerseanLeagueMembership.isDefeatedBlockadeOrPunEx();
    }
}
