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

import java.nio.file.Path;
import java.nio.file.Paths;

public class Constants
{
    // Version string.
    public static final String VERSION = "1.0.0";

    // Build string.
    public static final String BUILD = "1";

    // FileInjector folder path.
    public static Path getFileInjectorFolder (String path)
    {
        if (path != null)
        {
            return StringUtils.getOSPath(Paths.get(System.getProperty("user.home") + "\\CrashedLife\\FileInjector\\" + path));
        }
        else
        {
            return StringUtils.getOSPath(Paths.get(System.getProperty("user.home") + "\\CrashedLife\\FileInjector\\"));
        }
    }
}