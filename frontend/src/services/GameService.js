var KEY = 'minesweeper';

function saveGame(gameId) {
    var list = loadObject();
    if (list) {
        list.push(gameId)
    } else {
        list = [gameId]
    }
    saveObject(list);
}

function removeGame(gameId) {
    var list = loadObject();
    if (list) {
        list = list.filter(it => it !== gameId)
        saveObject(list)
    }
}

function loadGamesSaved() {
    return loadObject();
}

function saveObject(obj) {
    localStorage.setItem(KEY, JSON.stringify(obj));
}

function loadObject() {
    var retrievedObject = localStorage.getItem(KEY);
    if (!retrievedObject) {
        return null;
    }
    return JSON.parse(retrievedObject);
}

export { saveGame, removeGame, loadGamesSaved}