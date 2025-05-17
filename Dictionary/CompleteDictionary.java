
import java.io.*;
import java.util.*;

public class CompleteDictionary {
    public static void main(String[] args) {
        System.out.println("Welcome to the Complete Dictionary Program!");

        List<DictionaryEntry> dictionary = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);

        // Read from user-specified dictionary files
        readDictionaryFromUser(dictionary, scanner);

        // Sort the dictionary into lexically ascending order
        Collections.sort(dictionary);

        // Display the sorted dictionary
        System.out.println("\nSorted Slang Dictionary:");
        displayDictionary(dictionary);

        // Continuous operations until the user chooses to close the dictionary
        String answer;
        do {
            System.out.println("\nSelect an operation:");
            System.out.println("1. Add a word or phrase");
            System.out.println("2. Update the definition of a word or phrase");
            System.out.println("3. Delete a word or phrase");
            System.out.println("4. Search for a word or phrase");
            System.out.println("5. Close the dictionary");

            System.out.print("\nEnter the number of your choice: ");
            answer = scanner.nextLine().trim();

            switch (answer) {
                case "1":
                    add(dictionary, scanner);
                    break;
                case "2":
                    update(dictionary, scanner);
                    break;
                case "3":
                    delete(dictionary, scanner);
                    break;
                case "4":
                    search(dictionary, scanner);
                    break;
                case "5":
                    closeDictionary(dictionary, scanner);
                    break;
                default:
                    System.out.println("Invalid choice. Please enter a number from 1 to 5.");
            }
        } while (!answer.equals("5"));

