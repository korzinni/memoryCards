package com.korz.memorycards.interfaces

interface Card {
    val chapter: Folder
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