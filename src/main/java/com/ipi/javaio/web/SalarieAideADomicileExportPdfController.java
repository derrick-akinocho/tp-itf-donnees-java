package com.ipi.javaio.web;

import com.ipi.javaio.export.SalarieAideADomicileExportPdfService;
import com.ipi.javaio.export.SalarieAideADomicileMoisExportPdfService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
@RequestMapping("export")
public class SalarieAideADomicileExportPdfController {


    @Autowired
    private final SalarieAideADomicileExportPdfService salarieAideADomicileExportPdfService;

    public SalarieAideADomicileExportPdfController(SalarieAideADomicileExportPdfService salarieAideADomicileExportPdfService) {
        this.salarieAideADomicileExportPdfService = salarieAideADomicileExportPdfService;
    }

    @GetMapping("/salarieAideADomicile/pdf")
    public void salarieAideADomicilePdf(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=\"export_salaries.pdf\"");
        this.salarieAideADomicileExportPdfService.export(response.getOutputStream());
    }

    /**
     * PAS ENCORE IMPLEMENTEE
     * @param salarieId
     * @param request
     * @param response
     * @throws IOException
     */
    @GetMapping("/salarieAideADomicile/pdf/{salarieId}")
    public void salarieAideADomicilePdf(@PathVariable("salarieId") Long salarieId,
                                             HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=\"export_salarie_" + salarieId + ".pdf\"");
        this.salarieAideADomicileExportPdfService.export(response.getOutputStream(), salarieId);
    }


}
