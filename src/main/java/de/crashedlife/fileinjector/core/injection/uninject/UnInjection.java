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

package de.crashedlife.fileinjector.core.injection.uninject;

import de.crashedlife.fileinjector.FileInjector;
import de.crashedlife.fileinjector.core.ui.fx.alerts.InformationAlert;
import de.crashedlife.fileinjector.core.ui.fx.progressbar.ProgressBar;
import de.crashedlife.fileinjector.enums.InjectionMethod;
import de.crashedlife.fileinjector.utils.StringUtils;
import de.crashedlife.fileinjector.utils.injection.UnInjectionUtils;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class UnInjection
{
    // Uninjection method.
    public boolean uninject (AnchorPane anchorPane, Path fileToUninjectFilePath,  Path saveFilePath, ProgressBar progressBar, String injectionString)
    {
        // Updates the progress bar.
        progressBar.setProgressStep("Uninjecting... (Reading file bytes...)");
        progressBar.setProgress(0.01);

        // Initialize variable.
        byte[] injectedFileFileBytes;

        try
        {
            // Reading all the file bytes from the file.
            injectedFileFileBytes = Files.readAllBytes(fileToUninjectFilePath);
        }
        catch (IOException ioException)
        {
            StringUtils.sendInformation("IOException while reading the file bytes!");
            ioException.printStackTrace();
            new InformationAlert().showAlert(anchorPane, "Error", "Something went wrong!\nYour file can't be injected!\n(IOEXCEPTION WHILE READING THE FILE BYTES)", "Close");
            return false;
        }

        // Updates the progress bar.
        progressBar.setProgressStep("Uninjecting... (Prepare uninjection...)");
        progressBar.setProgress(0.4);

        // Resolves the injection method.
        InjectionMethod injectionMethod = InjectionMethod.Helper.resolveMethodViaFileExtensionName(FileInjector.INJECTOR_SETTINGS.getDecryptionMode());

        // Checks the injection method.
        switch (injectionMethod)
        {
            // Checks if the injection method is a typical image format.
            case JPG:
            case PNG:
                return UnInjectionUtils.imageUnInjection(anchorPane, injectedFileFileBytes, saveFilePath, progressBar, injectionString, injectionMethod.name());

            // Checks if the injection method is a jar file.
            case JAR:
                return UnInjectionUtils.jarUnInjection(anchorPane, injectedFileFileBytes, saveFilePath, progressBar);

            // Returns the default value.
            default:
                return false;
        }
    }
}