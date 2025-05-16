package co.edu.unbosque.juegoestructuraback.service;

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
	 * Mueve una unidad aplicando BFS y retornando la ruta en MyLinkedList<Point>.
	 */
	public MyLinkedList<Point> moverUnidad(Long id, int destX, int destY) {
		Unidad u = unidadRepository.findById(id).orElseThrow();

		// Terrenos permitidos como array
		Set<TipoTerreno> allowedSet = u.getAllowedTerrains();
		TipoTerreno[] allowed = new TipoTerreno[allowedSet.size()];
		int idx = 0;
		for (TipoTerreno t : allowedSet) {
			allowed[idx++] = t;
		}

		// Posiciones bloqueadas excepto la actual
		MyLinkedList<Point> blocked = new MyLinkedList<>();
		for (Unidad other : unidadRepository.findAll()) {
			if (!other.getId().equals(id)) {
				blocked.add(new Point(other.getCasilla().getX(), other.getCasilla().getY()));
			}
		}

		boolean flying = (u.getTipo() == Unidad.TipoUnidad.HELICOPTERO);
		Point origen = new Point(u.getCasilla().getX(), u.getCasilla().getY());
		Point destino = new Point(destX, destY);

		MyLinkedList<Point> ruta = bfs.findPath(origen, destino, allowed, blocked, flying);

		// Actualizar posición final si llegó
		if (ruta.size() > 0) {
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
