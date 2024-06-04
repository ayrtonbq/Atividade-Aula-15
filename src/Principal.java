import java.awt.Color;
import java.io.File;
import java.io.FileOutputStream;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import java.util.Random;
import java.text.NumberFormat;
import java.util.Locale;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

public class Principal {
    public static void main(String[] args) {
        Document document = new Document();
        try {
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream("C:\\Ayrton\\Atividade15.pdf"));
            CabecalhoRodapeEvent event = new CabecalhoRodapeEvent();
            writer.setPageEvent(event);

            document.open();

            BaseFont baseFont = BaseFont.createFont("C:\\Ayrton\\BRADHITC.ttf", BaseFont.WINANSI, BaseFont.EMBEDDED);
            Font font = new Font(baseFont, 16, Font.BOLD);

            Paragraph title = new Paragraph("Atividade 15", font);
            title.setAlignment(Paragraph.ALIGN_CENTER);
            document.add(title);
            
            document.add(new Paragraph(" "));

            Image img = Image.getInstance("C:\\Ayrton\\unipac logo.png");
            float newWidth = 200;  
            float newHeight = 100; 
            img.scaleToFit(newWidth, newHeight);
            
            float x = document.getPageSize().getWidth() - img.getScaledWidth() - document.rightMargin();
            float y = document.getPageSize().getHeight() - img.getScaledHeight() - document.topMargin();
            img.setAbsolutePosition(x, y);
            document.add(img);

            document.add(new Paragraph("\n\n\n\n\n\n"));

            String introText = "Documento apresenta a Atividade 15, usamos a biblioteca iText para " +
                               "criação e manipulação de documentos PDF em Java. Demonstramos como criar páginas, " +
                               "adicionar textos e imagens, e personalizar a formatação.";
            Paragraph introParagraph = new Paragraph(introText);
            introParagraph.setAlignment(Paragraph.ALIGN_JUSTIFIED);
            document.add(introParagraph);

            document.add(new Paragraph("\n"));

            Font titleFont = new Font(Font.FontFamily.HELVETICA, 16, Font.BOLD); 
            
            Paragraph title2 = new Paragraph("Tabela", titleFont);
            title2.setAlignment(Paragraph.ALIGN_CENTER);
            document.add(title2);
            
            document.add(new Paragraph("\n"));
            
            PdfPTable table = new PdfPTable(4); 
            table.setWidthPercentage(100);
            table.setSpacingBefore(6f);
            table.setSpacingAfter(6f);

            table.addCell(new PdfPCell(new Paragraph("ID")));
            table.addCell(new PdfPCell(new Paragraph("Nome")));
            table.addCell(new PdfPCell(new Paragraph("Marca")));
            table.addCell(new PdfPCell(new Paragraph("Total Vendas")));
            
            NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));
            
            String[] nomes = new String[5];
            int[] vendas = new int[5];
            Random random = new Random();
            for (int i = 1; i <= 5; i++) {
                table.addCell(String.valueOf(i));
                nomes[i - 1] = "Carro " + i;
                table.addCell(nomes[i - 1]);
                table.addCell("Marca " + i);
                vendas[i - 1] = random.nextInt(1000);
                table.addCell(currencyFormatter.format(vendas[i - 1]));
            }

            document.add(table);

            DefaultCategoryDataset dataset = new DefaultCategoryDataset();
            for (int i = 0; i < nomes.length; i++) {
                dataset.addValue(vendas[i], "Vendas", nomes[i]);
            }
            JFreeChart chart = ChartFactory.createBarChart(
                    "Vendas dos Carros",
                    "Carro",
                    "Vendas",
                    dataset,
                    PlotOrientation.VERTICAL,
                    false,
                    true,
                    false
            );

            chart.getTitle().setPaint(Color.BLACK);
            chart.getCategoryPlot().setRangeGridlinePaint(Color.BLACK);

            File chartFile = new File("C:\\Ayrton\\grafico_vendas.png");
            ChartUtils.saveChartAsPNG(chartFile, chart, 400, 200);

            Image chartImage = Image.getInstance("C:\\Ayrton\\grafico_vendas.png");
            chartImage.scaleToFit(500, 250); 
            document.add(chartImage);


            while (document.bottom() > document.bottomMargin()) {
                document.add(new Paragraph("Este é o conteúdo extra da página"));
            }

        } catch (DocumentException | java.io.IOException e) {
            e.printStackTrace();
        } finally {
            document.close();
        }
    }
}
