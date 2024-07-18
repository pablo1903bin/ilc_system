package com.ilc.controller;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDate;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ilc.model.ContractData;

import jakarta.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/contract")
public class ContratosController {

      /**
	 * Metodo que muestra la pagina para crear un nuevo contrato 
	 * @param model
	 * @param page
	 * @return
	 */
    @GetMapping("/create")
    public String showForm(Model model) {
        model.addAttribute("contractData", new ContractData());
        return "contratos";
    }

    @PostMapping("/generateContract")
    public String generateContract(@ModelAttribute ContractData contractData, Model model) {
        System.out.println("" + contractData.toString());
        model.addAttribute("contractData", contractData);
        return "contractPreview";
    }

    @PostMapping("/downloadContract")
    @ResponseBody
    public void downloadContract(@ModelAttribute ContractData contractData, HttpServletResponse response) throws IOException {
    	
    	  System.out.println("Doc a descargar:" + contractData.toString());
    	  
        XWPFDocument document = new XWPFDocument();

        // Crear párrafo y agregar contenido
        XWPFParagraph paragraph = document.createParagraph();
        XWPFRun run = paragraph.createRun();
        run.setText("Contrato de Arrendamiento");
        run.addBreak();
        run.setText("Arrendador: " + contractData.getArrendador());
        run.addBreak();
        run.setText("Arrendatario: " + contractData.getArrendatario());
        run.addBreak();
        run.setText("Fecha: " + contractData.getFecha());
        run.addBreak();
        run.setText("Lugar: " + contractData.getLugar());

        // Configurar la respuesta HTTP para descargar el documento
        response.setContentType("application/vnd.openxmlformats-officedocument.wordprocessingml.document");
        response.setHeader("Content-Disposition", "attachment; filename=contrato.docx");

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        document.write(out);
        document.close();

        ByteArrayInputStream in = new ByteArrayInputStream(out.toByteArray());
        response.getOutputStream().write(in.readAllBytes());
    }
    
    @PostMapping("/generate")
    public void downloadContract(@RequestParam("arrendador") String arrendador,
                                 @RequestParam("arrendatario") String arrendatario,
                                 @RequestParam("fecha") String fechaStr,
                                 @RequestParam("lugar") String lugar,
                                 HttpServletResponse response) throws IOException {
        LocalDate fecha = null;
        try {
            fecha = LocalDate.parse(fechaStr);
        } catch (Exception e) {
            // Manejar error de parsing, tal vez asignar una fecha por defecto o lanzar una excepción personalizada
            fecha = LocalDate.now(); // Asignar la fecha actual como valor por defecto
        }

        ContractData data = new ContractData();
        data.setArrendador(arrendador);
        data.setArrendatario(arrendatario);
        data.setFecha(fecha);
        data.setLugar(lugar);

        XWPFDocument document = new XWPFDocument();

        XWPFParagraph paragraph = document.createParagraph();
        XWPFRun run = paragraph.createRun();
        run.setText("Contrato de Arrendamiento");
        run.addBreak();
        run.setText("Arrendador: " + (data.getArrendador() != null ? data.getArrendador() : "No proporcionado"));
        run.addBreak();
        run.setText("Arrendatario: " + (data.getArrendatario() != null ? data.getArrendatario() : "No proporcionado"));
        run.addBreak();
        run.setText("Fecha: " + (data.getFecha() != null ? data.getFecha().toString() : "No proporcionada"));
        run.addBreak();
        run.setText("Lugar: " + (data.getLugar() != null ? data.getLugar() : "No proporcionado"));

        response.setContentType("application/vnd.openxmlformats-officedocument.wordprocessingml.document");
        response.setHeader("Content-Disposition", "attachment; filename=contract.docx");

        document.write(response.getOutputStream());
        document.close();
    }
}
