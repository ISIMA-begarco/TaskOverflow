package ovh.garcon.tasko

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

@EqualsAndHashCode(includes='username')
@ToString(includes='username', includeNames=true, includePackage=false)
class User implements Serializable {

	private static final long serialVersionUID = 1

	transient springSecurityService

	String username
	String password
	boolean enabled = true
	boolean accountExpired
	boolean accountLocked
	boolean passwordExpired

	static hasMany = [messages: MyMessage, badges: Badge, questions: Question]
    Set<Question> questions
    static hasOne = [profil: Profile]

	Set<Role> getAuthorities() {
		UserRole.findAllByUser(this)*.role
	}

	def beforeInsert() {
		encodePassword()
	}

	def beforeUpdate() {
		if (isDirty('password')) {
			encodePassword()
		}
	}

	protected void encodePassword() {
		password = springSecurityService?.passwordEncoder ? springSecurityService.encodePassword(password) : password
	}

	Integer getReputation() {
		Integer rep = 0
		for(MyMessage m : messages)
			rep += m.getValue()
		for(Question m : questions)
			rep += m.getValue()
		for(Badge m : badges)
			rep += m.getValue()
		return rep
	}

    Set<Question> getQuestions() {
        return questions?.sort{it.getValue()}?.reverse(true)
    }

	static transients = ['springSecurityService']

	static constraints = {
		password blank: false, password: true
		username blank: false, unique: true
		messages nullable: true
		badges nullable: true
		questions nullable: true
		profil nullable: true
	}

	static mapping = {
		password column: '`password`'
        messages sort: "value", order: "desc"
	}
}
