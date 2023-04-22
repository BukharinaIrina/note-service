package ru.netology

data class Notes(
    var noteId: Int = 1,
    val userId: Int,
    val title: String,
    val text: String,
    val privacy: Int,
    val commentPrivacy: Int,
    val privacyView: String,
    val privacyComment: String,
    var comment: MutableList<Comments> = mutableListOf(),
    var deleteComment: MutableList<Comments> = mutableListOf()
) {
    override fun toString(): String {
        return "Note: noteId = $noteId, userId = $userId, title = $title, text = $text, privacy = $privacy, " +
                "commentPrivacy = $commentPrivacy, privacyView = $privacyView, privacyComment = $privacyComment, " +
                "comment = $comment"
    }
}

data class Comments(
    var commentId: Int = 1,
    val userId: Int,
    val date: Double,
    val message: String
) {
    override fun toString(): String {
        return "Comments: commentId = $commentId, userId = $userId, date = $date, message = $message"
    }
}

object NotesService {
    var notes = mutableListOf<Notes>()
    var userNotes = mutableListOf<Notes>()
    private var lastNoteId = 1

    fun clear() {
        notes = mutableListOf()
        userNotes = mutableListOf()
        lastNoteId = 1
    }

    fun notesAdd(note: Notes): Int {
        notes += note.copy(noteId = lastNoteId++)
        val noteId = lastNoteId - 1
        return noteId
    }

    fun notesCreateComment(noteId: Int, comment: Comments): Int {
        for ((i, notesList) in notes.withIndex()) {
            if (notesList.noteId == noteId) {
                if (notes[i].comment.isNullOrEmpty()) {
                    notes[i].comment += comment.copy(commentId = 1)
                    return comment.commentId
                } else {
                    notes[i].comment += comment.copy(commentId = size(notes[i].comment) + 1)
                    return comment.commentId
                }
            }
        }
        throw NoteNotFoundException("No note with id $noteId")
    }

    fun notesEdit(noteId: Int): Boolean {
        for ((i, notesList) in notes.withIndex()) {
            if (notesList.noteId == noteId) {
                notes[i] = notes[i].copy(text = "TEXT")
                display(notes[i])
                return true
            }
        }
        throw NoteNotFoundException("No note with id $noteId")
    }

    fun notesEditComment(noteId: Int, commentId: Int): Boolean {
        for ((i, notesList) in notes.withIndex()) {
            if (notesList.noteId == noteId) {
                for ((j, commentsList) in notes[i].comment.withIndex())
                    if (commentsList.commentId == commentId) {
                        notes[i].comment[j] = notes[i].comment[j].copy(message = "MESSAGE")
                        display(notes[i].comment[j])
                        return true
                    }
                throw CommentNotFoundException("No comment with id $commentId")
            }
        }
        throw NoteNotFoundException("No note with id $noteId")
    }

    fun notesDelete(noteId: Int): Boolean {
        for ((i, notesList) in notes.withIndex()) {
            if (notesList.noteId == noteId) {
                notes.removeAt(i)
                displayList(notes)
                return true
            }
        }
        throw NoteNotFoundException("No note with id $noteId")
    }

    fun notesDeleteComment(noteId: Int, commentId: Int): Boolean {
        for ((i, notesList) in notes.withIndex()) {
            if (notesList.noteId == noteId) {
                for ((j, commentsList) in notes[i].comment.withIndex())
                    if (commentsList.commentId == commentId) {
                        notes[i].deleteComment += notes[i].comment[j].copy()
                        notes[i].comment.removeAt(j)
                        displayList(notes[i].comment)
                        return true
                    }
                throw CommentNotFoundException("No comment with id $commentId")
            }
        }
        throw NoteNotFoundException("No note with id $noteId")
    }

