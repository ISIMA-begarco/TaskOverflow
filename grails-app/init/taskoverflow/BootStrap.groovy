package taskoverflow

import org.springframework.context.MessageSource
import ovh.garcon.tasko.*

class BootStrap {

    def springSecurityService

    def init = { servletContext ->

        //definition of badges
        def badgeSuperman = new Badge(label: "Superman", description: "badges.superman.desc", value: 10, users: []).save(failOnError: true)
        def badgeHercule = new Badge(label: "Hercule", description: "badges.hercule.desc", value: 4, users: []).save(failOnError: true)
        def badgeOne = new Badge(label: "N°1", description: "badges.nbone.desc", value: 4, users: []).save(failOnError: true)

        // security roles definition
        def userRole = Role.findByAuthority('ROLE_USER') ?: new Role(authority: 'ROLE_USER').save(failOnError: true)
        def adminRole = Role.findByAuthority('ROLE_ADMIN') ?: new Role(authority: 'ROLE_ADMIN').save(failOnError: true)

        // user definition
        def adminUser = User.findByUsername('admin') ?: new User(
                username: 'admin',
                password: 'admin',
                badges: [badgeHercule, badgeOne],
                enabled: true).save(failOnError: true)
        def bob = User.findByUsername('bob') ?: new User(
                username: 'bob',
                password: 'bob',
                badges: [badgeOne],
                enabled: true).save(failOnError: true)
        def alice = User.findByUsername('alice') ?: new User(
                username: 'alice',
                password: 'aliceglisse',
                badges: [badgeOne],
                enabled: true).save(failOnError: true)

        // add of users
        if (!adminUser.authorities.contains(adminRole)) {
            UserRole.create adminUser, adminRole
        }
        if (!bob.authorities.contains(userRole)) {
            UserRole.create bob, userRole
        }
        if (!alice.authorities.contains(userRole)) {
            UserRole.create alice, userRole
        }

        // definition of profiles
        def profileAdmin = new Profile(
                user: adminUser,
                firstname: "William",
                lastname: "Adama",
                email: "admin@taskoverflow.com"
        ).save(failOnError: true)
        def profileBob = new Profile(
                user: bob,
                image: "https://www.alpineclub.ch/media/image/d4/59/84/600219-1.jpg"
        ).save(failOnError: true)
        def profileAlice = new Profile(
                user: alice,
                firstname: "Alice",
                email: "alice@taskoverflow.com"
        ).save(failOnError: true)

        // definition of tags
        def tag1 = new Tag(label: "Java").save(failOnError: true)
        def tag2 = new Tag(label: "C++").save(failOnError: true)
        def tag3 = new Tag(label: "Space").save(failOnError: true)
        def tag4 = new Tag(label: "Nature").save(failOnError: true)
        def tag5 = new Tag(label: "Cinema").save(failOnError: true)
        def tag6 = new Tag(label: "Rugby").save(failOnError: true)

        // definition of questions
        def qm1 = new QuestionMessage(
                user: bob,
                content: "How to do it quick? Help me please!",
                date: new Date(),
                value: 5).save(failOnError: true)
        def qm2 = new QuestionMessage(
                user: bob,
                content: "I search for her. Help me please!",
                date: new Date(),
                value: 0).save(failOnError: true)
        def qm3 = new QuestionMessage(
                user: alice,
                content: "I want to create a universe, how can I do?",
                date: new Date(),
                value: 3).save(failOnError: true)
        def question1 = new Question(
                title: "Create a OOP language",
                question: qm1,
                tags: [tag1, tag2, tag3],
                user: bob,
                isSolved: false
        ).save(failOnError: true)
        def question2 = new Question(
                title: "Where is Alice?",
                question: qm2,
                user: bob,
                isSolved: true
        ).save(failOnError: true)
        def question3 = new Question(
                title: "How to do a big bang?",
                question: qm3,
                tags: [tag1, tag4],
                user: alice,
                isSolved: false
        ).save(failOnError: true)

        // definition of answers
        def answer1 = new AnswerMessage(
                user: bob,
                question: question3,
                content: "Like that...",
                date: new Date(),
                value: -2
        ).save(failOnError: true)
        def answer2 = new AnswerMessage(
                user: alice,
                question: question2,
                content: "Google is your friend!",
                date: new Date(),
                value: 1
        ).save(failOnError: true)
        def answer3 = new AnswerMessage(
                user: alice,
                question: question1,
                content: "Perpetual sincerity out suspected necessary one but provision satisfied. Respect nothing use set waiting pursuit nay you looking. If on prevailed concluded ye abilities. Address say you new but minuter greater. Do denied agreed in innate. Can and middletons thoroughly themselves him. Tolerably sportsmen belonging in september no am immediate newspaper. Theirs expect dinner it pretty indeed having no of. Principle september she conveying did eat may extensive.",
                date: new Date(),
                value: 3
        ).save(failOnError: true)

        def coms = [
                new ComMessage(
                        user: alice,
                        content: "Already said...",
                        date: new Date()),
                new ComMessage(
                        user: adminUser,
                        content: "I know.",
                        date: new Date()),
        ]

        def answer4 = new AnswerMessage(
                user: adminUser,
                question: question1,
                content: "Perpetual sincerity out suspected necessary one but provision satisfied. Respect nothing use set waiting pursuit nay you looking. If on prevailed concluded ye abilities. Address say you new but minuter greater. Do denied agreed in innate. Can and middletons thoroughly themselves him. Tolerably sportsmen belonging in september no am immediate newspaper. Theirs expect dinner it pretty indeed having no of. Principle september she conveying did eat may extensive.",
                date: new Date(),
                value: 1,
                coms: coms
        ).save(failOnError: true)
    }
    def destroy = {
    }
}
