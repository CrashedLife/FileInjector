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

package de.crashedlife.fileinjector.core.ui.fx.progressbar;

import com.jfoenix.animation.alert.JFXAlertAnimation;
import com.jfoenix.controls.JFXAlert;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.JFXProgressBar;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.effect.BoxBlur;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.StageStyle;

import java.util.ArrayList;
import java.util.List;

public class ProgressBar
{
    // Initialize variables.
    private final JFXDialogLayout jfxDialogLayout = new JFXDialogLayout();
    private final JFXProgressBar jfxProgressBar = new JFXProgressBar();

    // Creates the progress bar.
    public void createProgressBar (AnchorPane anchorPane)
    {
        // Checks if the AnchorPane is not null.
        if (anchorPane != null)
        {
            // Initialize variables.
            JFXAlert<Void> jfxAlert = new JFXAlert<>();
            JFXButton jfxButton = new JFXButton("Cancel");
            BoxBlur boxBlur = new BoxBlur(5, 5, 5);
            List<Node> nodeList = new ArrayList<>();

            // Sets the progress to 0.0
            this.jfxProgressBar.setProgress(0.0);

            // Adds the nodes to the node list.
            nodeList.add(jfxProgressBar);
            nodeList.add(jfxButton);

            // Initialize the stage style.
            jfxAlert.initStyle(StageStyle.UNDECORATED);

            // Sets the animation and content.
            jfxAlert.setAnimation(JFXAlertAnimation.CENTER_ANIMATION);
            jfxAlert.setContent(jfxDialogLayout);

            // Sets the title from the alert and the body.
            this.jfxDialogLayout.setHeading(new Label("Initializing..."));
            this.jfxDialogLayout.setActions(nodeList);

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

    // Sets the progress.
    public void setProgress (double value)
    {
        this.jfxProgressBar.setProgress(value);
    }

    // Sets the progress step.
    public void setProgressStep (String value)
    {
        this.jfxDialogLayout.setHeading(new Label(value));
    }
}