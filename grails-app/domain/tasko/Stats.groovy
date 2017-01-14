package tasko

class Stats {
    
    int numberQuestions
    int numberAnswers
    int helpValue
	
	static belongsTo = [user: User]
	
    static constraints = {
        
    }
}
