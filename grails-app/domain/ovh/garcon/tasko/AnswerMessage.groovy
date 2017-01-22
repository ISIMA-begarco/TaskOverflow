package ovh.garcon.tasko

class AnswerMessage extends MyMessage {

	static hasMany = [coms: ComMessage]
	
    static constraints = {
        coms nullable: true
    }

    static mapping = {
        coms sort: "date"
    }
}
