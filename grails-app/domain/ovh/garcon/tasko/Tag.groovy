package ovh.garcon.tasko

class Tag {

    String label

    static hasMany = [questions: Question]
    static belongsTo = Question
    static constraints = {
        questions nullable: true
        label unique: true
    }

    @Override
    String toString() {
        return (label ?: "")
    }
}
