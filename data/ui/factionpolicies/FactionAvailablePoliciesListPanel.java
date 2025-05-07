package data.ui.factionpolicies;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.ui.Alignment;
import com.fs.starfarer.api.ui.CustomPanelAPI;
import com.fs.starfarer.api.ui.Fonts;
import com.fs.starfarer.api.ui.TooltipMakerAPI;
import com.fs.starfarer.api.util.Misc;
import data.scripts.managers.FactionManager;
import data.scripts.managers.FactionPolicySpecManager;
import data.scripts.models.FactionPolicySpec;

import java.util.Iterator;
import java.util.Map;

public class FactionAvailablePoliciesListPanel extends FactionCurrentPoliciesListPanel {
    public FactionAvailablePoliciesListPanel(float width, float height) {
        super(width, height);
    }
    public void createUI() {
        panels.clear();

        if (tooltipPanel != null) {
            mainPanel.removeComponent(tooltipPanel);
            mainPanel.removeComponent(headerPanel);
            tooltip = null;
        }

        float panelWidth = mainPanel.getPosition().getWidth();
        float panelHeight = mainPanel.getPosition().getHeight();

        headerPanel = Global.getSettings().createCustom(panelWidth,30,null);
        TooltipMakerAPI tooltipHeader = headerPanel.createUIElement(panelWidth,30,false);
        tooltipHeader.setParaFont(Fonts.ORBITRON_24AABOLD);
        tooltipHeader.addPara("Available Policies", Misc.getTooltipTitleAndLightHighlightColor(),2f).setAlignment(Alignment.MID);

        tooltipPanel = Global.getSettings().createCustom(panelWidth, panelHeight-30, null);
        tooltip = tooltipPanel.createUIElement(panelWidth, panelHeight-30, true);

        int amountOfItems =  FactionPolicySpecManager.getFactionPolicySpecs().values().size(); // Total number of items to place
        float separator = 5f;  // Example separator size

        int itemsPerRow = calculateMaxAmountOfItems(panelWidth, separator);
        int created = 0;
        Iterator<FactionPolicySpec>specs = FactionPolicySpecManager.getFactionPolicySpecs().values().iterator();
        while (specs.hasNext()) {
            int itemsThisRow = Math.min(itemsPerRow, amountOfItems - created);
            float currX = 0;
            // Create a row panel
            float widthT = calculateTotalWidthOfItems(itemsThisRow,separator);
            CustomPanelAPI container = Global.getSettings().createCustom(panelWidth, PolicyPanel.HEIGHT,null);
            CustomPanelAPI row = Global.getSettings().createCustom(widthT, PolicyPanel.HEIGHT, null);

            for (int i = 0; i < itemsThisRow; i++) {
                if(!specs.hasNext())break;
                FactionPolicySpec spec = specs.next();
                PolicyPanel item = new PolicyPanel(false,spec.getId());
                row.addComponent(item.getMainPanel()).inTL(currX,0);
                currX+=PolicyPanel.WIDTH+separator;
                created++;
            }

            container.addComponent(row).inTL((panelWidth-widthT)/2,0f);
            tooltip.addCustom(container, 0f);
            tooltip.addSpacer(10f); // Vertical space between rows
        }

        tooltipPanel.addUIElement(tooltip).inTL(0, 0);
        headerPanel.addUIElement(tooltipHeader).inTL(0,0);

        mainPanel.addComponent(tooltipPanel).inTL(0, 30);
        mainPanel.addComponent(headerPanel).inTL(0,0);
    }
}
