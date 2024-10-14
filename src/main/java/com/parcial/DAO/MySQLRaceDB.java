package com.parcial.DAO;

import java.util.ArrayList;
import java.util.List;

import com.parcial.model.Race;
import com.parcial.utils.JSONUtil;

public class MySQLRaceDB implements IDataAccess<Race> {

    private List<Race> races = new ArrayList<>();

    public MySQLRaceDB() {
        this.races = JSONUtil.readRacesFromFile();
    }

    @Override
    public List<Race> getAll() {
        return races;
    }

    @Override
    public Race getById(int id) {
        return races.stream().filter(r -> r.getId() == id).findFirst().orElse(null);
    }

    @Override
    public void save(Race race) {
        races.add(race);
        JSONUtil.writeRacesToFile(races);
    }

    @Override
    public void update(Race updatedRace) {
        System.out.println("RAZA A ACTUALIZAR: ");
        System.out.println(updatedRace);
        Race existingRace = getById(updatedRace.getId());
        if (existingRace != null) {
            // Actualizar los campos de la raza existente
            existingRace.setName(updatedRace.getName());
            existingRace.setSkinColor(updatedRace.getSkinColor());
            existingRace.setHairColor(updatedRace.getHairColor());
            existingRace.setTransformation(updatedRace.getTransformation());
            existingRace.setOriginPlanet(updatedRace.getOriginPlanet());
            existingRace.setAbilities(updatedRace.getAbilities());

            // Escribir cambios en el archivo JSON
            JSONUtil.writeRacesToFile(races);
        } else {
            throw new IllegalArgumentException("Race ID " + updatedRace.getId() + " no existe para actualizar.");
        }
    }

    @Override
    public void delete(int id) {
        races.removeIf(r -> r.getId() == id);
        JSONUtil.writeRacesToFile(races);
    }
}
