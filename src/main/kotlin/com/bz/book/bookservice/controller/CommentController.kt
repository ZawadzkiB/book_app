package com.bz.book.bookservice.controller

import com.bz.book.bookservice.service.CommentRequest
import com.bz.book.bookservice.service.CommentsService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/comment")
class CommentController(val commentsService: CommentsService) {

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    fun addComment(@RequestBody commentRequest : CommentRequest) = commentsService.addComment(commentRequest)

}