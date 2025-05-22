package data.scripts.timelineevents.templates;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.impl.campaign.ids.Industries;
import com.fs.starfarer.api.ui.Alignment;
import com.fs.starfarer.api.ui.TooltipMakerAPI;
import com.fs.starfarer.api.util.Misc;
import data.scripts.managers.AoTDFactionManager;
import data.scripts.models.BaseFactionTimelineEvent;
import data.scripts.models.TimelineEventType;

import java.awt.*;

public class FirstIncomeThresholdEvent extends BaseFactionTimelineEvent {
    int reqIncome;

    public FirstIncomeThresholdEvent(String entityId, int reqIncome, int romani) {
        this.entityId = entityId;
        this.reqIncome = reqIncome;
        this.romanNumeral = romani;

    }

    @Override
    public String getID() {
        return "FirstIncomeThresholdEvent"+ reqIncome;
    }

    @Override
    public String getImagePath() {
        return Global.getSettings().getIndustrySpec(Industries.COMMERCE).getImageName();
    }

    @Override
    public String getTitleOfEvent() {
        return "Income Tycoon "+getRomanNumeral();
    }

    @Override
    public void createDetailedTooltipOnHover(TooltipMakerAPI tooltip) {
        super.createDetailedTooltipOnHover(tooltip);
        tooltip.addPara(
                "People of %s rejoice due to immense wealth, that this colony brings.",
                5f,
                Color.ORANGE,
                getName(),
                ""+ reqIncome,
                Global.getSector().getPlayerFaction().getDisplayNameLong()
        );
    }

    public String getName(){
        return lastSavedName;
    }

    @Override
    public void updateDataUponEntryOfUI() {
        AoTDFactionManager.getMarketsUnderPlayer().stream().filter(x->x.getPrimaryEntity().getId().equals(entityId)).findFirst().ifPresent(x->lastSavedName = x.getName());
    }

    @Override
    public void createSmallNoteForEvent(TooltipMakerAPI tooltip) {
        tooltip.addPara(getName() +" reached income of %s",0f,Color.ORANGE, Misc.getDGSCredits(reqIncome)).setAlignment(Alignment.MID);
    }

    @Override
    public TimelineEventType getEventType() {
        return TimelineEventType.PROSPERITY;
    }


}
