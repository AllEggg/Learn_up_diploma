import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.canvas.parser.PdfTextExtractor;

import java.io.File;
import java.util.*;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) throws Exception {
        long start = System.currentTimeMillis();

        // создаём конфиг

        LinksSuggester linksSuggester = new LinksSuggester(new File("data/config"));

        //        перебираем пдфки в data/pdfs

        var dir = new File("data/pdfs");

        for (var fileIn : Objects.requireNonNull(dir.listFiles())) {

            // для каждой пдфки создаём новую в data/converted

            var fileOut = new File("data/converted/", fileIn.getName());
            var doc = new PdfDocument(new PdfReader(fileIn), new PdfWriter(fileOut));
            var convertedDocumentSize = doc.getNumberOfPages();
            List<Suggest> usedSuggest = new ArrayList<>();

            // перебираем страницы pdf

            for (int i = 1; i < convertedDocumentSize; i++) {

                var page = doc.getPage(i);
                var text = PdfTextExtractor.getTextFromPage(page).toLowerCase();
                var pageBuilder = new PageBuilder();

                // вставляем туда рекомендуемые ссылки из конфига

                List<Suggest> suggestFromText = new ArrayList<>(linksSuggester.suggest(text));
                Set<Suggest> unusedSuggest = suggestFromText
                        .stream()
                        .filter(p -> !usedSuggest.contains(p))
                        .collect(Collectors.toSet());

                if (!unusedSuggest.isEmpty()) {
                    var suggestPage = doc.addNewPage(i + 1);
                    Set<Suggest> suggestOnPage = pageBuilder.betaPage(suggestPage, unusedSuggest);
                    i++;
                    convertedDocumentSize++;
                    usedSuggest.addAll(suggestOnPage);
                }
            }
            doc.close();
        }
        long finish = System.currentTimeMillis();
        System.out.printf("Файлы сконвертированы. Время работы программы = %s мс.", (finish - start));
    }
}


