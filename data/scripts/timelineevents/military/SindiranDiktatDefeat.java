package data.scripts.timelineevents.military;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.impl.campaign.ids.Factions;
import com.fs.starfarer.api.impl.campaign.intel.events.SindrianDiktatHostileActivityFactor;
import com.fs.starfarer.api.ui.Alignment;
import com.fs.starfarer.api.ui.TooltipMakerAPI;
import com.fs.starfarer.api.util.Misc;
import data.scripts.timelineevents.templates.DefeatMajorFactionCrisisEvent;

public class SindiranDiktatDefeat extends DefeatMajorFactionCrisisEvent {
    public SindiranDiktatDefeat() {
        super(Factions.DIKTAT);
    }

    @Override
    public String getTitleOfEvent() {
        return "Lion Subdued";
    }

    @Override
    public void createDetailedTooltipOnHover(TooltipMakerAPI tooltip) {
        super.createDetailedTooltipOnHover(tooltip);
        tooltip.addPara(
                "The Sindrian Diktat, enraged by %s’s rise in fuel production, launched raids and eventually a full expedition in an effort to reassert dominance over the sector's fuel economy. However, the player’s defenses held fast. The expedition was defeated, and with it, the Diktat’s monopoly ambitions were dealt a major blow.",
                5f,
                Misc.getHighlightColor(),
                Global.getSector().getPlayerFaction().getDisplayNameLong()
        );
    }

    @Override
    public void createSmallNoteForEvent(TooltipMakerAPI tooltip) {
        tooltip.addPara(
                        "The Sindrian Diktat's fuel embargo collapsed after their expedition was defeated by " +
                                Global.getSector().getPlayerFaction().getDisplayNameLong(), Misc.getTextColor(), 0f)
                .setAlignment(Alignment.MID);
    }

    @Override
    public int getPointsForGoal() {
        return 80; // Adjustable depending on relative challenge of this faction
    }

    @Override
    public boolean checkForCondition() {
        return SindrianDiktatHostileActivityFactor.isPlayerDefeatedDiktatAttack();
    }
}
