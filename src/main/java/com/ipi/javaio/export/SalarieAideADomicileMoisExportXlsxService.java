package com.ipi.javaio.export;

import com.ipi.javaio.model.SalarieAideADomicile;
import com.ipi.javaio.model.SalarieAideADomicileMois;
import com.ipi.javaio.repository.SalarieAideADomicileMoisRepository;
import com.ipi.javaio.service.SalarieAideADomicileService;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.ServletOutputStream;
import java.io.IOException;
import java.time.LocalDate;

@Service
public class SalarieAideADomicileMoisExportXlsxService {


    @Autowired
    private final SalarieAideADomicileMoisRepository salarieAideADomicileMoisRepository;
    @Autowired
    private final SalarieAideADomicileService salarieAideADomicileService;

    public static final int COL_PREMIER_DU_MOIS = 0;
    public static final int COL_ID_SALARIE = 1;
    public static final int COL_NOM = 2;
    public static final int COL_JOUR_TRAVAILLES_ANNEE_N = 3;
    public static final int COL_CONGES_PAYES_ACQUIS_ANNEE_N = 4;
    public static final int COL_ANCIENNETE = 5;


    public SalarieAideADomicileMoisExportXlsxService(
            SalarieAideADomicileMoisRepository salarieAideADomicileMoisRepository,
            SalarieAideADomicileService salarieAideADomicileService) {
        this.salarieAideADomicileMoisRepository = salarieAideADomicileMoisRepository;
        this.salarieAideADomicileService = salarieAideADomicileService;
    }

    public void export(ServletOutputStream outputStream) throws IOException {
        Workbook workbook = new XSSFWorkbook();
        //Apache POI
        Sheet sheet = workbook.createSheet("Mois des salariés");
        Row headerRow = sheet.createRow(0);

        CellStyle styleHeader = styleColor(workbook);

        Cell cellHeaderPremierDuMois = headerRow.createCell(COL_PREMIER_DU_MOIS); // 0
        cellHeaderPremierDuMois.setCellValue("Premier du mois");
        cellHeaderPremierDuMois.setCellStyle(styleHeader);

        Cell cellHeaderIdSalarie = headerRow.createCell(COL_ID_SALARIE); // 0
        cellHeaderIdSalarie.setCellValue("Id Salarie");
        cellHeaderIdSalarie.setCellStyle(styleHeader);

        Cell cellHeaderNom = headerRow.createCell(COL_NOM);
        cellHeaderNom.setCellValue("Nom");
        cellHeaderNom.setCellStyle(styleHeader);

        Cell cellHeaderJoursTravaillesAnneeN = headerRow.createCell(COL_JOUR_TRAVAILLES_ANNEE_N);
        cellHeaderJoursTravaillesAnneeN.setCellValue("Jours travaillés année N");
        cellHeaderJoursTravaillesAnneeN.setCellStyle(styleHeader);

        Cell cellHeaderCongesPayesAcquisAnneeN = headerRow.createCell(COL_CONGES_PAYES_ACQUIS_ANNEE_N);
        cellHeaderCongesPayesAcquisAnneeN.setCellValue("Congés payés acquis annee N");
        cellHeaderCongesPayesAcquisAnneeN.setCellStyle(styleHeader);

        Cell cellHeaderAnciennete = headerRow.createCell(COL_ANCIENNETE);
        cellHeaderAnciennete.setCellValue("Ancienneté");
        cellHeaderAnciennete.setCellStyle(styleHeader);


        CellStyle cellStyleBorder = newStyleBorder(workbook);

        Iterable<SalarieAideADomicileMois> allMois = salarieAideADomicileMoisRepository.findAll();
        int rowIndex = 1;
        for (SalarieAideADomicileMois mois : allMois) {
            Row row = sheet.createRow(rowIndex++);

            SalarieAideADomicile salarie = mois.getSalarieAideADomicile();

            Cell cellPremierDuMois = row.createCell(COL_PREMIER_DU_MOIS); // 0
            cellPremierDuMois.setCellValue(mois.getPremierDuMois().toString());
            cellPremierDuMois.setCellStyle(styleHeader);

            Cell cellIdSalarie = row.createCell(COL_ID_SALARIE);
            cellIdSalarie.setCellValue(salarie.getId());
            cellIdSalarie.setCellStyle(cellStyleBorder);

            Cell cellNom = row.createCell(COL_NOM);
            cellNom.setCellValue(salarie.getNom());
            cellNom.setCellStyle(cellStyleBorder);

            Cell cellJoursTravaillesAnneeN = row.createCell(COL_JOUR_TRAVAILLES_ANNEE_N);
            cellJoursTravaillesAnneeN.setCellValue(salarie.getJoursTravaillesAnneeN());
            cellJoursTravaillesAnneeN.setCellStyle(cellStyleBorder);

            Cell cellCongesPayesAcquisAnneeN = row.createCell(COL_CONGES_PAYES_ACQUIS_ANNEE_N);
            cellCongesPayesAcquisAnneeN.setCellValue(salarie.getCongesPayesAcquisAnneeN());
            cellCongesPayesAcquisAnneeN.setCellStyle(cellStyleBorder);

            int anciennete = salarie.getMoisDebutContrat().until(LocalDate.now()).getYears();
            Cell cellAnciennete = row.createCell(COL_ANCIENNETE);
            cellAnciennete.setCellValue(anciennete + " ans");
            cellAnciennete.setCellStyle(cellStyleBorder);
        }

        workbook.write(outputStream);
        workbook.close();

    }

