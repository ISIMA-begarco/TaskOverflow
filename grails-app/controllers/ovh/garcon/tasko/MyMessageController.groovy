package ovh.garcon.tasko

import grails.plugin.springsecurity.annotation.Secured

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class MyMessageController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]
/**
    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond MyMessage.list(params).sort{it.value}, model:[myMessageCount: MyMessage.count()]
    }

    def show(MyMessage myMessage) {
        respond myMessage
    }

    def create() {
        respond new MyMessage(params)
    }
**/
    @Secured(['ROLE_USER', 'ROLE_ADMIN'])
    @Transactional
    def save(MyMessage myMessage) {
        if (myMessage == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (myMessage.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond myMessage.errors, view:'create'
            return
        }

        myMessage.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'myMessage.label', default: 'MyMessage'), myMessage.id])
                redirect myMessage
            }
            '*' { respond myMessage, [status: CREATED] }
        }
    }

    @Secured(['ROLE_USER', 'ROLE_ADMIN'])
    def edit(MyMessage myMessage) {
        respond myMessage
    }

    @Transactional
    def update(MyMessage myMessage) {
        if (myMessage == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (myMessage.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond myMessage.errors, view:'edit'
            return
        }

        myMessage.save flush:true
    }
/**
    @Transactional
    def delete(MyMessage myMessage) {

        if (myMessage == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        myMessage.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'myMessage.label', default: 'MyMessage'), myMessage.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }
**/
    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'myMessage.label', default: 'MyMessage'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }

    /**
     * Modify the value of a message
     * @return
     */
    @Secured(['ROLE_USER','ROLE_ADMIN'])
    @Transactional
    def vote(){
        MyMessage item = MyMessage.get(params.mId as Integer)
        item.value += (params.inc as Integer)
        update(item)

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'answerMessage.label', default: 'Question'), item.id])
                redirect controller:"question", action:"show", id:params.qId, method:"GET"
            }
            '*' { respond item, [status: CREATED] }
        }
    }
}