    fun notesRestoreComment(noteId: Int, commentId: Int): Boolean {
        for ((i, notesList) in notes.withIndex()) {
            if (notesList.noteId == noteId) {
                for ((j, deleteCommentsList) in notes[i].deleteComment.withIndex())
                    if (deleteCommentsList.commentId == commentId) {
                        notes[i].comment += notes[i].deleteComment[j].copy()
                        notes[i].comment.sortBy { it.commentId }
                        notes[i].deleteComment.removeAt(j)
                        displayList(notes[i].comment)
                        return true
                    }
                throw CommentNotFoundException("No comment with id $commentId")
            }
        }
        throw NoteNotFoundException("No note with id $noteId")
    }

    fun notesGet(userId: Int): MutableList<Notes> {
        for ((i, notesList) in notes.withIndex()) {
            if (notesList.userId == userId) {
                userNotes += notes[i].copy()
            }
        }
        if (!userNotes.isNullOrEmpty()) {
            displayList(userNotes)
            return userNotes
        } else {
            throw UserNotFoundException("No User with id $userId")
        }
    }

    fun notesGetById(noteId: Int): Notes {
        for ((i, notesList) in notes.withIndex()) {
            if (notesList.noteId == noteId) {
                display(notes[i])
                return notes[i]
            }
        }
        throw NoteNotFoundException("No note with id $noteId")
    }

    fun notesGetComments(noteId: Int, userId: Int): ArrayList<Any> {
        for ((i, notesList) in notes.withIndex()) {
            if (notesList.noteId == noteId && notesList.userId == userId) {
                if (!notes[i].comment.isNullOrEmpty()) {
                    var arrayComments = arrayListOf("noteId = $noteId ", "userId = $userId ", notes[i].comment)
                    display(arrayComments)
                    return arrayComments
                }
                throw CommentsNotFoundException("No comments with note id $noteId")
            }
        }
        throw NoteNotFoundException("No note with id $noteId or No User with id $userId")
    }
}

fun main() {
    NotesService.notesAdd(
        Notes(
            userId = 1, title = "title", text = "text", privacy = 0, commentPrivacy = 0, privacyView = "privacyView",
            privacyComment = "privacyComment"
        )
    )
    NotesService.notesAdd(
        Notes(
            userId = 2, title = "title", text = "text", privacy = 0, commentPrivacy = 0, privacyView = "privacyView",
            privacyComment = "privacyComment"
        )
    )
    NotesService.notesAdd(
        Notes(
            userId = 3, title = "title", text = "text", privacy = 0, commentPrivacy = 0, privacyView = "privacyView",
            privacyComment = "privacyComment"
        )
    )
    NotesService.notesAdd(
        Notes(
            userId = 1, title = "title", text = "text", privacy = 0, commentPrivacy = 0, privacyView = "privacyView",
            privacyComment = "privacyComment"
        )
    )
    displayList(NotesService.notes)
    println()
    NotesService.notesCreateComment(1, comment = Comments(userId = 4, date = 04.23, message = "message"))
    NotesService.notesCreateComment(1, comment = Comments(userId = 5, date = 04.23, message = "message"))
    NotesService.notesCreateComment(1, comment = Comments(userId = 4, date = 04.23, message = "message"))
    NotesService.notesCreateComment(1, comment = Comments(userId = 5, date = 04.23, message = "message"))
    NotesService.notesCreateComment(2, comment = Comments(userId = 6, date = 04.23, message = "message"))
    NotesService.notesCreateComment(3, comment = Comments(userId = 6, date = 04.23, message = "message"))
    NotesService.notesCreateComment(3, comment = Comments(userId = 7, date = 04.23, message = "message"))
    displayList(NotesService.notes)
    println()
    NotesService.notesEdit(2)
    println()
    NotesService.notesEditComment(1, 3)
    println()
    NotesService.notesDelete(2)
    println()
    NotesService.notesDeleteComment(1, 2)
    println()
    NotesService.notesRestoreComment(1, 2)
    println()
    NotesService.notesGet(1)
    println()
    NotesService.notesGetById(1)
    println()
    NotesService.notesGetComments(1, 1)
}