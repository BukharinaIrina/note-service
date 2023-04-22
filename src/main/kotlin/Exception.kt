package ru.netology

class NoteNotFoundException(message: String) : RuntimeException(message)
class CommentNotFoundException(message: String) : RuntimeException(message)
class CommentsNotFoundException(message: String) : RuntimeException(message)
class UserNotFoundException(message: String) : RuntimeException(message)