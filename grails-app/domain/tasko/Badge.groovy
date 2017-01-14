package tasko

class Badge {

    String label
    String description
	
	static hasMany = [users: User]
	
    static constraints = {
        users nullable: true
    }
    
}
