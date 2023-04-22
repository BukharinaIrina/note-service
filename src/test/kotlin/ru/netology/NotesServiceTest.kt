package ru.netology

import org.junit.Test

import org.junit.Assert.*
import org.junit.Before

class NotesServiceTest {

    @Before
    fun clearBeforeTest() {
        NotesService.clear()
    }

    @Test
    fun notesAdd_add() {
        val result = NotesService.notesAdd(
            Notes(
                userId = 1, title = "title", text = "text", privacy = 0, commentPrivacy = 0,
                privacyView = "privacyView", privacyComment = "privacyComment"
            )
        )
        assertEquals(1, result)
    }

    @Test
    fun notesCreateComment_createCommentFirst() {
        NotesService.notesAdd(
            Notes(
                userId = 1, title = "title", text = "text", privacy = 0, commentPrivacy = 0,
                privacyView = "privacyView", privacyComment = "privacyComment"
            )
        )
        val commentFirst = Comments(1, userId = 4, date = 04.23, message = "message")
        val result = NotesService.notesCreateComment(1, commentFirst)
        assertEquals(1, result)
    }

    @Test
    fun notesCreateComment_createCommentSecond() {
        NotesService.notesAdd(
            Notes(
                userId = 1, title = "title", text = "text", privacy = 0, commentPrivacy = 0,
                privacyView = "privacyView", privacyComment = "privacyComment"
            )
        )

        val commentFirst = Comments(1, userId = 4, date = 04.23, message = "message")
        val commentSecond = Comments(2, userId = 5, date = 04.23, message = "message")
        NotesService.notesCreateComment(1, commentFirst)
        val result = NotesService.notesCreateComment(1, commentSecond)
        assertEquals(2, result)
    }

    @Test(expected = NoteNotFoundException::class)
    fun notesCreateComment_noCreateComment() {
        NotesService.notesAdd(
            Notes(
                userId = 1, title = "title", text = "text", privacy = 0, commentPrivacy = 0,
                privacyView = "privacyView", privacyComment = "privacyComment"
            )
        )
        NotesService.notesCreateComment(2, comment = Comments(userId = 4, date = 04.23, message = "message"))
    }

    @Test
    fun notesEdit_edit() {
        NotesService.notesAdd(
            Notes(
                userId = 1, title = "title", text = "text", privacy = 0, commentPrivacy = 0,
                privacyView = "privacyView", privacyComment = "privacyComment"
            )
        )
        val result = NotesService.notesEdit(1)
        assertEquals(true, result)
    }

    @Test(expected = NoteNotFoundException::class)
    fun notesEdit_noEdit() {
        NotesService.notesAdd(
            Notes(
                userId = 1, title = "title", text = "text", privacy = 0, commentPrivacy = 0,
                privacyView = "privacyView", privacyComment = "privacyComment"
            )
        )
        NotesService.notesEdit(4)
    }

    @Test
    fun notesEditComment_editComment() {
        NotesService.notesAdd(
            Notes(
                userId = 1, title = "title", text = "text", privacy = 0, commentPrivacy = 0,
                privacyView = "privacyView", privacyComment = "privacyComment"
            )
        )
        NotesService.notesCreateComment(1, comment = Comments(userId = 4, date = 04.23, message = "message"))
        val result = NotesService.notesEditComment(1, 1)
        assertEquals(true, result)
    }

    @Test(expected = NoteNotFoundException::class)
    fun notesEditComment_noNote() {
        NotesService.notesAdd(
            Notes(
                userId = 1, title = "title", text = "text", privacy = 0, commentPrivacy = 0,
                privacyView = "privacyView", privacyComment = "privacyComment"
            )
        )
        NotesService.notesCreateComment(1, comment = Comments(userId = 4, date = 04.23, message = "message"))
        NotesService.notesEditComment(8, 1)
    }

    @Test(expected = CommentNotFoundException::class)
    fun notesEditComment_noComment() {
        NotesService.notesAdd(
            Notes(
                userId = 1, title = "title", text = "text", privacy = 0, commentPrivacy = 0,
                privacyView = "privacyView", privacyComment = "privacyComment"
            )
        )
        NotesService.notesCreateComment(1, comment = Comments(userId = 4, date = 04.23, message = "message"))
        NotesService.notesEditComment(1, 5)
    }


