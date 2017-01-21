package ovh.garcon.tasko

class Badge implements Comparable {

    String label
    String description
    Integer value
	
	static hasMany = [users: User]
    static belongsTo = User
	
    static constraints = {
        users nullable: true
    }

    static mapping = {
        order: 'asc'
    }

    @Override
    int compareTo(Object o) {
        int a = users != null ? users.size() : 0
        int b = ((Badge)o).getUsers() != null ? ((Badge)o).getUsers().size() : 0
        return a < b
    }

}
