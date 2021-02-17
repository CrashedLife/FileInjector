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

package de.crashedlife.fileinjector.core.injection.inject;

import de.crashedlife.fileinjector.FileInjector;
import de.crashedlife.fileinjector.core.ui.fx.alerts.InformationAlert;
import de.crashedlife.fileinjector.core.ui.fx.alerts.InteractionAlert;
import de.crashedlife.fileinjector.core.ui.fx.progressbar.ProgressBar;
import de.crashedlife.fileinjector.enums.InjectionMethod;
import de.crashedlife.fileinjector.utils.*;
import de.crashedlife.fileinjector.utils.injection.InjectionUtils;
import javafx.scene.layout.AnchorPane;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

public class Injection
{
    // Inject method.
    public boolean inject (AnchorPane anchorPane, Path fileToInjectFilePath, Path injectInFileFilePath, Path saveFilePath, ProgressBar progressBar, String injectionString)
    {
        // Updates the progress bar.
        progressBar.setProgressStep("Injecting... (Reading file bytes...)");
        progressBar.setProgress(0.01);

        // Initialize variables.
        byte[] fileToInjectFileBytes;
        byte[] injectInFileFileBytes;

        try
        {
            // Reading all file bytes from the files.
            fileToInjectFileBytes = Files.readAllBytes(fileToInjectFilePath);
            injectInFileFileBytes = Files.readAllBytes(injectInFileFilePath);
        }
        catch (IOException ioException)
        {
            StringUtils.sendInformation("IOException while reading the file bytes!");
            ioException.printStackTrace();
            new InformationAlert().showAlert(anchorPane, "Error", "Something went wrong!\nYour file can't be injected!\n(IOEXCEPTION WHILE READING THE FILE BYTES)", "Close");
            return false;
        }

        // Updates the progress bar.
        progressBar.setProgressStep("Injecting... (Prepare injection...)");
        progressBar.setProgress(0.4);

        // Resolves the injection method.
        InjectionMethod injectionMethod = InjectionMethod.Helper.resolveMethodViaFilePath(injectInFileFilePath);

        // Check if the injection method is not UNRESOLVED.
        if (!(injectionMethod.equals(InjectionMethod.UNRESOLVED)))
        {
            // Checks if the file encryption is enabled.
            if (FileInjector.INJECTOR_SETTINGS.isEncryptionEnabled())
            {
                // Updates the progress bar.
                progressBar.setProgressStep("Injecting... (Encrypting...)");

                // Sets the bytes for the encryption key.
                FileInjector.ENCRYPTION_MANAGER.setKeyBytes(FileInjector.INJECTOR_SETTINGS.getEncryptionPassword().getBytes());

                // Encrypts the file bytes.
                byte[] tempEncryptedFileBytes = FileInjector.ENCRYPTION_MANAGER.encrypt(fileToInjectFileBytes);

                // Checks if the encrypted files are not null.
                if (tempEncryptedFileBytes == null)
                {
                    // Shows warning about the failed encryption.
                    int resultEncryptionFailed = new InteractionAlert().showAlert(anchorPane, "Error", "Something went wrong while encrypting the file bytes!\nDo you want to continue? (File will be injected unencrypted!)", "Continue", "Cancel");

                    // Checks the result.
                    if (resultEncryptionFailed != 0)
                        return false;

                    // Initialize the file bytes.
                    tempEncryptedFileBytes = fileToInjectFileBytes;
                }

                // Initialize the file bytes.
                fileToInjectFileBytes = tempEncryptedFileBytes;
            }

            // Checks the injection method.
            switch (injectionMethod)
            {
                // Checks if the injection method is a typical image format.
                case JPG:
                case PNG:
                    return InjectionUtils.imageInjection(anchorPane, fileToInjectFileBytes, injectInFileFileBytes, saveFilePath, progressBar, injectionString, injectionMethod.name());

                // Checks if the injection method is a jar file.
                case JAR:
                    return InjectionUtils.jarInjection(anchorPane, fileToInjectFileBytes, injectInFileFileBytes, saveFilePath, progressBar);

                // Returns the default value.
                default:
                    return false;
            }
        }
        else
        {
            // Shows a warning that the injection failed.
            new InformationAlert().showAlert(anchorPane, "Error", "Something went wrong!\nYour file can't be injected!\n(METHOD: UNRESOLVED)", "Close");
            return false;
        }
    }
}