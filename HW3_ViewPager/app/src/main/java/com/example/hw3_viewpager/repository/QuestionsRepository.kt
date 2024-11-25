package com.example.hw3_viewpager.repository

import com.example.hw3_viewpager.QuestionModel

object QuestionsRepository {

    val questions = listOf(
        QuestionModel(
            id = 0,
            question = "Какой танк считается самым массовым в истории?",
            answers = listOf("Т-34", "M4 Sherman", "Т-54/Т-55", "Tiger I", "M1 Abrams")
        ),
        QuestionModel(
            id = 1,
            question = "В какой стране был создан танк 'Леопард 2'?",
            answers = listOf("США", "Германия", "Франция", "Великобритания")
        ),
        QuestionModel(
            id = 2,
            question = "Какой советский танк получил прозвище 'летучий'?",
            answers = listOf("Т-26", "БТ-7", "КВ-1", "ИС-2")
        ),
        QuestionModel(
            id = 3,
            question = "Как назывался британский первый в мире танк?",
            answers = listOf("Mark I", "Churchill", "Matilda", "Cromwell", "Comet")
        ),
        QuestionModel(
            id = 4,
            question = "Какой танк использовал двигатель 'Maybach HL230'?",
            answers = listOf("Panther", "Т-34", "M4 Sherman", "Tiger II")
        ),
        QuestionModel(
            id = 5,
            question = "Какая модель танка получила прозвище 'Эмча'?",
            answers = listOf("Т-34", "M3 Lee", "M4 Sherman", "M18 Hellcat")
        ),
        QuestionModel(
            id = 6,
            question = "Какой танк чаще всего называют 'танком Победы'?",
            answers = listOf("Т-34-85", "ИС-2", "КВ-1", "Т-70", "ИС-3")
        ),
        QuestionModel(
            id = 7,
            question = "Какую скорость мог развивать танк БТ-7 на колёсах?",
            answers = listOf("40 км/ч", "52 км/ч", "72 км/ч", "80 км/ч")
        ),
        QuestionModel(
            id = 8,
            question = "Какой танк был основным во время войны во Вьетнаме?",
            answers = listOf("M41 Walker Bulldog", "Т-55", "M48 Patton", "Т-62")
        ),
        QuestionModel(
            id = 9,
            question = "Что обозначает аббревиатура 'КВ' в названии танка?",
            answers = listOf("Кировский Военный", "Крупный Вооружённый", "Климент Ворошилов", "Командирский Вариант")
        ),
        QuestionModel(
            id = 10,
            question = "Какой танк известен своим уникальным круглым 'черепашьим' силуэтом башни?",
            answers = listOf("Т-62", "ИС-3", "M26 Pershing")
        ),
        QuestionModel(
            id = 11,
            question = "В каком году был впервые применён танк в бою?",
            answers = listOf("1914", "1916", "1918", "1920", "1922", "1924")
        ),
        QuestionModel(
            id = 12,
            question = "Какой современный танк называют 'Абрамс'?",
            answers = listOf("M1A1", "Leopard 2", "Challenger 2", "T-90")
        ),
        QuestionModel(
            id = 13,
            question = "Какая из этих стран НЕ производит собственные танки?",
            answers = listOf("Китай", "Индия", "Италия", "Япония", "КНДР")
        ),
        QuestionModel(
            id = 14,
            question = "Какой немецкий танк времён Второй мировой войны считался элитным, но очень дорогим?",
            answers = listOf("Tiger I", "Panzer IV", "Panther", "Elefant")
        )
    )

    fun getQuestionById(id: Int): QuestionModel? {
        return questions.find { it.id == id }
    }
}
