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

package de.crashedlife.fileinjector.utils.injection;

import de.crashedlife.fileinjector.FileInjector;
import de.crashedlife.fileinjector.core.ui.fx.alerts.InformationAlert;
import de.crashedlife.fileinjector.core.ui.fx.progressbar.ProgressBar;
import de.crashedlife.fileinjector.utils.Constants;
import de.crashedlife.fileinjector.utils.StringUtils;
import de.crashedlife.fileinjector.utils.converter.HEXConverter;
import javafx.application.Platform;
import javafx.scene.layout.AnchorPane;
import org.apache.commons.io.IOUtils;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Enumeration;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class UnInjectionUtils
{
    // Method for image uninjection.
    public static boolean imageUnInjection (AnchorPane anchorPane, byte[] fileToUninjectFileBytes, Path saveFilePath, ProgressBar progressBar, String injectionString, String mode)
    {
        // Initialize variable.
        AtomicBoolean result = new AtomicBoolean(false);

        // Updates the progress bar.
        progressBar.setProgressStep("Uninjecting... (" + mode + ")");
        progressBar.setProgress(0.2);

        // Create Platform thread.
        Platform.runLater(() ->
        {
            // Initialize variable and converts them to HEX.
            String fileToUnInjectHEX = HEXConverter.TO_HEX(fileToUninjectFileBytes);

            // Checks if the files bytes not null.
            if (fileToUnInjectHEX != null)
            {
                // Initialize variable and converts them to HEX.
                String injectionStringInHEX = HEXConverter.TO_HEX(injectionString.getBytes());

                // Checks if the injections string exists.
                if (fileToUnInjectHEX.contains(injectionStringInHEX))
                {
                    // Initialize variables.
                    String[] splitFileContentInHEX = fileToUnInjectHEX.split(injectionStringInHEX);
                    String injectedFileInHEX = splitFileContentInHEX[1].trim();
                    byte[] injectedFileBytes = HEXConverter.FROM_HEX(injectedFileInHEX);

                    // Checks if the file bytes from the injected file is not null.
                    if (injectedFileBytes != null)
                    {
                        // Checks if the decryption is enabled.
                        if (FileInjector.INJECTOR_SETTINGS.isDecryptionEnabled())
                        {
                            // Sets the key bytes for the decryption and decrypting the file bytes.
                            FileInjector.ENCRYPTION_MANAGER.setKeyBytes(FileInjector.INJECTOR_SETTINGS.getDecryptionPassword().getBytes());
                            injectedFileBytes = FileInjector.ENCRYPTION_MANAGER.decrypt(injectedFileBytes);
                        }

                        try
                        {
                            // Writes the file bytes to the file.
                            Files.write(saveFilePath, injectedFileBytes);
                        }
                        catch (IOException ioException)
                        {
                            StringUtils.sendInformation("IOException while writing the file bytes!");
                            ioException.printStackTrace();
                            new InformationAlert().showAlert(anchorPane, "Error", "Uninjection failed!\n(IOEXCEPTION WHILE WRITING THE FILE BYTES!)", "Close");
                            result.set(false);
                            return;
                        }

                        // Updates the progress bar.
                        progressBar.setProgressStep("Uninjected!");
                        progressBar.setProgress(1.0);

                        // Shows an alert that the uninjection is complete.
                        new InformationAlert().showAlert(anchorPane, "Uninjected", "File uninjected successfully!", "Thank you <3");
                        result.set(true);
                        return;
                    }
                    else
                    {
                        // Shows an alert that the uninjection failed.
                        new InformationAlert().showAlert(anchorPane, "Error", "Uninjection failed!\n(FILE BYTES FOR UNINJECTION NOT AVAILABLE!)", "Close");
                    }
                }
                else
                {
                    // Shows an alert that the uninjection failed.
                    new InformationAlert().showAlert(anchorPane, "Error", "Uninjection failed!\n(INJECTION STRING NOT FOUND!)", "Close");
                }

                return;
            }

            // Shows an alert that the uninjection failed.
            new InformationAlert().showAlert(anchorPane, "Error", "Injection failed!\n(JPG FILE BYTES = NULL)", "Close");
            result.set(false);
        });

        // Returns the result.
        return result.get();
    }

    // Method for jar uninjection.
    public static boolean jarUnInjection (AnchorPane anchorPane, byte[] fileToUninjectFileBytes, Path saveFilePath, ProgressBar progressBar)
    {
        // Initialize variable.
        AtomicBoolean result = new AtomicBoolean(false);

        // Updates the progress bar.
        progressBar.setProgressStep("Uninjecting... (JAR)");
        progressBar.setProgress(0.2);

        // Create Platform thread.
        Platform.runLater(() ->
        {
            try
            {
                // Writes the uninjection bytes to a temp jar file.
                Files.write(Constants.getFileInjectorFolder("\\temp\\temp.jar"), fileToUninjectFileBytes);
            }
            catch (IOException ioException)
            {
                StringUtils.sendInformation("IOException while writing the file bytes!");
                ioException.printStackTrace();
                result.set(false);
                return;
            }

            try
            {
                // Initialize variables.
                JarFile jarFile = new JarFile(Constants.getFileInjectorFolder("\\temp\\temp.jar").toFile());
                Enumeration<JarEntry> jarEntryEnumeration = jarFile.entries();
                FileOutputStream fileOutputStream = new FileOutputStream(saveFilePath.toFile());

                // Initialize variable.
                boolean uninjected = false;

                // Checks if the enumeration has more elements.
                while (jarEntryEnumeration.hasMoreElements())
                {
                    // Initialize variable.
                    JarEntry jarEntry = jarEntryEnumeration.nextElement();

                    // Checks if the jar entry has the file injection extension and is a directory.
                    if ((jarEntry.getName().endsWith(".fi/")) && (jarEntry.isDirectory()))
                    {
                        // Writes the file bytes to the output stream.
                        fileOutputStream.write(IOUtils.toByteArray(jarFile.getInputStream(jarEntry)));

                        // Sets the uninjected variable to true and breaks out of the enumeration loop.
                        uninjected = true;
                        break;
                    }
                }

                // Checks if the uninjected value is true.
                if (uninjected)
                {
                    // Updates the progress bar.
                    progressBar.setProgressStep("Uninjected!");
                    progressBar.setProgress(1.0);

                    // Shows that the uninjection was successful.
                    new InformationAlert().showAlert(anchorPane, "Uninjected", "File uninjected successfully!", "Thank you <3");
                    result.set(true);
                }
                else
                {
                    // Shows that the uninjection failed.
                    new InformationAlert().showAlert(anchorPane, "Error", "Uninjection failed!\n(POSSIBLE JAR ENTRY NOT FOUND OR WRITING THE FILE BYTES FAILED!)", "Close");
                    result.set(false);
                }

                // Closes the jar file.
                jarFile.close();

                // Closes the output stream.
                fileOutputStream.close();
                return;
            }
            catch (IOException ioException)
            {
                StringUtils.sendInformation("IOException while uninjecting the jar file!");
                ioException.printStackTrace();
            }

            // Shows that something went wrong.
            new InformationAlert().showAlert(anchorPane, "Error", "Injection failed!\n(JAR FILE BYTES = NULL)", "Close");
        });

        // Returns the result.
        return result.get();
    }
}