package com.gbc.inderdeep.actions;

import com.badlogic.gdx.scenes.scene2d.Action;
import com.gbc.inderdeep.ui.DialogBox;

/**
 * Created by markapptist on 2018-11-16.
 */

public class SetTextAction extends Action {

    protected String textToDisplay;

    public SetTextAction(String s) {
        textToDisplay = s;
    }

    public boolean act(float dt) {
        DialogBox db = (DialogBox)target;
        db.setText(textToDisplay);
        return true; //action completed
    }
}