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

package de.crashedlife.fileinjector.core.ui.fx.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;
import de.crashedlife.fileinjector.FileInjector;
import de.crashedlife.fileinjector.core.injection.inject.Injection;
import de.crashedlife.fileinjector.core.injection.uninject.UnInjection;
import de.crashedlife.fileinjector.core.system.network.github.update.UpdateChecker;
import de.crashedlife.fileinjector.core.ui.fx.alerts.InformationAlert;
import de.crashedlife.fileinjector.core.ui.fx.alerts.InteractionAlert;
import de.crashedlife.fileinjector.core.ui.fx.progressbar.ProgressBar;
import de.crashedlife.fileinjector.utils.Constants;
import de.crashedlife.fileinjector.utils.StringUtils;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Paths;

public class FXMainController
{
    // Initialize variables.
    private final ProgressBar progressBar = new ProgressBar();

    @FXML private AnchorPane aboutAnchorPane;
    @FXML private AnchorPane decryptionAnchorPane;
    @FXML private AnchorPane encryptionAnchorPane;
    @FXML private AnchorPane injectionAnchorPane;
    @FXML private AnchorPane settingsAnchorPane;
    @FXML private JFXButton injectionJFXButton;
    @FXML private JFXButton unInjectionJFXButton;
    @FXML private JFXCheckBox customJFXCheckBox;
    @FXML private JFXCheckBox enableDecryptionJFXCheckBox;
    @FXML private JFXCheckBox enableEncryptionJFXCheckBox;
    @FXML private JFXCheckBox requirementsMetJFXCheckBox;
    @FXML private JFXComboBox<String> decryptionAlgorithmJFXComboBox;
    @FXML private JFXComboBox<String> decryptionModeJFXComboBox;
    @FXML private JFXComboBox<String> encryptionAlgorithmJFXComboBox;
    @FXML private JFXComboBox<String> themeJFXComboBox;
    @FXML private Label decryptionAlgorithmLabel;
    @FXML private Label decryptionModeLabel;
    @FXML private Label encryptionAlgorithmLabel;
    @FXML private Label themeLabel;
    @FXML private TextField decryptionPasswordTextField;
    @FXML private TextField encryptionPasswordTextField;
    @FXML private TextField fileToInjectTextField;
    @FXML private TextField injectInFileTextField;
    @FXML private TextField injectionStringTextField;

    private Stage stage;

    // Initialize controller.
    @FXML
    private void initialize ()
    {
        this.reInitializeCSSStyleSheets();
        this.reInitializeJFX();
        this.searchUpdate();
        FileInjector.SETTINGS_MANAGER.saveSettings();
    }

    // Reinitialize the style sheets.
    private void reInitializeCSSStyleSheets ()
    {
        // Initialize the style sheets.
        String darkStyleSheet = this.getClass().getResource("/assets/css/dark.css").toString();
        String lightStyleSheet = this.getClass().getResource("/assets/css/light.css").toString();

        // Checks which theme is selected.
        switch (FileInjector.INJECTOR_SETTINGS.getTheme())
        {
            // Adds the dark stylesheet to every AnchorPane.
            case "Dark":
                this.injectionAnchorPane.getStylesheets().remove(lightStyleSheet);

                if (!(this.injectionAnchorPane.getStyleClass().contains(darkStyleSheet)))
                    this.injectionAnchorPane.getStylesheets().add(darkStyleSheet);

                if (!(this.encryptionAnchorPane.getStyleClass().contains(darkStyleSheet)))
                    this.encryptionAnchorPane.getStylesheets().add(darkStyleSheet);

                if (!(this.decryptionAnchorPane.getStyleClass().contains(darkStyleSheet)))
                    this.decryptionAnchorPane.getStylesheets().add(darkStyleSheet);

                if (!(this.settingsAnchorPane.getStyleClass().contains(darkStyleSheet)))
                    this.settingsAnchorPane.getStylesheets().add(darkStyleSheet);

                if (!(this.aboutAnchorPane.getStyleClass().contains(darkStyleSheet)))
                    this.aboutAnchorPane.getStylesheets().add(darkStyleSheet);
                break;

            // Adds the light stylesheet to every AnchorPane.
            case "Light":
                this.injectionAnchorPane.getStylesheets().remove(darkStyleSheet);

                if (!(this.injectionAnchorPane.getStyleClass().contains(lightStyleSheet)))
                    this.injectionAnchorPane.getStylesheets().add(lightStyleSheet);

                if (!(this.encryptionAnchorPane.getStyleClass().contains(lightStyleSheet)))
                    this.encryptionAnchorPane.getStylesheets().add(lightStyleSheet);

                if (!(this.decryptionAnchorPane.getStyleClass().contains(lightStyleSheet)))
                    this.decryptionAnchorPane.getStylesheets().add(lightStyleSheet);

                if (!(this.settingsAnchorPane.getStyleClass().contains(lightStyleSheet)))
                    this.settingsAnchorPane.getStylesheets().add(lightStyleSheet);

                if (!(this.aboutAnchorPane.getStyleClass().contains(lightStyleSheet)))
                    this.aboutAnchorPane.getStylesheets().add(lightStyleSheet);
                break;
        }
    }

