package ru.progwards.java2.lessons.graph;

import java.util.ArrayList;
import java.util.List;

/**
 * Задача поиска неиспользуемых объектов (вершин в ненаправленном графе - купированный алгоритм Тарьяна)
 */
public class FindUnused {
    /**
     * Объект дерева
     */
    class CObject {
        /**
         * Ссылки на другие объекты дерева
         */
        public List<CObject> references;
        /**
         * Пометка для алгоритма
         * @see Mark
         */
        Mark mark;
    }

    /**
     * Возвращает список неиспользуемых узлов
     * @param roots список корневых узлов (от них начнем сканирование)
     * @param objects список всех узлов графа
     * @return
     */
    public static List<CObject> find(List<CObject> roots, List<CObject> objects) {
        for (CObject o : objects)
            o.mark = Mark.NOT_USED; // белый
        for (CObject o : roots)
            if (o.mark == Mark.NOT_USED) // белый
                DFS(o);

        List<CObject> result = new ArrayList<CObject>();
        for (CObject o : objects)
            if (o.mark == Mark.NOT_USED) // не белый
                result.add(o);
        return result;
    }

    /**
     * Рекурсивный алгоритм Тарьяна - пометка всех зависимых не помеченных узлов
     * @param object стартовый узел
     */
    public static void DFS(CObject object) {
        object.mark = Mark.VISITED; // серый
        for (CObject o : object.references)
            if (o.mark == Mark.NOT_USED) // белый
                DFS(o);
        object.mark = Mark.IN_USE; // черный
    }


}
