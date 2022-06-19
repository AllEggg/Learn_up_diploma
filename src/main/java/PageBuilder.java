import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfPage;
import com.itextpdf.kernel.pdf.action.PdfAction;
import com.itextpdf.kernel.pdf.annot.PdfLinkAnnotation;
import com.itextpdf.layout.Canvas;
import com.itextpdf.layout.element.Link;
import com.itextpdf.layout.element.Paragraph;

import java.util.Set;

//    для того, что бы Main не был таким огромным, создание страниц документа вынес в отдельный класс

public class PageBuilder {

    public Set<Suggest> betaPage(PdfPage page, Set<Suggest> suggestList) {
        var rect = new Rectangle(page.getPageSize().moveRight(10).moveDown(10));
        Canvas canvas = new Canvas(page, rect);
        Paragraph paragraph = new Paragraph("Suggestions:\n");
        PdfLinkAnnotation annotation = new PdfLinkAnnotation(rect);
        paragraph.setFontSize(25);

        for (var suggest : suggestList) {
            PdfAction action = PdfAction.createURI(suggest.getUrl());
            annotation.setAction(action);
            Link link = new Link(suggest.getTitle(), annotation);
            paragraph.add(link.setUnderline());
            paragraph.add("\n");
        }
        canvas.add(paragraph);
        canvas.close();

        return suggestList;
    }
}
