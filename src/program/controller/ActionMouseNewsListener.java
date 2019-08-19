package program.controller;

import program.view.ErrorMessageFrame;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.net.URI;

public class ActionMouseNewsListener implements MouseListener {

    private URI uri;

    public void setUri(URI uri) {
        this.uri = uri;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        try {
            if (Desktop.isDesktopSupported()) {
                Desktop.getDesktop().browse(uri);
            }
        } catch (IOException exception) {
            new ErrorMessageFrame(exception);
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}