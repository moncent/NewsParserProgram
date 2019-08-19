package program.controller;

import program.view.mainButtons.ButtonMainMenu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Listener extends MouseAdapter implements ActionListener {
    private Controller controller;
    private ButtonMainMenu buttonMainMenu;

    public Listener(Controller controller, ButtonMainMenu buttonMainMenu) {
        this.controller = controller;
        this.buttonMainMenu = buttonMainMenu;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1) {
            actionPerformed(new ActionEvent(e.getSource(), e.getID(), e.paramString()));
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
         controller.openNewsAndWeatherFrame(buttonMainMenu);
    }
}
