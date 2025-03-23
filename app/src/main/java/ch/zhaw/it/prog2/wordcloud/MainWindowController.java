package ch.zhaw.it.prog2.wordcloud;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class MainWindowController implements IsObserver {

    @FXML
    private Label labelTitle;

    @FXML
    private TextField textEntry;

    @FXML
    private TextArea textHistory;

    // Modell über den Decorator (siehe WordModelDecorator :contentReference[oaicite:0]{index=0}&#8203;:contentReference[oaicite:1]{index=1} und WordModel :contentReference[oaicite:2]{index=2}&#8203;:contentReference[oaicite:3]{index=3})
    private WordModelDecorator wordModelDecorator;

    public void initialize() {
        // Bindung der Label-TextProperty an das Eingabefeld (bestehende Funktionalität aus :contentReference[oaicite:4]{index=4}&#8203;:contentReference[oaicite:5]{index=5})
        labelTitle.textProperty().bind(textEntry.textProperty());

        // Erzeugen des Modells und Registrierung als Observer
        wordModelDecorator = new WordModelDecorator(new WordModel());
        wordModelDecorator.addListener(this);
    }

    @FXML
    void addText(ActionEvent event) {
        String input = textEntry.getText().trim();
        if (!input.isEmpty()) {
            // Zerlegen des eingegebenen Textes in einzelne Wörter (getrennt durch Leerzeichen)
            String[] words = input.split("\\s+");
            for (String word : words) {
                // Jedes Wort in Kleinbuchstaben an das Modell übergeben
                wordModelDecorator.addWord(word.toLowerCase());
            }
            // Eingabefeld leeren
            textEntry.clear();
        }
    }

    @FXML
    void clearTextEntry(ActionEvent event) {
        textEntry.clear();
    }

    // Observer-Methode: Wird vom Modell aufgerufen, wenn sich die Daten ändern
    @Override
    public void update() {
        // Aktualisieren der History-Ansicht anhand des aktuellen Modellinhalts
        textHistory.setText(wordModelDecorator.toString());
    }
}