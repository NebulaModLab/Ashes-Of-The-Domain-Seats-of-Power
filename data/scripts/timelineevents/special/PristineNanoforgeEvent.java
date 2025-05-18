package data.scripts.timelineevents.special;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.impl.campaign.ids.Industries;
import com.fs.starfarer.api.impl.campaign.ids.Items;
import com.fs.starfarer.api.ui.Alignment;
import com.fs.starfarer.api.ui.TooltipMakerAPI;
import com.fs.starfarer.api.util.Misc;
import data.scripts.models.TimelineEventType;
import data.scripts.timelineevents.templates.FirstItemInstalled;

public class PristineNanoforgeEvent extends FirstItemInstalled {
    public PristineNanoforgeEvent() {
        super(Items.PRISTINE_NANOFORGE);
    }

    @Override
    public String getImagePath() {
        return Global.getSettings().getIndustrySpec(Industries.ORBITALWORKS).getImageName();
    }

    @Override
    public String getTitleOfEvent() {
        return "Ancient Forge";
    }

    @Override
    public TimelineEventType getEventType() {
        return TimelineEventType.UNIQUE;
    }

    @Override
    public int getPointsForGoal() {
        return 40;
    }

    @Override
    public void createDetailedTooltipOnHover(TooltipMakerAPI tooltip) {
        super.createDetailedTooltipOnHover(tooltip);
        tooltip.addPara(
                "A pristine nanoforge has been integrated into our infrastructure at %s. This Domain-era artifact automates production with unprecedented precision, boosting output and ship quality by significant margins.",
                10f,
                Misc.getHighlightColor(),
                getName()
        );
    }


}
