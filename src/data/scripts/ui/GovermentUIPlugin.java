package data.scripts.ui;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.*;
import com.fs.starfarer.api.graphics.SpriteAPI;
import com.fs.starfarer.api.input.InputEventAPI;
import com.fs.starfarer.api.ui.*;
import org.lazywizard.lazylib.ui.FontException;
import org.lazywizard.lazylib.ui.LazyFont;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;


import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class GovermentUIPlugin implements CustomUIPanelPlugin {
    protected InteractionDialogAPI dialog;
    protected CustomVisualDialogDelegate.DialogCallbacks callbacks;
    protected CustomPanelAPI panel;

    int dW, dH, pW, pH;
    protected TooltipMakerAPI mainTooltip;

    LazyFont font;
    LazyFont.DrawableString drawString;

    SpriteAPI testSprite;

    List<ButtonAPI> buttons = new ArrayList<>();
    HashMap<ButtonAPI, String> buttonMap = new HashMap<>();


    public static GovermentUIPlugin createDefault()
    {
        return new GovermentUIPlugin();
    }

    public void init(CustomPanelAPI panel, CustomVisualDialogDelegate.DialogCallbacks callbacks, InteractionDialogAPI dialog)
    {
        //so we can get back to the original InteractionDialogPlugin and do stuff with it or close it
        this.panel = panel;
        this.callbacks = callbacks;
        this.dialog = dialog;


        //these might be helpful if you are doing custom rendering
        dW = Display.getWidth();
        dH = Display.getHeight();
        pW = (int) this.panel.getPosition().getWidth();
        pH = (int) this.panel.getPosition().getHeight();


        //creates a LazyFont DrawableString, gets drawn in advance() method
        String aString = "Hi Mom";
        Color aColor = new Color(3, 75, 168);



        //loads a Sprite from file
        //if using this method, sprite must be defined in settings.json
        //will be drawn in render()
        testSprite = Global.getSettings().getSprite("characters", "andrada");
        //Could also use Global.getSettings().getSprite("filename") or Global.getSettings().getSprite("id")


        //when something changes in the UI and it needs to be re-drawn, call this
        reset();
    }

    public void reset()
    {
        //clears the ui panel
        if (mainTooltip != null)
        {
            panel.removeComponent(mainTooltip);
            buttons.clear();
            buttonMap.clear();
        }

        //create a new TooltipMakerAPI covering the entire UI panel
        //I don't think scrolling panels work here, but I might be doing them wrong
        mainTooltip = this.panel.createUIElement(this.panel.getPosition().getWidth(), this.panel.getPosition().getHeight(), false);
        mainTooltip.setForceProcessInput(true);
        panel.addUIElement(mainTooltip).inTL(0,0);


        //I split my panel into different sections to make things less confusing
        showHeader();
        showStuff1();
        //showStuff2();


    }

    public void showHeader()
    {

    }

    public void showStuff1()
    {
        //as an example, draw labels for every faction in the game and then put some buttons underneath them
        List<FactionAPI> factions = Global.getSector().getAllFactions();

        int index = 0;
        int xStart = 100;
        int yStart = 100;
        int ySpacer = 70;


        for (FactionAPI f : factions)
        {
            //the actual UI elements to draw
            LabelAPI fLabel = mainTooltip.addPara(f.getDisplayName(), 0);
            ButtonAPI fEnableButton = mainTooltip.addButton("Enable", null, Color.WHITE, new Color(35, 99, 35), 60, 24, 0);
            ButtonAPI fDisableButton = mainTooltip.addButton("Disable", null, Color.WHITE, new Color(113, 45, 45), 60, 24, 0);

            //where to draw them
            //in this example, everything is referenced from Top-Left being 0,0
            fLabel.getPosition().setLocation(0,0).inTL(xStart, index*ySpacer+yStart);
            fEnableButton.getPosition().setLocation(0,0).inTL(xStart, index*ySpacer+yStart+20);
            fDisableButton.getPosition().setLocation(0,0).inTL(xStart+80, index*ySpacer+yStart+20);

            //add our buttons to a list so we can check if they're clicked in advance()
            buttons.add(fEnableButton);
            buttons.add(fDisableButton);

            //add the buttons to a HashMap along with a string
            //once we check if a button has been pressed, we can get the string for it to decide what the button should do (all in advance())
            buttonMap.put(fEnableButton, "enable:"+f.getId());
            buttonMap.put(fDisableButton, "disable:"+f.getId());

            index++;

            //starts a new column if faction list gets too long
            if (index == 8)
            {
                xStart += 200;
                index = 0;
            }
        }
    }

    @Override
    public void positionChanged(PositionAPI position) {

    }

    @Override
    public void renderBelow(float alphaMult) {

    }

    @Override
    public void render(float alphaMult) {
        //this is where custom GL11 stuff is drawn


        // this will draw a box at x,y with width w and heigh h
        //remember OpenGL Bottom-Left is 0,0
        int x = dW - 700;
        int y = dH - 700;
        int w = 400;
        int h = 400;
        GL11.glPushMatrix();
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        Color color = new Color(241, 197, 4);
        GL11.glColor4f(color.getRed()/255f, color.getGreen()/255f, color.getBlue()/255f, 0.3f * alphaMult);
        for (int i=0; i<4; i++) {
            GL11.glBegin(GL11.GL_LINE_LOOP);
            {
                GL11.glVertex2f(x, y);
                GL11.glVertex2f(x + w, y);
                GL11.glVertex2f(x + w, y + h);
                GL11.glVertex2f(x, y + h);
            }
        }
        GL11.glEnd();
        GL11.glPopMatrix();


        //this will render a SpriteAPI
        //again Bottom-Left is 0,0
        testSprite.setSize(80,80);
        testSprite.render(150, 150);

    }

    @Override
    public void advance(float amount) {

        //draws the LazyFont DrawableString
        //I have no idea if these are the correct/best GL11 settings, but they work for me
        //positions for the draw command use Bottom-Left of screen as 0,0
        if (drawString != null)
        {
            GL11.glPushMatrix();
            //GL11.glScalef(1/uiscale, 1/uiscale, 1);
            int width = (int) (Display.getWidth() * Display.getPixelScaleFactor()),
                    height = (int) (Display.getHeight() * Display.getPixelScaleFactor());
            GL11.glViewport(0, 0, width, height);
            GL11.glOrtho(0, width, 0, height, -1, 1);
            drawString.draw(80, dH - 60);
            GL11.glPopMatrix();
        }


        //handles button input processing
        //if pressing a button changes something in the diplay, call reset()
        boolean needsReset = false;
        for (ButtonAPI b : buttons)
        {
            if (b.isChecked())
            {
                b.setChecked(false);

                //use the string to determine what the button should do
                String s = buttonMap.get(b);
                String[] tokens = s.split(":");
                if (tokens[0].equals("enable"))
                {
                    switch (tokens[1])
                    {
                        case "hegemony":
                            //do something
                            needsReset = true;
                            break;
                        case "pirates":
                            //do something else
                            needsReset = true;
                            break;
                    }
                }
                else if (tokens[0].equals("disable"))
                {
                    switch (tokens[1])
                    {
                        case "hegemony":
                            //do something
                            needsReset = true;
                            break;
                        case "pirates":
                            //do something else
                            needsReset = true;
                            break;
                    }
                }
            }
        }

        //pressing a button usually means something we are displaying has changed, so redraw the panel from scratch
        if (needsReset) reset();

    }

    @Override
    public void processInput(List<InputEventAPI> events) {

        //this works for keyboard events, but does not seem to capture other UI events like button presses, thus why we use advance()
        for (InputEventAPI event : events) {
            if (event.isConsumed()) continue;
            //is ESC is pressed, close the custom UI panel and the blank IDP we used to create it
            if (event.isKeyDownEvent() && event.getEventValue() == Keyboard.KEY_ESCAPE) {
                event.consume();
                callbacks.dismissDialog();
                dialog.dismiss();
                return;
            }
        }

    }

    @Override
    public void buttonPressed(Object buttonId) {

    }

}
