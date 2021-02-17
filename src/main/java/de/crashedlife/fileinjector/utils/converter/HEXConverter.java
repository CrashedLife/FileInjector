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

package de.crashedlife.fileinjector.utils.converter;

import javax.xml.bind.DatatypeConverter;

public class HEXConverter
{
    // Converts byte array to a HEX String.
    public static String TO_HEX (byte[] bytes)
    {
        return DatatypeConverter.printHexBinary(bytes);
    }

    // Converts a HEX String to byte array.
    public static byte[] FROM_HEX (String string)
    {
        return DatatypeConverter.parseHexBinary(string);
    }
}