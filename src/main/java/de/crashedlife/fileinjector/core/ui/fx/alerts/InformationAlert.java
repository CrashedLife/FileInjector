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
import javafx.scene.control.Label;
import javafx.scene.effect.BoxBlur;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.StageStyle;

public class InformationAlert
{
    // Shows the alert.
    public void showAlert (AnchorPane anchorPane, String title, String description, String buttonText)
    {
        // Checks if the AnchorPane is not null.
        if (anchorPane != null)
        {
            // Initialize variables.
            JFXAlert<Void> jfxAlert = new JFXAlert<>();
            JFXDialogLayout jfxDialogLayout = new JFXDialogLayout();
            BoxBlur boxBlur = new BoxBlur(5, 5, 5);
            JFXButton jfxButton = new JFXButton(buttonText);

            // Initialize the stage style.
            jfxAlert.initStyle(StageStyle.UNDECORATED);

            // Sets the animation and content.
            jfxAlert.setAnimation(JFXAlertAnimation.CENTER_ANIMATION);
            jfxAlert.setContent(jfxDialogLayout);

            // Sets the title from the alert and the body.
            jfxDialogLayout.setHeading(new Label(title));
            jfxDialogLayout.setBody(new Text(description));

            // Sets the actions for the dialog.
            jfxDialogLayout.setActions(jfxButton);

            // Sets the blur effect.
            anchorPane.setEffect(boxBlur);

            // Adds an event handler to the button.
            jfxButton.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent ->
            {
                // Resets the effect and closes the alert.
                anchorPane.setEffect(null);
                jfxAlert.close();
            });

            // Shows the alert.
            jfxAlert.show();
        }
    }
}