import tasko.*

class BootStrap {

    def init = { servletContext ->
        if (!User.count()) {
            def bob = new User( username: "bob", password: "bobiboo" ).save(failOnError: true)
            def alice = new User( username: "Alice", password: "caglisse" ).save(failOnError: true)
            def admin = new User( username: "admin", password: "admin" ).save(failOnError: true)
        
            def userRole = new Lambda('ROLE_USER').save()
            def adminRole = new Administrator('ROLE_USER').save()

            UserLambda.create bob, userRole
            UserLambda.create alice, userRole
            UserLambda.create admin, adminRole

            UserLambda.withSession {
                it.flush()
                it.clear()
            }
        }
    }
    def destroy = {
    }
}
