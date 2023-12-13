package db.model

enum class Collection {
    GameSession
}

enum class Document {
    GameDeck,
    GameData,
    CurrentPlayer,
    ActivePlayers
}

enum class Field {
    GameDirection,
    CurrentPlayer
}