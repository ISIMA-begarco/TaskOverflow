package ovh.garcon.tasko

import grails.databinding.BindingFormat

class MyMessage {

	static belongsTo = [user: User]
	String content
    @BindingFormat('dd-mm-yyyy hh:mm')
	Date date
    int value

    static constraints = {
        user nullable: true
    }

    static mapping = {
        sort value: "desc"
        content type: "text"
    }

}