    @Test
    fun notesDelete_delete() {
        NotesService.notesAdd(
            Notes(
                userId = 1, title = "title", text = "text", privacy = 0, commentPrivacy = 0,
                privacyView = "privacyView", privacyComment = "privacyComment"
            )
        )
        val result = NotesService.notesDelete(1)
        assertEquals(true, result)
    }

    @Test(expected = NoteNotFoundException::class)
    fun notesDelete_noNote() {
        NotesService.notesAdd(
            Notes(
                userId = 1, title = "title", text = "text", privacy = 0, commentPrivacy = 0,
                privacyView = "privacyView", privacyComment = "privacyComment"
            )
        )
        NotesService.notesDelete(2)
    }

    @Test
    fun notesDeleteComment_deleteComment() {
        NotesService.notesAdd(
            Notes(
                userId = 1, title = "title", text = "text", privacy = 0, commentPrivacy = 0,
                privacyView = "privacyView", privacyComment = "privacyComment"
            )
        )
        NotesService.notesCreateComment(1, comment = Comments(userId = 4, date = 04.23, message = "message"))
        val result = NotesService.notesDeleteComment(1, 1)
        assertEquals(true, result)
    }

    @Test(expected = NoteNotFoundException::class)
    fun notesDeleteComment_noNote() {
        NotesService.notesAdd(
            Notes(
                userId = 1, title = "title", text = "text", privacy = 0, commentPrivacy = 0,
                privacyView = "privacyView", privacyComment = "privacyComment"
            )
        )
        NotesService.notesCreateComment(1, comment = Comments(userId = 4, date = 04.23, message = "message"))
        NotesService.notesDeleteComment(3, 1)
    }

    @Test(expected = CommentNotFoundException::class)
    fun notesDeleteComment_noComment() {
        NotesService.notesAdd(
            Notes(
                userId = 1, title = "title", text = "text", privacy = 0, commentPrivacy = 0,
                privacyView = "privacyView", privacyComment = "privacyComment"
            )
        )
        NotesService.notesCreateComment(1, comment = Comments(userId = 4, date = 04.23, message = "message"))
        NotesService.notesDeleteComment(1, 9)
    }

    @Test
    fun notesRestoreComment_restoreComment() {
        NotesService.notesAdd(
            Notes(
                userId = 1, title = "title", text = "text", privacy = 0, commentPrivacy = 0,
                privacyView = "privacyView", privacyComment = "privacyComment"
            )
        )
        NotesService.notesCreateComment(1, comment = Comments(userId = 4, date = 04.23, message = "message"))
        NotesService.notesDeleteComment(1, 1)
        val result = NotesService.notesRestoreComment(1, 1)
        assertEquals(true, result)
    }

    @Test(expected = NoteNotFoundException::class)
    fun notesRestoreComment_noNote() {
        NotesService.notesAdd(
            Notes(
                userId = 1, title = "title", text = "text", privacy = 0, commentPrivacy = 0,
                privacyView = "privacyView", privacyComment = "privacyComment"
            )
        )
        NotesService.notesCreateComment(1, comment = Comments(userId = 4, date = 04.23, message = "message"))
        NotesService.notesDeleteComment(1, 1)
        NotesService.notesRestoreComment(2, 1)
    }

    @Test(expected = CommentNotFoundException::class)
    fun notesRestoreComment_noComment() {
        NotesService.notesAdd(
            Notes(
                userId = 1, title = "title", text = "text", privacy = 0, commentPrivacy = 0,
                privacyView = "privacyView", privacyComment = "privacyComment"
            )
        )
        NotesService.notesCreateComment(1, comment = Comments(userId = 4, date = 04.23, message = "message"))
        NotesService.notesDeleteComment(1, 1)
        NotesService.notesRestoreComment(1, 7)
    }

