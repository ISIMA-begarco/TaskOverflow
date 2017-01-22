package ovh.garcon.tasko

class Question {

	String title
	QuestionMessage question
	static hasMany = [answers: AnswerMessage, tags: Tag]
    Set<AnswerMessage> answers
	static hasOne = [user: User]
	Boolean isSolved

    static constraints = {
		answers nullable: true
		tags nullable: true
		user nullable: true
    }

	static mapping = {
		answers sort: "value"
	}

    Set<AnswerMessage> getAnswers() {
        return answers?.sort{it.value}?.reverse(true)
    }

	int getValue() {
		int res = question.getValue()
		return res
	}
}
