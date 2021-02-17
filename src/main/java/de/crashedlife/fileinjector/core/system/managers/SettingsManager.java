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

import com.google.gson.JsonObject;
import de.crashedlife.fileinjector.FileInjector;
import de.crashedlife.fileinjector.utils.Constants;

public class SettingsManager
{
    // Method to load the settings.
    public void loadSettings ()
    {
        // Initialize variable.
        JsonObject jsonObject = FileInjector.FILE_MANAGER.readFile(Constants.getFileInjectorFolder("\\settings\\injector.json"));

        // Checks if the JsonObject is not null.
        if (jsonObject != null)
        {
            // Check if the JsonObject has some specific values and sets them.
            if (jsonObject.has("Theme"))
                FileInjector.INJECTOR_SETTINGS.setTheme(jsonObject.get("Theme").getAsString());

            if (jsonObject.has("Algorithm"))
                FileInjector.INJECTOR_SETTINGS.setAlgorithm(jsonObject.get("Algorithm").getAsString());

            if (jsonObject.has("Decryption Mode"))
                FileInjector.INJECTOR_SETTINGS.setDecryptionMode(jsonObject.get("Decryption Mode").getAsString());

        }
        else
        {
            // Loop
            // Saves the settings and restart the method.
            this.saveSettings();
            this.loadSettings();
        }
    }

    // Method to save the settings.
    public void saveSettings ()
    {
        // Initialize variable.
        JsonObject jsonObject = new JsonObject();

        // Adds the properties with the values from the FileInjector settings.
        jsonObject.addProperty("Theme", FileInjector.INJECTOR_SETTINGS.getTheme());
        jsonObject.addProperty("Algorithm", FileInjector.INJECTOR_SETTINGS.getAlgorithm());
        jsonObject.addProperty("Decryption Mode", FileInjector.INJECTOR_SETTINGS.getDecryptionMode());

        // Saves the file.
        FileInjector.FILE_MANAGER.saveFile(Constants.getFileInjectorFolder("\\settings\\injector.json"), jsonObject);
    }
}