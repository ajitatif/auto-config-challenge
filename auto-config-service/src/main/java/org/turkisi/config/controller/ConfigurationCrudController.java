package org.turkisi.config.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.turkisi.config.domain.ConfigurationModel;
import org.turkisi.config.repository.ConfigurationModelRepository;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Gökalp Gürbüzer (gokalp.gurbuzer@yandex.com)
 */
@Controller
@RequestMapping("/admin")
public class ConfigurationCrudController {

    private ConfigurationModelRepository configurationModelRepository;

    public ConfigurationCrudController(ConfigurationModelRepository configurationModelRepository) {
        this.configurationModelRepository = configurationModelRepository;
    }

    @RequestMapping(path = "/", method = RequestMethod.POST, consumes = "application/json")
    public ResponseEntity<Void> addConfiguration(HttpServletRequest request, @RequestBody ConfigurationModel configurationModel) {

        if (configurationModel.getId() == null) {
            ConfigurationModel savedModel = configurationModelRepository.save(configurationModel);
            HttpHeaders headers = new HttpHeaders();
            headers.add("Location", request.getRequestURL().toString() + "/" + savedModel.getId());
            return new ResponseEntity<>(headers, HttpStatus.CREATED);
        } else {
            throw new UnsupportedOperationException();
        }
    }

    @RequestMapping(path = "/{configId}", method = RequestMethod.PUT, consumes = "application/json")
    public ResponseEntity<Void> updateConfiguration(@PathVariable("configId") Long configId, @RequestBody ConfigurationModel configurationModel) {
        ConfigurationModel modelOnDb = configurationModelRepository.findOne(configId);
        if (modelOnDb == null) {
            throw new EntityNotFoundException();
        }

        modelOnDb.setName(configurationModel.getName());
        modelOnDb.setActive(configurationModel.isActive());
        modelOnDb.setApplicationName(configurationModel.getApplicationName());
        modelOnDb.setType(configurationModel.getType());
        modelOnDb.setValue(configurationModel.getValue());

        configurationModelRepository.save(modelOnDb);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(path="/", method = RequestMethod.GET)
    public @ResponseBody List<ConfigurationModel> getAllConfigurations() {

        ArrayList<ConfigurationModel> list = new ArrayList<>();
        configurationModelRepository.findAll().forEach(list::add);
        return list;
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    public @ResponseBody ConfigurationModel getConfiguration(@PathVariable("id") Long id) {
        return configurationModelRepository.findOne(id);
    }
}
