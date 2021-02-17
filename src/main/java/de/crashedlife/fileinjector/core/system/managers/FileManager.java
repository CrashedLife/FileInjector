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

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import de.crashedlife.fileinjector.utils.Constants;
import de.crashedlife.fileinjector.utils.StringUtils;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileManager
{
    // Public constructor.
    // Creates the project directories, if it's not already exists.
    public FileManager ()
    {
        if (!(Files.exists(Constants.getFileInjectorFolder(null))))
        {
            try
            {
                Files.createDirectories(Constants.getFileInjectorFolder(null));
            }
            catch (IOException ioException)
            {
                StringUtils.sendInformation("IOException while creating directories!");
                ioException.printStackTrace();
            }
        }

        if (!(Files.exists(Constants.getFileInjectorFolder("\\backups\\"))))
        {
            try
            {
                Files.createDirectories(Constants.getFileInjectorFolder("\\backups\\"));
            }
            catch (IOException ioException)
            {
                StringUtils.sendInformation("IOException while creating directories!");
                ioException.printStackTrace();
            }
        }

        if (!(Files.exists(Constants.getFileInjectorFolder("\\temp\\"))))
        {
            try
            {
                Files.createDirectories(Constants.getFileInjectorFolder("\\temp\\"));
            }
            catch (IOException ioException)
            {
                StringUtils.sendInformation("IOException while creating directories!");
                ioException.printStackTrace();
            }
        }

        if (!(Files.exists(Constants.getFileInjectorFolder("\\settings\\"))))
        {
            try
            {
                Files.createDirectories(Constants.getFileInjectorFolder("\\settings\\"));
            }
            catch (IOException ioException)
            {
                StringUtils.sendInformation("IOException while creating directories!");
                ioException.printStackTrace();
            }
        }
    }

    // Reading the JsonObject.
    public JsonObject readFile (Path filePath)
    {
        // Checks if a file exists.
        if (Files.exists(filePath))
        {
            // Initialize variables.
            GsonBuilder gsonBuilder = new GsonBuilder();
            Gson gson = gsonBuilder.setPrettyPrinting().create();

            // Try to create the file reader.
            try (FileReader fileReader = new FileReader(filePath.toFile()))
            {
                // Return the JsonObject.
                return gson.fromJson(fileReader, JsonObject.class);
            }
            catch (IOException ioException)
            {
                StringUtils.sendInformation("IOException while loading the file!");
                ioException.printStackTrace();
            }
        }
        return null;
    }

    // Try to save the JsonObject.
    public void saveFile (Path filePath, JsonObject jsonObject)
    {
        // Initialize variables.
        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.setPrettyPrinting().create();

        // Try to create the file writer.
        try (FileWriter fileWriter = new FileWriter(filePath.toFile()))
        {
            // Writes the JsonObject to the file.
            gson.toJson(jsonObject, fileWriter);
        }
        catch (IOException ioException)
        {
            StringUtils.sendInformation("IOException while saving the file!");
            ioException.printStackTrace();
        }
    }
}