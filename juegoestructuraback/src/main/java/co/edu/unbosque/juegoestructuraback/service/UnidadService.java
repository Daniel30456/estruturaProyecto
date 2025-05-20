package co.edu.unbosque.juegoestructuraback.service;

import co.edu.unbosque.juegoestructuraback.dto.CasillaDTO;
import co.edu.unbosque.juegoestructuraback.dto.UnidadDTO;
import co.edu.unbosque.juegoestructuraback.model.Casilla;
import co.edu.unbosque.juegoestructuraback.model.Casilla.TipoTerreno;
import co.edu.unbosque.juegoestructuraback.model.Unidad;
import co.edu.unbosque.juegoestructuraback.repository.CasillaRepository;
import co.edu.unbosque.juegoestructuraback.repository.UnidadRepository;
import co.edu.unbosque.juegoestructuraback.util.BfsPathFinder;
import co.edu.unbosque.util.structure.linkedlist.MyLinkedList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.Point;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Random;
import java.util.Set;

@Service
public class UnidadService {

	@Autowired
	private UnidadRepository unidadRepository;

	@Autowired
	private CasillaRepository casillaRepository;

	@Autowired
	private BfsPathFinder bfs;

	/**
	 * Lista todas las unidades existentes.
	 */
	public MyLinkedList<UnidadDTO> listarUnidades() {
		MyLinkedList<UnidadDTO> lista = new MyLinkedList<>();
		for (Unidad u : unidadRepository.findAll()) {
			lista.add(toDTO(u));
		}
		return lista;
	}
	
