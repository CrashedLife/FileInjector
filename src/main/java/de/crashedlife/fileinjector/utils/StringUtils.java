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

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class StringUtils
{
    // Sends an empty line to the console.
    public static void sendEmptyLine ()
    {
        System.out.println(" ");
    }

    // Sends the output.
    public static void sendInformation (String message)
    {
        // Prints the output.
        System.out.println("[" + getDateAndTime("CONSOLE") + "] | [FileInjector]: " + message);
    }

    // Method to get the date and time in a string.
    public static String getDateAndTime (String mode)
    {
        // Initialize variables.
        Date date = new Date();
        DateFormat dateFormat;

        // Checks if the mode is for the console or not.
        if (mode.equals("CONSOLE"))
        {
            dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        }
        else
        {
            dateFormat = new SimpleDateFormat("dd-MM-yyyy HH-mm-ss");
        }

        // Returns the time date format to a string.
        return dateFormat.format(date);
    }

    // Returns the string between a specific range.
    public static String getStringInRange (String text, int min, int max)
    {
        // Checks if the text is shorter than min and longer than max.
        if ((text.length() < min) || (text.length() > max))
        {
            // Initialize variable.
            StringBuilder textBuilder = new StringBuilder(text);

            // Checks the length from the text.
            for (int length = 0; length < max; length ++)
            {
                // Fill the missed chars with "#".
                if (textBuilder.length() < min)
                    textBuilder.append("#");

                // Removes the unnecessary chars.
                if (textBuilder.length() > max)
                {
                    text = text.substring(0, max);
                    length = max;
                }
            }

            // Replace the TextBuilder string with the original string.
            text = textBuilder.toString();
        }

        // Returns the text.
        return text;
    }

    // Replaces "\\" with the system file separator.
    public static Path getOSPath (Path path)
    {
        return Paths.get(path.toString().trim().replace("\\", File.separator));
    }
}