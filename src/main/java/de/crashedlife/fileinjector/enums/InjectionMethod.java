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

package de.crashedlife.fileinjector.enums;

import java.nio.file.Path;

public enum InjectionMethod
{
    // Current injection methods.
    JPG, PNG, JAR, UNRESOLVED;

    // Helper class.
    public static class Helper
    {
        // Resolves the method over the file path.
        public static InjectionMethod resolveMethodViaFilePath (Path filePath)
        {
            // Initialize variables.
            String fileName = filePath.getFileName().toString().trim();
            String fileType = fileName.split("\\.")[1].trim();

            // Checks the file types.
            switch (fileType)
            {
                case "jpg":
                case "jpeg":
                    return JPG;

                case "png":
                    return PNG;

                case "jar":
                    return JAR;

                default:
                    return UNRESOLVED;
            }
        }

        // Resolves the method over the file extension.
        public static InjectionMethod resolveMethodViaFileExtensionName (String fileExtensionName)
        {
            // Checks the file types.
            switch (fileExtensionName)
            {
                case "JPG":
                case "JPEG":
                    return JPG;

                case "PNG":
                    return PNG;

                case "JAR":
                    return JAR;

                default:
                    return UNRESOLVED;
            }
        }
    }
}