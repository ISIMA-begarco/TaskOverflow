package tasko

import grails.gorm.DetachedCriteria
import groovy.transform.ToString

import org.apache.commons.lang.builder.HashCodeBuilder

@ToString(cache=true, includeNames=true, includePackage=false)
class UserLambda implements Serializable {

	private static final long serialVersionUID = 1

	User user
	Lambda lambda

	@Override
	boolean equals(other) {
		if (other instanceof UserLambda) {
			other.userId == user?.id && other.lambdaId == lambda?.id
		}
	}

	@Override
	int hashCode() {
		def builder = new HashCodeBuilder()
		if (user) builder.append(user.id)
		if (lambda) builder.append(lambda.id)
		builder.toHashCode()
	}

	static UserLambda get(long userId, long lambdaId) {
		criteriaFor(userId, lambdaId).get()
	}

	static boolean exists(long userId, long lambdaId) {
		criteriaFor(userId, lambdaId).count()
	}

	private static DetachedCriteria criteriaFor(long userId, long lambdaId) {
		UserLambda.where {
			user == User.load(userId) &&
			lambda == Lambda.load(lambdaId)
		}
	}

	static UserLambda create(User user, Lambda lambda) {
		def instance = new UserLambda(user: user, lambda: lambda)
		instance.save()
		instance
	}

	static boolean remove(User u, Lambda r) {
		if (u != null && r != null) {
			UserLambda.where { user == u && lambda == r }.deleteAll()
		}
	}

	static int removeAll(User u) {
		u == null ? 0 : UserLambda.where { user == u }.deleteAll()
	}

	static int removeAll(Lambda r) {
		r == null ? 0 : UserLambda.where { lambda == r }.deleteAll()
	}

	static constraints = {
		lambda validator: { Lambda r, UserLambda ur ->
			if (ur.user?.id) {
				UserLambda.withNewSession {
					if (UserLambda.exists(ur.user.id, r.id)) {
						return ['userRole.exists']
					}
				}
			}
		}
	}

	static mapping = {
		id composite: ['user', 'lambda']
		version false
	}
}
