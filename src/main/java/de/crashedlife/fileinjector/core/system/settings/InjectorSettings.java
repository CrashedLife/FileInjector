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

package de.crashedlife.fileinjector.core.system.settings;

public class InjectorSettings
{
    // Initialize the settings variables.
    private String theme = "Dark";
    private String algorithm = "AES";
    private String encryptionPassword = "123456789012345";
    private String decryptionPassword = "123456789012345";
    private String decryptionMode = "JPG";
    private boolean enableEncryption = false;
    private boolean enableDecryption = false;

    // Sets the settings variables.
    public void setTheme (String value)
    {
        this.theme = value;
    }

    public void setAlgorithm (String value)
    {
        this.algorithm = value;
    }

    public void setEncryptionPassword (String value)
    {
        this.encryptionPassword = value;
    }

    public void setDecryptionPassword (String value)
    {
        this.decryptionPassword = value;
    }

    public void setDecryptionMode (String value)
    {
        this.decryptionMode = value;
    }

    public void setEnableEncryption (boolean value)
    {
        this.enableEncryption = value;
    }

    public void setEnableDecryption (boolean value)
    {
        this.enableDecryption = value;
    }

    // Returns the settings variables.
    public String getTheme ()
    {
        return this.theme;
    }

    public String getAlgorithm ()
    {
        return this.algorithm;
    }

    public String getEncryptionPassword ()
    {
        return this.encryptionPassword;
    }

    public String getDecryptionPassword ()
    {
        return this.decryptionPassword;
    }

    public String getDecryptionMode ()
    {
        return this.decryptionMode;
    }

    public boolean isEncryptionEnabled ()
    {
        return this.enableEncryption;
    }

    public boolean isDecryptionEnabled ()
    {
        return this.enableDecryption;
    }
}