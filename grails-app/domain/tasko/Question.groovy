package tasko

class Question {

	QuestionMessage question
	static hasMany = [answers: AnswerMessage]
	Boolean isSolved

    static constraints = {
    }
}