    // Reinitialize the JFX stuff.
    private void reInitializeJFX ()
    {
        // Create Platform thread.
        Platform.runLater(() ->
        {
            // Clears the combo boxes.
            this.encryptionAlgorithmJFXComboBox.getItems().clear();
            this.decryptionAlgorithmJFXComboBox.getItems().clear();
            this.decryptionModeJFXComboBox.getItems().clear();
            this.themeJFXComboBox.getItems().clear();


            // Adds the items to the combo boxes.
            this.encryptionAlgorithmJFXComboBox.getItems().add("AES");
            this.decryptionAlgorithmJFXComboBox.getItems().add("AES");

            this.encryptionAlgorithmJFXComboBox.getItems().add("RC2");
            this.decryptionAlgorithmJFXComboBox.getItems().add("RC2");

            this.encryptionAlgorithmJFXComboBox.getItems().add("RC4");
            this.decryptionAlgorithmJFXComboBox.getItems().add("RC4");

            this.encryptionAlgorithmJFXComboBox.getItems().add("Blowfish");
            this.decryptionAlgorithmJFXComboBox.getItems().add("Blowfish");

            this.encryptionAlgorithmJFXComboBox.getItems().add("Base64");
            this.decryptionAlgorithmJFXComboBox.getItems().add("Base64");

            this.decryptionModeJFXComboBox.getItems().add("JPG");
            this.decryptionModeJFXComboBox.getItems().add("JPEG");
            this.decryptionModeJFXComboBox.getItems().add("PNG");
            this.decryptionModeJFXComboBox.getItems().add("JAR");

            this.themeJFXComboBox.getItems().add("Dark");
            this.themeJFXComboBox.getItems().add("Light");

            // Check the algorithm and changes the labels.
            switch (FileInjector.INJECTOR_SETTINGS.getAlgorithm())
            {
                case "AES":
                    this.encryptionAlgorithmLabel.setText("Encryption Algorithm (AES) (16 Characters)");
                    this.decryptionAlgorithmLabel.setText("Decryption Algorithm (AES) (16 Characters)");
                    break;

                case "RC2":
                    this.encryptionAlgorithmLabel.setText("Encryption Algorithm (RC2) (5 - 128 Characters)");
                    this.decryptionAlgorithmLabel.setText("Decryption Algorithm (RC2) (5 - 128 Characters)");
                    break;

                case "RC4":
                    this.encryptionAlgorithmLabel.setText("Encryption Algorithm (RC4) (5 - 128 Characters)");
                    this.decryptionAlgorithmLabel.setText("Decryption Algorithm (RC4) (5 - 128 Characters)");
                    break;

                case "Blowfish":
                    this.encryptionAlgorithmLabel.setText("Encryption Algorithm (Blowfish) (1 - 56 Characters)");
                    this.decryptionAlgorithmLabel.setText("Decryption Algorithm (Blowfish) (1 - 56 Characters)");
                    break;

                case "Base64":
                    this.encryptionAlgorithmLabel.setText("Encryption Algorithm (Base64)");
                    this.decryptionAlgorithmLabel.setText("Decryption Algorithm (Base64)");
                    break;
            }

            // Sets the labels.
            this.decryptionModeLabel.setText("Decryption Mode (" + FileInjector.INJECTOR_SETTINGS.getDecryptionMode() + ")");
            this.themeLabel.setText("Theme (" + FileInjector.INJECTOR_SETTINGS.getTheme() + ")");

            // If Base64 is selected, the passwords will be overwritten.
            if (FileInjector.INJECTOR_SETTINGS.getAlgorithm().equals("Base64"))
            {
                FileInjector.INJECTOR_SETTINGS.setEncryptionPassword("BASE64");
                FileInjector.INJECTOR_SETTINGS.setDecryptionPassword("BASE64");
            }
        });
    }

