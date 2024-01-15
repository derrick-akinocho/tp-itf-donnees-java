package com.ipi.javaio.export;

import com.ipi.javaio.model.SalarieAideADomicile;
import com.ipi.javaio.model.SalarieAideADomicileMois;
import com.ipi.javaio.repository.SalarieAideADomicileMoisRepository;
import com.ipi.javaio.repository.SalarieAideADomicileRepository;
import com.ipi.javaio.service.SalarieAideADomicileService;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.OutputStream;
import java.time.LocalDate;
import java.util.List;

@Service
public class SalarieAideADomicileExportPdfService {

    @Autowired
    private final SalarieAideADomicileRepository salarieAideADomicileRepository;
    @Autowired
    private final SalarieAideADomicileService salarieAideADomicileService;

    public SalarieAideADomicileExportPdfService(SalarieAideADomicileRepository salarieAideADomicileRepository, SalarieAideADomicileService salarieAideADomicileService) {
        this.salarieAideADomicileRepository = salarieAideADomicileRepository;
        this.salarieAideADomicileService = salarieAideADomicileService;
    }

    public void export(OutputStream outputStream) throws IOException {
        Iterable<SalarieAideADomicile> salaries = salarieAideADomicileRepository.findAll();
        exportBase(outputStream, salaries);
    }

    public void export(OutputStream outputStream, Long salarieId/*, LocalDate premierDuMois*/) throws IOException {
        Iterable<SalarieAideADomicile> salaries = salarieAideADomicileRepository.findAll();
        exportBase(outputStream, salaries, salarieId);
    }

    public void exportBase(OutputStream outputStream, Iterable<SalarieAideADomicile> salaries) throws IOException {

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

            for (SalarieAideADomicile salarie : salaries) {
           
                document.add(new Paragraph("Nom : " + salarie.getNom()));

                document.add(new Paragraph("\n"));

                Paragraph pMois = new Paragraph("Mois : " + salarie.getMoisEnCours().getMonth(), boldFont);
                Paragraph pAnnee = new Paragraph("L'Année : " + salarie.getMoisEnCours().getYear(), boldFont);

                // Centrer le mois et l'année
                pAnnee.setAlignment(Element.ALIGN_CENTER);
                pMois.setAlignment(Element.ALIGN_CENTER);

                document.add(pMois);
                document.add(pAnnee);

                document.add(new Paragraph("\n"));

                int anciennete = salarie.getMoisDebutContrat().until(LocalDate.now()).getYears();

                // Remplissage du tableau
                table.addCell("Anciennete");
                table.addCell(String.valueOf(anciennete));

                table.addCell("Conges Payes Acquis AnneeN");
                table.addCell(String.valueOf(salarie.getCongesPayesAcquisAnneeN()));

                table.addCell("Jours Travailles AnneeN");
                table.addCell(String.valueOf(salarie.getJoursTravaillesAnneeN()));

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

    public void exportBase(OutputStream outputStream, Iterable<SalarieAideADomicile> salaries, Long salarieId) throws IOException {

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

            for (SalarieAideADomicile salarie : salaries) {

                if (salarie.getId().equals(salarieId)) {
                    document.add(new Paragraph("Nom : " + salarie.getNom()));

                    document.add(new Paragraph("\n"));

                    Paragraph pMois = new Paragraph("Mois : " + salarie.getMoisEnCours().getMonth(), boldFont);
                    Paragraph pAnnee = new Paragraph("L'Année : " + salarie.getMoisEnCours().getYear(), boldFont);

                    // Centrer le mois et l'année
                    pAnnee.setAlignment(Element.ALIGN_CENTER);
                    pMois.setAlignment(Element.ALIGN_CENTER);

                    document.add(pMois);
                    document.add(pAnnee);

                    document.add(new Paragraph("\n"));

                    int anciennete = salarie.getMoisDebutContrat().until(LocalDate.now()).getYears();

                    // Remplissage du tableau
                    table.addCell("Anciennete");
                    table.addCell(String.valueOf(anciennete));

                    table.addCell("Conges Payes Acquis AnneeN");
                    table.addCell(String.valueOf(salarie.getCongesPayesAcquisAnneeN()));

                    table.addCell("Jours Travailles AnneeN");
                    table.addCell(String.valueOf(salarie.getJoursTravaillesAnneeN()));

                    document.add(table);
                    document.add(new Paragraph("\n"));

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
