package data.misc;

import com.fs.starfarer.api.ui.ButtonAPI;
import com.fs.starfarer.api.ui.UIComponentAPI;
import com.fs.starfarer.api.ui.UIPanelAPI;

public class AoTDSopMisc {
    public static ButtonAPI tryToGetButtonProd(String name) {
        ButtonAPI button = null;
        try {
            for (UIComponentAPI componentAPI : ReflectionUtilis.getChildrenCopy((UIPanelAPI) ProductionUtil.getCurrentTab())) {
                if (componentAPI instanceof ButtonAPI) {
                    if (((ButtonAPI) componentAPI).getText().toLowerCase().contains(name)) {
                        button = (ButtonAPI) componentAPI;
                        break;
                    }
                }
            }
            return button;
        } catch (Exception e) {

        }
        return button;

    }
}
