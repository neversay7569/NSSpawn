package ru.ns.spawn.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.Getter;
import lombok.Setter;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

public class Config {
    private final Path path;
    private final Gson gson;
    private SpawnData data;
    
    public Config(Path path) {
        this.path = path;
        this.gson = new GsonBuilder().setPrettyPrinting().create();
        this.data = new SpawnData();
    }
    
    public void load() {
        try {
            if (Files.exists(path)) {
                try (Reader reader = Files.newBufferedReader(path)) {
                    data = gson.fromJson(reader, SpawnData.class);
                    if (data == null) {
                        data = new SpawnData();
                    }
                }
            } else {
                Files.createDirectories(path.getParent());
                save();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void save() {
        try {
            try (Writer writer = Files.newBufferedWriter(path)) {
                gson.toJson(data, writer);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public Location getSpawnLocation() {
        return data.getSpawnLocation();
    }
    
    public void setSpawnLocation(float x, float y, float z, int dimension, String world) {
        data.setSpawnLocation(new Location(x, y, z, dimension, world));
        save();
    }
    
    public static class SpawnData {
        @Getter
        @Setter
        private Location spawnLocation;
        
        public SpawnData() {
            this.spawnLocation = null;
        }
    }
    
    public static class Location {
        @Getter private final float x;
        @Getter private final float y;
        @Getter private final float z;
        @Getter private final int dimension;
        @Getter private final String world;
        
        public Location(float x, float y, float z, int dimension, String world) {
            this.x = x;
            this.y = y;
            this.z = z;
            this.dimension = dimension;
            this.world = world;
        }
    }
}
