package com.ipi.javaio.export;

import com.ipi.javaio.model.SalarieAideADomicile;
import com.ipi.javaio.model.SalarieAideADomicileMois;
import com.ipi.javaio.repository.SalarieAideADomicileMoisRepository;
import com.ipi.javaio.service.SalarieAideADomicileService;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.IBlockElement;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.UnitValue;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;


@Service
public class SalarieAideADomicileMoisExportPdfService {

    DateTimeFormatter frenchDateTimeFormatter = DateTimeFormatter.ofPattern("'MMM' yyyy");

    @Autowired
    private final SalarieAideADomicileMoisRepository salarieAideADomicileMoisRepository;
    @Autowired
    private final SalarieAideADomicileService salarieAideADomicileService;

    public SalarieAideADomicileMoisExportPdfService(
            SalarieAideADomicileMoisRepository salarieAideADomicileMoisRepository,
            SalarieAideADomicileService salarieAideADomicileService) {
        this.salarieAideADomicileMoisRepository = salarieAideADomicileMoisRepository;
        this.salarieAideADomicileService = salarieAideADomicileService;
    }

    public void export(OutputStream outputStream) throws IOException {
        Iterable<SalarieAideADomicileMois> allMois = salarieAideADomicileMoisRepository.findAll();
        exportBase(outputStream, allMois);
    }

    public void export(OutputStream outputStream, Long salarieId/*, LocalDate premierDuMois*/) throws IOException {
        SalarieAideADomicile salarieAideADomicile = salarieAideADomicileService.getSalarie(salarieId);
        //SalarieAideADomicileMois mois = salarieAideADomicileMoisRepository
        //        .findBySalarieAideADomicileAndMois(salarieAideADomicile, premierDuMois).get();
        List<SalarieAideADomicileMois> allMois = salarieAideADomicileMoisRepository
                .findBySalarieAideADomicile(salarieAideADomicile);
        exportBase(outputStream, allMois, salarieId);
    }

    public void exportBase(OutputStream outputStream, Iterable<SalarieAideADomicileMois> allMois) throws IOException {

        try {
            Document document = new Document();
            PdfWriter.getInstance(document, outputStream);

            document.open();

            Image logo = Image.getInstance("src\\main\\resources\\templates\\static\\img\\spring.png");
            logo.scaleToFit(75f, 75f);
            document.add(logo);

            Font boldFont = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD);

            // création d'un tableau
            PdfPTable table = new PdfPTable(2);

            for (SalarieAideADomicileMois mois : allMois) {
                SalarieAideADomicile salarie = mois.getSalarieAideADomicile();

                document.add(new Paragraph("Nom : " + salarie.getNom()));

                document.add(new Paragraph("\n"));

                Paragraph pMois = new Paragraph("Mois : " + mois.getPremierDuMois().getMonth(), boldFont);
                Paragraph pAnnee = new Paragraph("L'Année : " + mois.getPremierDuMois().getYear(), boldFont);

                // Centrer le mois et l'année
                pAnnee.setAlignment(Element.ALIGN_CENTER);
                pMois.setAlignment(Element.ALIGN_CENTER);

                document.add(pMois);
                document.add(pAnnee);

                document.add(new Paragraph("\n"));

                // Remplissage du tableau
                table.addCell("Mois");
                table.addCell(mois.getPremierDuMois().toString());

                table.addCell("Conges Payes Pris AnneeN");
                table.addCell(String.valueOf(mois.getCongesPayesAcquisAnneeN()));

                table.addCell("Jours Travailles AnneeN");
                table.addCell(String.valueOf(mois.getJoursTravaillesAnneeN()));

                document.add(table);
                document.add(new Paragraph("\n"));

                table.deleteBodyRows();
            }

            outputStream.write(outputStream.toString().getBytes());
            outputStream.flush();

            document.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void exportBase(OutputStream outputStream, Iterable<SalarieAideADomicileMois> allMois, Long salarieId) throws IOException {

        try {
            Document document = new Document();
            PdfWriter.getInstance(document, outputStream);

            document.open();

            Image logo = Image.getInstance("src\\main\\resources\\templates\\static\\img\\spring.png");
            logo.scaleToFit(75f, 75f);
            document.add(logo);

            Font boldFont = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD);

            // création d'un tableau
            PdfPTable table = new PdfPTable(2);

            for (SalarieAideADomicileMois mois : allMois) {
                SalarieAideADomicile salarie = mois.getSalarieAideADomicile();

                if (salarie.getId().equals(salarieId)) {

                    document.add(new Paragraph("Nom : " + salarie.getNom()));
                    document.add(new Paragraph("\n"));

                    Paragraph pMois = new Paragraph("Mois : " + mois.getPremierDuMois().getMonth(), boldFont);
                    Paragraph pAnnee = new Paragraph("L'Année : " + mois.getPremierDuMois().getYear(), boldFont);

                    // Centrer le mois et l'année
                    pAnnee.setAlignment(Element.ALIGN_CENTER);
                    pMois.setAlignment(Element.ALIGN_CENTER);

                    // ajout du mois et de l'année en gras
                    document.add(pMois);
                    document.add(pAnnee);
                    document.add(new Paragraph("\n"));

                    // Remplissage du tableau
                    table.addCell("Mois");
                    table.addCell(mois.getPremierDuMois().toString());

                    table.addCell("Conges Payes Pris AnneeN");
                    table.addCell(String.valueOf(mois.getCongesPayesAcquisAnneeN()));

                    table.addCell("Jours Travailles AnneeN");
                    table.addCell(String.valueOf(mois.getJoursTravaillesAnneeN()));

                    document.add(new Paragraph(""));

                    // Ajout de la table au pdf
                    document.add(table);
                    document.add(new Paragraph("\n"));
                    // efface la table
                    table.deleteBodyRows();
                }
            }

            outputStream.write(outputStream.toString().getBytes());
            outputStream.flush();

            document.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
