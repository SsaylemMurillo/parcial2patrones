package com.parcial.DAO.OracleDAO;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.parcial.DAO.IDataAccess;
import com.parcial.model.Technique;
import com.parcial.utils.TechniqueJSONUtil;

public class OracleTechniqueDB implements IDataAccess<Technique> {

    private final Map<Integer, Technique> techniques;
    private final TechniqueJSONUtil jsonUtil = new TechniqueJSONUtil();

    public OracleTechniqueDB() {
        // Cargar técnicas en el Map
        techniques = jsonUtil.readFromFile().stream()
                .collect(Collectors.toMap(Technique::getId, technique -> technique));
    }

    @Override
    public List<Technique> getAll() {
        return techniques.values().stream().collect(Collectors.toList());
    }

    @Override
    public Technique getById(int id) {
        return techniques.get(id);
    }

    @Override
    public Technique save(Technique technique) {
        techniques.put(technique.getId(), technique);
        jsonUtil.writeToFile(getAll());
        return technique;
    }

    @Override
    public void update(Technique technique) {
        if (techniques.containsKey(technique.getId())) {
            techniques.put(technique.getId(), technique);
            jsonUtil.writeToFile(getAll());
        } else {
            throw new IllegalArgumentException("Technique ID " + technique.getId() + " no existe para actualizar.");
        }
    }

    @Override
    public void delete(int id) {
        techniques.remove(id);
        jsonUtil.writeToFile(getAll());
    }
}
