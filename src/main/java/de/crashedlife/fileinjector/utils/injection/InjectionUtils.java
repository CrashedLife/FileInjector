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

import de.crashedlife.fileinjector.core.ui.fx.alerts.InformationAlert;
import de.crashedlife.fileinjector.core.ui.fx.progressbar.ProgressBar;
import de.crashedlife.fileinjector.utils.CharUtils;
import de.crashedlife.fileinjector.utils.Constants;
import de.crashedlife.fileinjector.utils.converter.HEXConverter;
import de.crashedlife.fileinjector.utils.StringUtils;
import javafx.application.Platform;
import javafx.scene.layout.AnchorPane;
import org.apache.commons.io.IOUtils;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Enumeration;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.jar.JarOutputStream;
import java.util.jar.Manifest;

public class InjectionUtils
{
    // Method for image injection.
    public static boolean imageInjection (AnchorPane anchorPane, byte[] fileToInjectFileBytes, byte[] injectInFileFileBytes, Path saveFilePath, ProgressBar progressBar, String injectionString, String mode)
    {
        // Initialize variable.
        AtomicBoolean result = new AtomicBoolean(false);

        // Updates the progress bar.
        progressBar.setProgressStep("Injecting... (" + mode + ")");
        progressBar.setProgress(0.2);

        // Create Platform thread.
        Platform.runLater(() ->
        {
            // Initialize variables and converts them to HEX.
            String fileToInjectHEX = HEXConverter.TO_HEX(fileToInjectFileBytes);
            String injectInFileHEX = HEXConverter.TO_HEX(injectInFileFileBytes);
            String injectionStringHEX = HEXConverter.TO_HEX(injectionString.getBytes());

            // Adds the strings to one and converts them to byte[]
            String jpgFileStringInHEX = injectInFileHEX + injectionStringHEX + fileToInjectHEX;
            byte[] jpgFileBytes = HEXConverter.FROM_HEX(jpgFileStringInHEX);

            // Checks if the files bytes not null.
            if (jpgFileBytes != null)
            {
                try
                {
                    // Writes the file bytes to a file.
                    Files.write(saveFilePath, jpgFileBytes);
                }
                catch (IOException ioException)
                {
                    StringUtils.sendInformation("IOException while writing the file bytes!");
                    ioException.printStackTrace();
                    new InformationAlert().showAlert(anchorPane, "Error", "Injection failed!\n(IOEXCEPTION WHILE WRITING THE FILE BYTES!)", "Close");
                    result.set(false);
                    return;
                }

                // Updates the progress bar.
                progressBar.setProgressStep("Injected!");
                progressBar.setProgress(1.0);

                // Shows an alert that the injection is complete.
                new InformationAlert().showAlert(anchorPane, "Injected", "File injected successfully!", "Thank you <3");

                // Sets result to true.
                result.set(true);
                return;
            }

            // Shows an alert that the injection failed.
            new InformationAlert().showAlert(anchorPane, "Error", "Injection failed!\n(JPG FILE_BYTES = NULL)", "Close");
        });

        // Returns the result.
        return result.get();
    }

    // Method for jar injection.
    public static boolean jarInjection (AnchorPane anchorPane, byte[] fileToInjectFileBytes, byte[] injectInFileFileBytes, Path saveFilePath, ProgressBar progressBar)
    {
        // Initialize variable.
        AtomicBoolean result = new AtomicBoolean(false);

        // Updates the progress bar.
        progressBar.setProgressStep("Injecting... (JAR)");
        progressBar.setProgress(0.2);

        // Create Platform thread.
        Platform.runLater(() ->
        {
            // Initialize variable.
            String injectedFileName = CharUtils.getRandomString(25) + ".fi";

            try
            {
                // Writes the file bytes to a file.
                Files.write(Constants.getFileInjectorFolder("\\temp\\" + saveFilePath.getFileName()), injectInFileFileBytes);
                Files.write(Constants.getFileInjectorFolder("\\temp\\" + injectedFileName), fileToInjectFileBytes);
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
                JarFile jarFile = new JarFile(Constants.getFileInjectorFolder("\\temp\\" + saveFilePath.getFileName()).toFile());
                JarEntry hideFileJarEntry = new JarEntry(injectedFileName + "/");
                Manifest manifest = jarFile.getManifest();
                JarOutputStream jarOutputStream = new JarOutputStream(Files.newOutputStream(saveFilePath), manifest);
                Enumeration<JarEntry> jarEntryEnumeration = jarFile.entries();

                // Checks if the enumeration has more elements.
                while (jarEntryEnumeration.hasMoreElements())
                {
                    // Initialize variable.
                    JarEntry jarEntry = jarEntryEnumeration.nextElement();

                    // Checks if the JarEntry is not the Jar manifest.
                    if (!(jarEntry.getName().equals("META-INF/MANIFEST.MF")))
                    {
                        // Puts the entry to the output stream.
                        jarOutputStream.putNextEntry(jarEntry);

                        // Checks if the entry is not a directory and writes the file bytes to the jar file.
                        if (!(jarEntry.isDirectory()))
                            jarOutputStream.write(IOUtils.toByteArray(jarFile.getInputStream(jarEntry)));

                        // Closes the jar entry.
                        jarOutputStream.closeEntry();
                    }
                }

                // Puts the file for the injection to the output stream and writes the file bytes to the jar file.
                jarOutputStream.putNextEntry(hideFileJarEntry);
                jarOutputStream.write(IOUtils.toByteArray(new FileInputStream(Constants.getFileInjectorFolder("\\temp\\" + injectedFileName).toFile())));

                // Closes the injection jar entry.
                jarOutputStream.closeEntry();

                // Closes the output stream.
                jarOutputStream.close();

                // Closes the jar file.
                jarFile.close();
            }
            catch (IOException ioException)
            {
                StringUtils.sendInformation("IOException while injecting the temp file into the jar archive!");
                ioException.printStackTrace();
                result.set(false);
                return;
            }

            // Updates the progress bar.
            progressBar.setProgressStep("Try to clean up...");
            progressBar.setProgress(0.8);

            try
            {
                // Deletes the temp files.
                Files.deleteIfExists(Constants.getFileInjectorFolder("\\temp\\" + saveFilePath.getFileName()));
                Files.deleteIfExists(Constants.getFileInjectorFolder("\\temp\\" + injectedFileName));
            }
            catch (IOException ioException)
            {
                StringUtils.sendInformation("IOException while deleting the temp file!");
                ioException.printStackTrace();
            }

            // Updates the progress bar.
            progressBar.setProgressStep("Injected!");
            progressBar.setProgress(1.0);

            // Shows that the injection was successful.
            new InformationAlert().showAlert(anchorPane, "Injected", "File injected successfully!", "Thank you <3");
            result.set(true);
        });

        // Returns the result.
        return result.get();
    }
}