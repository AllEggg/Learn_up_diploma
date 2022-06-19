import java.io.*;
import java.util.*;

public class LinksSuggester {

    private final File file;

    public LinksSuggester(File file) throws WrongLinksFormatException {
        this.file = file;
    }

    public List<Suggest> getAllSuggest(File file) throws FileNotFoundException {
        var scanner = new Scanner(file);
        var divider = "\\t";
        List<Suggest> suggestList = new ArrayList<>();
        while (scanner.hasNextLine()) {
            var line = scanner.nextLine();
            String[] divided = line.split(divider);
            if (divided.length != 3) {
                throw new WrongLinksFormatException("Строка конфигурации ссылок состоит не из 3-х частей." +
                        " Пожалуйста, проверьте правильность заполнения");
            }
            var suggest = new Suggest(divided[0], divided[1], divided[2]);
            suggestList.add(suggest);
        }
        return suggestList;
    }

    public List<Suggest> suggest(String text) throws IOException {

        List<Suggest> allSuggest = getAllSuggest(file);
        List<Suggest> resultSuggest = new ArrayList<>();

        for (var suggest : allSuggest)
            if (text.contains(suggest.getKeyWord())) {
                resultSuggest.add(suggest);
            }
        return resultSuggest;
    }
}
//         в задании не было информации о том, требуется ли точное совпадение,
//         поэтому на всякий случай написал вариант и для этой логики

//        String[] newText = text.replaceAll("[^\\da-zA-Zа-яёА-ЯЁ ]", " ").split(" ");
//        Set<String> textByWordFormat = Arrays.stream(newText)
//                .map(String::new)
//                .filter(p -> !p.isEmpty())
//                .collect(Collectors.toSet());
//                  for (var suggest:allSuggest)
//                        for (var word:textByWordFormat) {
//                            if (suggest.getKeyWord().equals(word)) {
//                                resultSuggest.add(suggest);
//                            }
//        return resultSuggest;