	 /**
     * Calcula todas las casillas a las que puede llegar una unidad
     * dado su atributo movimiento, usando BFS sobre el grid.
     */
    public MyLinkedList<Point> calcularRango(Long id) {
        // 1) Recupera la unidad y su movimiento
        Unidad u = unidadRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Unidad no encontrada: " + id));
        int mov = u.getMovimiento();
        Point start = new Point(u.getCasilla().getX(), u.getCasilla().getY());

        // 2) Obtiene todas las casillas del mapa
        List<Casilla> allCasillas = casillaRepository.findAll();
        // 2.1) Determina dimensiones
        int maxX = 0, maxY = 0;
        for (Casilla c : allCasillas) {
            maxX = Math.max(maxX, c.getX());
            maxY = Math.max(maxY, c.getY());
        }
        int width  = maxX + 1;
        int height = maxY + 1;
        // 2.2) Construye matriz de pasabilidad según terreno
        boolean[][] passable = new boolean[height][width];
        for (Casilla c : allCasillas) {
            passable[c.getY()][c.getX()] = c.esTransitable();
        }

        // 3) Prepara BFS para distancias mínimas
        int[][] dist = new int[height][width];
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                dist[y][x] = -1;
            }
        }
        Queue<Point> queue = new LinkedList<>();
        queue.add(start);
        dist[start.y][start.x] = 0;

        MyLinkedList<Point> rango = new MyLinkedList<>();
        int[][] dirs = {{1,0},{-1,0},{0,1},{0,-1}};

        // 4) BFS para llenar 'rango' con puntos cuya distancia <= mov
        while (!queue.isEmpty()) {
            Point p = queue.poll();
            int d = dist[p.y][p.x];
            if (d > mov) continue;

            // Añade al rango
            rango.add(p);

            // Explora vecinos
            for (int[] dir : dirs) {
                int nx = p.x + dir[0], ny = p.y + dir[1];
                if (nx >= 0 && ny >= 0 && nx < width && ny < height
                        && dist[ny][nx] == -1
                        && passable[ny][nx]) {
                    dist[ny][nx] = d + 1;
                    queue.add(new Point(nx, ny));
                }
            }
        }

        return rango;
    }

	
	/**
	 * Genera n unidades aleatorias del tipo y jugador indicados.
	 */
	public MyLinkedList<UnidadDTO> generarUnidades(int n, Unidad.TipoUnidad tipo, String jugador) {
		Random rnd = new Random();
		for (int i = 0; i < n; i++) {
			Casilla casilla;
			do {
				int x = rnd.nextInt(12), y = rnd.nextInt(12);
				casilla = casillaRepository.findByXAndY(x, y).filter(Casilla::esTransitable).orElse(null);
			} while (casilla == null);
			Unidad u = new Unidad(tipo, 100, 20, 10, 1, tipo == Unidad.TipoUnidad.TANQUE ? 4 : 3, jugador, casilla);
			unidadRepository.save(u);
		}
		return listarUnidades();
	}

	/**
     * Genera n unidades del tipo y jugador indicados en las primeras casillas
     * del rectángulo [minX..maxX]×[minY..maxY], recorriéndolas por filas.
     */
    public MyLinkedList<UnidadDTO> generarUnidadesEnRegion(
            int n,
            Unidad.TipoUnidad tipo,
            String jugador,
            int minX, int maxX,
            int minY, int maxY) {

        MyLinkedList<UnidadDTO> generadas = new MyLinkedList<>();
        int count = 0;

        for (int x = minX; x <= maxX && count < n; x++) {
            for (int y = minY; y <= maxY && count < n; y++) {

                // Copiamos x,y en variables efectivamente finales
                final int fx = x;
                final int fy = y;

                // 1) ¿La casilla existe y es transitable?
                Casilla casilla = casillaRepository
                        .findByXAndY(fx, fy)
                        .filter(Casilla::esTransitable)
                        .orElse(null);
                if (casilla == null) continue;

                // 2) ¿Ya hay unidad ocupando esa casilla?
                boolean ocupada = unidadRepository.findAll().stream()
                        .anyMatch(u -> u.getCasilla().getX() == fx
                                    && u.getCasilla().getY() == fy);
                if (ocupada) continue;

                // 3) Crear y guardar la unidad
                Unidad u = new Unidad(
                    tipo,
                    100,  // vida
                    20,   // ataque
                    10,   // defensa
                    1,    // alcance
                    tipo == Unidad.TipoUnidad.TANQUE ? 4 : 3,  // movimiento
                    jugador,
                    casilla
                );
                unidadRepository.save(u);

                // 4) Añadir su DTO a la lista
                generadas.add(toDTO(u));
                count++;
            }
        }

        return generadas;
    }


	/**
	 * Mueve una unidad aplicando BFS y retornando la ruta en MyLinkedList<Point>.
	 */
    public MyLinkedList<Point> moverUnidad(Long id, int destX, int destY) {
        Unidad u = unidadRepository.findById(id).orElseThrow();

        // Construir blocked list
        MyLinkedList<Point> blocked = new MyLinkedList<>();
        for (Unidad other : unidadRepository.findAll()) {
            if (!other.getId().equals(id)) {
                blocked.add(new Point(other.getCasilla().getX(), other.getCasilla().getY()));
            }
        }

        Point origen  = new Point(u.getCasilla().getX(), u.getCasilla().getY());
        Point destino = new Point(destX, destY);

        // Llama al BFS
        MyLinkedList<Point> ruta = bfs.findPath(
            origen,
            destino,
            u.getAllowedTerrains(),            // Set<TipoTerreno>
            blocked,
            u.getTipo() == Unidad.TipoUnidad.HELICOPTERO
        );

        // Si hay ruta, mueve la unidad
        if (!ruta.isEmpty()) {
            Point fin = ruta.get(ruta.size() - 1).getInfo();
            Casilla cdest = casillaRepository.findByXAndY(fin.x, fin.y).orElseThrow();
            u.setCasilla(cdest);
            unidadRepository.save(u);
        }
        return ruta;
    }

	private UnidadDTO toDTO(Unidad u) {
		Casilla c = u.getCasilla();
		return new UnidadDTO(u.getId(), u.getTipo().name(), u.getVida(), u.getAtaque(), u.getDefensa(), u.getAlcance(),
				u.getMovimiento(), u.getJugador(), c.getX(), c.getY());
	}
}
