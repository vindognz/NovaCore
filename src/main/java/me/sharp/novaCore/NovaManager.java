package me.sharp.novaCore;

import me.sharp.novaCore.novas.Nova;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class NovaManager {
    private final Map<String, Nova> novas = new HashMap<>();

    public void registerNova(Nova nova) {
        novas.put(nova.name.toLowerCase(), nova);
    }

    public Nova getNova(String name) {
        return novas.get(name.toLowerCase());
    }

    public Collection<Nova> getAllNovas() {
        return novas.values();
    }
}
