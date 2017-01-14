package tasko

class Profile {
    
    String firstname
    String lastname
    String email
    
    static belongsTo = [user: User]

    static constraints = {
    }
}
