package data.scripts.timelineevents.templates;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.econ.MarketAPI;
import com.fs.starfarer.api.impl.campaign.ids.Industries;
import com.fs.starfarer.api.impl.campaign.ids.Stats;
import com.fs.starfarer.api.ui.Alignment;
import com.fs.starfarer.api.ui.TooltipMakerAPI;
import com.fs.starfarer.api.util.Misc;
import data.scripts.managers.FactionManager;
import data.scripts.models.BaseFactionTimelineEvent;
import data.scripts.models.TimelineEventType;

import java.awt.*;

public class GroundDefenceModifierEvent extends BaseFactionTimelineEvent {
    public int groundModifier;

    public GroundDefenceModifierEvent( int groundModifier, int romani) {
        this.groundModifier = groundModifier;
        this.romanNumeral = romani;

    }

    @Override
    public TimelineEventType getEventType() {
        return TimelineEventType.MILITARY;
    }

    @Override
    public boolean checkForCondition() {
        MarketAPI marketValid = FactionManager.getMarketsUnderPlayer().stream().filter(x -> x.getStats().getDynamic().getMod(Stats.GROUND_DEFENSES_MOD).computeEffective(0f) >= groundModifier).findFirst().orElse(null);
        if (marketValid != null) {
            entityId = marketValid.getPrimaryEntity().getId();
        }

        return marketValid != null;
    }
    @Override
    public void updateDataUponEntryOfUI() {
        FactionManager.getMarketsUnderPlayer().stream().filter(x->x.getPrimaryEntity().getId().equals(entityId)).findFirst().ifPresent(x->lastSavedName = x.getName());
    }

    @Override
    public void createDetailedTooltipOnHover(TooltipMakerAPI tooltip) {
        super.createDetailedTooltipOnHover(tooltip);
        tooltip.addPara(
                "%s has reached ground defence of value %s, becoming the first colony of with such strong ground defences under %s.",
                5f,
                Color.ORANGE,
                getName(),
                ""+groundModifier,
                Global.getSector().getPlayerFaction().getDisplayNameLong()
        );
    }
    @Override
    public void createSmallNoteForEvent(TooltipMakerAPI tooltip) {
        tooltip.addPara(getName() +" reached ground defence of "+groundModifier+" points", Misc.getTextColor(),0f).setAlignment(Alignment.MID);
    }

    @Override
    public String getID() {
        return super.getID()+""+groundModifier;
    }

    @Override
    public String getTitleOfEvent() {
        return "Planetary Defence "+toRoman(romanNumeral);
    }

    @Override
    public String getImagePath() {
        return Global.getSettings().getIndustrySpec(Industries.HEAVYBATTERIES).getImageName();
    }
}
