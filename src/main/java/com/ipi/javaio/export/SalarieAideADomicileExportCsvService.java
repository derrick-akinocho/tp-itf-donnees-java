package com.ipi.javaio.export;

import com.ipi.javaio.model.SalarieAideADomicile;
import com.ipi.javaio.model.SalarieAideADomicileMois;
import com.ipi.javaio.service.SalarieAideADomicileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.PrintWriter;
import java.util.Arrays;

@Service
public class SalarieAideADomicileExportCsvService {
    @Autowired
    private final SalarieAideADomicileService salarieAideADomicileService;

    public SalarieAideADomicileExportCsvService(
            SalarieAideADomicileService salarieAideADomicileService) {
        this.salarieAideADomicileService = salarieAideADomicileService;
    }

    public void export(PrintWriter writer, Long salarieId) {
        SalarieAideADomicile salarieAideADomicile = salarieAideADomicileService.getSalarie(salarieId);
        exportBase(writer, Arrays.asList(salarieAideADomicile));
    }
    public void export(PrintWriter writer) {
        Iterable<SalarieAideADomicile> salaries = salarieAideADomicileService.getSalaries();
        exportBase(writer, salaries);
    }
    public void exportBase(PrintWriter writer, Iterable<SalarieAideADomicile> salaries) {

        // en-têtes de colonnes :
        writer.println("Mois En Cour" + ";" + "ID salarié"
                + ";" + "Nom" + ";" + "Jours Travailles Annee N"
                + ";" + "Conges Payes Acquis Annee N");

        // lignes :
        for (SalarieAideADomicile salarie : salaries) {

            writer.println("\"" +salarie.getMoisEnCours() + "\"" +
                    ";" + "\"" + salarie.getId() + "\"" +
                    ";" + "\"" + salarie.getNom() + "\"" +
                    ";" + "\"" + salarie.getJoursTravaillesAnneeN() + "\"" +
                    ";" + "\"" + salarie.getCongesPayesAcquisAnneeN() + "\""
            );
        }

        writer.close();
    }


}
