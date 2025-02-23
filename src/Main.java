package src;
import src.utility.mainSolver;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.stage.FileChooser;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.text.TextFlow;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.paint.Color;
import javafx.scene.layout.Region;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import java.nio.file.Files;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Main extends Application {
    
    private TextArea inputArea;
    private TextFlow outputArea;
    private String fileName;
    private Label saveSuccessful;

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("IQ Puzzler Pro Solver");

        Button loadFileButton = new Button("Pilih File");
        Button startProcessButton = new Button("Mulai proses");
        Button saveButton = new Button("Simpan solusi");
        
        Label outputLabel = new Label("Solusi Hasil Brute Force:");
        outputLabel.setMinHeight(30);
        outputLabel.setMaxHeight(30);
        
        Label resultLabel = new Label("");
        resultLabel.setMinHeight(50);
        resultLabel.setMaxHeight(50);
        
        Label saveLabel = new Label("Apakah Anda ingin menyimpan solusi?");
        saveSuccessful = new Label("");

        saveLabel.setVisible(false);
        saveButton.setVisible(false);
        saveSuccessful.setVisible(false);

        loadFileButton.setOnAction(e -> {
            outputLabel.setText("Solusi Hasil Brute Force:");
            outputArea.getChildren().clear();
            resultLabel.setText("");
            saveLabel.setVisible(false);
            saveButton.setVisible(false);
            saveSuccessful.setVisible(false);
            openFileChooser(primaryStage);
        });
        loadFileButton.setMinHeight(30);
        loadFileButton.setMaxHeight(30);
        
        inputArea = new TextArea();
        inputArea.setWrapText(false);
        inputArea.setPrefSize(150, 400);

        outputArea = new TextFlow();
        outputArea.setMaxWidth(Double.MAX_VALUE);
        outputArea.setMinWidth(Region.USE_PREF_SIZE);        

        ScrollPane outputScrollPane = new ScrollPane(outputArea);
        outputScrollPane.setFitToWidth(false);
        outputScrollPane.setFitToHeight(false);
        outputScrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        outputScrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);

        VBox outputBox;

        startProcessButton.setOnAction(e -> {
            String inputText = inputArea.getText();
            saveLabel.setVisible(false);
            saveButton.setVisible(false);
            saveSuccessful.setVisible(false);

            if (inputText.trim().isEmpty()) {
                outputLabel.setText("Input tidak dapat dikosongkan.");
                outputArea.getChildren().clear();
                resultLabel.setText("");
            
            } else {
                outputArea.getChildren().clear();
                resultLabel.setText("");

                mainSolver solver = new mainSolver();
                solver.solve(inputText);
                
                String introText = solver.MessageIntro.toString();
                outputLabel.setText(introText);

                char[] entry = solver.MessageEntry.toString().toCharArray();
                for (char c : entry) {
                    Text text = new Text(String.valueOf(c));

                    if (entry[entry.length - 1] == '.') {
                        text.setFill(getColorForChar(c, false));
                        
                    } else {
                        text.setFill(getColorForChar(c, true));
                        text.setFont(Font.font("Courier New", FontWeight.BOLD, 14));
                        
                    }
                    outputArea.getChildren().add(text);
                }

                resultLabel.setText(solver.MessageOutro.toString());

                if (introText.equals("Puzzle dapat diselesaikan!")) {
                    saveLabel.setVisible(true);
                    saveButton.setVisible(true);

                    saveButton.setOnAction(ev -> {saveTextToFile(solver.MessageEntry.toString()); saveSuccessful.setVisible(true);});
                }

            }
        });
        startProcessButton.setMinHeight(30);
        startProcessButton.setMaxHeight(30);

        VBox inputBox = new VBox(10, loadFileButton, inputArea, startProcessButton);
        inputBox.setPadding(new Insets(20));

        outputBox = new VBox(10, outputLabel, outputScrollPane, resultLabel, saveLabel, saveButton, saveSuccessful);
        outputBox.setPadding(new Insets(20));
        inputBox.setAlignment(Pos.TOP_LEFT);

        HBox mainLayout = new HBox(5, inputBox, outputBox);
        mainLayout.setAlignment(Pos.CENTER);
        outputBox.setAlignment(Pos.TOP_LEFT);
        outputBox.setPrefWidth(350);

        HBox.setHgrow(inputBox, Priority.ALWAYS);
        HBox.setHgrow(outputBox, Priority.ALWAYS);

        Scene scene = new Scene(mainLayout, 700, 500);

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void openFileChooser(Stage stage) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text Files", "*.txt"));
        File selectedFile = fileChooser.showOpenDialog(stage);

        if (selectedFile != null) {
            try {
                String content = Files.readString(selectedFile.toPath());
                inputArea.setText(content);

                fileName = selectedFile.getName().replaceFirst("[.][^.]+$", "");
            
            } catch (IOException e) {
                inputArea.setText("Terdapat kesalahan dalam membaca file .txt");
            }
        }
    }

    private Color getColorForChar(char c, boolean result) {
        if (result) {
            switch (c) {
                case 'A': return Color.BLACK;
                case 'B': return Color.RED;
                case 'C': return Color.GREEN;
                case 'D': return Color.YELLOW;
                case 'E': return Color.BLUE;
                case 'F': return Color.MAGENTA;
                case 'G': return Color.CYAN;
                case 'H': return Color.ORANGE;
                case 'I': return Color.PINK;
                case 'J': return Color.BROWN;
                case 'K': return Color.GRAY;
                case 'L': return Color.DARKRED;
                case 'M': return Color.DARKGREEN;
                case 'N': return Color.DARKBLUE;
                case 'O': return Color.DARKCYAN;
                case 'P': return Color.DARKMAGENTA;
                case 'Q': return Color.DARKORANGE;
                case 'R': return Color.DARKSLATEBLUE;
                case 'S': return Color.GOLD;
                case 'T': return Color.LIME;
                case 'U': return Color.MAROON;
                case 'V': return Color.NAVY;
                case 'W': return Color.OLIVE;
                case 'X': return Color.PURPLE;
                case 'Y': return Color.SILVER;
                case 'Z': return Color.TEAL;

                default: return Color.BLACK;
            }
        } else {
            return Color.BLACK;
        }
    }

    private void saveTextToFile(String content) {

        File saveDirectory = new File(new File(System.getProperty("user.dir")), "test");
        if (!saveDirectory.exists()) {
            saveDirectory.mkdirs();
        }

        String defaultFileName = "solusi_" + fileName + ".txt";
        File newFile = new File(saveDirectory, defaultFileName);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(newFile))) {
            String inputText = inputArea.getText();

            writer.write("IQ Puzzler Pro dengan konfigurasi berikut:\n");
            writer.write(inputText + "\n\n");
            writer.write("Memiliki penyelesaian sebagai berikut:\n");
            writer.write(content);
            saveSuccessful.setText("File berhasil disimpan dalam folder test!\n" + "dengan nama file: " + defaultFileName);
            
        } catch (IOException e) {
            saveSuccessful.setText("Terjadi kesalahan dalam menyimpan file.");
        }
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}