    // Searches for a new update.
    private void searchUpdate ()
    {
        // Checks if an update is available.
        if (UpdateChecker.checkForUpdate())
        {
            // Prints the message to the console.
            StringUtils.sendInformation("Update available!");

            // Shows an alert.
            new InformationAlert().showAlert(this.injectionAnchorPane, "Update found", "We found a new update for this application!\nPlease download it under https://www.github.com/CrashedLife/FileInjector/releases/latest\nOr just open the Github Repository in the about tab and go to releases!", "Close");
        }
    }

    // CheckBox listener for the custom injection string check box.
    @FXML
    private void customJFXCheckBoxValueChangeListener ()
    {
        // Checks if the checkbox is selected.
        if (this.customJFXCheckBox.isSelected())
        {
            // Enables the text field.
            this.injectionStringTextField.setDisable(false);
        }
        else
        {
            // Disables the text field and resets the text in the text field.
            this.injectionStringTextField.setDisable(true);
            this.injectionStringTextField.setText("Injected with FileInjector by CrashedLife");
        }
    }

    // Enable encryption checkbox listener.
    @FXML
    private void enableEncryptionJFXCheckBoxValueChangeListener ()
    {
        // Enables encryption or disable it.
        FileInjector.INJECTOR_SETTINGS.setEnableEncryption(!(FileInjector.INJECTOR_SETTINGS.isEncryptionEnabled()));
    }

    // Enable decryption checkbox listener.
    @FXML
    private void enableDecryptionJFXCheckBoxValueChangeListener ()
    {
        // Enables decryption or disable it.
        FileInjector.INJECTOR_SETTINGS.setEnableDecryption(!(FileInjector.INJECTOR_SETTINGS.isDecryptionEnabled()));
    }

    // Encryption algorithm combobox listener.
    @FXML
    private void encryptionAlgorithmJFXComboBoxValueChangeListener ()
    {
        // Check if the combobox selection is not null.
        if (this.encryptionAlgorithmJFXComboBox.getSelectionModel().getSelectedItem() != null)
        {
            // Sets the algorithm.
            FileInjector.INJECTOR_SETTINGS.setAlgorithm(this.encryptionAlgorithmJFXComboBox.getSelectionModel().getSelectedItem());

            // If it's Base64 disable the encryption and decryption password fields.
            this.encryptionPasswordTextField.setDisable(this.encryptionAlgorithmJFXComboBox.getSelectionModel().getSelectedItem().equals("Base64"));
            this.decryptionPasswordTextField.setDisable(this.encryptionAlgorithmJFXComboBox.getSelectionModel().getSelectedItem().equals("Base64"));

            // Reinitialize JFX.
            this.reInitializeJFX();
        }
    }

    // Decryption algorithm combobox listener.
    @FXML
    private void decryptionAlgorithmJFXComboBoxValueChangeListener ()
    {
        // Check if the combobox selection is not null.
        if (this.decryptionAlgorithmJFXComboBox.getSelectionModel().getSelectedItem() != null)
        {
            // Sets the algorithm.
            FileInjector.INJECTOR_SETTINGS.setAlgorithm(this.decryptionAlgorithmJFXComboBox.getSelectionModel().getSelectedItem());

            // If it's Base64 disable the encryption and decryption password fields.
            this.encryptionPasswordTextField.setDisable(this.decryptionAlgorithmJFXComboBox.getSelectionModel().getSelectedItem().equals("Base64"));
            this.decryptionPasswordTextField.setDisable(this.decryptionAlgorithmJFXComboBox.getSelectionModel().getSelectedItem().equals("Base64"));

            // Reinitialize JFX.
            this.reInitializeJFX();
        }
    }

    // Decryption mode combobox listener.
    @FXML
    private void decryptionModeJFXComboBoxValueChangeListener ()
    {
        // Check if the combobox selection is not null.
        if (this.decryptionModeJFXComboBox.getSelectionModel().getSelectedItem() != null)
        {
            // Sets the decryption mode with the selection.
            FileInjector.INJECTOR_SETTINGS.setDecryptionMode(this.decryptionModeJFXComboBox.getSelectionModel().getSelectedItem());

            // Reinitialize JFX.
            this.reInitializeJFX();
        }
    }