    public void export(ServletOutputStream outputStream, long salarieId) throws IOException {
        Workbook workbook = new XSSFWorkbook();
        //Apache POI
        Sheet sheet = workbook.createSheet("Mois des salariés");
        Row headerRow = sheet.createRow(0);

        CellStyle styleHeader = styleColor(workbook);

        Cell cellHeaderPremierDuMois = headerRow.createCell(COL_PREMIER_DU_MOIS); // 0
        cellHeaderPremierDuMois.setCellValue("Premier du mois");
        cellHeaderPremierDuMois.setCellStyle(styleHeader);

        Cell cellHeaderIdSalarie = headerRow.createCell(COL_ID_SALARIE); // 0
        cellHeaderIdSalarie.setCellValue("Id Salarie");
        cellHeaderIdSalarie.setCellStyle(styleHeader);

        Cell cellHeaderNom = headerRow.createCell(COL_NOM);
        cellHeaderNom.setCellValue("Nom");
        cellHeaderNom.setCellStyle(styleHeader);

        Cell cellHeaderJoursTravaillesAnneeN = headerRow.createCell(COL_JOUR_TRAVAILLES_ANNEE_N);
        cellHeaderJoursTravaillesAnneeN.setCellValue("Jours travaillés année N");
        cellHeaderJoursTravaillesAnneeN.setCellStyle(styleHeader);

        Cell cellHeaderCongesPayesAcquisAnneeN = headerRow.createCell(COL_CONGES_PAYES_ACQUIS_ANNEE_N);
        cellHeaderCongesPayesAcquisAnneeN.setCellValue("Congés payés acquis annee N");
        cellHeaderCongesPayesAcquisAnneeN.setCellStyle(styleHeader);

        Cell cellHeaderAnciennete = headerRow.createCell(COL_ANCIENNETE);
        cellHeaderAnciennete.setCellValue("Ancienneté");
        cellHeaderAnciennete.setCellStyle(styleHeader);


        CellStyle cellStyleBorder = newStyleBorder(workbook);

        Iterable<SalarieAideADomicileMois> allMois = salarieAideADomicileMoisRepository.findAll();
        int rowIndex = 1;

        for (SalarieAideADomicileMois mois : allMois) {
            if (mois.getSalarieAideADomicile().getId().equals(salarieId)) {
                Row row = sheet.createRow(rowIndex++);

                SalarieAideADomicile salarie = mois.getSalarieAideADomicile();

                Cell cellPremierDuMois = row.createCell(COL_PREMIER_DU_MOIS); // 0
                cellPremierDuMois.setCellValue(mois.getPremierDuMois().toString());
                cellPremierDuMois.setCellStyle(styleHeader);

                Cell cellIdSalarie = row.createCell(COL_ID_SALARIE);
                cellIdSalarie.setCellValue(salarie.getId());
                cellIdSalarie.setCellStyle(cellStyleBorder);

                Cell cellNom = row.createCell(COL_NOM);
                cellNom.setCellValue(salarie.getNom());
                cellNom.setCellStyle(cellStyleBorder);

                Cell cellJoursTravaillesAnneeN = row.createCell(COL_JOUR_TRAVAILLES_ANNEE_N);
                cellJoursTravaillesAnneeN.setCellValue(salarie.getJoursTravaillesAnneeN());
                cellJoursTravaillesAnneeN.setCellStyle(cellStyleBorder);

                Cell cellCongesPayesAcquisAnneeN = row.createCell(COL_CONGES_PAYES_ACQUIS_ANNEE_N);
                cellCongesPayesAcquisAnneeN.setCellValue(salarie.getCongesPayesAcquisAnneeN());
                cellCongesPayesAcquisAnneeN.setCellStyle(cellStyleBorder);

                int anciennete = salarie.getMoisDebutContrat().until(LocalDate.now()).getYears();
                Cell cellAnciennete = row.createCell(COL_ANCIENNETE);
                cellAnciennete.setCellValue(anciennete + " ans");
                cellAnciennete.setCellStyle(cellStyleBorder);
            }
        }

        workbook.write(outputStream);
        workbook.close();

    }

    private CellStyle styleColor(Workbook workbook) {
        CellStyle style = workbook.createCellStyle();
        setBorderColor(style);

        Font font = workbook.createFont();
        font.setColor(IndexedColors.GREEN.getIndex());
        style.setFont(font);
        return style;
    }

    private CellStyle newStyleBorder(Workbook workbook) {
        CellStyle style = workbook.createCellStyle();
        setBorderColor(style);
        return style;
    }

    private void setBorderColor(CellStyle style) {
        style.setBorderBottom(BorderStyle.MEDIUM);
        style.setBottomBorderColor(IndexedColors.BLUE.getIndex());

        style.setBorderTop(BorderStyle.MEDIUM);
        style.setTopBorderColor(IndexedColors.BLUE.getIndex());

        style.setBorderLeft(BorderStyle.MEDIUM);
        style.setLeftBorderColor(IndexedColors.BLUE.getIndex());

        style.setBorderRight(BorderStyle.MEDIUM);
        style.setRightBorderColor(IndexedColors.BLUE.getIndex());
    }


}
