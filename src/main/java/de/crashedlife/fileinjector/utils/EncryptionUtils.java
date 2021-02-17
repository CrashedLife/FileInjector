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

import de.crashedlife.fileinjector.FileInjector;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class EncryptionUtils
{
    // Key bytes for the encryption and decryption.
    private byte[] keyBytes = FileInjector.INJECTOR_SETTINGS.getEncryptionPassword().getBytes();

    // Sets the key bytes.
    public void setKeyBytes (byte[] value)
    {
        keyBytes = value;
    }

    // Encrypts the file bytes.
    public byte[] encryptBytes (byte[] bytes) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException
    {
        // Initialize variable.
        String algorithm = FileInjector.INJECTOR_SETTINGS.getAlgorithm().trim();

        // Checks if the algorithm is Base64, if true it will encrypt the bytes with Base64.
        if (algorithm.equals("Base64"))
            return Base64.getEncoder().encode(bytes);

        // Initialize variables.
        SecretKeySpec secretKeySpec = new SecretKeySpec(this.keyBytes, algorithm);
        Cipher cipher = Cipher.getInstance(algorithm);

        // Initialize the cipher mode.
        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);

        // Returns the encrypted file bytes.
        return cipher.doFinal(bytes);
    }

    // Decrypts the file bytes.
    public byte[] decryptBytes (byte[] bytes) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException
    {
        // Initialize variable.
        String algorithm = FileInjector.INJECTOR_SETTINGS.getAlgorithm().trim();

        // Checks if the algorithm is Base64, if true it will decrypt the bytes with Base64.
        if (algorithm.equals("Base64"))
            return Base64.getDecoder().decode(bytes);

        // Initialize variable.
        SecretKeySpec secretKeySpec = new SecretKeySpec(this.keyBytes, algorithm);
        Cipher cipher = Cipher.getInstance(algorithm);

        // Initialize the cipher mode.
        cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);

        // Returns the decrypted file bytes.
        return cipher.doFinal(bytes);
    }
}