package ovh.garcon.tasko

import grails.plugin.springsecurity.annotation.Secured

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class AnswerMessageController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]
/**
    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond AnswerMessage.list(params), model:[answerMessageCount: AnswerMessage.count()]
    }

    def show(AnswerMessage answerMessage) {
        respond answerMessage
    }

    def create() {
        respond new AnswerMessage(params)
    }
**/
    @Secured(['ROLE_USER', 'ROLE_ADMIN'])
    @Transactional
    def save(AnswerMessage answerMessage) {
        if (answerMessage == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (answerMessage.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond answerMessage.errors, view:'create'
            return
        }

        answerMessage.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'answerMessage.label', default: 'AnswerMessage'), answerMessage.id])
                redirect controller:"question", action:"show", id:answerMessage.question.id, method:"GET"
            }
            '*' { respond answerMessage, [status: CREATED] }
        }
    }

    @Secured(['ROLE_USER','ROLE_ADMIN'])
    def edit(AnswerMessage answerMessage) {
        respond answerMessage
    }

    @Secured(['ROLE_USER', 'ROLE_ADMIN'])
    @Transactional
    def update(AnswerMessage answerMessage) {
        if (answerMessage == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (answerMessage.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond answerMessage.errors, view:'edit'
            return
        }

        answerMessage.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'answerMessage.label', default: 'AnswerMessage'), answerMessage.id])
                redirect controller:"question", action:"show", id:answerMessage.question.id, method:"GET"
            }
            '*'{ respond answerMessage, [status: OK] }
        }
    }
/**
    @Transactional
    def delete(AnswerMessage answerMessage) {

        if (answerMessage == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        answerMessage.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'answerMessage.label', default: 'AnswerMessage'), answerMessage.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }
**/
    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'answerMessage.label', default: 'AnswerMessage'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }

    @Secured(['ROLE_USER','ROLE_ADMIN'])
    @Transactional
    def add(){
        AnswerMessage item = new AnswerMessage(
                date: new Date(),
                content: params.content,
                user: (User)getAuthenticatedUser(),
                value: 0,
                question: Question.get(params.qId as Integer)
        )

        if (item == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (item.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond item.errors, view:'create'
            return
        }

        item.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'answerMessage.label', default: 'AnswerMessage'), item.id])
                redirect controller:"question", action:"show", id:params.qId, method:"GET"
            }
            '*' { respond item, [status: CREATED] }
        }
    }
}
