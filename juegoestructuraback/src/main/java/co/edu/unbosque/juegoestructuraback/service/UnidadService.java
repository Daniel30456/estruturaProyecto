package co.edu.unbosque.juegoestructuraback.service;

import co.edu.unbosque.juegoestructuraback.dto.UnidadDTO;
import co.edu.unbosque.juegoestructuraback.model.Casilla;
import co.edu.unbosque.juegoestructuraback.model.Unidad;
import co.edu.unbosque.juegoestructuraback.repository.CasillaRepository;
import co.edu.unbosque.juegoestructuraback.repository.UnidadRepository;
import co.edu.unbosque.juegoestructuraback.util.BfsPathFinder;
import co.edu.unbosque.util.structure.linkedlist.MyLinkedList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.awt.Point;
import java.util.List;
import java.util.Queue;
import java.util.LinkedList;
import java.util.Random;

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
	 * Calcula todas las casillas a las que puede llegar una unidad dado su atributo
	 * movimiento, usando BFS sobre el grid.
	 */
	public MyLinkedList<Point> calcularRango(Long id) {
		Unidad u = unidadRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Unidad no encontrada: " + id));
		int mov = u.getMovimiento();
		Point start = new Point(u.getCasilla().getX(), u.getCasilla().getY());

		List<Casilla> allCasillas = casillaRepository.findAll();
		int maxX = 0, maxY = 0;
		for (Casilla c : allCasillas) {
			maxX = Math.max(maxX, c.getX());
			maxY = Math.max(maxY, c.getY());
		}
		int width = maxX + 1, height = maxY + 1;

		boolean[][] passable = new boolean[height][width];
		for (Casilla c : allCasillas) {
			passable[c.getY()][c.getX()] = c.esTransitable();
		}

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
		int[][] dirs = { { 1, 0 }, { -1, 0 }, { 0, 1 }, { 0, -1 } };

		while (!queue.isEmpty()) {
			Point p = queue.poll();
			int d = dist[p.y][p.x];
			if (d > mov)
				continue;
			rango.add(p);
			for (int[] dir : dirs) {
				int nx = p.x + dir[0], ny = p.y + dir[1];
				if (nx >= 0 && ny >= 0 && nx < width && ny < height && dist[ny][nx] == -1 && passable[ny][nx]) {
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

			Unidad u = new Unidad(tipo, vidaPorTipo(tipo), // vida variable
					20, // ataque fijo
					10, // defensa fija
					1, // alcance fijo
					(tipo == Unidad.TipoUnidad.TANQUE ? 4 : 3), // movimiento
					casilla.getX(), casilla.getY(), jugador, casilla);
			unidadRepository.save(u);
		}
		return listarUnidades();
	}

	/**
	 * Genera n unidades en una región [minX..maxX]×[minY..maxY].
	 */
	@Transactional
	public MyLinkedList<UnidadDTO> generarUnidadesEnRegion(int n, String jugador, int minX, int maxX, int minY,
			int maxY) {

		MyLinkedList<UnidadDTO> generadas = new MyLinkedList<>();
		Unidad.TipoUnidad[] tipos = Unidad.TipoUnidad.values();
		Random rnd = new Random();
		int count = 0;

		for (int x = minX; x <= maxX && count < n; x++) {
			for (int y = minY; y <= maxY && count < n; y++) {
				// 1) Buscar casilla transitable y libre
				final int fx = x, fy = y;
				Casilla casilla = casillaRepository.findByXAndY(fx, fy).filter(Casilla::esTransitable).orElse(null);
				if (casilla == null)
					continue;

				boolean ocupada = unidadRepository.findAll().stream()
						.anyMatch(o -> o.getCasilla().getX() == fx && o.getCasilla().getY() == fy);
				if (ocupada)
					continue;

				// 2) Escoger tipo aleatorio
				Unidad.TipoUnidad tipoAleatorio = tipos[rnd.nextInt(tipos.length)];

				// 3) Vida y movimiento según tipo
				int vida = vidaPorTipo(tipoAleatorio);
				int mov = movPorTipo(tipoAleatorio);

				// 4) Crear y persistir unidad
				Unidad u = new Unidad(tipoAleatorio, vida, 20, // ataque
						10, // defensa
						1, // alcance
						mov, fx, fy, jugador, casilla);
				unidadRepository.save(u);

				// 5) Añadir al DTO
				generadas.add(toDTO(u));
				count++;
			}
		}

		return generadas;
	}

	@Transactional
	public MyLinkedList<Point> moverUnidad(Long id, int destX, int destY) {
		Unidad u = unidadRepository.findById(id).orElseThrow();

		// bloqueos por otras unidades
		MyLinkedList<Point> blocked = new MyLinkedList<>();
		for (Unidad other : unidadRepository.findAll()) {
			if (!other.getId().equals(id)) {
				blocked.add(new Point(other.getCasilla().getX(), other.getCasilla().getY()));
			}
		}

		Point origen = new Point(u.getCasilla().getX(), u.getCasilla().getY());
		Point destino = new Point(destX, destY);

		MyLinkedList<Point> ruta = bfs.findPath(origen, destino, u.getAllowedTerrains(), blocked,
				u.getTipo() == Unidad.TipoUnidad.HELICOPTERO);

		if (!ruta.isEmpty()) {
			Point fin = ruta.get(0).getInfo();
			Casilla cdest = casillaRepository.findByXAndY(fin.x, fin.y)
					.orElseThrow(() -> new IllegalStateException("Casilla no encontrada"));
			u.setCasilla(cdest);
			u.setX(fin.x);
			u.setY(fin.y);
			unidadRepository.saveAndFlush(u);
			System.out.printf("→ [Servicio] Unidad %d movida a (%d,%d)%n", u.getId(), u.getX(), u.getY());
		}
		return ruta;
	}

	private UnidadDTO toDTO(Unidad u) {
		return new UnidadDTO(u.getId(), u.getTipo().name(), u.getVida(), u.getAtaque(), u.getDefensa(), u.getAlcance(),
				u.getMovimiento(), u.getJugador(), u.getX(), u.getY(), false);
	}

	private int vidaPorTipo(Unidad.TipoUnidad tipo) {
		return switch (tipo) {
		case TANQUE -> 20;
		case INFANTERIA -> 10;
		case BARCO -> 15;
		case HELICOPTERO -> 40;
		default -> 100;
		};
	}

	/**
	 * Movimiento según tipo de unidad.
	 */
	private int movPorTipo(Unidad.TipoUnidad tipo) {
		return switch (tipo) {
		case TANQUE -> 4;
		case INFANTERIA -> 3;
		case BARCO -> 5;
		case HELICOPTERO -> 6;
		default -> 3;
		};
	}

}
