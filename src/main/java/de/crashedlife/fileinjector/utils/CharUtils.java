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

import java.security.SecureRandom;

public class CharUtils
{
    private static final SecureRandom secureRandom = new SecureRandom();

    // Creates and returns a random string.
    public static String getRandomString (int length)
    {
        // Initialize variables.
        char[] chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789".toCharArray();
        char[] string = new char[length];

        // Loops the process for every letter.
        for (int charPosition = 0; charPosition < length; charPosition++)
        {
            string[charPosition] = chars[secureRandom.nextInt(chars.length)];
        }

        // Returns the string.
        return new String(string);
    }
}