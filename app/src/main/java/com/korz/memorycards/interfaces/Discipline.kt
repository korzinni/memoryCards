package com.korz.memorycards.interfaces


interface Chapter {
    val parentChapter: Chapter?
    val childChapters: List<Chapter>?
    val id: Long
    val image: String
}

interface Card {
    val chapter: Chapter
    val parentCard: Card
    val image: String
    val question: String
    val answer: String
    val hint: String
    val rating: String
    val possibleAnswers: List<Answer>?

}

interface Answer {
    val id: Long
    val text: String
}

interface CardModel {

}