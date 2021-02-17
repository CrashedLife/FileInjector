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

package de.crashedlife.fileinjector.core.system.managers;

import de.crashedlife.fileinjector.core.ui.fx.progressbar.ProgressBar;
import de.crashedlife.fileinjector.utils.Constants;
import de.crashedlife.fileinjector.utils.StringUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class BackupManager
{
    // Method for creating the backup.
    public boolean createBackup (Path filePath, Path filePath2, ProgressBar progressBar)
    {
        try
        {
            // Updates the progress bar.
            progressBar.setProgressStep("Creating backup... (Creating file bytes...)");
            progressBar.setProgress(0.01);

            // Initialize variables and reading all file bytes from the files.
            byte[] fileBytes1 = Files.readAllBytes(filePath);
            byte[] fileBytes2 = Files.readAllBytes(filePath2);

            // Updates the progress bar.
            progressBar.setProgressStep("Creating backup... (Creating directories...)");
            progressBar.setProgress(0.1);

            // Initialize the backup folder.
            Path currentTime = Constants.getFileInjectorFolder("\\backups\\" + StringUtils.getDateAndTime("BACKUP") + "\\");

            // Creates the backup folder.
            Files.createDirectories(currentTime);

            // Updates the progress bar.
            progressBar.setProgressStep("Creating backup... (Backup files... (1/2))");
            progressBar.setProgress(0.2);

            // Writes the file bytes from the first file into the backup folder.
            Files.write(StringUtils.getOSPath(Paths.get(currentTime + "\\" + filePath.getFileName())), fileBytes1);

            // Updates the progress bar.
            progressBar.setProgressStep("Creating backup... (Backup files... (2/2))");
            progressBar.setProgress(0.5);

            // Writes the file bytes from the second file into the backup folder.
            Files.write(StringUtils.getOSPath(Paths.get(currentTime + "\\" + filePath2.getFileName())), fileBytes2);

            // Updates the progress bar.
            progressBar.setProgressStep("Backup created!");
            progressBar.setProgress(1.0);

            // Returns true (Everything works fine).
            return true;
        }
        catch (IOException ioException)
        {
            StringUtils.sendInformation("IOException while creating the backup!");
            ioException.printStackTrace();
        }

        // Returns false (Something went wrong).
        return false;
    }
}