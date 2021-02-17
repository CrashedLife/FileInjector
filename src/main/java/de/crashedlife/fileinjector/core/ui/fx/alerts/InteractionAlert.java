/*
 * Copyright © 2021 CrashedLife
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package de.crashedlife.fileinjector.core.ui.fx.alerts;

import com.jfoenix.animation.alert.JFXAlertAnimation;
import com.jfoenix.controls.JFXAlert;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialogLayout;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.effect.BoxBlur;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.StageStyle;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class InteractionAlert
{
    // Shows the alert.
    public int showAlert (AnchorPane anchorPane, String title, String description, String buttonText1, String buttonText2)
    {
        // Checks if the AnchorPane is not null.
        if (anchorPane != null)
        {
            // Initialize variables.
            JFXAlert<Void> jfxAlert = new JFXAlert<>();
            JFXDialogLayout jfxDialogLayout = new JFXDialogLayout();
            BoxBlur boxBlur = new BoxBlur(5, 5, 5);
            JFXButton jfxButton1 = new JFXButton(buttonText1);
            JFXButton jfxButton2 = new JFXButton(buttonText2);

            // Initialize the stage style.
            jfxAlert.initStyle(StageStyle.UNDECORATED);

            // Sets the animation and content.
            jfxAlert.setAnimation(JFXAlertAnimation.CENTER_ANIMATION);
            jfxAlert.setContent(jfxDialogLayout);

            List<Node> actionNodeList = new ArrayList<>();
            actionNodeList.add(jfxButton1);
            actionNodeList.add(jfxButton2);

            // Sets the title from the alert and the body.
            jfxDialogLayout.setHeading(new Label(title));
            jfxDialogLayout.setBody(new Text(description));

            // Sets the actions for the dialog.
            jfxDialogLayout.setActions(actionNodeList);

            // Sets the blur effect.
            anchorPane.setEffect(boxBlur);

            // Initialize variables.
            AtomicBoolean button1Clicked = new AtomicBoolean(false);
            AtomicBoolean button2Clicked = new AtomicBoolean(false);

            // Adds the event handlers to the buttons.
            jfxButton1.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent ->
            {
                button1Clicked.set(true);
                anchorPane.setEffect(null);
                jfxAlert.close();
            });

            jfxButton2.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent ->
            {
                button2Clicked.set(true);
                anchorPane.setEffect(null);
                jfxAlert.close();
            });

            // Shows the alert and waits for the result.
            jfxAlert.showAndWait();

            // Returns the button clicks.
            if (button1Clicked.get())
                return 0;

            if (button2Clicked.get())
                return 1;
        }

        // Returns -1 (no button clicked)
        return -1;
    }
}