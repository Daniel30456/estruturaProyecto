package co.edu.unbosque.juegoestructuraback.util;

import co.edu.unbosque.juegoestructuraback.model.Casilla;
import co.edu.unbosque.juegoestructuraback.model.Casilla.TipoTerreno;
import co.edu.unbosque.juegoestructuraback.repository.CasillaRepository;
import co.edu.unbosque.util.structure.linkedlist.MyLinkedList;
import co.edu.unbosque.structure.queue.QueueImpl;
import co.edu.unbosque.util.structure.stack.StackImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.awt.Point;
import java.util.Set;

@Component
public class BfsPathFinder {

    @Autowired
    private CasillaRepository casillaRepository;

    /**
     * Encuentra la ruta mínima (en pasos) desde origen hasta destino,
     * respetando los terrenos permitidos y evitando las casillas bloqueadas.
     *
     * @param origen           Punto de partida
     * @param destino          Punto de llegada
     * @param allowedTerrains  Conjunto de tipos de terreno por los que puede transitar
     * @param blocked          Lista de puntos ocupados (tropas amigas/enemigas)
     * @param flying           Si es true, ignora bloqueos de terreno y unidades
     * @return                 Ruta como MyLinkedList<Point>, vacía si no hay camino
     */
    public MyLinkedList<Point> findPath(
            Point origen,
            Point destino,
            Set<TipoTerreno> allowedTerrains,
            MyLinkedList<Point> blocked,
            boolean flying) {

        final int N = 12;
        boolean[][] visited = new boolean[N][N];
        Point[][] prev = new Point[N][N];
        QueueImpl<Point> queue = new QueueImpl<>();

        // Enqueue inicial
        queue.enqueue(origen);
        visited[origen.x][origen.y] = true;

        // Desplazamientos en 4 direcciones
        int[][] dirs = { { 1, 0 }, { -1, 0 }, { 0, 1 }, { 0, -1 } };
        boolean found = false;

        while (queue.size() > 0 && !found) {
            Point curr = queue.dequeue();
            if (curr.equals(destino)) {
                found = true;
                break;
            }
            for (int[] d : dirs) {
                int nx = curr.x + d[0], ny = curr.y + d[1];
                // Límites del mapa
                if (nx < 0 || nx >= N || ny < 0 || ny >= N) {
                    continue;
                }

                // Bloqueo por unidades (si no vuela)
                if (!flying) {
                    boolean isBlocked = false;
                    for (int i = 0; i < blocked.size(); i++) {
                        Point b = blocked.get(i).getInfo();
                        if (b.x == nx && b.y == ny) {
                            isBlocked = true;
                            break;
                        }
                    }
                    if (isBlocked) {
                        continue;
                    }
                }

                // Obtener casilla y su tipo
                Casilla c = casillaRepository.findByXAndY(nx, ny).orElse(null);
                if (c == null) {
                    continue;
                }
                TipoTerreno t = c.getTipo();

                // Bloqueo por terreno (si no vuela)
                if (!flying) {
                    if (c.isObstaculo() || !allowedTerrains.contains(t)) {
                        continue;
                    }
                }

                // Visitar
                if (!visited[nx][ny]) {
                    visited[nx][ny] = true;
                    prev[nx][ny] = curr;
                    queue.enqueue(new Point(nx, ny));
                }
            }
        }

        // Reconstruir ruta usando stack para invertir
        MyLinkedList<Point> path = new MyLinkedList<>();
        if (!visited[destino.x][destino.y]) {
            // Sin ruta válida
            return path;
        }
        StackImpl<Point> stack = new StackImpl<>();
        Point step = destino;
        while (step != null) {
            stack.push(step);
            if (step.equals(origen)) {
                break;
            }
            step = prev[step.x][step.y];
        }
        while (stack.size() > 0) {
            path.add(stack.pop());
        }
        return path;
    }
}
