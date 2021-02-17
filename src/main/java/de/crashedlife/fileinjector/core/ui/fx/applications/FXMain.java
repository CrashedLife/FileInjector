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

package de.crashedlife.fileinjector.core.ui.fx.applications;

import de.crashedlife.fileinjector.utils.Constants;
import de.crashedlife.fileinjector.utils.StringUtils;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class FXMain extends Application
{
    // Starts the application.
    public void start (Stage stage) throws IOException
    {
        this.initializeApplication(stage);
    }

    public void initializeApplication (Stage stage) throws IOException
    {
        // Initialize the variables.
        Parent root = FXMLLoader.load(this.getClass().getResource("/assets/fxml/main.fxml"));
        Scene scene = new Scene(root, 937, 539);

        // Close event.
        stage.setOnCloseRequest(event ->
        {
            StringUtils.sendInformation("Stopping application..");
            Platform.exit();
            System.exit(0);
        });

        // Set up stage.
        stage.setTitle("FileInjector | v" + Constants.VERSION + " | b" + Constants.BUILD + " | Developed by CrashedLife");
        stage.getIcons().add(new Image("/assets/images/logo.png"));
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }
}