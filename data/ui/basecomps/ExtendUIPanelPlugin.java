package data.ui.basecomps;

import com.fs.starfarer.api.campaign.CustomUIPanelPlugin;
import com.fs.starfarer.api.ui.CustomPanelAPI;

public interface ExtendUIPanelPlugin extends CustomUIPanelPlugin {
    public CustomPanelAPI getMainPanel();
    public void createUI();
}
