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

package de.crashedlife.fileinjector.core.system.network.github.update;

import com.google.gson.JsonObject;
import de.crashedlife.fileinjector.utils.Constants;
import de.crashedlife.fileinjector.utils.JsonUtils;

public class UpdateChecker
{
    // Method to search for a new update.
    public static boolean checkForUpdate ()
    {
        // Initialize variable.
        JsonObject jsonObject = JsonUtils.fetchJsonObjectFromURL("https://api.github.com/repos/CrashedLife/FileInjector/releases/latest");

        // Checks if the JsonObject is not null and has the key "tag_name"
        // Returns true if the latest version is not equals to the application version.
        if ((jsonObject != null) && (jsonObject.has("tag_name")))
            return !(jsonObject.get("tag_name").getAsString().equals(Constants.VERSION));

        // Returns false (Update not available)
        return false;
    }
}