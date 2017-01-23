package ovh.garcon.tasko

import grails.plugin.springsecurity.annotation.Secured

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class ComMessageController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]
/**
    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond ComMessage.list(params), model:[comMessageCount: ComMessage.count()]
    }

    def show(ComMessage comMessage) {
        respond comMessage
    }
**/
    @Secured(['ROLE_USER', 'ROLE_ADMIN'])
    def create() {
        respond new ComMessage(params)
    }

    @Secured(['ROLE_USER', 'ROLE_ADMIN'])
    @Transactional
    def save(ComMessage comMessage) {
        if (comMessage == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (comMessage.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond comMessage.errors, view:'create'
            return
        }

        comMessage.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'comMessage.label', default: 'ComMessage'), comMessage.id])
                redirect comMessage
            }
            '*' { respond comMessage, [status: CREATED] }
        }
    }
/**
    def edit(ComMessage comMessage) {
        respond comMessage
    }

    @Transactional
    def update(ComMessage comMessage) {
        if (comMessage == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (comMessage.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond comMessage.errors, view:'edit'
            return
        }

        comMessage.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'comMessage.label', default: 'ComMessage'), comMessage.id])
                redirect comMessage
            }
            '*'{ respond comMessage, [status: OK] }
        }
    }

    @Transactional
    def delete(ComMessage comMessage) {

        if (comMessage == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        comMessage.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'comMessage.label', default: 'ComMessage'), comMessage.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }
**/
    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'comMessage.label', default: 'ComMessage'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }

    @Secured(['ROLE_USER','ROLE_ADMIN'])
    @Transactional
    def add(){
        ComMessage com = new ComMessage(
                date: new Date(),
                content: params.content,
                user: (User)getAuthenticatedUser(),
                parentMessage: MyMessage.get(params.mId as Integer),
                value: 0,
                question: Question.get(params.qId as Integer)
        )

        if (com == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (com.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond com.errors, view:'create'
            return
        }

        com.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'comMessage.label', default: 'Question'), com.id])
                redirect controller:"question", action:"show", id:params.qId, method:"GET"
            }
            '*' { respond com, [status: CREATED] }
        }
    }
}
