package data.ui.factionpolicies;

import ashlib.data.plugins.ui.models.BasePopUpDialog;
import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.ui.*;
import com.fs.starfarer.api.util.Misc;
import data.misc.ProductionUtil;
import data.scripts.managers.AoTDFactionManager;
import data.scripts.models.FactionPolicySpec;

import java.util.Iterator;

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

        headerPanel = Global.getSettings().createCustom(panelWidth, 30, null);
        TooltipMakerAPI tooltipHeader = headerPanel.createUIElement(panelWidth, 30, false);
        tooltipHeader.setParaFont(Fonts.ORBITRON_24AABOLD);
        tooltipHeader.addPara("Available Policies", Misc.getTooltipTitleAndLightHighlightColor(), 2f).setAlignment(Alignment.MID);

        tooltipPanel = Global.getSettings().createCustom(panelWidth, panelHeight - 30, null);
        tooltip = tooltipPanel.createUIElement(panelWidth, panelHeight - 30, true);

        int amountOfItems = AoTDFactionManager.getInstance().getSpecsForAvailableUI().size(); // Total number of items to place
        float separator = 5f;  // Example separator size

        int itemsPerRow = calculateMaxAmountOfItems(panelWidth, separator);
        int created = 0;
        Iterator<FactionPolicySpec> specs = AoTDFactionManager.getInstance().getSpecsForAvailableUI().iterator();
        while (specs.hasNext()) {
            int itemsThisRow = Math.min(itemsPerRow, amountOfItems - created);
            float currX = 0;
            // Create a row panel
            float widthT = calculateTotalWidthOfItems(itemsThisRow, separator);
            CustomPanelAPI container = Global.getSettings().createCustom(panelWidth, PolicyPanel.HEIGHT, null);
            CustomPanelAPI row = Global.getSettings().createCustom(widthT, PolicyPanel.HEIGHT, null);

            for (int i = 0; i < itemsThisRow; i++) {
                if (!specs.hasNext()) break;
                FactionPolicySpec spec = specs.next();
                PolicyPanel item = new PolicyPanel(false, AoTDFactionManager.getInstance().getPolicyFromList(spec.getId()));
                if(AoTDFactionManager.getInstance().getCopyOfPolicies().size()+1> AoTDFactionManager.getInstance().getAvailablePolicies().getModifiedInt()){
                    item.getButton().setClickable(false);
                }
                panels.add(item);
                row.addComponent(item.getMainPanel()).inTL(currX, 0);
                currX += PolicyPanel.WIDTH + separator;
                created++;
            }

            container.addComponent(row).inTL((panelWidth - widthT) / 2, 0f);
            tooltip.addCustom(container, 0f);
            tooltip.addSpacer(10f); // Vertical space between rows
        }

        tooltipPanel.addUIElement(tooltip).inTL(0, 0);
        headerPanel.addUIElement(tooltipHeader).inTL(0, 0);

        mainPanel.addComponent(tooltipPanel).inTL(0, 30);
        mainPanel.addComponent(headerPanel).inTL(0, 0);
    }

    @Override
    public void advance(float amount) {
        for (PolicyPanel panel : panels) {
            if(panel.getButton().isChecked()){
                panel.getButton().setChecked(false);
                if(panel.isDisplayDialog()){
                    BasePopUpDialog dialog = new FactionPolicyWarningDialog("Policy Warning!");
                    CustomPanelAPI panelAPI = Global.getSettings().createCustom(700, 300, dialog);
                    UIPanelAPI panelAPI1 = ProductionUtil.getCoreUI();
                    dialog.init(panelAPI, panelAPI1.getPosition().getCenterX() - (panelAPI.getPosition().getWidth() / 2), panelAPI1.getPosition().getCenterY() + (panelAPI.getPosition().getHeight() / 2), true);
                }
                else{
                    AoTDFactionManager.getInstance().addPolicyToCopy(panel.policy.getSpec().getId());
                    changeRequired = true;
                    break;
                }

            }
        }
    }

}
