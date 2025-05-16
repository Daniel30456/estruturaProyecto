package co.edu.unbosque.juegoestructuraback.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.annotation.PostConstruct;

import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Component
public class PredefinedMapProvider {

    // Aquí almacenamos las 10 plantillas cargadas desde JSON
    private final List<int[][]> templates = new ArrayList<>();
    private final Random rng = new Random();

    @PostConstruct
    public void init() {
        try {
            PathMatchingResourcePatternResolver resolver = 
                new PathMatchingResourcePatternResolver();
            Resource[] resources = resolver.getResources("classpath:maps/*.json");
            ObjectMapper mapper = new ObjectMapper();

            for (Resource res : resources) {
                System.out.println(">> Cargando plantilla " + res.getFilename());
                // Leemos el JSON como árbol
                JsonNode root = mapper.readTree(res.getInputStream());
                JsonNode mapNode = root.get("map");
                if (mapNode == null || !mapNode.isArray()) {
                    throw new IllegalStateException(
                        "No se encontró clave 'map' en " + res.getFilename());
                }
                // Convertimos ese nodo al tipo int[][]
                int[][] mapa = mapper.convertValue(mapNode, int[][].class);
                if (mapa.length != 12 || mapa[0].length != 12) {
                    throw new IllegalStateException(
                        "Mapa inválido en " + res.getFilename());
                }
                templates.add(mapa);
            }

            if (templates.isEmpty()) {
                throw new IllegalStateException("No se cargaron plantillas de mapa");
            }
            System.out.println(">> Cargados " + templates.size() + " mapas predefinidos");
        } catch (IOException e) {
            throw new RuntimeException("Error cargando mapas JSON", e);
        }
    }


    /**  
     * Retorna una de las plantillas al azar  
     */
    public int[][] getRandomTemplate() {
        return templates.get(rng.nextInt(templates.size()));
    }
}