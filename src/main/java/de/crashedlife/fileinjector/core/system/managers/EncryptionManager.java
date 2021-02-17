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

import de.crashedlife.fileinjector.utils.EncryptionUtils;
import de.crashedlife.fileinjector.utils.StringUtils;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class EncryptionManager
{
    // Initialize the EncryptionUtils.
    private final EncryptionUtils encryptionUtils = new EncryptionUtils();

    // Sets the key bytes for the EncryptionUtils.
    public void setKeyBytes (byte[] keyBytes)
    {
        this.encryptionUtils.setKeyBytes(keyBytes);
    }

    // Encrypts the file bytes.
    public byte[] encrypt (byte[] bytes)
    {
        try
        {
            // Returns the encrypted file bytes.
            return this.encryptionUtils.encryptBytes(bytes);
        }
        catch (NoSuchAlgorithmException noSuchAlgorithmException)
        {
            StringUtils.sendInformation("NoSuchAlgorithmException while encrypting the bytes!");
            noSuchAlgorithmException.printStackTrace();
        }
        catch (NoSuchPaddingException noSuchPaddingException)
        {
            StringUtils.sendInformation("NoSuchPaddingException while encrypting the bytes!");
            noSuchPaddingException.printStackTrace();
        }
        catch (InvalidKeyException invalidKeyException)
        {
            StringUtils.sendInformation("InvalidKeyException while encrypting the bytes!");
            invalidKeyException.printStackTrace();
        }
        catch (IllegalBlockSizeException illegalBlockSizeException)
        {
            StringUtils.sendInformation("IllegalBlockSizeException while encrypting the bytes!");
            illegalBlockSizeException.printStackTrace();
        }
        catch (BadPaddingException badPaddingException)
        {
            StringUtils.sendInformation("BadPaddingException while encrypting the bytes!");
            badPaddingException.printStackTrace();
        }

        // Returns null (File bytes can't encrypted)
        return null;
    }

    // Decrypts the file bytes.
    public byte[] decrypt (byte[] bytes)
    {
        try
        {
            // Returns the decrypted file bytes.
            return this.encryptionUtils.decryptBytes(bytes);
        }
        catch (NoSuchAlgorithmException noSuchAlgorithmException)
        {
            StringUtils.sendInformation("NoSuchAlgorithmException while decrypting the bytes!");
            noSuchAlgorithmException.printStackTrace();
        }
        catch (NoSuchPaddingException noSuchPaddingException)
        {
            StringUtils.sendInformation("NoSuchPaddingException while decrypting the bytes!");
            noSuchPaddingException.printStackTrace();
        }
        catch (InvalidKeyException invalidKeyException)
        {
            StringUtils.sendInformation("InvalidKeyException while decrypting the bytes!");
            invalidKeyException.printStackTrace();
        }
        catch (IllegalBlockSizeException illegalBlockSizeException)
        {
            StringUtils.sendInformation("IllegalBlockSizeException while decrypting the bytes!");
            illegalBlockSizeException.printStackTrace();
        }
        catch (BadPaddingException badPaddingException)
        {
            StringUtils.sendInformation("BadPaddingException while decrypting the bytes!");
            badPaddingException.printStackTrace();
        }

        // Returns null (File bytes can't decrypted)
        return null;
    }
}