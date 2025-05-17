package data.listeners.timeline;

import com.fs.starfarer.api.campaign.PlanetAPI;
import com.fs.starfarer.api.campaign.econ.MarketAPI;
import com.fs.starfarer.api.campaign.listeners.PlayerColonizationListener;
import data.scripts.managers.TimelineListenerManager;

public class ParadiseColonyListenerEnforcer implements PlayerColonizationListener {
    @Override
    public void reportPlayerColonizedPlanet(PlanetAPI planet) {
        TimelineListenerManager.getInstance().executeListenersOfClass(1f, ParadiseColonyListener.class);
    }

    @Override
    public void reportPlayerAbandonedColony(MarketAPI colony) {

    }
}
