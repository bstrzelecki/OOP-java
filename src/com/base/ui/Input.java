package com.base.ui;

import com.base.IFilteredActionReceiver;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

public class Input extends JComponent {

    private IFilteredActionReceiver receiver;

    public Input() {
        for (char c : new char[]{'q', 'w', 'e', 'a', 's', 'd', 'x', ' '}) {
            getInputMap(WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(c), "allowedAction." + c);
            getActionMap().put("allowedAction." + c, new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    receiver.onActionPerformed(c);
                }
            });
        }
        getInputMap(WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0), "allowedAction.w");
        getInputMap(WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0), "allowedAction.a");
        getInputMap(WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0), "allowedAction.d");
        getInputMap(WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0), "allowedAction.s");
    }

    public void setActionReceiver(IFilteredActionReceiver receiver) {
        this.receiver = receiver;
    }

}