    // Encryption password input listener.
    @FXML
    private void encryptionPasswordInputListener ()
    {
        // Checks if the encryption password field is not empty.
        if (!(this.encryptionPasswordTextField.getText().isEmpty()))
        {
            // Enables the enable encryption checkbox.
            this.enableEncryptionJFXCheckBox.setDisable(false);

            // Checks the selected algorithm.
            switch (FileInjector.INJECTOR_SETTINGS.getAlgorithm())
            {
                case "AES":
                    this.encryptionPasswordTextField.setText(StringUtils.getStringInRange(this.encryptionPasswordTextField.getText(), 16, 16));
                    break;

                case "RC2":
                case "RC4":
                    this.encryptionPasswordTextField.setText(StringUtils.getStringInRange(this.encryptionPasswordTextField.getText(), 5, 128));
                    break;

                case "Blowfish":
                    this.encryptionPasswordTextField.setText(StringUtils.getStringInRange(this.encryptionPasswordTextField.getText(), 1, 56));
                    break;
            }

            // Sets the encryption password.
            FileInjector.INJECTOR_SETTINGS.setEncryptionPassword(this.encryptionPasswordTextField.getText());
        }
        else
        {
            // Disables the enable encryption checkbox.
            this.enableEncryptionJFXCheckBox.setDisable(true);

            // Sets the password to null.
            FileInjector.INJECTOR_SETTINGS.setEncryptionPassword(null);
        }
    }

    // Decryption password input listener.
    @FXML
    private void decryptionPasswordInputListener ()
    {
        // Checks if the decryption password field is not empty.
        if (!(this.decryptionPasswordTextField.getText().isEmpty()))
        {
            // Enables the enable decryption checkbox.
            this.enableDecryptionJFXCheckBox.setDisable(false);

            // Checks the selected algorithm.
            switch (FileInjector.INJECTOR_SETTINGS.getAlgorithm())
            {
                case "AES":
                    this.decryptionPasswordTextField.setText(StringUtils.getStringInRange(this.decryptionPasswordTextField.getText(), 16, 16));
                    break;

                case "RC2":
                case "RC4":
                    this.decryptionPasswordTextField.setText(StringUtils.getStringInRange(this.decryptionPasswordTextField.getText(), 5, 128));
                    break;

                case "Blowfish":
                    this.decryptionPasswordTextField.setText(StringUtils.getStringInRange(this.decryptionPasswordTextField.getText(), 1, 56));
                    break;
            }

            // Sets the decryption password.
            FileInjector.INJECTOR_SETTINGS.setDecryptionPassword(this.decryptionPasswordTextField.getText());
        }
        else
        {
            // Disables the enable decryption checkbox.
            this.enableDecryptionJFXCheckBox.setDisable(true);

            // Sets the password to null.
            FileInjector.INJECTOR_SETTINGS.setDecryptionPassword(null);
        }
    }

    // Opens the backup folder.
    @FXML
    private void openBackupFolder ()
    {
        try
        {
            // Opens the folder.
            Desktop.getDesktop().open(Constants.getFileInjectorFolder("\\backups\\").toFile());
        }
        catch (IOException ioException)
        {
            StringUtils.sendInformation("IOException while opening the backup folder!");
            ioException.printStackTrace();
        }
    }

    // Theme combobox listener.
    @FXML
    private void themeJFXComboBoxValueChangeListener (ActionEvent actionEvent)
    {
        // Initialize variable.
        this.stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

        // Check if the combobox selection is not null.
        if (this.themeJFXComboBox.getSelectionModel().getSelectedItem() != null)
        {
            // Sets the theme.
            FileInjector.INJECTOR_SETTINGS.setTheme(this.themeJFXComboBox.getSelectionModel().getSelectedItem());

            // Restarts the application.
            FileInjector.restartApplication(this.stage);
        }
    }

