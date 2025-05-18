package data.scripts.timelineevents.templates;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.econ.MarketAPI;
import com.fs.starfarer.api.impl.campaign.ids.Factions;
import com.fs.starfarer.api.ui.Alignment;
import com.fs.starfarer.api.ui.TooltipMakerAPI;
import com.fs.starfarer.api.util.Misc;
import data.scripts.managers.FactionManager;
import data.scripts.models.BaseFactionTimelineEvent;
import data.scripts.models.TimelineEventType;

public class FirstItemInstalled extends BaseFactionTimelineEvent {
    String itemId;

    public FirstItemInstalled(String itemId) {
        this.itemId = itemId;

    }

    @Override
    public String getTitleOfEvent() {
        return "First "+ Global.getSettings().getSpecialItemSpec(itemId).getName();
    }

    @Override
    public String getID() {
        return super.getID()+itemId;
    }

    public String getName(){
        return lastSavedName;
    }
    public String getItemId() {
        return itemId;
    }
    @Override
    public void createSmallNoteForEvent(TooltipMakerAPI tooltip) {
        tooltip.addPara("Working "+ Global.getSettings().getSpecialItemSpec(getItemId()).getName()+ " installed on one of industries in " + getName(),
                Misc.getTextColor(), 0f).setAlignment(Alignment.MID);
    }
    @Override
    public TimelineEventType getEventType() {
        return TimelineEventType.PROSPERITY;
    }

    @Override
    public boolean checkForCondition() {
        MarketAPI market = getMarketFittingCriteria();

        return market!=null;
    }
    @Override
    public void updateDataUponEntryOfUI() {
        FactionManager.getMarketsUnderPlayer()  .stream().filter(x->x.getPrimaryEntity().getId().equals(entityId)).findFirst().ifPresent(x->lastSavedName = x.getName());
    }
    public MarketAPI getMarketFittingCriteria(){
        MarketAPI marketFound = FactionManager.getMarketsUnderPlayer().stream().filter(x -> x.getIndustries().stream().anyMatch(y -> y.getSpecialItem() != null && y.getSpecialItem().getId().equals(itemId))).findFirst().orElse(null);
        if (marketFound != null) {
            entityId = marketFound.getPrimaryEntity().getId();
        }
        return marketFound;
    }
}
