package data.ui.basecomps;

import ashlib.data.plugins.ui.models.resizable.ButtonComponent;
import ashlib.data.plugins.ui.models.resizable.ImageViewer;
import ashlib.data.plugins.ui.models.resizable.LabelComponent;
import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.ui.Fonts;
import com.fs.starfarer.api.util.Misc;

public class FactionFlagButtonComponent extends ButtonComponent {

    public FactionFlagButtonComponent(float width, float height) {
        super(width, height);
        LabelComponent component = new LabelComponent(Fonts.ORBITRON_20AA,20, Global.getSector().getPlayerFaction().getDisplayNameLong(), Misc.getTextColor().brighter(),width*4,30);

        String crestName = Global.getSector().getPlayerFaction().getCrest();
        addComponent(new ImageViewer(width,height,crestName),0,0);
        addComponent(component,((width/2)-(component.getTextWidth()/2)),-22);

    }

    @Override
    public void performActionOnClick(boolean isRightClick) {
        Global.getSector().getCampaignUI().showPlayerFactionConfigDialog();
    }
}