    // Browse the file to inject.
    @FXML
    private void browseFileToInject (ActionEvent actionEvent)
    {
        // Initialize variables.
        this.stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        FileChooser fileChooser = new FileChooser();

        // Sets the file chooser title.
        fileChooser.setTitle("Browse file");

        // Checks the selected file from the file chooser.
        File file = fileChooser.showOpenDialog(stage);

        // Checks if the file is not null.
        if (file != null)
        {
            // Sets the path from the file to the file injection text field.
            this.fileToInjectTextField.setText(file.getPath().trim());

            // Enables the requirements met checkbox if the inject in file text field is already not null.
            this.requirementsMetJFXCheckBox.setSelected(!(this.injectInFileTextField.getText().isEmpty()));

            // Checks if the inject in file text field is not empty, if is it's disables the injection button.
            if (!(this.injectInFileTextField.getText().isEmpty()))
                this.injectionJFXButton.setDisable(false);
        }
    }

    // Browse the file that get inject or get uninjected.
    @FXML
    private void browseFileThatGetInjected (ActionEvent actionEvent)
    {
        // Initialize variables.
        this.stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        FileChooser fileChooser = new FileChooser();

        // Sets the file chooser title.
        fileChooser.setTitle("Browse file");

        // Sets the file chooser extensions.
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("JPG", "*.jpg"), new FileChooser.ExtensionFilter("JPEG", " *.jpeg"), new FileChooser.ExtensionFilter("PNG", "*.png"), new FileChooser.ExtensionFilter("Jar", "*.jar"));

        // Checks the selected file from the file chooser.
        File file = fileChooser.showOpenDialog(stage);

