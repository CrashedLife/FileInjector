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

package de.crashedlife.fileinjector.utils;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class JsonUtils
{
    // Fetches the JsonObject from an url.
    public static JsonObject fetchJsonObjectFromURL (String url)
    {
        try
        {
            // Initialize variables and opens the url connection.
            JsonParser jsonParser = new JsonParser();
            URLConnection urlConnection = new URL(url).openConnection();

            // Connects to the url.
            urlConnection.connect();

            // Returns the website content as JsonObject.
            return jsonParser.parse(new InputStreamReader((InputStream) urlConnection.getContent())).getAsJsonObject();
        }
        catch (MalformedURLException malformedURLException)
        {
            StringUtils.sendInformation("MalformedURLException while reading json object from " + url + "!");
            malformedURLException.printStackTrace();
        }
        catch (IOException ioException)
        {
            StringUtils.sendInformation("IOException while reading json object from " + url + "!");
            ioException.printStackTrace();
        }

        // Returns null (Object can't fetched)
        return null;
    }
}