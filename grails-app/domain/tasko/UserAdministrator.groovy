package tasko

import grails.gorm.DetachedCriteria
import groovy.transform.ToString

import org.apache.commons.lang.builder.HashCodeBuilder

@ToString(cache=true, includeNames=true, includePackage=false)
class UserAdministrator implements Serializable {

	private static final long serialVersionUID = 1

	User user
	Administrator administrator

	@Override
	boolean equals(other) {
		if (other instanceof UserAdministrator) {
			other.userId == user?.id && other.administratorId == administrator?.id
		}
	}

	@Override
	int hashCode() {
		def builder = new HashCodeBuilder()
		if (user) builder.append(user.id)
		if (administrator) builder.append(administrator.id)
		builder.toHashCode()
	}

	static UserAdministrator get(long userId, long administratorId) {
		criteriaFor(userId, administratorId).get()
	}

	static boolean exists(long userId, long administratorId) {
		criteriaFor(userId, administratorId).count()
	}

	private static DetachedCriteria criteriaFor(long userId, long administratorId) {
		UserAdministrator.where {
			user == User.load(userId) &&
			administrator == Administrator.load(administratorId)
		}
	}

	static UserAdministrator create(User user, Administrator administrator) {
		def instance = new UserAdministrator(user: user, administrator: administrator)
		instance.save()
		instance
	}

	static boolean remove(User u, Administrator r) {
		if (u != null && r != null) {
			UserAdministrator.where { user == u && administrator == r }.deleteAll()
		}
	}

	static int removeAll(User u) {
		u == null ? 0 : UserAdministrator.where { user == u }.deleteAll()
	}

	static int removeAll(Administrator r) {
		r == null ? 0 : UserAdministrator.where { administrator == r }.deleteAll()
	}

	static constraints = {
		administrator validator: { Administrator r, UserAdministrator ur ->
			if (ur.user?.id) {
				UserAdministrator.withNewSession {
					if (UserAdministrator.exists(ur.user.id, r.id)) {
						return ['userRole.exists']
					}
				}
			}
		}
	}

	static mapping = {
		id composite: ['user', 'administrator']
		version false
	}
}
