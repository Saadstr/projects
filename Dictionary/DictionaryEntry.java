import java.util.Comparator;

public class DictionaryEntry implements Comparable<DictionaryEntry> {

    private String wordOrPhrase;
    private String definition;

    public DictionaryEntry() {}

    public DictionaryEntry(String wordOrPhrase, String definition) {
        this.wordOrPhrase = wordOrPhrase;
        this.definition = definition;
    }

    public String getWordOrPhrase() {
        return wordOrPhrase;
    }

    public void setWordOrPhrase(String wordOrPhrase) {
        this.wordOrPhrase = wordOrPhrase;
    }

    public String getDefinition() {
        return definition;
    }

    public void setDefinition(String definition) {
        this.definition = definition;
    }

    public String[] getData() {
        return new String[]{wordOrPhrase, definition};
    }

    public void setData(String[] data) {
        if (data != null && data.length == 2) {
            wordOrPhrase = data[0];
            definition = data[1];
        }
    }

    @Override
    public int compareTo(DictionaryEntry other) {
        return this.wordOrPhrase.compareToIgnoreCase(other.wordOrPhrase);
    }

    public static Comparator<DictionaryEntry> LexicalComparator = new Comparator<DictionaryEntry>() {
        @Override
        public int compare(DictionaryEntry entry1, DictionaryEntry entry2) {
            return entry1.wordOrPhrase.compareToIgnoreCase(entry2.wordOrPhrase);
        }
    };
}
