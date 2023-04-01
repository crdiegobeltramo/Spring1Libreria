
package com.egg.biblioteca.controladores;

import com.egg.biblioteca.entidades.Editorial;
import com.egg.biblioteca.excepciones.MiException;
import com.egg.biblioteca.servicios.EditorialServicio;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/editorial")
public class EditorialControlador {
     @Autowired
    private EditorialServicio editorialServicio;

    @GetMapping("/registrar")//localhost:8080/editorial/registrar
    public String registrar() {
        return "editorial_form.html";
    }

    @PostMapping("/registro")
    public String registro(@RequestParam String nombre, ModelMap modelo) {

        try {
            editorialServicio.crearEditorial(nombre);
        } catch (MiException ex) {
           
           //Logger.getLogger(AutorControlador.class.getName()).log(Level.SEVERE, null, ex);
           modelo.put("error",ex.getMessage());
            return "editorial_form.html";
        }

        return "index.html";

    }
     @GetMapping("/lista")
    public String listar(ModelMap modelo) {
        List<Editorial> editoriales = editorialServicio.listaEditoriales();
        modelo.addAttribute("editoriales", editoriales);
        return "editorial_list.html";
    }

    @GetMapping("/modificar/{Id}")
    public String modificar(@PathVariable String Id, ModelMap modelo){
        modelo.put("editorial", editorialServicio.getOne(Id));
        
        
        return "editorial_modificar.html";
    }
    
    @PostMapping("/modificar/{Id}")
    
    public String modificar(@PathVariable String Id, String nombre, ModelMap modelo){
       try {
        editorialServicio.modificarEditorial(Id, nombre);

        return "redirect:../lista";
    } catch (MiException ex){
   modelo.put("error", ex.getMessage());
   return "editorial_modificar.html";
    }
    }
}