        // Close the scanner after its usage
        scanner.close();
    }

    private static void readDictionaryFromUser(List<DictionaryEntry> dictionary, Scanner scanner) {
        String filename;
        do {
            System.out.print("\nEnter the name of the dictionary file (or type 'done' to finish): ");
            filename = scanner.nextLine().trim();
            if (!filename.equalsIgnoreCase("done")) {
                readDictionaryFile(filename, dictionary);
            }
        } while (!filename.equalsIgnoreCase("done"));
    }

    private static void readDictionaryFile(String filename, List<DictionaryEntry> dictionary) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(" - ", 2);
                if (parts.length == 2) {
                    String wordOrPhrase = parts[0].trim();
                    String definition = parts[1].trim();

                    // Check if the dictionary already contains the word or phrase
                    boolean found = false;
                    for (DictionaryEntry entry : dictionary) {
                        if (entry.getWordOrPhrase().equalsIgnoreCase(wordOrPhrase)) {
                            // Update the definition
                            entry.setDefinition(definition);
                            found = true;
                            break;
                        }
                    }

                    if (!found) {
                        // Add a new entry if the word or phrase is not already present
                        DictionaryEntry entry = new DictionaryEntry(wordOrPhrase, definition);
                        dictionary.add(entry);
                    }
                } else {
                    System.out.println("Invalid entry format in " + filename + ": " + line);
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading " + filename + ": " + e.getMessage());
        }
    }

    private static void add(List<DictionaryEntry> dictionary, Scanner scanner) {
        System.out.println("\nAdd a word and its definition to the dictionary:");

        System.out.print("Enter the word or phrase: ");
        String wordOrPhrase = scanner.nextLine();

        // Check if the word or phrase already exists in the dictionary
        for (DictionaryEntry entry : dictionary) {
            if (entry.getWordOrPhrase().equalsIgnoreCase(wordOrPhrase)) {
                System.out.println("The word or phrase '" + wordOrPhrase + "' already exists in the dictionary.");
                System.out.println("Please try again with a different word or phrase.");
                return; // Prompt the user for their next command
            }
        }

        // If the word or phrase doesn't exist, proceed with adding it
        System.out.print("Enter the definition: ");
        String definition = scanner.nextLine();

        // Add a new entry to the dictionary
        DictionaryEntry entry = new DictionaryEntry(wordOrPhrase, definition);
        dictionary.add(entry);

        System.out.println("The word or phrase '" + wordOrPhrase + "' has been successfully added to the dictionary.");
    }

    private static void update(List<DictionaryEntry> dictionary, Scanner scanner) {
        System.out.println("\nUpdate the definition for a word or phrase in the dictionary:");

        System.out.print("Enter the word or phrase to update: ");
        String wordOrPhraseToUpdate = scanner.nextLine();

        // Find the exact match for the word or phrase in the dictionary
        boolean found = false;
        for (DictionaryEntry entry : dictionary) {
            if (entry.getWordOrPhrase().equalsIgnoreCase(wordOrPhraseToUpdate)) {
                System.out.print("Enter the new definition: ");
                String newDefinition = scanner.nextLine();

                // Update the definition
                entry.setDefinition(newDefinition);
                found = true;
                System.out.println("The definition for '" + wordOrPhraseToUpdate + "' has been successfully updated.");
                break;
            }
        }

        if (!found) {
            System.out.println("The word or phrase '" + wordOrPhraseToUpdate + "' does not exist in the dictionary.");
        }
    }

    private static void delete(List<DictionaryEntry> dictionary, Scanner scanner) {
        System.out.println("\nDelete a word or phrase from the dictionary:");

        System.out.print("Enter the word or phrase to delete: ");
        String wordOrPhraseToDelete = scanner.nextLine().trim();

        // Find exact match for the word or phrase in the dictionary
        boolean foundExactMatch = false;
        boolean foundPartialMatch = false;
        List<DictionaryEntry> partialMatches = new ArrayList<>();

        for (Iterator<DictionaryEntry> iterator = dictionary.iterator(); iterator.hasNext();) {
            DictionaryEntry entry = iterator.next();
            if (entry.getWordOrPhrase().equalsIgnoreCase(wordOrPhraseToDelete)) {
                // Exact match found, remove it from the dictionary
                iterator.remove();
                foundExactMatch = true;
            } else if (entry.getWordOrPhrase().toLowerCase().contains(wordOrPhraseToDelete.toLowerCase())) {
                // Partial match found
                foundPartialMatch = true;
                partialMatches.add(entry);
            }
        }

        if (foundExactMatch) {
            System.out.println("The word or phrase '" + wordOrPhraseToDelete + "' has been successfully removed from the dictionary.");
        } else if (foundPartialMatch) {
            // Report partial matches
            System.out.println("Multiple partial matches found for the word or phrase '" + wordOrPhraseToDelete + "'.");
            System.out.println("Please refine your search. Partial matches:");
            for (DictionaryEntry entry : partialMatches) {
                System.out.println(entry.getWordOrPhrase() + ": " + entry.getDefinition());
            }
        } else {
            System.out.println("No match found for the word or phrase '" + wordOrPhraseToDelete + "'.");
        }
    }

    private static void search(List<DictionaryEntry> dictionary, Scanner scanner) {
        System.out.println("\nSearch for a word or phrase in the dictionary:");

        System.out.print("Enter the word or phrase to search: ");
        String wordOrPhraseToSearch = scanner.nextLine().trim();

        boolean foundExactMatch = false;
        boolean foundPartialMatch = false;
        List<DictionaryEntry> partialMatches = new ArrayList<>();

        for (DictionaryEntry entry : dictionary) {
            if (entry.getWordOrPhrase().equalsIgnoreCase(wordOrPhraseToSearch)) {
                // Exact match found
                foundExactMatch = true;
                System.out.println("Exact match found for the word or phrase '" + wordOrPhraseToSearch + "':");
                System.out.println(entry.getWordOrPhrase() + ": " + entry.getDefinition());
            } else if (entry.getWordOrPhrase().toLowerCase().contains(wordOrPhraseToSearch.toLowerCase())) {
                // Partial match found
                foundPartialMatch = true;
                partialMatches.add(entry);
            }
        }

        if (foundPartialMatch) {
            System.out.println("Partial matches found for the word or phrase '" + wordOrPhraseToSearch + "':");
            for (DictionaryEntry entry : partialMatches) {
                System.out.println(entry.getWordOrPhrase() + ": " + entry.getDefinition());
            }
        }

        if (!foundExactMatch && !foundPartialMatch) {
            System.out.println("No match found for the word or phrase '" + wordOrPhraseToSearch + "'.");
        }
    }

    private static void closeDictionary(List<DictionaryEntry> dictionary, Scanner scanner) {
        try {
            // Prompt the user for the name of an output file
            System.out.print("\nEnter the name of the output file to save the dictionary: ");
            String outputFile = scanner.nextLine();

            // Open the output file for writing
            PrintWriter writer = new PrintWriter(outputFile);

            // Write the current contents of the dictionary (sorted in lexically ascending order) to the output file
            for (DictionaryEntry entry : dictionary) {
                writer.println(entry.getWordOrPhrase() + " - " + entry.getDefinition());
            }

            // Close the output file
            writer.close();

            // Print a message to the user indicating that the dictionary has been closed
            System.out.println("The dictionary has been successfully closed and saved to '" + outputFile + "'. Exiting the program.");

            // Exit from the program
            System.exit(0);
        } catch (FileNotFoundException e) {
            System.out.println("Error: Unable to open the output file. Please check the file name and try again.");
        }
    }

    private static void displayDictionary(List<DictionaryEntry> dictionary) {
        for (DictionaryEntry entry : dictionary) {
            System.out.println(entry.getWordOrPhrase() + ": " + entry.getDefinition());
        }
    }
}
