package ovh.garcon.tasko

class Question {

	String title
	QuestionMessage question
	static hasMany = [answers: AnswerMessage, tags: Tag]
	static hasOne = [user: User]
	Boolean isSolved

    static constraints = {
		answers nullable: true
		tags nullable: true
		user nullable: true
    }

	int getValue() {
		int res = question.getValue()
		return res
	}
}
