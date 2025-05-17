package data.scripts.timelineevents.military;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.impl.campaign.ids.Factions;
import com.fs.starfarer.api.impl.campaign.intel.events.LuddicChurchHostileActivityFactor;
import com.fs.starfarer.api.ui.Alignment;
import com.fs.starfarer.api.ui.TooltipMakerAPI;
import com.fs.starfarer.api.util.Misc;
import data.scripts.timelineevents.templates.DefeatMajorFactionCrisisEvent;

public class LuddicChurchDefeat extends DefeatMajorFactionCrisisEvent {
    public LuddicChurchDefeat(){
        super(Factions.LUDDIC_CHURCH);
    }

    @Override
    public String getTitleOfEvent() {
        return "Silent Choirs";
    }

    @Override
    public boolean checkForCondition() {
        return LuddicChurchHostileActivityFactor.isDefeatedExpedition();
    }

    @Override
    public void createDetailedTooltipOnHover(TooltipMakerAPI tooltip) {
        super.createDetailedTooltipOnHover(tooltip);
        tooltip.addPara(
                "The Knights of Ludd launched a crusade to reclaim what they deemed spiritually imperiled territory. But %s met them not with sermons, but with shields and fire. The Knights are broken, and the Church’s grip on immigration has loosened ever since. The colony now stands firm—faithful, perhaps, but free.",
                5f,
                Misc.getHighlightColor(),
                Global.getSector().getPlayerFaction().getDisplayNameLong()
        );
    }

    @Override
    public void createSmallNoteForEvent(TooltipMakerAPI tooltip) {
        tooltip.addPara("The Knights of Ludd were repelled by " + Global.getSector().getPlayerFaction().getDisplayNameLong(), Misc.getTextColor(), 0f).setAlignment(Alignment.MID);
    }
}