        // Checks if the file is not null.
        if (file != null)
        {
            // Sets the path from the file to the inject in file text field.
            this.injectInFileTextField.setText(file.getPath().trim());

            // Sets the requirements met checkbox to true.
            this.requirementsMetJFXCheckBox.setSelected(true);

            // Enables the uninjection button.
            this.unInjectionJFXButton.setDisable(false);

            // Checks if the inject in file text field is not empty, if is it's disables the injection button.
            if (!(this.fileToInjectTextField.getText().isEmpty()))
                this.injectionJFXButton.setDisable(false);
        }
    }

    // Starts the injection.
    @FXML
    private void startInjection ()
    {
        // Initialize variables.
        boolean createBackup = false;
        FileChooser fileChooser = new FileChooser();

        // Gets the result of the warning.
        int resultWarning = new InteractionAlert().showAlert(this.injectionAnchorPane, "Warning", "This injector can't be probably handle big size of files.\nLikewise, we recommend initiating a backup in the next steps.\nThe two files will then be saved in a backup folder.\nThis backup folder is located in the home directory of your PC.\nI am not responsible for any damage caused by using the program!", "I agree", "I disagree");

        // If the injection string text field is empty it'll fill the text field with the default text.
        if (this.injectionStringTextField.getText().isEmpty())
            this.injectionStringTextField.setText("Injected with FileInjector by CrashedLife");

        // Checks if continue selected for the warning.
        if (resultWarning == 0)
        {
            // Checks if encryption is enabled.
            if (FileInjector.INJECTOR_SETTINGS.isEncryptionEnabled())
            {
                // Gets the result of the warning.
                int resultEncryptionWarning = new InteractionAlert().showAlert(this.injectionAnchorPane, "Encryption Warning", "You try to inject a file with an encryption!\nPlease save your password for decryption by the uninjection.\nOtherwise you will lose all your data.", "Continue", "Let me out here!");

                // Checks if continue selected for the warning or it'll cancel the injection.
                if (resultEncryptionWarning != 0)
                    return;
            }

            // Gets the result of the backup creation.
            int resultBackupCreation = new InteractionAlert().showAlert(this.injectionAnchorPane, "Recommend", "Do you want to create a backup for your files?\nIt's just to stay safe, if something went wrong.\nYou can find then the backup folder in the settings tab!", "Yes, please", "No, thank you");

            // If the result is yes it'll set the createBackup value to true.
            if (resultBackupCreation == 0)
                createBackup = true;

            // Creates the progress bar for the injection AnchorPane.
            this.progressBar.createProgressBar(this.injectionAnchorPane);

            // If createBackup is true it'll create a backup.
            if (createBackup)
            {
                // Updates the progress bar.
                this.progressBar.setProgressStep("Creating backup...");

                // Gets the result of the backup creation.
                boolean resultBackup = FileInjector.BACKUP_MANAGER.createBackup(Paths.get(this.fileToInjectTextField.getText()), Paths.get(this.injectInFileTextField.getText()), this.progressBar);

                // If the result is false, it'll create a warning.
                if (!(resultBackup))
                {
                    // Gets the result of the warning.
                    int resultContinue = new InteractionAlert().showAlert(this.injectionAnchorPane, "Warning", "Something went wrong while backup the files!\nIf you continue, you can risk a data lose!\nDo you want to continue?", "Continue", "Cancel");

                    // Checks if continue selected for the warning or it'll cancel the injection.
                    if (resultContinue != 0)
                        return;
                }
            }

            // Sets the title for the file chooser.
            fileChooser.setTitle("Select output file");

            // Gets the file from the file chooser.
            File file = fileChooser.showSaveDialog(stage);

            // Checks if the file is not null.
            if (file != null)
            {
                // Updates the progress bar.
                this.progressBar.setProgressStep("Starting injection...");
                this.progressBar.setProgress(0.01);

                // Starts the injection.
                new Injection().inject(this.injectionAnchorPane, Paths.get(this.fileToInjectTextField.getText()), Paths.get(this.injectInFileTextField.getText()), file.toPath(), this.progressBar, this.injectionStringTextField.getText());
            }
            else
            {
                // Shows an alert that the injection got canceled.
                new InformationAlert().showAlert(this.injectionAnchorPane, "Injection canceled", "Injection canceled by user.", "Close");
            }
        }
        else
        {
            // Shows an alert that the injection got canceled.
            new InformationAlert().showAlert(this.injectionAnchorPane, "Injection canceled", "Injection canceled by user.", "Close");
        }
    }

    // Starts the uninjection.
    @FXML
    private void startUnInjection ()
    {
        // Initalize variable.
        FileChooser fileChooser = new FileChooser();

        // Gets the result of the warning.
        int resultWarning = new InteractionAlert().showAlert(this.injectionAnchorPane, "Warning", "This injector can't be probably handle big size of files.\nI am not responsible for any damage caused by using the program!", "I agree", "I disagree");

        // If the injection string text field is empty it'll fill the text field with the default text.
        if (this.injectionStringTextField.getText().isEmpty())
            this.injectionStringTextField.setText("Injected with FileInjector by CrashedLife");

        // Checks if continue selected for the warning.
        if (resultWarning == 0)
        {
            // Checks if the decryption is enabled.
            if (FileInjector.INJECTOR_SETTINGS.isDecryptionEnabled())
            {
                // Gets the result of the warning.
                int resultDecryptionWarning = new InteractionAlert().showAlert(this.injectionAnchorPane, "Decryption Warning", "You try to uninject a file with decryption mode on!\nIf isn't encrypted, you will get errors!", "Continue", "Let me out here!");

                // Checks if continue selected for the warning or it'll cancel the uninjection.
                if (resultDecryptionWarning != 0)
                    return;
            }

            // Creates the progress bar.
            this.progressBar.createProgressBar(this.injectionAnchorPane);

            // Sets the title for the file chooser.
            fileChooser.setTitle("Select output file");

            // Gets the file from the file chooser.
            File file = fileChooser.showSaveDialog(stage);

            // Checks if the file is not null.
            if (file != null)
            {
                // Updates the progress bar.
                this.progressBar.setProgressStep("Starting uninjection...");
                this.progressBar.setProgress(0.01);

                // Starts the uninjection.
                new UnInjection().uninject(this.injectionAnchorPane, Paths.get(this.injectInFileTextField.getText()), file.toPath(), this.progressBar, this.injectionStringTextField.getText());
            }
            else
            {
                // Shows an alert that the uninjection got canceled.
                new InformationAlert().showAlert(this.injectionAnchorPane, "Injection canceled", "Injection canceled by user.", "Close");
            }
        }
        else
        {
            // Shows an alert that the uninjection got canceled.
            new InformationAlert().showAlert(this.injectionAnchorPane, "Injection canceled", "Injection canceled by user.", "Close");
        }
    }

    // Opens the github repository.
    @FXML
    private void openGithubRepository ()
    {
        try
        {
            // Opens the URL.
            Desktop.getDesktop().browse(new URI("https://github.com/CrashedLife/FileInjector"));
        }
        catch (URISyntaxException uriSyntaxException)
        {
            StringUtils.sendInformation("URISyntaxException while opening the Github repository!");
            uriSyntaxException.printStackTrace();
        }
        catch (IOException ioException)
        {
            StringUtils.sendInformation("IOException while opening the Github repository!");
            ioException.printStackTrace();
        }
    }
}