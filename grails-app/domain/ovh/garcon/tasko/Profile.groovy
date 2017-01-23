package ovh.garcon.tasko

class Profile {
    
    String firstname
    String lastname
    String email
    String image
    
    static belongsTo = [user: User]

    static constraints = {
        user nullable: true, display: false, editable: false
        firstname nullable: true
        lastname nullable: true
        email nullable: true
        image nullable: true
    }

    String getImage() {
        if(image!=null)
            return image
        else
            return "0"
    }

}
