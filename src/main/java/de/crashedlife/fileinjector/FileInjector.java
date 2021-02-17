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

package de.crashedlife.fileinjector;

import de.crashedlife.fileinjector.core.system.managers.BackupManager;
import de.crashedlife.fileinjector.core.system.managers.EncryptionManager;
import de.crashedlife.fileinjector.core.system.managers.FileManager;
import de.crashedlife.fileinjector.core.system.managers.SettingsManager;
import de.crashedlife.fileinjector.core.system.settings.InjectorSettings;
import de.crashedlife.fileinjector.core.ui.fx.applications.FXMain;
import de.crashedlife.fileinjector.utils.Constants;
import de.crashedlife.fileinjector.utils.StringUtils;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;

import java.io.IOException;

public class FileInjector
{
    // Initialize variables.
    public static final BackupManager BACKUP_MANAGER = new BackupManager();
    public static final FileManager FILE_MANAGER = new FileManager();
    public static final InjectorSettings INJECTOR_SETTINGS = new InjectorSettings();
    public static final EncryptionManager ENCRYPTION_MANAGER = new EncryptionManager();
    public static final SettingsManager SETTINGS_MANAGER = new SettingsManager();

    // Main method of this project.
    public static void main (String[] arguments)
    {
        // Initialize injector.
        new FileInjector().initializeFileInjector();
    }

    // Initialize injector.
    private void initializeFileInjector ()
    {
        System.out.println("\n" +
                "01000110 01101001 01101100 01100101 01001001 01101110 01101010 01100101 01100011 01110100 01101111 01110010\n" +
                "                                .___ .   ._.             ,       \n" +
                "                                [__ *| _  | ._   * _  _.-+- _ ._.\n" +
                "                                |   ||(/,_|_[ )  |(/,(_. | (_)[  \n" +
                "                                               ._|               \n" +
                "01000110 01101001 01101100 01100101 01001001 01101110 01101010 01100101 01100011 01110100 01101111 01110010 ");

        StringUtils.sendEmptyLine();
        StringUtils.sendInformation("Copyright © 2021 CrashedLife");
        StringUtils.sendInformation("This is a non-commercial project.");
        StringUtils.sendInformation("All rights belong to their respective owners.");
        StringUtils.sendEmptyLine();
        StringUtils.sendInformation("Version: " + Constants.VERSION);
        StringUtils.sendInformation("Build version: " + Constants.BUILD);
        StringUtils.sendEmptyLine();
        StringUtils.sendInformation("Loading settings...");
        SETTINGS_MANAGER.loadSettings();
        StringUtils.sendInformation("Settings loaded!");

        Application.launch(FXMain.class);
    }

    // Restarts the application.
    public static void restartApplication (Stage stage)
    {
        // Closes the stage,
        stage.close();

        // Create Platform thread.
        Platform.runLater(() ->
        {
            try
            {
                // Initialize the application.
                new FXMain().initializeApplication(stage);
            }
            catch (IOException ioException)
            {
                StringUtils.sendInformation("IOException while restarting the application!");
                ioException.printStackTrace();
            }
        });
    }
}