    @Test
    fun notesGet_get() {
        NotesService.notesAdd(
            Notes(
                userId = 1, title = "title", text = "text", privacy = 0, commentPrivacy = 0,
                privacyView = "privacyView", privacyComment = "privacyComment"
            )
        )
        NotesService.notesAdd(
            Notes(
                userId = 1, title = "title", text = "text", privacy = 0, commentPrivacy = 0,
                privacyView = "privacyView", privacyComment = "privacyComment"
            )
        )
        val userNotes = mutableListOf<Notes>(
            Notes(1, 1, "title", "text", 0, 0, "privacyView", "privacyComment"),
            Notes(2, 1, "title", "text", 0, 0, "privacyView", "privacyComment")
        )
        val result = NotesService.notesGet(1)
        assertEquals(userNotes, result)
    }

    @Test(expected = UserNotFoundException::class)
    fun notesGet_noUser() {
        NotesService.notesAdd(
            Notes(
                userId = 1, title = "title", text = "text", privacy = 0, commentPrivacy = 0,
                privacyView = "privacyView", privacyComment = "privacyComment"
            )
        )
        NotesService.notesAdd(
            Notes(
                userId = 1, title = "title", text = "text", privacy = 0, commentPrivacy = 0,
                privacyView = "privacyView", privacyComment = "privacyComment"
            )
        )
        NotesService.notesGet(3)
    }

    @Test
    fun notesGetById_getById() {
        NotesService.notesAdd(
            Notes(
                userId = 1, title = "title", text = "text", privacy = 0, commentPrivacy = 0,
                privacyView = "privacyView", privacyComment = "privacyComment"
            )
        )
        val result = NotesService.notesGetById(1)
        assertEquals(1, result.noteId)
    }

    @Test(expected = NoteNotFoundException::class)
    fun notesGetById_noNote() {
        NotesService.notesAdd(
            Notes(
                userId = 1, title = "title", text = "text", privacy = 0, commentPrivacy = 0,
                privacyView = "privacyView", privacyComment = "privacyComment"
            )
        )
        NotesService.notesGetById(6)
    }

    @Test
    fun notesGetComments_getComments() {
        NotesService.notesAdd(
            Notes(
                userId = 1, title = "title", text = "text", privacy = 0, commentPrivacy = 0,
                privacyView = "privacyView", privacyComment = "privacyComment"
            )
        )
        NotesService.notesCreateComment(1, comment = Comments(userId = 4, date = 04.23, message = "message"))
        NotesService.notesCreateComment(1, comment = Comments(userId = 5, date = 04.23, message = "message"))
        val noteId = 1
        val userId = 1
        val array = arrayListOf(
            Comments(1, 4, 4.23, "message"),
            Comments(2, 5, 4.23, "message")
        )
        val arrayComments = arrayListOf("noteId = $noteId ", "userId = $userId ", array)
        val result = NotesService.notesGetComments(1, 1)
        assertEquals(arrayComments, result)
    }

    @Test(expected = NoteNotFoundException::class)
    fun notesGetComments_noNote() {
        NotesService.notesAdd(
            Notes(
                userId = 1, title = "title", text = "text", privacy = 0, commentPrivacy = 0,
                privacyView = "privacyView", privacyComment = "privacyComment"
            )
        )
        NotesService.notesCreateComment(1, comment = Comments(userId = 4, date = 04.23, message = "message"))
        NotesService.notesCreateComment(1, comment = Comments(userId = 5, date = 04.23, message = "message"))
        NotesService.notesGetComments(4, 1)
    }

    @Test(expected = NoteNotFoundException::class)
    fun notesGetComments_noUser() {
        NotesService.notesAdd(
            Notes(
                userId = 1, title = "title", text = "text", privacy = 0, commentPrivacy = 0,
                privacyView = "privacyView", privacyComment = "privacyComment"
            )
        )
        NotesService.notesCreateComment(1, comment = Comments(userId = 4, date = 04.23, message = "message"))
        NotesService.notesCreateComment(1, comment = Comments(userId = 5, date = 04.23, message = "message"))
        NotesService.notesGetComments(1, 4)
    }

    @Test(expected = CommentsNotFoundException::class)
    fun notesGetComments_noComments() {
        NotesService.notesAdd(
            Notes(
                userId = 1, title = "title", text = "text", privacy = 0, commentPrivacy = 0,
                privacyView = "privacyView", privacyComment = "privacyComment"
            )
        )
        NotesService.notesGetComments(1, 1)
    }
}