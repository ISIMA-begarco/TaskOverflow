package tasko

class MyMessage {

	static belongsTo = [user: User]
	String content
	Date date
    int value
    Boolean isBest

    static constraints = {
    }
}
