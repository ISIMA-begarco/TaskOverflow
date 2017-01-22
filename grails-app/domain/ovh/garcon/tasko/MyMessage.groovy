package ovh.garcon.tasko

import grails.databinding.BindingFormat

class MyMessage {

	static belongsTo = [user: User, question: Question]
	String content
    @BindingFormat('dd-mm-yyyy hh:mm')
	Date date
    int value
    Question question

    static constraints = {
        user nullable: true
        question nullable: true
    }

    static mapping = {
        sort value: "desc"
        content type: "text"
    }

}
