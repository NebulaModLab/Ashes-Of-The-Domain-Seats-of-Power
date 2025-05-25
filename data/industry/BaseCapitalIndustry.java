package data.industry;

import com.fs.starfarer.api.impl.campaign.econ.impl.BaseIndustry;
import data.ui.basecomps.ExtendUIPanelPlugin;
import data.ui.overview.capitalbuilding.BaseCapitalButton;

public abstract class BaseCapitalIndustry extends BaseIndustry {
    public BaseCapitalButton createButton(float width, float height) {

        return new BaseCapitalButton(width,height);
    }
    public ExtendUIPanelPlugin createPanel(float width, float height) {
        return null;
    }
}
