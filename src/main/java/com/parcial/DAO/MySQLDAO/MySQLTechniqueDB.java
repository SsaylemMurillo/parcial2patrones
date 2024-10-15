package com.parcial.DAO.MySQLDAO;

import java.util.List;

import com.parcial.DAO.IDataAccess;
import com.parcial.model.Technique;
import com.parcial.utils.TechniqueJSONUtil;

public class MySQLTechniqueDB implements IDataAccess<Technique> {

    private final TechniqueJSONUtil jsonUtil = new TechniqueJSONUtil();

    @Override
    public List<Technique> getAll() {
        return jsonUtil.readFromFile();
    }

    @Override
    public Technique getById(int id) {
        return jsonUtil.readFromFile().stream()
                .filter(technique -> technique.getId() == id)
                .findFirst()
                .orElse(null);
    }

    @Override
    public Technique save(Technique technique) {
        jsonUtil.addItem(technique);
        return technique;
    }

    @Override
    public void update(Technique technique) {
        List<Technique> techniques = jsonUtil.readFromFile();
        techniques.stream()
                .filter(t -> t.getId() == technique.getId())
                .forEach(t -> {
                    t.setName(technique.getName());
                    t.setStyle(technique.getStyle());
                    t.setDescription(technique.getDescription());
                });
        jsonUtil.writeToFile(techniques);
    }

    @Override
    public void delete(int id) {
        List<Technique> techniques = jsonUtil.readFromFile();
        techniques.removeIf(technique -> technique.getId() == id);
        jsonUtil.writeToFile(techniques);
    }
}
