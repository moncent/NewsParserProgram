package program.view.mainButtons;

import program.controller.Controller;
import program.controller.Listener;

import javax.swing.*;
import java.awt.*;

public abstract class MainButtons extends JButton {
    private ButtonMainMenu buttonMainMenu;
    private String buttonName;
    protected Controller controller;


    public MainButtons(ButtonMainMenu buttonMainMenu) {
        this.buttonMainMenu = buttonMainMenu;
        initButton();
    }

    public void initButton() {
        defineAndSetButtonName();
        setMainButtonSizeAndAlignment();
    }

    public void defineAndSetButtonName() {
        defineNameButton();
        this.setText(buttonName);
    }

    public void defineNameButton() {
        buttonName = "";
        switch (buttonMainMenu) {
            case YANDEX_NEWS:
                buttonName = "Яндекс Новости";
                break;
            case MAIL_NEWS:
                buttonName = "Mail Новости";
                break;
            case YANDEX_WEATHER:
                buttonName = "Яндекс Погода";
        }
    }

    public void setMainButtonSizeAndAlignment() {
        this.setSize(50, 50);
        this.setAlignmentX(Component.CENTER_ALIGNMENT);
    }

    public void setControllerAndAddListener(Controller controller) {
        this.controller = controller;
        addActionListener();
    }

    public void addActionListener() {
        this.addActionListener(new Listener(controller, buttonMainMenu));
    }
}