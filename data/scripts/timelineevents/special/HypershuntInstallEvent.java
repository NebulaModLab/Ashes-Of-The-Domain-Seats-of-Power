package data.scripts.timelineevents.special;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.econ.MarketAPI;
import com.fs.starfarer.api.impl.campaign.econ.impl.PopulationAndInfrastructure;
import com.fs.starfarer.api.impl.campaign.ids.Items;
import com.fs.starfarer.api.ui.TooltipMakerAPI;
import com.fs.starfarer.api.util.Misc;
import data.scripts.models.TimelineEventType;
import data.scripts.timelineevents.templates.FirstItemInstalled;

public class HypershuntInstallEvent extends FirstItemInstalled {
    public HypershuntInstallEvent() {
        super(Items.CORONAL_PORTAL);
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
    public String getTitleOfEvent() {
        return "Power of the Sun";
    }
    @Override
    public void createDetailedTooltipOnHover(TooltipMakerAPI tooltip) {
        super.createDetailedTooltipOnHover(tooltip);
        tooltip.addPara(
                "With the successful activation of a hypershunt tap in vicinty of  %s, your faction has gained access to a power source beyond anything most colonies can dream of. Once only the domain of Domain-era mega-installations, the tap now hums at the heart of your infrastructure, delivering unlimited energy to fuel industries, research, and defense.",
                10f,
                Misc.getHighlightColor(),
                getName()
        );
    }


    @Override
    public String getImagePath() {
        return Global.getSettings().getSpriteName("illustrations","hypershunt");
    }


    @Override
    public boolean checkForCondition() {
        if(getMarketFittingCriteria()!=null) {
            MarketAPI market  = getMarketFittingCriteria();
            return PopulationAndInfrastructure.getNearestCoronalTap(market.getLocationInHyperspace(), true) != null;
        }
        return false;
    }

}
