import java.io.IOException;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.*;

public class CabecalhoRodapeEvent extends PdfPageEventHelper {

    @Override
    public void onEndPage(PdfWriter writer, Document document) {
        PdfContentByte canvas = writer.getDirectContent();

        try {
            Image cabecalho = Image.getInstance("C:\\Ayrton\\cabecalho.png");
            cabecalho.setAbsolutePosition(0, document.top() + 0); // Ajuste a posição vertical conforme necessário
            cabecalho.scaleToFit(document.getPageSize().getWidth(), cabecalho.getScaledHeight());
            canvas.addImage(cabecalho);

            Image rodape = Image.getInstance("C:\\Ayrton\\rodape.png");
            rodape.setAbsolutePosition(0, document.bottom() + 10 - rodape.getScaledHeight());
            rodape.scaleToFit(document.getPageSize().getWidth(), rodape.getScaledHeight());
            canvas.addImage(rodape);
        } catch (DocumentException | IOException e) {
            e.printStackTrace();
        }
    }
}
