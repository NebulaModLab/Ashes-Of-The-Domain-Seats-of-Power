package data.ui.timeline;

import ashlib.data.plugins.misc.AshMisc;
import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.input.InputEventAPI;
import com.fs.starfarer.api.ui.CustomPanelAPI;
import com.fs.starfarer.api.ui.PositionAPI;
import data.ui.basecomps.ExtendUIPanelPlugin;
import org.lwjgl.opengl.GL11;

import java.util.List;

public class DashLinePanel implements ExtendUIPanelPlugin {
    CustomPanelAPI mainPanel;
    public DashLinePanel(float width,float height) {
        mainPanel = Global.getSettings().createCustom(width,height,this);
    }
    @Override
    public CustomPanelAPI getMainPanel() {
        return mainPanel;
    }

    @Override
    public void createUI() {

    }

    @Override
    public void positionChanged(PositionAPI position) {

    }

    @Override
    public void renderBelow(float alphaMult) {

    }

    @Override
    public void render(float alphaMult) {
    renderDashedLine(alphaMult);
    }
    public void renderDashedLine(float alphaMult) {

        PositionAPI pos = mainPanel.getPosition();
        float centerY = mainPanel.getPosition().getCenterY();

        float startX = pos.getX();
        float endX = pos.getX() + pos.getWidth();

        float dashLength = 20f;
        float gapLength = 15f;

        GL11.glPushMatrix();
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glLineWidth(1f);
        GL11.glColor4f(1f, 1f, 1f, alphaMult); // white with alpha

        GL11.glBegin(GL11.GL_LINES);

        float x = startX;
        while (x < endX) {
            float dashEnd = Math.min(x + dashLength, endX);
            GL11.glVertex2f(x, centerY);
            GL11.glVertex2f(dashEnd, centerY);
            x += dashLength + gapLength;
        }

        GL11.glEnd();

        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glPopMatrix();
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
