package storage;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class InMemoryStorage implements Storage {
    private Map<String, Map<Integer, String>> userTodos;

    public InMemoryStorage() {
        this.userTodos = new HashMap<>();
    }

    public int add(String user, String todo) {
        if (!userTodos.containsKey(user)) {
            userTodos.put(user, new HashMap<>());
        }

        var toDos = userTodos.get(user);
        int id = toDos.size();
        toDos.put(id, todo);

        return id;
    }

    public void remove(String user, int todoID) {
        var toDos = userTodos.get(user);
        if (toDos == null || !toDos.containsKey(todoID)) {
            return;
        }

        toDos.remove(todoID);
    }

    @Override
    public Map<Integer, String> list(String user) {
        user = user.trim();
        var toDos = userTodos.get(user);

        if (toDos == null) {
            return Collections.emptyMap();
        }

        return Collections.unmodifiableMap(toDos);
    }
}