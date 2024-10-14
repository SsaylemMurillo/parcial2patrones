package com.parcial.DAO;

import java.util.ArrayList;
import java.util.List;

import com.parcial.model.Technique;
import com.parcial.utils.JSONUtil;

public class MySQLTechniqueDB implements IDataAccess<Technique> {

    private List<Technique> techniques = new ArrayList<>();

    public MySQLTechniqueDB() {
        this.techniques = JSONUtil.readTechniquesFromFile();
    }

    @Override
    public List<Technique> getAll() {
        return techniques;
    }

    @Override
    public Technique getById(int id) {
        return techniques.stream().filter(t -> t.getId() == id).findFirst().orElse(null);
    }

    @Override
    public void save(Technique technique) {
        techniques.add(technique);
        JSONUtil.writeTechniquesToFile(techniques);
    }

    @Override
    public void delete(int id) {
        techniques.removeIf(t -> t.getId() == id);
        JSONUtil.writeTechniquesToFile(techniques);
    }

    @Override
    public void update(Technique item) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
