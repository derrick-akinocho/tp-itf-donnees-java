package com.ipi.javaio.io;

import com.ipi.javaio.exception.SalarieException;
import com.ipi.javaio.model.SalarieAideADomicile;
import com.ipi.javaio.repository.SalarieAideADomicileMoisRepository;
import com.ipi.javaio.service.SalarieAideADomicileService;
import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvException;
import com.opencsv.exceptions.CsvValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;
import java.time.LocalDate;
import java.util.List;

@Service
public class SalarieAideADomicileImportCsvService {

    @Autowired
    private final SalarieAideADomicileService salarieAideADomicileService;

    public SalarieAideADomicileImportCsvService(
            SalarieAideADomicileMoisRepository salarieAideADomicileMoisRepository,
            SalarieAideADomicileService salarieAideADomicileService) {
        this.salarieAideADomicileService = salarieAideADomicileService;
    }

    public void importFile(String filePath) throws SalarieException {

        try {
            FileReader reader = new FileReader(filePath);
            CSVParser csvParser = new CSVParserBuilder().withSeparator(',').build();
            CSVReader csvReader = new CSVReaderBuilder(reader)
                    .withCSVParser(csvParser)
                    .withSkipLines(1)  // Pour ignorer la première ligne si elle contient des en-têtes
                    .build();

            List<String[]> records = csvReader.readAll();

            for (String[] record : records) {
                creerSalarieAideADomicile(record);
            }

            csvReader.close();
        } catch (IOException | CsvException e) {
            e.printStackTrace();
        }
    }

    private void creerSalarieAideADomicile(String[] values) throws SalarieException {

        SalarieAideADomicile s3 = this.salarieAideADomicileService.creerSalarieAideADomicile(
                new SalarieAideADomicile(values[1], LocalDate.parse(values[2]), LocalDate.parse(values[3]), Double.parseDouble(values[4]), Double.parseDouble(values[5]),
                        Double.parseDouble(values[6]), Double.parseDouble(values[7]), Double.parseDouble(values[8])));

        // 2022
        this.salarieAideADomicileService.clotureMois(s3, 4);
        this.salarieAideADomicileService.clotureMois(s3, 9);
        this.salarieAideADomicileService.clotureMois(s3, 7);
        // 2023
        this.salarieAideADomicileService.clotureMois(s3, 10);
        this.salarieAideADomicileService.clotureMois(s3, 12);
        this.salarieAideADomicileService.clotureMois(s3, 8);

    }

}
