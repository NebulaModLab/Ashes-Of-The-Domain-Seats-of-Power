package data.ui.overview.population;

import ashlib.data.plugins.ui.models.resizable.ImageViewer;
import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.FactionAPI;
import com.fs.starfarer.api.campaign.econ.MarketAPI;
import com.fs.starfarer.api.input.InputEventAPI;
import com.fs.starfarer.api.ui.CustomPanelAPI;
import com.fs.starfarer.api.ui.LabelAPI;
import com.fs.starfarer.api.ui.PositionAPI;
import com.fs.starfarer.api.ui.TooltipMakerAPI;
import com.fs.starfarer.api.util.Misc;
import data.scripts.managers.AoTDFactionManager;
import data.ui.basecomps.ExtendUIPanelPlugin;

import java.awt.*;
import java.util.List;

public class MarketPopData implements ExtendUIPanelPlugin {
    CustomPanelAPI mainPanel, contentPanel;
    MarketAPI market;
    FactionAPI faction;
    public MarketPopData(MarketAPI market, float size) {
        this.market = market;
        mainPanel = Global.getSettings().createCustom(size, size, this);
        createUI();
    }
    public MarketPopData(FactionAPI faction, float size) {
        this.faction = faction;
        mainPanel = Global.getSettings().createCustom(size, size, this);
        createUI();
    }

    public void createUI() {
        if (contentPanel != null) {
            mainPanel.removeComponent(contentPanel);
        }
        contentPanel = Global.getSettings().createCustom(mainPanel.getPosition().getWidth() * 3, mainPanel.getPosition().getHeight(), null);
        TooltipMakerAPI tooltip = contentPanel.createUIElement(1000, contentPanel.getPosition().getHeight(), false);
        ImageViewer viewer = new ImageViewer(mainPanel.getPosition().getHeight(), mainPanel.getPosition().getHeight(), Global.getSettings().getSpriteName("rendering", "GlitchSquare"));
        if(market!=null){
            viewer.setColorOverlay(AoTDFactionManager.getInstance().getMarketColor(market.getId()));
            tooltip.addCustom(viewer.getComponentPanel(), 0f);
            Color[] colors = new Color[] {Misc.getBasePlayerColor(),Color.ORANGE,Color.ORANGE};
            LabelAPI label = tooltip.addPara( "%s" + " (%s) - %s", 5f,colors, market.getName(),""+market.getSize(), Misc.getRoundedValueMaxOneAfterDecimal(100f * AoTDFactionManager.getPercentageOfPopulationOnMarket(market)) + "%");

            label.getPosition().inTL(55, mainPanel.getPosition().getHeight() / 2 - (label.computeTextHeight(label.getText()) / 2));
            float combinedWidth = 55 + label.computeTextWidth(label.getText());
            mainPanel.getPosition().setSize(combinedWidth, mainPanel.getPosition().getHeight());
        }
        else{
            viewer.setColorOverlay(faction.getBaseUIColor().darker());
            tooltip.addCustom(viewer.getComponentPanel(), 0f);
            Color[] colors = new Color[] {faction.getBaseUIColor().darker(),Color.ORANGE};
            LabelAPI label = tooltip.addPara( "%s" + " (%s) - %s", 5f,colors, AoTDFactionManager.getFactionName(faction),""+(int) AoTDFactionManager.getTotalSize(faction),Misc.getRoundedValueMaxOneAfterDecimal(100 * AoTDFactionManager.getPercentageOfFactionInSector(faction)) + "%");

            label.getPosition().inTL(55, mainPanel.getPosition().getHeight() / 2 - (label.computeTextHeight(label.getText()) / 2));
            float combinedWidth = 55 + label.computeTextWidth(label.getText())+5;
            mainPanel.getPosition().setSize(combinedWidth, mainPanel.getPosition().getHeight());
        }

        contentPanel.addUIElement(tooltip).inTL(0, 0);
        mainPanel.addComponent(contentPanel).inTL(0, 0);
    }

    @Override
    public CustomPanelAPI getMainPanel() {
        return mainPanel;
    }

    @Override
    public void positionChanged(PositionAPI position) {

    }

    @Override
    public void renderBelow(float alphaMult) {

    }

    @Override
    public void render(float alphaMult) {

    }

    @Override
    public void advance(float amount) {

    }

    @Override
    public void processInput(List<InputEventAPI> events) {

    }

    @Override
    public void buttonPressed(Object buttonId) {

    }
}
